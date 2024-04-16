package pw.avvero.word;

import pw.avvero.board.Board;
import pw.avvero.board.Cell;

public class FootPrint extends WordObject implements Walkable {

    @Override
    public Runnable process(Board<WordObject> board, Cell<WordObject> cell) {
        return null;
    }
}
