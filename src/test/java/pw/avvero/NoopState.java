package pw.avvero;

import pw.avvero.board.Board;
import pw.avvero.board.Cell;

import java.util.List;

public class NoopState implements State {
    @Override
    public Cell calculate(Cell current, List<Board.Neighbour> neighbours) {
        return current;
    }
}
