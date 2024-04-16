package pw.avvero.board;

import java.util.List;

public interface SearchPath<T> {

    List<Cell<T>> find(Cell<T> from, Cell<T> to);

}
