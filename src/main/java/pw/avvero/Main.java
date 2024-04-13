package pw.avvero;

import pw.avvero.board.Board;
import pw.avvero.board.BoardBordered;
import pw.avvero.board.Cell;
import pw.avvero.gol.GameOfLife;

import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;


public class Main {
    public static void main(String[] args) throws InterruptedException {
        if (args.length < 3) {
//            System.out.println("Usage: java GameOfLife <x> <y>");
//            System.exit(1);
            args = new String[]{"5", "5", "move"};
        }
        int x = Integer.parseInt(args[0]);
        int y = Integer.parseInt(args[1]);
        String mode = args[2];
        //
        Board board = new BoardBordered(x, y);
        board.nextCycle((current, list) -> () -> current.value = 0);
        board.nextCycle((current, list) -> () -> {
            if (ThreadLocalRandom.current().nextBoolean()) {
                current.value = 1;
            }
        });
        // Engine
        int sleepTime = 200;
        display(board, 0);
        Thread.sleep(sleepTime);
        while (true) {
            long start = System.currentTimeMillis();
            board.nextCycle(new GameOfLife());
            long cycleTime = System.currentTimeMillis() - start;
            display(board, cycleTime);
            Thread.sleep(sleepTime - cycleTime);
        }
    }

    private static void display(Board board, long cycleTime) {
        String payload = toString(board.value(), cycleTime);
        clear();
        System.out.println(payload);
    }

    public static String toString(Cell<Integer>[][] board, long cycleTime) {
        StringBuilder sb = new StringBuilder();
        sb.append("--".repeat(board[0].length));
        sb.append("\n");
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                Cell<Integer> cell = board[i][j];
                String c = switch (cell.value) { // ■ ◼ ⬛ ■ ▦ ⬛ ⛶ ⬜
                    case (1) -> "\033[31m⬜\033[0m";
//                    case (3) -> "\033[34m" + cell.getRole().sign() + "\033[0m";
                    case (2) -> " *"; // < ! \
                    case (4) -> " @";
                    default -> "  ";
                };
                sb.append(c);
            }
            sb.append("\n");
        }
        sb.append("--".repeat(board[0].length) + cycleTime);
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