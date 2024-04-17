package pw.avvero.word;

import pw.avvero.board.Board;
import pw.avvero.board.Cell;

public class Pell extends WordObject implements Damageable, Mortal {

    private int health;

    public Pell(int health) {
        this.health = health;
    }

    public int getHealth() {
        return health;
    }

    @Override
    public Runnable process(Board<WordObject> board, Cell<WordObject> cell) {
        if (health <= 0) {
            return () -> {
                cell.value = new Tomb();
            };
        }
        return null;
    }

    @Override
    public void dealDamage(int amount) {
        health -= amount;
    }

    @Override
    public boolean alive() {
        return health > 0;
    }
}
