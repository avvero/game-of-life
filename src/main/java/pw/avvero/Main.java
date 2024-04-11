package pw.avvero;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        int x = 50, y = 100;
        GameOfLife life = new GameOfLife(50, 100);
        clear();
        System.out.println(toString(life.getBoard()));
        Thread.sleep(100);
        while (true) {
            life.next();
            //
            clear();
            System.out.println(toString(life.getBoard()));
            //
            Thread.sleep(200);
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
        } catch (IOException | InterruptedException ex) {
        }
    }
}