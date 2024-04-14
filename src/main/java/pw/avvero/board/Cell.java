package pw.avvero.board;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class Cell<T> {

    private final static AtomicInteger ids = new AtomicInteger();
    public final int id = ids.getAndIncrement();
    public T value;

    public Cell(T value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value != null ? value.toString() : null;
    }

    public abstract Runnable nextState(List<Neighbour<T>> neighbours);
}
