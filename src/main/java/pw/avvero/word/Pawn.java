package pw.avvero.word;

import pw.avvero.board.Cell;

import java.util.function.Predicate;

public class Pawn extends Unit implements Movable {

    public final int value;

    public Pawn(Predicate<Cell<WordObject>> order) {
        super(order);
        value = 1;
    }

    public Pawn(int value, Predicate<Cell<WordObject>> order) {
        super(order);
        this.value = value;
    }

    @Override
    public WordObject footprints() {
        return new FootPrint();
    }
}
