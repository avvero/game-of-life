package pw.avvero;

import pw.avvero.board.Cell;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static pw.avvero.board.Cell.ZERO;

public class GameOfLife implements State {

    private Cell newCell() {
        return Cell.of(1, new Cell.Role("⬛", 0, 0, 0, 0, 0, 0));
    }

    @Override
    public Cell calculate(Cell current, Map<Integer, List<Cell>> neighbours) {
        int n = Optional.ofNullable(neighbours.get(1)).orElse(List.of()).size();
        if (current.value() != 0 && (n == 2 || n == 3)) {
            return current;
        } else if (n == 3) {
            return newCell().acquire(current);
        }
        return ZERO.acquire(current);
    }
}
