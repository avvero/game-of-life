package pw.avvero.word;

import pw.avvero.board.Cell;

public class Move implements Action {

    private final Cell<WordObject> source;
    private final Cell<WordObject> destination;

    public Move(Cell<WordObject> source, Cell<WordObject> destination) {
        this.source = source;
        this.destination = destination;
    }

    @Override
    public void run() {
        if (!isWalkable(destination)) return; // acquired already
        if (source.value instanceof Movable movable) {
            destination.value = source.value;
            source.value = movable.footprints();
        } else {
            destination.value = source.value;
            source.value = null;
        }
    }

    public static boolean isWalkable(Cell<WordObject> candidate) {
        return candidate.value == null || candidate.value instanceof Walkable;
    }
}
