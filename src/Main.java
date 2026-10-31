
import dp.LCS;
import dp.EditDistance;
import matching.NaiveSearch;
import matching.KMP;
import matching.RabinKarp;
import suffix.Kasai;
import java.util.Arrays;
public class Main {

    public static void main(String[] args) {

        System.out.println("LCS Tests:");

        // Test 1: Normal strings
        System.out.println("Test 1: " +
                LCS.findLCS("ABCDE", "ACE"));

        // Test 2: Identical strings
        System.out.println("Test 2: " +
                LCS.findLCS("HELLO", "HELLO"));

        // Test 3: Completely different strings
        System.out.println("Test 3: " +
                LCS.findLCS("ABC", "XYZ"));

        // Test 4: Empty string
        System.out.println("Test 4: " +
                LCS.findLCS("", "ABC"));

        // Test 5: Non-contiguous subsequence
        System.out.println("Test 5: " +
                LCS.findLCS("AGGTAB", "GXTXAYB"));

        System.out.println("\nEdit Distance Tests:");

        System.out.println("Test 1: " +
                EditDistance.calculate("HELLO", "HELLO"));

        System.out.println("Test 2: " +
                EditDistance.calculate("", "ABC"));

        System.out.println("Test 3: " +
                EditDistance.calculate("CAT", "CUT"));

        System.out.println("Test 4: " +
                EditDistance.calculate("CAT", "CATS"));

        System.out.println("Test 5: " +
                EditDistance.calculate("CATS", "CAT"));

        System.out.println("Test 6: " +
                EditDistance.calculate("KITTEN", "SITTING"));

        System.out.println("Test 7: " +
                EditDistance.calculate("", ""));

        System.out.println("\nNaive String Matching Tests:");

        System.out.println("Test 1: " +
                NaiveSearch.search("ABCDEF", "ABC"));

        System.out.println("Test 2: " +
                NaiveSearch.search("ABCDEF", "CD"));

        System.out.println("Test 3: " +
                NaiveSearch.search("ABCDEF", "EF"));

        System.out.println("Test 4: " +
                NaiveSearch.search("ABABABA", "ABA"));

        System.out.println("Test 5: " +
                NaiveSearch.search("ABCDEF", "XYZ"));

        System.out.println("Test 6: " +
                NaiveSearch.search("ABC", "ABCDE"));

        System.out.println("Test 7: " +
                NaiveSearch.search("ABC", ""));

        System.out.println("Test 8: " +
                NaiveSearch.search("HELLO", "HELLO"));

        System.out.println("Test 9: " +
                NaiveSearch.search("", "ABC"));

        System.out.println("Test 10: " +
                NaiveSearch.search("", ""));

        System.out.println("\nKMP String Matching Tests:");

        System.out.println("Test 1: " +
                KMP.search("ABCDEF", "ABC"));

        System.out.println("Test 2: " +
                KMP.search("ABCDEF", "CD"));

        System.out.println("Test 3: " +
                KMP.search("ABCDEF", "EF"));

        System.out.println("Test 4: " +
                KMP.search("ABABABA", "ABA"));

        System.out.println("Test 5: " +
                KMP.search("ABCDEF", "XYZ"));

        System.out.println("Test 6: " +
                KMP.search("ABC", "ABCDE"));

        System.out.println("Test 7: " +
                KMP.search("ABC", ""));

        System.out.println("Test 8: " +
                KMP.search("HELLO", "HELLO"));

        System.out.println("Test 9: " +
                KMP.search("", "ABC"));

        System.out.println("Test 10: " +
                KMP.search("", ""));

        System.out.println("Test 11 LPS: " +
                java.util.Arrays.toString(
                        KMP.computeLPS("ABABCABAB")));

        System.out.println("\nRabin-Karp String Matching Tests:");

        System.out.println("Test 1: " +
                RabinKarp.search("ABCDEF", "ABC"));

        System.out.println("Test 2: " +
                RabinKarp.search("ABCDEF", "CD"));

        System.out.println("Test 3: " +
                RabinKarp.search("ABCDEF", "EF"));

        System.out.println("Test 4: " +
                RabinKarp.search("ABABABA", "ABA"));

        System.out.println("Test 5: " +
                RabinKarp.search("ABCDEF", "XYZ"));

        System.out.println("Test 6: " +
                RabinKarp.search("ABC", "ABCDE"));

        System.out.println("Test 7: " +
                RabinKarp.search("ABC", ""));

        System.out.println("Test 8: " +
                RabinKarp.search("HELLO", "HELLO"));

        System.out.println("Test 9: " +
                RabinKarp.search("", "ABC"));

        System.out.println("Test 10: " +
                RabinKarp.search("", ""));

        System.out.println("Test 11: " +
                RabinKarp.search("AAAAA", "AAA"));

        System.out.println("\nString Matching Comparison:");

        String[][] cases = {
                {"ABCDEF", "ABC"},
                {"ABCDEF", "CD"},
                {"ABCDEF", "EF"},
                {"ABABABA", "ABA"},
                {"ABCDEF", "XYZ"},
                {"ABC", "ABCDE"},
                {"ABC", ""},
                {"HELLO", "HELLO"},
                {"", "ABC"},
                {"", ""},
                {"AAAAA", "AAA"}
        };

        for (int i = 0; i < cases.length; i++) {

            String text = cases[i][0];
            String pattern = cases[i][1];

            boolean equal = NaiveSearch.search(text, pattern)
                    .equals(KMP.search(text, pattern))
                    && KMP.search(text, pattern)
                    .equals(RabinKarp.search(text, pattern));

            System.out.println("Comparison " + (i + 1)
                    + ": " + (equal ? "PASS" : "FAIL"));

            if (!equal) {
                throw new AssertionError(
                        "String matching comparison failed: " + (i + 1));

            }
        }


        System.out.println("\nKasai LCP Tests:");

        String text1 = "banana";
        int[] sa1 = {5, 3, 1, 0, 4, 2};
        int[] expected1 = {0, 1, 3, 0, 0, 2};
        int[] actual1 = Kasai.buildLCP(text1, sa1);

        System.out.println("Test 1: " + Arrays.toString(actual1));
        if (!Arrays.equals(actual1, expected1)) {
            throw new AssertionError("Kasai Test 1 failed");
        }

        String text2 = "aaaa";
        int[] sa2 = {3, 2, 1, 0};
        int[] expected2 = {0, 1, 2, 3};
        int[] actual2 = Kasai.buildLCP(text2, sa2);

        System.out.println("Test 2: " + Arrays.toString(actual2));
        if (!Arrays.equals(actual2, expected2)) {
            throw new AssertionError("Kasai Test 2 failed");
        }

        String text3 = "abcd";
        int[] sa3 = {0, 1, 2, 3};
        int[] expected3 = {0, 0, 0, 0};
        int[] actual3 = Kasai.buildLCP(text3, sa3);

        System.out.println("Test 3: " + Arrays.toString(actual3));
        if (!Arrays.equals(actual3, expected3)) {
            throw new AssertionError("Kasai Test 3 failed");
        }

        String text4 = "a";
        int[] sa4 = {0};
        int[] expected4 = {0};
        int[] actual4 = Kasai.buildLCP(text4, sa4);

        System.out.println("Test 4: " + Arrays.toString(actual4));
        if (!Arrays.equals(actual4, expected4)) {
            throw new AssertionError("Kasai Test 4 failed");
        }

        String text5 = "";
        int[] sa5 = {};
        int[] expected5 = {};
        int[] actual5 = Kasai.buildLCP(text5, sa5);

        System.out.println("Test 5: " + Arrays.toString(actual5));
        if (!Arrays.equals(actual5, expected5)) {
            throw new AssertionError("Kasai Test 5 failed");
        }

        System.out.println("All Kasai tests passed.");

    }

}
