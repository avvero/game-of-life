package pw.avvero.word;

import pw.avvero.BoardTestDisplay;
import pw.avvero.board.Board;
import pw.avvero.board.BoardBordered;
import pw.avvero.board.MoorNeighborhood;
import pw.avvero.board.Neighborhood;

import java.util.function.Function;

public class TestWordConstructor {

    public static Board<WordObject> constructFrom(String string, Neighborhood<WordObject> neighborhood,
                                                  Function<Character, WordObject> factory) {
        String[] lines = BoardTestDisplay.trim(string).split("\n");
        Board<WordObject> board = new BoardBordered<>(lines.length, lines[0].length(), neighborhood, () -> new WordCell(null));
        for (int i = 0; i < lines.length; i++) {
            for (int j = 0; j < lines[i].length(); j++) {
                char ch = lines[i].charAt(j);
                board.update(i, j, (current) -> current.value = factory.apply(ch));
            }
        }
        return board;
    }

    public static Board<WordObject> constructFrom(String string, Function<Character, WordObject> factory) {
        return constructFrom(string, new MoorNeighborhood<>(), factory);
    }
}
