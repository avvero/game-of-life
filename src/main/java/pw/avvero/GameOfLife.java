package pw.avvero;

import pw.avvero.board.Board;

import java.util.List;

public class GameOfLife implements State {

    @Override
    public int calculate(int current, List<Board.Cell> neighbours) {
        int n = neighbours.size();
        if (current != 0 && (n == 2 || n == 3)) {
            return current;
        } else if (n == 3) {
            return 1;
        }
        return 0;
    }
}
