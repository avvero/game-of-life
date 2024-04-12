package pw.avvero;

import pw.avvero.board.Cell;

import java.util.List;

import static pw.avvero.board.Cell.ZERO;

public class GameOfLife implements State {

    @Override
    public Cell calculate(Cell current, List<Cell> neighbours) {
        int n = neighbours.size();
        if (current.value() != 0 && (n == 2 || n == 3)) {
            return current;
        } else if (n == 3) { // new
            return Cell.insteadOf(current, Cell.of(1));
        }
        return Cell.insteadOf(current, ZERO);
    }
}
