package pw.avvero;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        if (args.length < 2) {
            System.out.println("Usage: java GameOfLife <x> <y>");
            System.exit(1);
        }
        int x = Integer.parseInt(args[0]);
        int y = Integer.parseInt(args[1]);
        GameOfLife life = new GameOfLife(x, y);
        int sleepTime = 100;
        display(life);
        Thread.sleep(sleepTime);
        while (true) {
            life.next();
            display(life);
            Thread.sleep(sleepTime);
        }
    }

    private static void display(GameOfLife life) {
        clear();
        System.out.println(toString(life.getBoard()));
    }

    public static String toString(int[][] board) {
        StringBuilder sb = new StringBuilder();
        sb.append("--".repeat(board[0].length));
        sb.append("\n");
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                sb.append(board[i][j] == 1 ? " ▦" : "  ");// ■ ◼ ⬛ ■ ▦ ⬛ ⛶ ⬜
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