import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadLocalRandom;


public class CheckArray {
    public static void main(String[] args) {
        CompletableFuture<List<Character>> createArrayFuture = CompletableFuture.supplyAsync(() -> {
            long taskStartTime = System.currentTimeMillis();
            List<Character> charArray = new ArrayList<>();
            for (int i = 0; i < 20; i++) {
                char randomChar = (char) ThreadLocalRandom.current().nextInt(32, 127); // Символи ASCII
                charArray.add(randomChar);
            }
            System.out.println("Initial array: " + charArray);
            printTime("Array creation", taskStartTime);
            System.out.println("\nTimes: ");
            return charArray;
        });


        CompletableFuture<List<Character>> alphabeticFuture = createArrayFuture.thenApplyAsync(charArray -> {
            long taskStartTime = System.currentTimeMillis();
            List<Character> alphabetic = new ArrayList<>();
            for (char c : charArray) {
                if (Character.isAlphabetic(c)) {
                    alphabetic.add(c);
                }
            }

            printTime("Alphabetic processing", taskStartTime);
            return alphabetic;
        });

        CompletableFuture<List<Character>> numbersFuture = createArrayFuture.thenApplyAsync(charArray -> {
            long taskStartTime = System.currentTimeMillis();
            List<Character> numbers = new ArrayList<>();
            for (char c : charArray) {
                if (Character.isDigit(c)) {
                    numbers.add(c);
                }
            }

            printTime("Numbers processing", taskStartTime);
            return numbers;
        });

        CompletableFuture<List<Character>> spacesFuture = createArrayFuture.thenApplyAsync(charArray -> {
            long taskStartTime = System.currentTimeMillis();
            List<Character> spaces = new ArrayList<>();
            for (char c : charArray) {
                if (c == ' ') {
                    spaces.add(c);
                }
            }

            printTime("Spaces processing", taskStartTime);
            return spaces;
        });

        CompletableFuture<List<Character>> tabsFuture = createArrayFuture.thenApplyAsync(charArray -> {
            long taskStartTime = System.currentTimeMillis();
            List<Character> tabs = new ArrayList<>();
            for (char c : charArray) {
                if (c == '\t') {
                    tabs.add(c);
                }
            }

            printTime("Tabs processing", taskStartTime);
            return tabs;
        });


        CompletableFuture<List<Character>> symbolsFuture = createArrayFuture.thenApplyAsync(charArray -> {
            long taskStartTime = System.currentTimeMillis();
            List<Character> symbols = new ArrayList<>();
            for (char c : charArray) {
                if (!Character.isAlphabetic(c) && !Character.isDigit(c) && c != ' ' && c != '\t') {
                    symbols.add(c);
                }
            }

            printTime("Others processing", taskStartTime);
            return symbols;
        });

        CompletableFuture.allOf(alphabeticFuture, numbersFuture, spacesFuture, tabsFuture, symbolsFuture).join();

        System.out.println("\nResults: ");

        alphabeticFuture.thenAcceptAsync(alphabetic -> {
            System.out.println("Alphabetic: " + alphabetic);
        });

        numbersFuture.thenAcceptAsync(numbers -> {
            System.out.println("Numbers: " + numbers);
        });

        spacesFuture.thenAcceptAsync(spaces -> {
            System.out.println("Spaces: " + spaces);
        });

        tabsFuture.thenAcceptAsync(tabs -> {
            System.out.println("Tabs: " + tabs);
        });

        symbolsFuture.thenAcceptAsync(symbols -> {
            System.out.println("Symbols: " + symbols);
        });
    }


    private static void printTime(String taskName, long startTime) {
        long elapsedTimeMillis = System.currentTimeMillis() - startTime;
        double elapsedTime = elapsedTimeMillis + (System.nanoTime() % 1_000_000) / 1_000_000.0;
        System.out.println(taskName + " completed in " + String.format("%.2f", elapsedTime) + " ms");
    }
}
