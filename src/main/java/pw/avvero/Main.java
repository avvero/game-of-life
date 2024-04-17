package pw.avvero;

import pw.avvero.board.*;
import pw.avvero.convey.ConveyCell;
import pw.avvero.walk.FootPrint;
import pw.avvero.walk.Hound;
import pw.avvero.walk.Kennel;
import pw.avvero.walk.WalkableCell;
import pw.avvero.word.*;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;


public class Main {
    public static void main(String[] args) throws InterruptedException {
        if (args.length < 3) {
//            System.out.println("Usage: java GameOfLife <x> <y>");
//            System.exit(1);
            args = new String[]{"39", "39", "hounds"};
        }
        int x = Integer.parseInt(args[0]);
        int y = Integer.parseInt(args[1]);
        String mode = args[2];
        //
        switch (mode) {
            case "hounds": {
                Board<Object> board = new BoardBordered<>(x, y, new MoorNeighborhood<>(), () -> new WalkableCell<>(null));
                AtomicInteger hids = new AtomicInteger(1);
                board.update(1, 1, (current) -> current.value = "~");
                board.update(x - 1, 0, (current) -> current.value = "~");
                board.update(0, y - 1, (current) -> current.value = "~");
                board.update(x - 1, y - 1, (current) -> current.value = new Kennel<>(
                        new Hound<>(hids.getAndIncrement(), (c) -> c == "~"),
                        new Hound<>(hids.getAndIncrement(), (c) -> c == "~"),
                        new Hound<>(hids.getAndIncrement(), (c) -> c == "~")
                ));
                Render<Cell<Object>> render = cell -> {
                    if (cell.value instanceof Kennel) {
                        return "\033[31m\uD83C\uDFE0\033[0m";
                    }
                    if (cell.value instanceof Hound) {
                        return "\uD83D\uDC15";
                    }
                    if (cell.value instanceof FootPrint) {
                        return "\uD83D\uDC3E";
                    }
                    if (cell.value != null) {
                        return " \uD83E\uDDB4";
                    }
                    return "  ";
                };
                new Engine<Object>().run(board, render, 200);
                break;
            }
            case "fight": {
                Board<WordObject> board = new BoardBordered<>(x, y, new VonNeumannNeighborhood<>(), () -> new WordCell(null));
                for (int i = 0; i < board.value().length; i+=5) {
                    board.update(i, 0, cell -> {
                        cell.value =  new Knight(5, "red", Aligned.findEnemyAndFight("red"));
                    });
                }
                for (int i = 0; i < board.value().length; i+=5) {
                    board.update(i, board.value()[0].length - 1, cell -> {
                        cell.value =  new Knight(5, "green", Aligned.findEnemyAndFight("green"));
                    });
                }
                Render<Cell<WordObject>> render = cell -> {
                    if (cell.value instanceof Knight knight && "red".equals(knight.getAllegiance())) {
                        return " ⚔";
                    }
                    if (cell.value instanceof Knight knight && "green".equals(knight.getAllegiance())) {
                        return " ⚒";
                    }
                    if (cell.value instanceof pw.avvero.word.FootPrint) {
                        return " .";
                    }
                    if (cell.value instanceof Tomb) {
                        return " †";
                    }
                    if (cell.value == null) return "  ";
                    return " " + cell.value;
                };
                new Engine<WordObject>().run(board, render, 200);
                break;
            }
            default: {
                Board<Integer> board = new BoardBordered<>(x, y, new MoorNeighborhood<>(), ConveyCell::new);
                for (int i = 0; i < board.value().length; i++) {
                    for (int j = 0; j < board.value()[i].length; j++) {
                        board.update(i, j, cell -> cell.value = ThreadLocalRandom.current().nextBoolean() ? 1 : 0);
                    }
                }
                // ■ ◼ ⬛ ■ ▦ ⬛ ⛶ ⬜
                Render<Cell<Integer>> render = cell -> cell.value == 1 ? "\033[31m⬜\033[0m" : "  ";
                new Engine<Integer>().run(board, render, 200);
                break;
            }
        }
    }
}