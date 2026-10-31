
package dp;

public class LCS {

    public static int findLCS(String a, String b) {

        int m = a.length();
        int n = b.length();

        // Create the dynamic programming table
        int[][] dp = new int[m + 1][n + 1];

        // Fill the table
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {

                if (a.charAt(i - 1) == b.charAt(j - 1)) {
                    // Characters match
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    // Characters do not match
                    dp[i][j] = Math.max(
                            dp[i - 1][j],
                            dp[i][j - 1]
                    );
                }
            }
        }

        // Return the length of the LCS
        return dp[m][n];
    }
}
