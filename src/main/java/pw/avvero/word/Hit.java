package pw.avvero.word;

import pw.avvero.board.Cell;

public class Hit implements Action {

    private final Cell<WordObject> source;
    private final Cell<WordObject> destination;
    private final int hitValue;

    public Hit(Cell<WordObject> source, Cell<WordObject> destination, int hitValue) {
        this.source = source;
        this.destination = destination;
        this.hitValue = hitValue;
    }

    public void run() {
        if (source.value == null || destination.value == null) return;
        if (destination.value instanceof Damageable damageable) {
            damageable.dealDamage(hitValue);
        }
    }
}
