package pw.avvero.word;

import pw.avvero.board.AStarSearch;
import pw.avvero.board.Board;
import pw.avvero.board.Cell;

import java.util.List;
import java.util.function.Predicate;

public abstract class Unit extends WordObject {

    private final Predicate<Cell<WordObject>> order;

    public Unit(Predicate<Cell<WordObject>> order) {
        this.order = order;
    }

    @Override
    public Runnable process(Board<WordObject> board, Cell<WordObject> currentCell) {
        // Mortal
        if (this instanceof Mortal mortal && !mortal.alive()) {
            return () -> currentCell.value = mortal.remains();
        }
        if (order == null) return null;  // no order
        Cell<WordObject> target = board.findFirst(order); //todo not order but target
        if (target == null) return null; // can't find
        //
        AStarSearch<WordObject> search = new AStarSearch<>(new AStarSearch.ManhattanDistance<>());
        List<Cell<WordObject>> path = search.path(currentCell, target, c -> board.nearCells(c).stream()
                .filter(candidate -> candidate.equals(target) || Move.isWalkable(candidate)).toList()); //todo maybe move to nearCells?
        if (path.isEmpty()) return null; // can't find
        int distanceToTarget = path.size() - 1; //path[0] - current object, path[last] - target object
        //
        if (this instanceof DamageDealer damageDealer
                && target.value instanceof Damageable
                && damageDealer.range() >= distanceToTarget) {
            return new Hit(currentCell, target.value, 1);
        }
        // Movable
        if (this instanceof Movable && path.size() > 2) {
            return new Move(currentCell, path.get(1)); // move on 1 cell
        }
        // We are close
        return null;
    }
}
