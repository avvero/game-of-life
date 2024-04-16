package pw.avvero.board;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiFunction;

public abstract class Cell<T> {

    private final static AtomicInteger ids = new AtomicInteger();
    public final int id = ids.getAndIncrement();
    public T value;
    public int x;
    public int y;

    public Cell(T value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value != null ? value.toString() : String.valueOf(id);
    }

    public abstract Runnable nextState(Integer i, Integer j, BiFunction<Integer, Integer, List<Neighbour<T>>> findNeighbour);
}
