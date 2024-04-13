package pw.avvero.board;

public class Cell<T> {

    public T value;

    @Override
    public String toString() {
        return value.toString();
    }
}
