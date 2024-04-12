package pw.avvero;

import pw.avvero.board.Board;

import java.util.List;

public class GameOfLife implements State {

    @Override
    public Board.Cell calculate(Board.Cell current, List<Board.Cell> neighbours) {
        int n = neighbours.size();
        if (current.value != 0 && (n == 2 || n == 3)) {
            return current;
        } else if (n == 3) {
            return new Board.Cell(current.i, current.j, 1);
        }
        return new Board.Cell(current.i, current.j, 0);
    }
}
