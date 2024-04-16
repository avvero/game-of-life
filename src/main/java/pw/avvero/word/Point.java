package pw.avvero.word;

import pw.avvero.board.Board;
import pw.avvero.board.Cell;

public class Point extends WordObject {

    public final String label;

    public Point(String label) {
        this.label = label;
    }

    @Override
    public Runnable process(Board<WordObject> board, Cell<WordObject> cell) {
        return null;
    }
}
