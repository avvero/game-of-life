package pw.avvero.word;

import pw.avvero.board.Cell;

import java.util.function.Predicate;

public class Knight extends DamageableUnit implements DamageDealer, Movable {

    public Knight(int health, Predicate<Cell<WordObject>> order) {
        super(health, order);
    }

    @Override
    public int range() {
        return 1;
    }

    @Override
    public WordObject footprints() {
       return new FootPrint();
    }

    @Override
    public WordObject remains() {
        return new Tomb();
    }
}
