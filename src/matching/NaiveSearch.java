
package matching;

import java.util.ArrayList;
import java.util.List;

public class NaiveSearch {

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

        for (int i = 0; i <= n - m; i++) {
            int j = 0;

            while (j < m &&
                    text.charAt(i + j) == pattern.charAt(j)) {
                j++;
            }

            if (j == m) {
                positions.add(i);
            }
        }

        return positions;
    }
}
