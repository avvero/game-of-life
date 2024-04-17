package pw.avvero.word;

import pw.avvero.board.AStarSearch;
import pw.avvero.board.Board;
import pw.avvero.board.Cell;

import java.util.List;
import java.util.function.Predicate;

public class Unit extends WordObject implements Damageable, Mortal, Movable, DamageDealer {

    private final Predicate<Cell<WordObject>> order;
    private int health = 1;

    public Unit(Predicate<Cell<WordObject>> order) {
        this.order = order;
    }

    @Override
    public Runnable process(Board<WordObject> board, Cell<WordObject> currentCell) {
        // Mortal
        if (this instanceof Mortal mortal && !mortal.alive()) {
            return () -> {
                currentCell.value = new Tomb();
            };
        }
        // Movable
        if (this instanceof Movable movable) {
            if (order == null) return null;
            Cell<WordObject> target = board.findFirst(order); //todo not order but target
            // todo return Neighbour to check the distance, what about walls?
            if (target == null) return null; // can't find
            AStarSearch<WordObject> search = new AStarSearch<>(new AStarSearch.ManhattanDistance<>());
            List<Cell<WordObject>> path = search.path(currentCell, target, c -> board.nearCells(c).stream()
                    .filter(candidate -> candidate.equals(target) || Move.isWalkable(candidate)).toList()); //todo maybe move to nearCells?
            if (path.isEmpty()) return null; // can't find
            // path[0] - current object
            // path[last] - target object
            if (path.size() > 2) {
                return new Move(currentCell, path.get(1)); // move on 1 cell
            }
            // if no return means we are close
        }
        if (this instanceof DamageDealer damageDealer) {
            if (order == null) return null;
            Cell<WordObject> target = board.findFirst(order); //todo not order but target
            // we are near
            if (target.value instanceof Damageable damageable) {
//            new Hit(cell, target, 1).run(); // TODO direct update, forbidden
                return new Hit(currentCell, target, 1);
            }
        }
        return null;
    }

    @Override
    public void dealDamage(int amount) {
        health -= amount;
    }

    @Override
    public boolean alive() {
        return health >= 0;
    }
}
