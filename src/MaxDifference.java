import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class MaxDifference {
    static int length = 20;

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();

        CompletableFuture<List<Double>> generateSequenceFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println("Генерація послідовності дійсних чисел...\n");
            return generateSequence(length);
        });

        CompletableFuture<List<Double>> differencesFuture = generateSequenceFuture.thenApplyAsync(sequence -> {
            System.out.println("Згенерована послідовність:\n" + sequence + "\n");
            return calculateDifferences(sequence);
        });

        CompletableFuture<Double> maxDifferenceFuture = differencesFuture.thenApplyAsync(differences -> {
            System.out.println("Обчислені різниці модулів:\n" + differences + "\n");
            return findMax(differences);
        });

        try {
            Double maxDifference = maxDifferenceFuture.get();
            System.out.println("Максимальна різниця модулів між сусідніми числами: " + maxDifference);
        } catch (Exception e) {
            System.err.println("Помилка виконання: " + e.getMessage());
        }

        long endTime = System.currentTimeMillis();
        System.out.println("Час виконання програми: " + (endTime - startTime) + " мс");
    }

    private static List<Double> generateSequence(int count) {
        Random random = new Random();
        List<Double> sequence = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            sequence.add(random.nextDouble() * 100);
        }
        return sequence;
    }

    private static List<Double> calculateDifferences(List<Double> sequence) {
        List<Double> differences = new ArrayList<>();
        for (int i = 0; i < sequence.size() - 1; i++) {
            double difference = Math.abs(sequence.get(i) - sequence.get(i + 1));
            differences.add(difference);
        }
        return differences;
    }

    private static Double findMax(List<Double> differences) {
        double maxDifference = 0;
        for (double difference : differences) {
            if (difference > maxDifference) {
                maxDifference = difference;
            }
        }
        return maxDifference;
    }
}
