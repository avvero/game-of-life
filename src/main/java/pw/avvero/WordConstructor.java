package pw.avvero;

import pw.avvero.board.Board;
import pw.avvero.board.BoardBordered;
import pw.avvero.board.Neighborhood;
import pw.avvero.word.WordCell;
import pw.avvero.word.WordObject;

import java.util.List;
import java.util.function.BiFunction;

public class WordConstructor {

    public static Board<WordObject> constructFrom(Neighborhood<WordObject> neighborhood,
                                                  BiFunction<String, Character, WordObject> factory,
                                                  String red, String green) {
        String[] redLines = trim(red).split("\n");
        String[] greenLines = trim(green).split("\n");
        assert redLines.length == greenLines.length;
        //
        Board<WordObject> board = new BoardBordered<>(redLines.length, redLines[0].length(), neighborhood, () -> new WordCell(null));
        for (Object[] army : List.of(new Object[]{"red", redLines}, new Object[]{"green", greenLines})) {
            String armyName = (String) army[0];
            String[] lines = (String[]) army[1];
            for (int i = 0; i < lines.length; i++) {
                for (int j = 0; j < lines[i].length(); j++) {
                    char ch = lines[i].charAt(j);
                    if(board.get(i, j).value == null) {
                        board.update(i, j, (current) -> current.value = factory.apply(armyName, ch));
                    }
                }
            }
        }
        return board;
    }

    static String trim(String string) {
        return string.replace(" ", "");
    }
}
