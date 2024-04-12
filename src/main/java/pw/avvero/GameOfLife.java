package pw.avvero;

import pw.avvero.board.Board;

import java.util.List;

public class GameOfLife implements State {

    @Override
    public Board.Cell calculate(int i, int j, Board.Cell current, List<Board.Cell> neighbours) {
        int n = neighbours.size();
        if (current != null && (n == 2 || n == 3)) {
            return current;
        } else if (n == 3) {
            return new Board.Cell(i, j, 1);
        }
        return null;
    }
}
