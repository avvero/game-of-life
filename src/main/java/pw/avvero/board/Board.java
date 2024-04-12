package pw.avvero.board;

import pw.avvero.State;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import static java.lang.Math.abs;
import static java.lang.Math.max;

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
                List<Neighbour> neighbours = neighbours(i, j);
                next[i][j] = state.calculate(value[i][j], neighbours).nextCycle();
            }
        }
        value = next;
    }

    public record Neighbour(int level, Cell cell){

    }

    public List<Neighbour> neighbours(int i, int j) {
        List<Neighbour> result = new ArrayList<>();
        for (int x = -2; x < 3; x++) {
            for (int y = -2; y < 3; y++) {
                if (x == 0 && y == 0) continue;
                if (exists(i + x, j + y)) {
                    result.add(new Neighbour(max(abs(x), abs(y)), get(i + x, j + y)));
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
