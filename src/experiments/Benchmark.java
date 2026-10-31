
package experiments;

import dp.LCS;
import dp.EditDistance;
import matching.NaiveSearch;
import matching.KMP;
import matching.RabinKarp;
import suffix.Kasai;

import java.util.Random;
import java.util.Arrays;
import java.util.Locale;
import java.util.function.Supplier;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class Benchmark {

    private static final int RUNS = 5;
    private static final long SEED = 42;
    private static final String ALPHABET = "ABCD";

    private static String randomString(int length, Random random) {
        StringBuilder sb = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            sb.append(ALPHABET.charAt(
                    random.nextInt(ALPHABET.length())));
        }

        return sb.toString();
    }

    private static double measure(Supplier<?> task) {
        long total = 0;

        for (int i = 0; i < RUNS; i++) {
            long start = System.nanoTime();
            Object result = task.get();
            long end = System.nanoTime();

            if (result == null) {
                throw new AssertionError("Null result");
            }

            total += end - start;
        }

        return total / (double) RUNS / 1_000_000.0;
    }

    private static void saveResult(String filename, String line) {
        try {
            Path directory = Path.of("results");
            Files.createDirectories(directory);

            Path file = directory.resolve(filename);

            Files.writeString(
                    file,
                    line + System.lineSeparator(),
                    StandardOpenOption.CREATE,
                    StandardOpenOption.APPEND
            );

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void clearResults() {
        try {
            Files.createDirectories(Path.of("results"));

            Files.deleteIfExists(
                    Path.of("results/dp_results.csv"));

            Files.deleteIfExists(
                    Path.of("results/matching_results.csv"));

            Files.deleteIfExists(
                    Path.of("results/kasai_results.csv"));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void benchmarkDP(Random random) {
        System.out.println("\nDynamic Programming (ms)");
        System.out.println("Size,LCS,EditDistance");

        saveResult(
                "dp_results.csv",
                "Size,LCS,EditDistance"
        );

        int[] sizes = {100, 500, 1000};

        for (int size : sizes) {
            String a = randomString(size, random);
            String b = randomString(size, random);

            double lcs = measure(
                    () -> LCS.findLCS(a, b));

            double edit = measure(
                    () -> EditDistance.calculate(a, b));

            System.out.printf(
                    Locale.US,
                    "%d,%.4f,%.4f%n",
                    size, lcs, edit
            );

            saveResult(
                    "dp_results.csv",
                    String.format(
                            Locale.US,
                            "%d,%.4f,%.4f",
                            size, lcs, edit
                    )
            );
        }
    }

    private static void benchmarkMatching(Random random) {
        System.out.println("\nString Matching (ms)");
        System.out.println("Size,Case,Naive,KMP,RabinKarp");

        saveResult(
                "matching_results.csv",
                "Size,Case,Naive,KMP,RabinKarp"
        );

        int[] sizes = {10000, 50000, 100000};

        for (int size : sizes) {
            String text = randomString(size, random);

            String present = text.substring(
                    size / 2, size / 2 + 10);

            String absent = "ZZZZZZZZZZ";

            String[] patterns = {present, absent};
            String[] labels = {"Present", "Absent"};

            for (int p = 0; p < patterns.length; p++) {
                String pattern = patterns[p];

                if (!NaiveSearch.search(text, pattern)
                        .equals(KMP.search(text, pattern))
                        || !KMP.search(text, pattern)
                        .equals(RabinKarp.search(text, pattern))) {

                    throw new AssertionError(
                            "Matching results differ");
                }

                double naive = measure(
                        () -> NaiveSearch.search(text, pattern));

                double kmp = measure(
                        () -> KMP.search(text, pattern));

                double rk = measure(
                        () -> RabinKarp.search(text, pattern));

                System.out.printf(
                        Locale.US,
                        "%d,%s,%.4f,%.4f,%.4f%n",
                        size, labels[p], naive, kmp, rk
                );

                saveResult(
                        "matching_results.csv",
                        String.format(
                                Locale.US,
                                "%d,%s,%.4f,%.4f,%.4f",
                                size, labels[p], naive, kmp, rk
                        )
                );
            }
        }
    }

    private static int[] suffixArray(String text) {
        Integer[] order = new Integer[text.length()];

        for (int i = 0; i < order.length; i++) {
            order[i] = i;
        }

        Arrays.sort(order, (a, b) -> {
            int i = a;
            int j = b;

            while (i < text.length() && j < text.length()) {
                int cmp = Character.compare(
                        text.charAt(i), text.charAt(j));

                if (cmp != 0) {
                    return cmp;
                }

                i++;
                j++;
            }

            return Integer.compare(
                    text.length() - a,
                    text.length() - b);
        });

        int[] sa = new int[order.length];

        for (int i = 0; i < order.length; i++) {
            sa[i] = order[i];
        }

        return sa;
    }

    private static void benchmarkKasai(Random random) {
        System.out.println("\nKasai (ms)");
        System.out.println("Size,Time");

        saveResult(
                "kasai_results.csv",
                "Size,Time"
        );

        int[] sizes = {1000, 5000, 10000};

        for (int size : sizes) {
            String text = randomString(size, random);
            int[] sa = suffixArray(text);

            double time = measure(
                    () -> Kasai.buildLCP(text, sa));

            System.out.printf(
                    Locale.US,
                    "%d,%.4f%n",
                    size, time
            );

            saveResult(
                    "kasai_results.csv",
                    String.format(
                            Locale.US,
                            "%d,%.4f",
                            size, time
                    )
            );
        }
    }

    public static void main(String[] args) {
        clearResults();

        Random random = new Random(SEED);

        System.out.println("Seed: " + SEED);
        System.out.println("Alphabet: " + ALPHABET);
        System.out.println("Runs: " + RUNS);

        benchmarkDP(random);
        benchmarkMatching(random);
        benchmarkKasai(random);

        System.out.println("\nResults saved successfully.");
    }
}
