package pw.avvero.walk;

import pw.avvero.board.Cell;
import pw.avvero.board.Neighbour;

import java.util.List;
import java.util.function.BiFunction;

public class WalkableCell<T> extends Cell<T> {

    public interface Walker<T> {

        boolean isTarget(T t);

    }

    public interface Walkable {}

    public WalkableCell(T value) {
        super(value);
    }

    @Override
    public Runnable nextState(Integer i, Integer j, BiFunction<Integer, Integer, List<Neighbour<T>>> findNeighbour) {
        if (this.value == null) return null;
        if (this.value instanceof WalkableCell.Walker<?> walker) {
            Neighbour<T> neighbour = find(findNeighbour.apply(i, j), (Walker<T>) walker); //todo dirty casting
            if (neighbour == null) return null; // can't find
            if (neighbour.level() > 1) { // is near
                return move(this, neighbour.path().get(0)); // move on 1 cell
            } else {
                return null;
            }
        }
        return null;
    }

    private Runnable move(Cell<T> source, Cell<T> destination) {
        return () -> {
            if (destination.value != null) return; // acquired already
            destination.value = source.value;
            source.value = (T) new FootPrint(); // dirty
        };
    }

    private Neighbour<T> find(List<Neighbour<T>> neighbours, Walker<T> walker) {
        for (Neighbour<T> neighbour : neighbours) {
            if (neighbour.cell().value != null && walker.isTarget(neighbour.cell().value)) {
                return neighbour;
            }
        }
        return null;
    }
}
