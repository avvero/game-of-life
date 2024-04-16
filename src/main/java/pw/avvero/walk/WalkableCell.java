package pw.avvero.walk;

import pw.avvero.board.AStarSearch;
import pw.avvero.board.Cell;
import pw.avvero.board.Neighbour;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

public class WalkableCell<T> extends Cell<T> {

    private static final AStarSearch SEARCH = new AStarSearch(new AStarSearch.ManhattanDistance());  //todo no casting

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
            List<Cell<T>> path = SEARCH.path(this, target, (Function<Cell, List<Cell>>) cell -> board.nearCells(cell));
            return move(this, path.get(1)); // move on 1 cell
        }
        if (this.value instanceof Factory) {
            var factory =  (Factory<T>) this.value; //todo dirty casting
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
        return cell.value == null; // todo check access, maybe we should create method to check it's empty
    }

    private Runnable move(Cell<T> source, Cell<T> destination) {
        return () -> {
            if (destination.value != null && !(destination.value instanceof Walkable)) return; // acquired already
            destination.value = source.value;
            source.value = (T) new FootPrint(); // dirty
        };
    }
}
