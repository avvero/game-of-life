package pw.avvero;

import pw.avvero.board.Cell;
import pw.avvero.board.Neighbour;

import java.util.List;

public interface State<I> {
    Runnable calculate(Cell<I> current, List<Neighbour<I>> neighbours);
}
