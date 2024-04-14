package pw.avvero.board;

import java.util.List;
import java.util.function.BiFunction;

public class NoopCell<T> extends Cell<T> {
    public NoopCell(T value) {
        super(value);
    }

    @Override
    public Runnable nextState(Integer i, Integer j, BiFunction<Integer, Integer, List<Neighbour<T>>> findNeighbour) {
        return null;
    }
}
