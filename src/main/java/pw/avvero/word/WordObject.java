package pw.avvero.word;

import pw.avvero.board.Board;
import pw.avvero.board.Cell;

import java.util.concurrent.atomic.AtomicInteger;

public abstract class WordObject {

    private final static AtomicInteger IDS = new AtomicInteger();
    public final int id = IDS.getAndIncrement();

    public abstract Runnable process(Board<WordObject> board, Cell<WordObject> cell);
}
