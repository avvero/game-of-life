package pw.avvero.word;

import pw.avvero.BoardTestDisplay;
import pw.avvero.board.Board;
import pw.avvero.board.BoardBordered;
import pw.avvero.board.MoorNeighborhood;

import java.util.function.Function;

public class WordConstructor {

    public static Board<WordObject> constructFrom(String string, Function<Character, WordObject> factory) {
        String[] lines = BoardTestDisplay.trim(string).split("\n");
        Board<WordObject> board = new BoardBordered<>(lines.length, lines[0].length(), new MoorNeighborhood<>(), () -> new WordCell(null));
        for (int i = 0; i < lines.length; i++) {
            for (int j = 0; j < lines[i].length(); j++) {
                char ch = lines[i].charAt(j);
                board.update(i, j, (current) -> current.value = factory.apply(ch));
            }
        }
        return board;
    }

}
