package pw.avvero.word;

import pw.avvero.board.Cell;

import java.util.function.Predicate;

public class Knight extends DamageableUnit implements DamageDealer, Movable, Aligned {

    private final String allegiance;

    public Knight(int health, String allegiance, Predicate<Cell<WordObject>> order) {
        super(health, order);
        this.allegiance = allegiance;
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

    @Override
    public String getAllegiance() {
        return allegiance;
    }
}
