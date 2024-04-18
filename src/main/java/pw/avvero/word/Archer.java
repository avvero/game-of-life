package pw.avvero.word;

import pw.avvero.board.Cell;

import java.util.function.Predicate;

public class Archer extends DamageableUnit implements DamageDealer, Movable, Aligned {

    private final String allegiance;

    public Archer(int health, String allegiance, Predicate<Cell<WordObject>> order) {
        this(allegiance, order);
        this.health = health;
    }

    public Archer(String allegiance, Predicate<Cell<WordObject>> order) {
        super(order);
        this.allegiance = allegiance;
    }

    @Override
    public int range() {
        return stats.range();
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
