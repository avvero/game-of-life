package pw.avvero.board;

import pw.avvero.Game;

import java.util.ArrayList;
import java.util.List;

public abstract class Board {

    protected int[][] value;
    protected Game game;

    public Board(int[][] value, Game game) {
        this.value = value;
        this.game = game;
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
                List<int[]> neighbours = neighbours(i, j);
                next[i][j] = game.calculate(value[i][j], neighbours);
            }
        }
        value = next;
    }

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
