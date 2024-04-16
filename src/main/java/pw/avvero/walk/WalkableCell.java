package pw.avvero.walk;

import pw.avvero.board.AStarSearch;
import pw.avvero.board.Cell;
import pw.avvero.board.Neighbour;

import java.util.List;
import java.util.function.Function;

public class WalkableCell<T> extends Cell<T> {

    private static final AStarSearch SEARCH = new AStarSearch(new AStarSearch.ManhattanDistance());  //todo no casting

    public interface Walker<T> {

        boolean isTarget(Cell<T> cell);

    }

    public interface Walkable {
    }

    public WalkableCell(T value) {
        super(value);
    }

    @Override
    public Runnable nextState() {
        if (this.value == null) return null;
        if (!(this.value instanceof Walker)) return null;
        //
        WalkableCell.Walker<T> walker = (Walker<T>) this.value; //todo dirty casting
        Neighbour<T> target = board.findFirstNeighbour(walker::isTarget);
        if (target == null) return null; // can't find
        if (target.level() > 1) { // is far
            List<Cell<T>> path = SEARCH.path(this, target.cell(), (Function<Cell, List<Cell>>) cell -> board.nearCells(cell));
            return move(this, path.get(1)); // move on 1 cell
        } else {
            return null;
        }
    }

    private Runnable move(Cell<T> source, Cell<T> destination) {
        return () -> {
            if (destination.value != null && !(destination.value instanceof Walkable)) return; // acquired already
            destination.value = source.value;
            source.value = (T) new FootPrint(); // dirty
        };
    }
}
