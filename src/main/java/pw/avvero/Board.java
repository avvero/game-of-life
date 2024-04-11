package pw.avvero;

import java.util.ArrayList;
import java.util.List;

public abstract class Board {

    protected int[][] value;

    public Board(int[][] value) {
        this.value = value;
    }

    public int[][] value() {
        return value;
    }

    void update(int[][] value) {
        this.value = value;
    }

    abstract boolean exists(int i, int j);

    public List<int[]> neighbours(int i, int j) {
        List<int[]> result = new ArrayList<>();
        for (int x = -1; x < 2; x++) {
            for (int y = -1; y < 2; y++) {
                if (x == 0 && y == 0) continue;
                if (exists(i + x, j + y)) {
                    result.add(new int[]{i + x, j + y, value[i + x][j + y]});
                }
            }
        }
        return result;
    }
}
