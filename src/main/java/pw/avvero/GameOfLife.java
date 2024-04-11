package pw.avvero;

import java.util.concurrent.ThreadLocalRandom;

public class GameOfLife {

    private final Board board;

    public GameOfLife(Board board) {
        this.board = board;
        initializeRandom(board.value(), 0, board.value().length / 3, 0,  board.value()[0].length / 3, 1);
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
                    if (n < 2) {
                        next[i][j] = 0;
                    } else if (n <= 3) {
                        next[i][j] = v;
                    } else {
                        next[i][j] = 0;
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

    private void initializeRandom(int[][] board, int is, int ie, int js, int je, int value) {
        for (int i = is; i < ie; i++) {
            for (int j = js; j < je; j++) {
                board[i][j] = ThreadLocalRandom.current().nextBoolean() ? value : 0;
            }
        }
    }
}
