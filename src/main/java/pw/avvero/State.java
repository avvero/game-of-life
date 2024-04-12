package pw.avvero;

import pw.avvero.board.Cell;

import java.util.List;

public interface State {
    Cell calculate(Cell current, List<Cell> neighbours);
}
