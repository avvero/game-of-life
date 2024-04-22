package pw.avvero;

import pw.avvero.board.Board;
import pw.avvero.board.BoardBordered;
import pw.avvero.board.Cell;
import pw.avvero.board.MoorNeighborhood;
import pw.avvero.convey.ConveyCell;
import pw.avvero.word.*;

import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.BiFunction;

import static java.util.Objects.requireNonNull;


public class Main {
    public static void main(String[] args) throws InterruptedException, IOException {
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
            case "fight": {
                String red = readArmySchema("red.txt");
                String green = readArmySchema("green.txt");

                var factory = new BiFunction<String, Character, WordObject>() {
                    @Override
                    public WordObject apply(String army, Character ch) {
                        switch (ch) {
                            case '⚔':
                                return new Knight(army, Aligned.findEnemyAndFight(army));
                            case '↑':
                                return new Archer(army, Aligned.findEnemyAndFight(army));
                            case '●':
                                return new Stone();
                            default:
                                return null;
                        }
                    }
                };
                Board<WordObject> board = WordConstructor.constructFrom(new MoorNeighborhood<>(), factory, red, green);
                Render<Cell<WordObject>> render = cell -> {
                    if (cell.value instanceof Knight knight && "red".equals(knight.getAllegiance())) {
                        return "\033[31m ⚔\033[0m";
                    }
                    if (cell.value instanceof Knight knight && "green".equals(knight.getAllegiance())) {
                        return "\033[32m ⚔\033[0m";
                    }
                    if (cell.value instanceof Archer archer && "red".equals(archer.getAllegiance())) {
                        return "\033[31m ↑\033[0m";
                    }
                    if (cell.value instanceof Archer archer && "green".equals(archer.getAllegiance())) {
                        return "\033[32m ↑\033[0m";
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

    private static String readArmySchema(String filePath) throws IOException {
        return new String(requireNonNull(Main.class.getClassLoader().getResourceAsStream(filePath)).readAllBytes());
    }
}