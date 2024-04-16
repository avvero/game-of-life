package pw.avvero.board;

import java.util.ArrayList;
import java.util.List;

public class MoorNeighborhood<T> implements Neighborhood<T> {

    @Override
    public List<int[]> neighbours(Board<T> board, int i, int j) {
        List<int[]> result = new ArrayList<>();
        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                if (x == 0 && y == 0) continue; // current
                int ni = i + x, nj = j + y;
                if (board.exists(ni, nj)) {
                    result.add(new int[]{ni, nj});
                }
            }
        }
        return result;
    }
}
