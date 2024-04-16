package pw.avvero.board;

import java.util.List;

public interface Neighborhood<T> {

    List<int[]> neighbours(Board<T> board, int i, int j);

}
