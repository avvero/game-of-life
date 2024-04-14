package pw.avvero.board;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static java.lang.Math.abs;
import static java.lang.Math.max;

public abstract class Board<T> {

    protected Cell<T>[][] value;
    protected List<Claim> claims;

    private record Claim(Runnable value) {
    }

    public Board(int x, int y, Supplier<Cell<T>> factory) {
        this.value = new Cell[x][y];
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                this.value[i][j] = factory.get();
            }
        }
        this.claims = new ArrayList<>(value.length * value[0].length);
    }

    abstract boolean exists(int i, int j);

    public void nextCycle() {
        for (int i = 0; i < value.length; i++) {
            for (int j = 0; j < value[i].length; j++) {
                List<Neighbour<T>> neighbours = neighbours(i, j);
                Cell<T> cell = value[i][j];
                Runnable claimValue = cell.nextState(neighbours);
                if (claimValue != null) {
                    claims.add(new Claim(claimValue));
                }
            }
        }
        for (Claim claim : claims) {
            claim.value.run();
            // There is no conflict solving, so flip is possible. But we should consider it as a blink mechanic!
            // 0 1 0 2 0
            // 0 2 1 0 0
        }
        claims = new ArrayList<>();
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

    public void update(int i, int j, Consumer<Cell<T>> consumer) {
        consumer.accept(value[i][j]);
    }

    public Cell<T>[][] value() {
        return value;
    }
}
