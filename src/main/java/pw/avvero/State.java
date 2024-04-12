package pw.avvero;

import pw.avvero.board.Board;

import java.util.List;

public interface State {
    int calculate(int current, List<Board.Cell> neighbours);
}
