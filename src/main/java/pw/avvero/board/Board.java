package pw.avvero.board;

import pw.avvero.State;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;
import static java.lang.Math.max;

public abstract class Board<T> {

    protected Cell<T>[][] value;
    protected List<Claim> claims;

    private record Claim(Runnable value) {
    }

    public Board(int x, int y) {
        this.value = new Cell[x][y];
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                this.value[i][j] = new Cell<T>();
            }
        }
        this.claims = new ArrayList<>(value.length * value[0].length);
    }

    abstract boolean exists(int i, int j);

    public void nextCycle(State<T> state) {
        for (int i = 0; i < value.length; i++) {
            for (int j = 0; j < value[i].length; j++) {
                List<Neighbour<T>> neighbours = neighbours(i, j);
                Cell<T> cell = value[i][j];
                Runnable claimValue = state.calculate(cell, neighbours);
                if (claimValue != null) {
                    claims.add(new Claim(claimValue));
                }
            }
        }
        for (Claim claim : claims) {
            claim.value.run();
        }
        claims = new ArrayList<>();
    }

    public record Neighbour<T>(int level, Cell<T> cell) {

    }

    public List<Neighbour<T>> neighbours(int i, int j) {
        List<Neighbour<T>> result = new ArrayList<>();
        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                if (x == 0 && y == 0) continue;
                if (exists(i + x, j + y)) {
                    result.add(new Neighbour<>(max(abs(x), abs(y)), get(i + x, j + y)));
                }
            }
        }
        return result;
    }

    public abstract Cell<T> get(int i, int j);
    public Cell<T>[][] value() {
        return value;
    }
}
