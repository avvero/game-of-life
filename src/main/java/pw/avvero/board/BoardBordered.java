package pw.avvero.board;

import java.util.function.Supplier;

public class BoardBordered<T> extends Board<T> {

    public BoardBordered(int x, int y, Supplier<Cell<T>> factory) {
        super(x, y, factory);
    }

    @Override
    public boolean exists(int i, int j) {
        if (i < 0 || i >= value.length || j < 0 || j >= value[i].length) return false;
        return true;
    }

    @Override
    public Cell<T> get(int i, int j) {
        return value[i][j];
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < value.length; i++) {
            for (int j = 0; j < value[i].length; j++) {
                Cell<T> cell = value[i][j];
                sb.append(cell.toString());
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
