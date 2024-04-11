package pw.avvero;

import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        int x = 50, y = 100;
        int[][] board = new int[x][y];
        // Seed
//        initializeV(board);
        initializeRandom(board);
        clear();
        System.out.println(toString(board));
        Thread.sleep(100);
        while (true) {
            board = nextGeneration(board);
            //
            clear();
            System.out.println(toString(board));
            //
            Thread.sleep(200);
        }
    }

    private static int[][] nextGeneration(int[][] board) {
        int[][] next = new int[board.length][board[0].length];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                int n = neighbours(board, i, j);
                if (board[i][j] == 1) {
                    if (n < 2) {
                        next[i][j] = 0;
                    } else if (n < 4) {
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

    private static int neighbours(int[][] board, int i, int j) {
        int result = 0;
        for (int x = -1; x < 2; x++) {
            for (int y = -1; y < 2; y++) {
                if (x == 0 && y == 0) continue;
                result += exists(board, i + x, j + y) ? 1 : 0;
            }
        }
        return result;
    }

    private static boolean exists(int[][] board, int i, int j) {
        if(i < 0 || i == board.length || j < 0 || j == board[i].length) return false;
        return board[i][j] == 1;
    }

    private static int[][] nextGenerationRandom(int[][] board) {
        int i = ThreadLocalRandom.current().nextInt(0, board.length);
        int j = ThreadLocalRandom.current().nextInt(0, board[i].length);
        board[i][j] = board[i][j] ^ 1;
        return board;
    }

    private static void initializeRandom(int[][] board) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j] = ThreadLocalRandom.current().nextBoolean() ? 1 : 0;
            }
        }
    }

    private static void initializeV(int[][] board) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (i == j) {
                    board[i][j] = 1;
                }
            }
        }
    }

    public static String toString(int[][] board) {
        StringBuilder sb = new StringBuilder();
        sb.append("--".repeat(board[0].length));
        sb.append("\n");
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                sb.append(board[i][j] == 1 ? " *" : "  ");
            }
            sb.append("\n");
        }
        sb.append("--".repeat(board[0].length));
        return sb.toString();
    }

    private static void clear() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (IOException | InterruptedException ex) {}
    }
}