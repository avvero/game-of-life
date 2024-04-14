package pw.avvero.board;

import java.util.List;

public abstract class Cell<T> {

    public T value;

    public Cell(T value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value.toString();
    }

    public abstract Runnable nextState(List<Neighbour<T>> neighbours);
}
