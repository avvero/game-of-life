package pw.avvero.word;

import pw.avvero.board.Cell;

import java.util.function.Predicate;

public class Knight extends DamageableUnit implements DamageDealer, Movable, Aligned {

    private final String allegiance;

    public Knight(String allegiance, Predicate<Cell<WordObject>> order) {
        super(order);
        this.allegiance = allegiance;
    }

    public Knight(int health, String allegiance, Predicate<Cell<WordObject>> order) {
        super(order);
        this.allegiance = allegiance;
        this.health = health;
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
