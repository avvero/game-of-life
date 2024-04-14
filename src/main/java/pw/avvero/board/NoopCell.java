package pw.avvero.board;

import java.util.List;

public class NoopCell<T> extends Cell<T> {
    public NoopCell(T value) {
        super(value);
    }

    @Override
    public Runnable nextState(List<Neighbour<T>> neighbours) {
        return null;
    }
}
