
package matching;

import java.util.ArrayList;
import java.util.List;

public class RabinKarp {

    private static final long BASE = 256;
    private static final long MOD = 1_000_000_007;

    public static List<Integer> search(String text, String pattern) {

        List<Integer> positions = new ArrayList<>();

        int n = text.length();
        int m = pattern.length();

        if (m == 0) {
            for (int i = 0; i <= n; i++) {
                positions.add(i);
            }
            return positions;
        }

        if (m > n) {
            return positions;
        }

        long patternHash = 0;
        long textHash = 0;
        long highestPower = 1;

        for (int i = 1; i < m; i++) {
            highestPower = (highestPower * BASE) % MOD;
        }

        for (int i = 0; i < m; i++) {
            patternHash = (patternHash * BASE
                    + pattern.charAt(i)) % MOD;

            textHash = (textHash * BASE
                    + text.charAt(i)) % MOD;
        }

        for (int i = 0; i <= n - m; i++) {

            if (patternHash == textHash) {

                boolean match = true;

                for (int j = 0; j < m; j++) {
                    if (text.charAt(i + j) != pattern.charAt(j)) {
                        match = false;
                        break;
                    }
                }

                if (match) {
                    positions.add(i);
                }
            }

            if (i < n - m) {

                textHash = (textHash
                        - text.charAt(i) * highestPower % MOD
                        + MOD) % MOD;

                textHash = (textHash * BASE
                        + text.charAt(i + m)) % MOD;
            }
        }

        return positions;
    }
}

