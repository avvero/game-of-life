package pw.avvero.word;

import pw.avvero.board.AStarSearch;
import pw.avvero.board.Board;
import pw.avvero.board.Cell;

import java.util.List;
import java.util.function.Predicate;

public abstract class Unit extends WordObject {

    protected final UnitStatsSchema.Stats stats;
    protected final Predicate<Cell<WordObject>> target;

    public Unit(Predicate<Cell<WordObject>> target) {
        this.target = target;
        this.stats = UnitStatsSchema.getBalance(this);
    }

    @Override
    public Runnable process(Board<WordObject> board, Cell<WordObject> currentCell) {
        // Mortal
        if (this instanceof Mortal mortal && !mortal.alive()) {
            return () -> currentCell.value = mortal.remains();
        }
        if (target == null) return null;     // no target
        Cell<WordObject> targetCell = board.findCloses(currentCell, target);
        if (targetCell == null) return null; // can't find
        //
        List<Cell<WordObject>> path = findShortestPath(board, currentCell, targetCell);
        if (path.isEmpty()) return null;     // can't find
        int distanceToTarget = path.size();
        //
        if (this instanceof DamageDealer damageDealer
                && targetCell.value instanceof Damageable
                && damageDealer.range() >= distanceToTarget) {
            return new Hit(currentCell, targetCell.value, 1);
        }
        // Movable
        if (this instanceof Movable && path.size() > 1) {
            return new Move(currentCell, path.get(0)); // move on 1 cell
        }
        // We are close
        return null;
    }

    private List<Cell<WordObject>> findShortestPath(Board<WordObject> board,
                                                    Cell<WordObject> currentCell,
                                                    Cell<WordObject> targetCell) {
        AStarSearch<WordObject> search = new AStarSearch<>(new AStarSearch.ManhattanDistance<>());
        List<Cell<WordObject>> path = search.path(currentCell, targetCell, c -> board.nearCells(c).stream()
                .filter(candidate -> candidate.equals(targetCell) || Move.isWalkable(candidate)).toList()); //todo maybe move to nearCells?
        //path[0] - current object, path[last] - target object
        if (!path.isEmpty()) {
            path.removeFirst();
        }
        return path;
    }
}
