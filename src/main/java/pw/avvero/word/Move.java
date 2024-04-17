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
        destination.value = source.value;
        source.value = new FootPrint();
    }

    public static boolean isWalkable(Cell<WordObject> candidate) {
        return candidate.value == null || candidate.value instanceof Walkable;
    }
}
