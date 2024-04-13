package pw.avvero;

import pw.avvero.board.Board;
import pw.avvero.board.Cell;

import java.util.List;

import static pw.avvero.board.Cell.ZERO;

public class GameOfLife implements State {

    private Cell newCell() {
        return Cell.of(1, new Cell.Role("⬛", 0, 0, 0, 0, 0, 0, 0));
    }

    @Override
    public Cell calculate(Cell current, List<Board.Neighbour> neighbours) {
        int n = neighbours.stream().filter(neighbour -> neighbour.level() == 1).toList().size();
        if (current.value() != 0 && (n == 2 || n == 3)) {
            return current;
        } else if (n == 3) {
            return newCell().acquire(current);
        }
        return ZERO.acquire(current);
    }
}
