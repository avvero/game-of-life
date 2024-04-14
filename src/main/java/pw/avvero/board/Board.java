package pw.avvero.board;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public abstract class Board<T> {

    protected int x;
    protected int y;
    protected Cell<T>[][] value;
    protected List<Claim> claims;

    private record Claim(Runnable value) {
    }

    public Board(int x, int y, Supplier<Cell<T>> factory) {
        this.x = x;
        this.y = y;
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
                Cell<T> cell = value[i][j];
                Runnable claimValue = cell.nextState(i, j, this::neighbours);
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
        //
        boolean[][] visited = new boolean[x][y];
        LinkedList<Object[]> tovisit = new LinkedList<>();
        tovisit.add(new Object[]{0, i, j, new ArrayList<>()});
        while (!tovisit.isEmpty()) {
            Object[] target = tovisit.removeFirst();
            int level = (Integer) target[0], ti = (Integer) target[1], tj = (Integer) target[2];
            List<Cell<T>> path = (List<Cell<T>>) target[3];
            //
            if (visited[ti][tj]) continue;
            visited[ti][tj] = true;
            if (level > 0) {
                result.add(new Neighbour<>(level, get(ti, tj), path));
            }
            //
            for (int x = -1; x <= 1; x++) {
                for (int y = -1; y <= 1; y++) {
                    if (x == 0 && y == 0) continue; // current
                    int ni = ti + x, nj = tj + y;
                    if (exists(ni, nj) && !visited[ni][nj]) {
                        ArrayList<Cell<T>> subpath = new ArrayList<>(path);
                        subpath.add(get(ni, nj));
                        tovisit.add(new Object[]{level + 1, ni, nj, subpath});
                    }
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
