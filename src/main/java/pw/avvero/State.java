package pw.avvero;

import pw.avvero.board.Cell;

import java.util.List;
import java.util.Map;

public interface State {
    Cell calculate(Cell current, Map<Integer, List<Cell>> neighbours);
}
