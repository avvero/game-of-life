package pw.avvero.walk;

import pw.avvero.board.AStarSearch;
import pw.avvero.board.AStarSearch.ManhattanDistance;
import pw.avvero.board.Board;
import pw.avvero.board.Cell;

import java.util.List;
import java.util.function.Supplier;

public class WalkableCell<T> extends Cell<T> {

    public interface Walker<T> {

        boolean isTarget(Cell<T> cell);

    }

    public interface Walkable {
    }

    public interface Factory<T> extends Supplier<T> {

    }

    public WalkableCell(T value) {
        super(value);
    }

    @Override
    public Runnable nextState() {
        if (this.value == null) return null;
        if (this.value instanceof Walker) {
            var walker = (Walker<T>) this.value; //todo dirty casting
            Cell<T> target = board.findFirst(walker::isTarget);
            if (target == null) return null; // can't find
            AStarSearch<T> search = new AStarSearch<>(new ManhattanDistance<>());
            List<Cell<T>> path = search.path(this, target, cell -> board.nearCells(cell));
//            List<Cell<T>> path = search.path(this, target, cell -> board.nearCells(cell).stream().filter(c -> c.value == null || c.value instanceof Walkable).toList());
            if (path.isEmpty()) return null;
            // path.get(0) is current element
//            return move(this, path.get(1)); // move on 1 cell
            if (path.size() > 2) {
                return move(this, path.get(1)); // move on 1 cell
            } else {
                Cell<T> destination = path.get(1);
//                return null;
                // eat
                return () -> {
                    if (destination.value == null) return; // acquired already
                    path.get(1).value = null;
                };
            }
        }
        if (this.value instanceof Factory) {
            var factory = (Factory<T>) this.value; //todo dirty casting
            Cell<T> nearestEmptyCell = board.nearCells(this).stream().filter(this::isAvailable).findFirst().orElse(null);
            if (nearestEmptyCell == null) return null;      // no empty cell to create unit
            return () -> {
                if (!isAvailable(nearestEmptyCell)) return; // no empty cell to create unit
                nearestEmptyCell.value = factory.get();
            };
        }
        return null;
    }

    private boolean isAvailable(Cell<T> cell) {
        return cell.value == null || cell.value instanceof Walkable; // todo check access, maybe we should create method to check it's empty
    }

    private Runnable move(Cell<T> source, Cell<T> destination) {
        return () -> {
            if (destination.value != null && !(destination.value instanceof Walkable)) return; // acquired already
            destination.value = source.value;
            source.value = (T) new FootPrint(); // dirty
        };
    }
}
