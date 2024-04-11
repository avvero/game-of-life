package pw.avvero;

import java.util.concurrent.ThreadLocalRandom;

public class GameOfLife {

    private int[][] board;

    public GameOfLife(int x, int y) {
        this.board = new int[x][y];
        initializeRandom(board, 0, board.length / 3, 0, board[0].length / 3);
    }

    public void next() {
        this.board = nextGeneration(board);
    }

    public int[][] getBoard() {
        return board;
    }

    private int[][] nextGeneration(int[][] board) {
        int[][] next = new int[board.length][board[0].length];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                int n = neighbours(board, i, j);
                if (board[i][j] == 1) {
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

    private int neighbours(int[][] board, int i, int j) {
        int result = 0;
        for (int x = -1; x < 2; x++) {
            for (int y = -1; y < 2; y++) {
                if (x == 0 && y == 0) continue;
                result += exists(board, i + x, j + y) ? 1 : 0;
            }
        }
        return result;
    }

    private boolean exists(int[][] board, int i, int j) {
        if(i < 0 || i == board.length || j < 0 || j == board[i].length) return false;
        return board[i][j] == 1;
    }

    private boolean existsOnOverlap(int[][] board, int i, int j) {
        if (i < 0) {
            i = board.length + i;
        }
        if (i == board.length) {
            i = 0;
        }
        if (j < 0) {
            j = board[i].length + j;
        }
        if (j == board[i].length) {
            j = 0;
        }
        return board[i][j] == 1;
    }

    private void initializeV(int[][] board) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (i == j) {
                    board[i][j] = 1;
                }
            }
        }
    }

    private int[][] nextGenerationRandom(int[][] board) {
        int i = ThreadLocalRandom.current().nextInt(0, board.length);
        int j = ThreadLocalRandom.current().nextInt(0, board[i].length);
        board[i][j] = board[i][j] ^ 1;
        return board;
    }

    private void initializeRandom(int[][] board, int is, int ie, int js, int je) {
        for (int i = is; i < ie; i++) {
            for (int j = js; j < je; j++) {
                board[i][j] = ThreadLocalRandom.current().nextBoolean() ? 1 : 0;
            }
        }
    }
}
