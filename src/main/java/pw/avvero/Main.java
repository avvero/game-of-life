package pw.avvero;

import pw.avvero.board.Board;
import pw.avvero.board.BoardBordered;
import pw.avvero.board.Cell;
import pw.avvero.convey.ConveyCell;
import pw.avvero.move.*;

import java.util.concurrent.atomic.AtomicInteger;


public class Main {
    public static void main(String[] args) throws InterruptedException {
        if (args.length < 3) {
//            System.out.println("Usage: java GameOfLife <x> <y>");
//            System.exit(1);
            args = new String[]{"5", "5", "randmove"};
        }
        int x = Integer.parseInt(args[0]);
        int y = Integer.parseInt(args[1]);
        String mode = args[2];
        //
        switch (mode) {
            case "enemy": {
                Board<MoveTarget> board = new BoardBordered<>(x, y, () -> new FirstAvailableEnemyMoveCell(null));
                AtomicInteger ids = new AtomicInteger(1);
                board.update(0, 0, (current) -> current.value = new Pawn(ids.getAndIncrement()));
                board.update(0, y - 1, (current) -> current.value = new Pawn(ids.getAndIncrement()));
                board.update(x - 1, 5, (current) -> current.value = new Pawn(ids.getAndIncrement()));
                board.update(x - 1, y - 1, (current) -> current.value = new Pawn(ids.getAndIncrement()));
                // ■ ◼ ⬛ ■ ▦ ⬛ ⛶ ⬜
                Render<Cell<MoveTarget>> render = cell -> {
                    if (cell.value instanceof Pawn) {
                        return "\033[31m " + ((Pawn) cell.value).id + "\033[0m";
                    }
                    return "  ";
//                    return (cell.id < 10 ? " " : "") + cell.id;
                };
                new Engine<MoveTarget>().run(board, render, 200);
                break;
            }
            case "randmove": {
                Board<MoveTarget> board = new BoardBordered<>(x, y, () -> new RandomMoveCell(null));
                AtomicInteger ids = new AtomicInteger(1);
                for (int i = 0; i < x; i++) {
                    board.update(i, y / 2, (current) -> current.value = new Pawn(ids.getAndIncrement()));
                }
                // ■ ◼ ⬛ ■ ▦ ⬛ ⛶ ⬜
                Render<Cell<MoveTarget>> render = cell -> {
                    if (cell.value instanceof Pawn) {
                        return "\033[31m " + ((Pawn) cell.value).id + "\033[0m";
                    }
                    return "  ";
//                    return (cell.id < 10 ? " " : "") + cell.id;
                };
                new Engine<MoveTarget>().run(board, render, 200);
                break;
            }
            default: {
                Board<Integer> board = new BoardBordered<>(x, y, ConveyCell::new);
                // ■ ◼ ⬛ ■ ▦ ⬛ ⛶ ⬜
                Render<Cell<Integer>> render = cell -> cell.value == 1 ? "\033[31m⬜\033[0m" : "  ";
                new Engine<Integer>().run(board, render, 200);
                break;
            }
        }
    }
}