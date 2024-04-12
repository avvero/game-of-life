package pw.avvero.board;

import pw.avvero.State;

import java.util.ArrayList;
import java.util.List;

public abstract class Board {

    protected int[][] value;
    protected State state;

    public Board(int[][] value, State state) {
        this.value = value;
        this.state = state;
    }

    public int[][] value() {
        return value;
    }

    public void update(int[][] value) {
        this.value = value;
    }

    abstract boolean exists(int i, int j);

    public void nextCycle() {
        int[][] next = new int[value.length][value[0].length];
        for (int i = 0; i < value.length; i++) {
            for (int j = 0; j < value[i].length; j++) {
                List<Cell> neighbours = neighbours(i, j);
                next[i][j] = state.calculate(value[i][j], neighbours);
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
                    result.add(new Cell(i + x, j + y, value[i + x][j + y]));
                }
            }
        }
        return result;
    }

    public static class Cell {
        public int i;
        public int j;
        public int value;

        public Cell(int i, int j, int value) {
            this.i = i;
            this.j = j;
            this.value = value;
        }
    }
}
