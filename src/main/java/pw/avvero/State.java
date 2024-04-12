package pw.avvero;

import pw.avvero.board.Board;
import pw.avvero.board.Cell;

import java.util.List;
import java.util.Map;

public interface State {
    Cell calculate(Cell current, List<Board.Neighbour> neighbours);
}
