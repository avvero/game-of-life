package pw.avvero.board;

import java.util.List;

public interface Neighborhood<T> {

    List<Neighbour<T>> neighbours(Board<T> board, int i, int j);

}
