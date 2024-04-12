package pw.avvero;

import pw.avvero.board.Board;

import java.util.List;

public interface State {
    Board.Cell calculate(Board.Cell current, List<Board.Cell> neighbours);
}
