package pw.avvero;

import pw.avvero.board.Board;
import pw.avvero.board.BoardBordered;
import pw.avvero.board.Cell;
import pw.avvero.seed.DirectSeed;
import pw.avvero.seed.RandomSeed;

import java.io.IOException;

import static pw.avvero.board.Cell.ZERO;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        if (args.length < 2) {
//            System.out.println("Usage: java GameOfLife <x> <y>");
//            System.exit(1);
            args = new String[]{"20", "20"};
        }
        int x = Integer.parseInt(args[0]);
        int y = Integer.parseInt(args[1]);
        //
//        Board board = new BoardBordered(x, y, new GameOfLife());
//        new RandomSeed().initialize(board, 0, board.value().length, 0, board.value()[0].length, ZERO);
//        new RandomSeed().initialize(board, 0, board.value().length, 0,  board.value()[0].length, Cell.of(1));
        //
        Board board = new BoardBordered(x, y, new GameOfWar());
        new DirectSeed().initialize(board, 0, board.value().length, 0, board.value()[0].length, ZERO);
        new RandomSeed().initialize(board, 0, board.value().length / 5, 0, board.value()[0].length / 5, Cell.of(1));
        new RandomSeed().initialize(board, board.value().length / 5 * 4, board.value().length, board.value()[0].length / 5 * 4, board.value()[0].length, Cell.of(3));
//        new RandomSeed().initialize(board.value(), 0, board.value().length / 3, board.value()[0].length / 3 * 2, board.value()[0].length, 2);
//        new RandomSeed().initialize(board.value(), board.value().length / 3 * 2, board.value().length, 0, board.value()[0].length / 3, 4);
        // Engine
        int sleepTime = 100;
        display(board);
        Thread.sleep(sleepTime);
        while (true) {
            board.nextCycle();
            display(board);
            Thread.sleep(sleepTime);
        }
    }

    private static void display(Board board) {
        String payload = toString(board.value());
        clear();
        System.out.println(payload);
    }

    public static String toString(Cell[][] board) {
        StringBuilder sb = new StringBuilder();
        sb.append("--".repeat(board[0].length));
        sb.append("\n");
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                String c = switch (board[i][j].value()) { // ■ ◼ ⬛ ■ ▦ ⬛ ⛶ ⬜
                    case (1) -> "\033[31m⬛\033[0m";
                    case (3) -> "\033[34m⬛\033[0m";
                    case (2) -> " *"; // < ! \
                    case (4) -> " @";
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