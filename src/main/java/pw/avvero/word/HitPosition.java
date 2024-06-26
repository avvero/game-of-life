package pw.avvero.word;

import pw.avvero.board.Cell;

public class HitPosition implements Action {

    private final Cell<WordObject> source;
    private final Cell<WordObject> destination;
    private final int hitValue;

    public HitPosition(Cell<WordObject> source, Cell<WordObject> destination, int hitValue) {
        this.source = source;
        this.destination = destination;
        this.hitValue = hitValue;
    }

    public void run() {
        if (source.value == null || destination.value == null) return;
        if (destination.value instanceof Damageable damageable) {
            damageable.handleDamage(hitValue);
        }
    }
}
