package pw.avvero.board;

import java.util.concurrent.atomic.AtomicInteger;

public abstract class Cell<T> {

    private final static AtomicInteger ids = new AtomicInteger();
    public final int id = ids.getAndIncrement();
    public T value;
    protected Board<T> board;
    int x;
    int y;

    public Cell(T value) {
        this.value = value;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        return value != null ? value.toString() : String.valueOf(id);
    }

    public abstract Runnable nextState();
}
