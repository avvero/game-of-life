package pw.avvero;

import java.util.concurrent.ThreadLocalRandom;

public class GameOfLife {

    private final Board board;

    public GameOfLife(Board board) {
        this.board = board;
        initializeRandom(board.value(), 0, board.value().length / 3, 0,  board.value()[0].length / 3);
    }

    public void newCycle() {
        this.board.update(nextGeneration());
    }

    private int[][] nextGeneration() {
        int[][] next = new int[board.value().length][board.value()[0].length];
        for (int i = 0; i < board.value().length; i++) {
            for (int j = 0; j < board.value()[i].length; j++) {
                int n = board.neighbours(i, j);
                if (board.value()[i][j] == 1) {
                    if (n < 2) {
                        next[i][j] = 0;
                    } else if (n <= 3) {
                        next[i][j] = 1;
                    } else {
                        next[i][j] = 0;
                    }
                } else {
                    if (n == 3) {
                        next[i][j] = 1;
                    }
                }
            }
        }
        return next;
    }

    private void initializeRandom(int[][] board, int is, int ie, int js, int je) {
        for (int i = is; i < ie; i++) {
            for (int j = js; j < je; j++) {
                board[i][j] = ThreadLocalRandom.current().nextBoolean() ? 1 : 0;
            }
        }
    }
}
