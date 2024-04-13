package pw.avvero;

import pw.avvero.board.Board;
import pw.avvero.board.Cell;

import java.util.List;

public interface State<I> {
    Runnable calculate(Cell<I> current, List<Board.Neighbour<I>> neighbours);
}
