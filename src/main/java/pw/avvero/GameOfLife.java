package pw.avvero;

import pw.avvero.board.Board;

public class GameOfLife {

    private final Board board;

    public GameOfLife(Board board) {
        this.board = board;
    }

    public void newCycle() {
        this.board.update(nextGeneration());
    }

    private int[][] nextGeneration() {
        int[][] next = new int[board.value().length][board.value()[0].length];
        for (int i = 0; i < board.value().length; i++) {
            for (int j = 0; j < board.value()[i].length; j++) {
                int n = board.neighbours(i, j).size();
                if (board.value()[i][j] != 0) {
                    int v = board.value()[i][j];
                    if (n == 2 || n == 3) {
                        next[i][j] = v;
                    }
                } else {
                    if (n == 3) {
                        next[i][j] = 1; // we don't know how to act here
                    }
                }
            }
        }
        return next;
    }
}
