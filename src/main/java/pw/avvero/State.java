package pw.avvero;

import pw.avvero.board.Board;

import java.util.List;

public interface State {
    Board.Cell calculate(int i, int j, Board.Cell current, List<Board.Cell> neighbours);
}
