package pw.avvero.word;

import pw.avvero.board.AStarSearch;
import pw.avvero.board.Board;
import pw.avvero.board.Cell;

import java.util.List;
import java.util.function.Predicate;

public class Unit extends WordObject {

    private final Predicate<Cell<WordObject>> order;

    public Unit(Predicate<Cell<WordObject>> order) {
        this.order = order;
    }

    @Override
    public Runnable process(Board<WordObject> board, Cell<WordObject> cell) {
        if (order == null) return null;
        Cell<WordObject> target = board.findFirst(order);
        if (target == null) return null; // can't find
        AStarSearch<WordObject> search = new AStarSearch<>(new AStarSearch.ManhattanDistance<>());
        List<Cell<WordObject>> path = search.path(cell, target, c -> board.nearCells(c).stream()
                .filter(candidate -> candidate.equals(target) || isWalkable(candidate)).toList()); //todo maybe move to nearCells?
        if (path.isEmpty()) return null; // can't find
        return move(cell, path.get(1)); // move on 1 cell
        // find target
        // move
        // hit if hittable
    }

    private boolean isWalkable(Cell<WordObject> candidate) {
        return candidate.value == null || candidate.value instanceof Walkable;
    }

    private Runnable move(Cell<WordObject> source, Cell<WordObject> destination) {
        return () -> {
            if (!isWalkable(destination)) return; // acquired already
            destination.value = source.value;
            source.value = new FootPrint();
        };
    }
}
