package pw.avvero.word;

import pw.avvero.board.Cell;

import java.util.function.Predicate;

public class Pawn extends Unit implements Movable {
    public Pawn(Predicate<Cell<WordObject>> order) {
        super(order);
    }

    @Override
    public WordObject footprints() {
        return new FootPrint();
    }
}
