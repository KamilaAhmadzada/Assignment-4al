
package dp;

public class EditDistance {

    public static int calculate(String a, String b) {

        int m = a.length();
        int n = b.length();

        // Create the dynamic programming table
        int[][] dp = new int[m + 1][n + 1];

        // Base case: convert a prefix to/from an empty string
        for (int i = 0; i <= m; i++) {
            dp[i][0] = i;
        }

        for (int j = 0; j <= n; j++) {
            dp[0][j] = j;
        }

        // Fill the DP table
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {

                if (a.charAt(i - 1) == b.charAt(j - 1)) {

                    // Matching characters: no new edit
                    dp[i][j] = dp[i - 1][j - 1];

                } else {

                    // Delete from a
                    int deletion = dp[i - 1][j];

                    // Insert into a
                    int insertion = dp[i][j - 1];

                    // Replace a character
                    int substitution = dp[i - 1][j - 1];

                    dp[i][j] = 1 + Math.min(
                            deletion,
                            Math.min(insertion, substitution)
                    );
                }
            }
        }

        // Minimum number of edits
        return dp[m][n];
    }
}
