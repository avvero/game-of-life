package pw.avvero.word;

import pw.avvero.board.Cell;

import java.util.function.Predicate;

public abstract class DamageableUnit extends Unit implements Damageable, Mortal {

    protected int health;

    public DamageableUnit(int health, Predicate<Cell<WordObject>> order) {
        super(order);
        this.health = health;
    }

    @Override
    public void handleDamage(int amount) {
        health -= amount;
    }

    @Override
    public boolean alive() {
        return health > 0;
    }
}
