package pw.avvero.board;

import pw.avvero.State;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public abstract class Board {

    protected Cell[][] value;
    protected State state;

    public Board(Cell[][] value, State state) {
        this.value = value;
        this.state = state;
    }

    public Cell[][] value() {
        return value;
    }

    abstract boolean exists(int i, int j);

    public void nextCycle() {
        Cell[][] next = new Cell[value.length][value[0].length];
        for (int i = 0; i < value.length; i++) {
            for (int j = 0; j < value[i].length; j++) {
                List<Cell> neighbours = neighbours(i, j);
                next[i][j] = state.calculate(value[i][j], neighbours).nextCycle();
            }
        }
        value = next;
    }

    public List<Cell> neighbours(int i, int j) {
        List<Cell> result = new ArrayList<>();
        for (int x = -1; x < 2; x++) {
            for (int y = -1; y < 2; y++) {
                if (x == 0 && y == 0) continue;
                if (exists(i + x, j + y)) {
                    result.add(get(i + x, j + y));
                }
            }
        }
        return result;
    }

    abstract Cell get(int i, int j);

    public void set(int i, int j, Supplier<Cell> factory) {
        value[i][j] = factory.get().acquire(i, j);
    }
}
