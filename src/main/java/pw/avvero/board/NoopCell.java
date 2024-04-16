package pw.avvero.board;

public class NoopCell<T> extends Cell<T> {
    public NoopCell(T value) {
        super(value);
    }

    @Override
    public Runnable nextState() {
        return null;
    }
}
