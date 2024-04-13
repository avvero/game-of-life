package pw.avvero;

import pw.avvero.board.Board;
import pw.avvero.board.BoardBordered;
import pw.avvero.board.Cell;
import pw.avvero.seed.DirectSeed;
import pw.avvero.seed.RandomSeed;
import pw.avvero.seed.RoleFactory;

import java.io.IOException;

import static pw.avvero.board.Cell.zero;

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
        Board board;
        if ("war".equals(mode)) {
            board = new BoardBordered(x, y, new GameOfWar3());
            new DirectSeed().initialize(board, 0, board.value().length, 0, board.value()[0].length, () -> zero());
            new RandomSeed().initialize(board, 0, board.value().length / 5, 0, board.value()[0].length / 5,
                    () -> Cell.of(1, RoleFactory.get()));
            new RandomSeed().initialize(board, board.value().length / 5 * 4, board.value().length, board.value()[0].length / 5 * 4, board.value()[0].length,
                    () -> Cell.of(3, RoleFactory.get()));
//            new RandomSeed().initialize(board, 0, board.value().length / 3, board.value()[0].length / 3 * 2, board.value()[0].length, 2);
//            new RandomSeed().initialize(board, board.value().length / 3 * 2, board.value().length, 0, board.value()[0].length / 3, 4);
        } else if ("move".equals(mode)) {
            board = new BoardBordered(x, y, new Move());
            new RandomSeed().initialize(board, 0, board.value().length, 0, board.value()[0].length, () -> zero());
            new DirectSeed().initialize(board, 2, 3, 0, 1, () -> Cell.of(1, RoleFactory.PAWN));
        } else {
            board = new BoardBordered(x, y, new GameOfLife());
            new RandomSeed().initialize(board, 0, board.value().length, 0, board.value()[0].length, () -> zero());
            new RandomSeed().initialize(board, 0, board.value().length, 0, board.value()[0].length, () -> Cell.of(1, new Cell.Role("⬛", 0, 0, 0, 0, 0, 0, 0)));
        }
        // Engine
        int sleepTime = 200;
        display(board, 0);
        Thread.sleep(sleepTime);
        while (true) {
            long start = System.currentTimeMillis();
            board.nextCycle();
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

    public static String toString(Cell[][] board, long cycleTime) {
        StringBuilder sb = new StringBuilder();
        sb.append("--".repeat(board[0].length));
        sb.append("\n");
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                Cell cell = board[i][j];
                String c = switch (cell.value()) { // ■ ◼ ⬛ ■ ▦ ⬛ ⛶ ⬜
                    case (1) -> "\033[31m" + cell.getRole().sign() + "\033[0m";
                    case (3) -> "\033[34m" + cell.getRole().sign() + "\033[0m";
                    case (2) -> " *"; // < ! \
                    case (4) -> " @";
                    default -> " ⛶";
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