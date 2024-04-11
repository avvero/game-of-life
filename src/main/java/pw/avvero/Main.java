package pw.avvero;

import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        int size = 20;
        int[][] board = new int[size][size];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (i == j) {
                    board[i][j] = 1;
                }
            }
        }
        while (true) {
            // Update
            int i = ThreadLocalRandom.current().nextInt(0, board.length);
            int j = ThreadLocalRandom.current().nextInt(0, board[i].length);
            board[i][j] = board[i][j] ^ 1;
            //
            clear();
            System.out.println(toString(board));
            //
            Thread.sleep(50);
        }
    }

    public static String toString(int[][] board) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < board.length; i++) {
            sb.append("--");
        }
        sb.append("\n");
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                sb.append(board[i][j] == 1 ? " *" : "  ");
            }
            sb.append("\n");
        }
        for (int i = 0; i < board.length; i++) {
            sb.append("--");
        }
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