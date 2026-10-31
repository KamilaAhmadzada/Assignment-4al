
import dp.LCS;
import dp.EditDistance;
import matching.NaiveSearch;
import matching.KMP;
import matching.RabinKarp;
import suffix.Kasai;

import java.util.Arrays;
import java.util.List;

public class AlgorithmTests {

    private static int passed = 0;
    private static int failed = 0;

    public static void main(String[] args) {

        testLCS();
        testEditDistance();
        testStringMatching();
        testKasai();

        System.out.println("\n======================");
        System.out.println("Total Passed: " + passed);
        System.out.println("Total Failed: " + failed);
        System.out.println("======================");

        if (failed > 0) {
            throw new AssertionError(failed + " tests failed");
        }
    }

    private static void check(String name, boolean condition) {
        if (condition) {
            passed++;
            System.out.println("PASS: " + name);
        } else {
            failed++;
            System.out.println("FAIL: " + name);
        }
    }

    private static void testLCS() {
        System.out.println("\nLCS Tests:");

        check("Identical strings",
                LCS.findLCS("ABC", "ABC") == 3);

        check("Different strings",
                LCS.findLCS("ABC", "XYZ") == 0);

        check("Empty first string",
                LCS.findLCS("", "ABC") == 0);

        check("Empty second string",
                LCS.findLCS("ABC", "") == 0);

        check("Normal subsequence",
                LCS.findLCS("ABCDE", "ACE") == 3);
    }

    private static void testEditDistance() {
        System.out.println("\nEdit Distance Tests:");

        check("Identical strings",
                EditDistance.calculate("HELLO", "HELLO") == 0);

        check("Empty first string",
                EditDistance.calculate("", "ABC") == 3);

        check("Empty second string",
                EditDistance.calculate("ABC", "") == 3);

        check("Substitution",
                EditDistance.calculate("CAT", "CUT") == 1);

        check("Insertion",
                EditDistance.calculate("CAT", "CATS") == 1);

        check("Deletion",
                EditDistance.calculate("CATS", "CAT") == 1);

        check("Multiple edits",
                EditDistance.calculate("KITTEN", "SITTING") == 3);
    }

    private static void testStringMatching() {
        System.out.println("\nString Matching Tests:");

        checkMatching("Beginning", "ABCDEF", "ABC",
                Arrays.asList(0));

        checkMatching("Middle", "ABCDEF", "CD",
                Arrays.asList(2));

        checkMatching("End", "ABCDEF", "EF",
                Arrays.asList(4));

        checkMatching("Multiple occurrences", "ABABABA", "ABA",
                Arrays.asList(0, 2, 4));

        checkMatching("Overlapping occurrences", "AAAAA", "AAA",
                Arrays.asList(0, 1, 2));

        checkMatching("Pattern absent", "ABCDEF", "XYZ",
                List.of());

        checkMatching("Pattern longer than text", "ABC", "ABCDE",
                List.of());

        checkMatching("Empty pattern", "ABC", "",
                Arrays.asList(0, 1, 2, 3));

        checkMatching("Identical text and pattern", "HELLO", "HELLO",
                Arrays.asList(0));

        checkMatching("Empty text", "", "ABC",
                List.of());

        checkMatching("Both empty", "", "",
                Arrays.asList(0));
    }

    private static void checkMatching(
            String name,
            String text,
            String pattern,
            List<Integer> expected) {

        List<Integer> naive = NaiveSearch.search(text, pattern);
        List<Integer> kmp = KMP.search(text, pattern);
        List<Integer> rabinKarp = RabinKarp.search(text, pattern);

        boolean correct = naive.equals(expected)
                && kmp.equals(expected)
                && rabinKarp.equals(expected);

        check(name, correct);
    }

    private static void testKasai() {
        System.out.println("\nKasai LCP Tests:");

        check("Banana",
                Arrays.equals(
                        Kasai.buildLCP("banana",
                                new int[]{5, 3, 1, 0, 4, 2}),
                        new int[]{0, 1, 3, 0, 0, 2}
                ));

        check("Repeated characters",
                Arrays.equals(
                        Kasai.buildLCP("aaaa",
                                new int[]{3, 2, 1, 0}),
                        new int[]{0, 1, 2, 3}
                ));

        check("Different characters",
                Arrays.equals(
                        Kasai.buildLCP("abcd",
                                new int[]{0, 1, 2, 3}),
                        new int[]{0, 0, 0, 0}
                ));

        check("Single character",
                Arrays.equals(
                        Kasai.buildLCP("a",
                                new int[]{0}),
                        new int[]{0}
                ));

        check("Empty string",
                Arrays.equals(
                        Kasai.buildLCP("",
                                new int[]{}),
                        new int[]{}
                ));
    }
}
