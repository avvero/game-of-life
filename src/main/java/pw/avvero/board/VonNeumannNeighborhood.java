package pw.avvero.board;

import java.util.ArrayList;
import java.util.List;

public class VonNeumannNeighborhood<T> implements Neighborhood<T> {

    @Override
    public List<int[]> neighbours(Board<T> board, int i, int j) {
        List<int[]> result = new ArrayList<>();
        for (int[] position : List.of(new int[]{i - 1, j}, new int[]{i + 1, j}, new int[]{i, j - 1}, new int[]{i, j + 1})) {
            if (board.exists(position[0], position[1])) {
                result.add(position);
            }
        }
        return result;
    }
}
