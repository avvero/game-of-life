package pw.avvero.board;

import java.util.List;

public record Neighbour<T>(Cell<T> cell, List<Cell<T>> path) {

    @Override
    public String toString() {
        return "[cell=" + cell + ", path=" + path + ']';
    }

    public int distance() {
        return path.size();
    }
}
