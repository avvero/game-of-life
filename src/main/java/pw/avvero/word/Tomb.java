package pw.avvero.word;

import pw.avvero.board.Board;
import pw.avvero.board.Cell;

public class Tomb extends WordObject implements Walkable {
    @Override
    public Runnable process(Board<WordObject> board, Cell<WordObject> cell) {
        return null;
    }
}
