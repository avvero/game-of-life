package pw.avvero;

import pw.avvero.board.Board;
import pw.avvero.board.BoardBordered;
import pw.avvero.gol.ConveyCell;
import pw.avvero.move.RandomMoveCell;
import pw.avvero.move.RandomMoveCell.Movable;
import pw.avvero.move.RandomMoveCell.MoveTarget;
import pw.avvero.move.RandomMoveCell.Pawn;


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
                Board<MoveTarget> board = new BoardBordered<>(x, y, RandomMoveCell::new);
                for (int i = 0; i < x / 2; i++) {
                    board.update(i * 2, 0, (current) -> current.value = new Pawn(){});
                }
                // ■ ◼ ⬛ ■ ▦ ⬛ ⛶ ⬜
                Render<MoveTarget> render = value -> value instanceof Movable ? "\033[31m⬜\033[0m" : "  ";
                new Engine<MoveTarget>().run(board, render, 200);
                break;
            }
            default: {
                Board<Integer> board = new BoardBordered<>(x, y, ConveyCell::new);
                // ■ ◼ ⬛ ■ ▦ ⬛ ⛶ ⬜
                Render<Integer> render = value -> value == 1 ? "\033[31m⬜\033[0m" : "  ";
                new Engine<Integer>().run(board, render, 200);
                break;
            }
        }
    }
}