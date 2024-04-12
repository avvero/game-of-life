package pw.avvero;

import pw.avvero.board.Board;
import pw.avvero.board.BoardBordered;
import pw.avvero.seed.RandomSeed;
import pw.avvero.seed.Seed;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        if (args.length < 2) {
//            System.out.println("Usage: java GameOfLife <x> <y>");
//            System.exit(1);
            args = new String[]{"46", "90"};
        }
        int x = Integer.parseInt(args[0]);
        int y = Integer.parseInt(args[1]);
        //
        Board board = new BoardBordered(x, y);
        //
        Seed seed = new RandomSeed();
        seed.initialize(board.value(), 0, board.value().length / 3, 0,  board.value()[0].length / 3, 1);
        GameOfLife game = new GameOfLife(board);
        //
//        GameOfLifeAndWar game = new GameOfLifeAndWar(board);
        // Engine
        int sleepTime = 100;
        display(board);
        Thread.sleep(sleepTime);
        while (true) {
            game.newCycle();
            display(board);
            Thread.sleep(sleepTime);
        }
    }

    private static void display(Board board) {
        String payload = toString(board.value());
        clear();
        System.out.println(payload);
    }

    public static String toString(int[][] board) {
        StringBuilder sb = new StringBuilder();
        sb.append("--".repeat(board[0].length));
        sb.append("\n");
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                String c = switch (board[i][j]) { // ■ ◼ ⬛ ■ ▦ ⬛ ⛶ ⬜
                    case (1) -> " ▦";
                    case (-1) -> " ⛶";
                    default -> "  ";
                };
                sb.append(c);
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