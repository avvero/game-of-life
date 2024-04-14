package pw.avvero;

import pw.avvero.board.Board;
import pw.avvero.board.BoardBordered;
import pw.avvero.gol.GameOfLife;
import pw.avvero.move.RandomMove;
import pw.avvero.move.RandomMove.Immovable;
import pw.avvero.move.RandomMove.Movable;
import pw.avvero.move.RandomMove.MoveTarget;

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
        switch (mode) {
            case "randmove": {
                Board<MoveTarget> board = new BoardBordered<>(x, y);
                board.nextCycle((current, list) -> () -> current.value = new Immovable(){});
                board.update(2, 0, (current) -> current.value = new Movable(){});
                board.update(2, 1, (current) -> current.value = new Movable(){});
                board.update(2, y - 1, (current) -> current.value = new Movable(){});
                board.update(2, y - 2, (current) -> current.value = new Movable(){});
                // ■ ◼ ⬛ ■ ▦ ⬛ ⛶ ⬜
                Render<MoveTarget> render = value -> value instanceof Movable ? "\033[31m⬜\033[0m" : "  ";
                new Engine<MoveTarget>().run(board, RandomMove::new, render, 200);
                break;
            }
            default: {
                Board<Integer> board = new BoardBordered<>(x, y);
                board.nextCycle((current, list) -> () -> current.value = 0);
                board.nextCycle((current, list) -> () -> {
                    if (ThreadLocalRandom.current().nextBoolean()) {
                        current.value = 1;
                    }
                });
                // ■ ◼ ⬛ ■ ▦ ⬛ ⛶ ⬜
                Render<Integer> render = value -> value == 1 ? "\033[31m⬜\033[0m" : "  ";
                new Engine<Integer>().run(board, GameOfLife::new, render, 200);
                break;
            }
        }
    }
}