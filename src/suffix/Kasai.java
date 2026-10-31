
package suffix;

public class Kasai {

    public static int[] buildLCP(String text, int[] suffixArray) {

        int n = text.length();

        if (suffixArray.length != n) {
            throw new IllegalArgumentException(
                    "Suffix array length must match text length");
        }

        int[] lcp = new int[n];
        int[] rank = new int[n];
        boolean[] seen = new boolean[n];

        for (int i = 0; i < n; i++) {
            int position = suffixArray[i];

            if (position < 0 || position >= n || seen[position]) {
                throw new IllegalArgumentException(
                        "Invalid suffix array");
            }

            seen[position] = true;
            rank[position] = i;
        }

        int h = 0;

        for (int i = 0; i < n; i++) {

            if (rank[i] == 0) {
                h = 0;
                continue;
            }

            int j = suffixArray[rank[i] - 1];

            while (i + h < n &&
                    j + h < n &&
                    text.charAt(i + h) == text.charAt(j + h)) {
                h++;
            }

            lcp[rank[i]] = h;

            if (h > 0) {
                h--;
            }
        }

        return lcp;
    }
}
