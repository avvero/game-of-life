package pw.avvero.word;

import pw.avvero.board.Cell;

public class Hit implements Action {

    private final Cell<WordObject> source;
    private final WordObject target;
    private final int hitValue;

    public Hit(Cell<WordObject> source, WordObject target, int hitValue) {
        this.source = source;
        this.target = target;
        this.hitValue = hitValue;
    }

    public void run() {
        if (source.value == null) return; //killed?
        if (target instanceof Damageable damageable) {
            damageable.handleDamage(hitValue);
        }
    }
}
