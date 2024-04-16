package pw.avvero.walk;

import pw.avvero.board.Cell;
import pw.avvero.board.Neighbour;

import java.util.List;
import java.util.function.BiFunction;

public class WalkableCell<T> extends Cell<T> {

    public interface Walker<T> {

        boolean isTarget(T t);

    }

    public interface Walkable {
    }

    public WalkableCell(T value) {
        super(value);
    }

    @Override
    public Runnable nextState(Integer i, Integer j, BiFunction<Integer, Integer, List<Neighbour<T>>> findNeighbour) {
        if (this.value == null) return null;
        if (!(this.value instanceof Walker)) return null;
        //
        WalkableCell.Walker<T> walker = (Walker<T>) this.value; //todo dirty casting
        Neighbour<T> neighbour = find(findNeighbour.apply(i, j), walker);
        if (neighbour == null) return null; // can't find
        if (neighbour.level() > 1) { // is far
            List<Cell<T>> path = new AStarSearch<T>(new AStarSearch.ManhattanDistance<T>()).path(this, neighbour.cell(),
                    (i1, j1) -> findNeighbour.apply(i1, j1).stream().filter(n -> n.level() == 1).map(Neighbour::cell).toList());

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

    private Neighbour<T> find(List<Neighbour<T>> neighbours, Walker<T> walker) {
        for (Neighbour<T> neighbour : neighbours) {
            if (neighbour.cell().value != null && walker.isTarget(neighbour.cell().value)) {
                return neighbour;
            }
        }
        return null;
    }
}
