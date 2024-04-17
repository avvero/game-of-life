package pw.avvero.board;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

public abstract class Board<T> {

    protected int x;
    protected int y;
    protected Cell<T>[][] value;
    protected List<Claim> claims;
    private final Neighborhood<T> neighborhood;

    private record Claim(Runnable value) {
    }

    public Board(int x, int y, Neighborhood<T> neighborhood, Supplier<Cell<T>> factory) {
        this.x = x;
        this.y = y;
        this.value = new Cell[x][y];
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                Cell<T> cell = factory.get();
                cell.x = i;        // TODO
                cell.y = j;        // TODO
                cell.board = this; // TODO
                this.value[i][j] = cell;
            }
        }
        this.claims = new ArrayList<>(value.length * value[0].length);
        this.neighborhood = neighborhood;
    }

    public Board(int x, int y, Supplier<Cell<T>> factory) {
        this(x, y, new MoorNeighborhood<>(), factory);
    }

    abstract boolean exists(int i, int j);

    public void nextCycle() {
        for (int i = 0; i < value.length; i++) {
            for (int j = 0; j < value[i].length; j++) {
                Cell<T> cell = value[i][j];
                Runnable claimValue = cell.nextState();
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

    /**
     * BFS search
     *
     * @param i
     * @param j
     * @return
     */
    public List<Neighbour<T>> neighbours(int i, int j) {
        List<Neighbour<T>> result = new ArrayList<>();
        //
        boolean[][] visited = new boolean[value.length][value[0].length];
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
                result.add(new Neighbour<>(get(ti, tj), path));
            }
            for (int[] next : neighborhood.neighbours(this, ti, tj)) {
                int ni = next[0], nj = next[1];
                if (!visited[ni][nj]) {
                    ArrayList<Cell<T>> subpath = new ArrayList<>(path);
                    subpath.add(get(ni, nj));
                    tovisit.add(new Object[]{level + 1, ni, nj, subpath});
                }
            }
        }
        return result;
    }

    public List<Cell<T>> nearCells(Cell<T> cell) {
        List<Cell<T>> result = new ArrayList<>();
        for (int[] next : neighborhood.neighbours(this, cell.x, cell.y)) {
            result.add(get(next[0], next[1]));
        }
        return result;
    }

    public Neighbour<T> findFirstNeighbour(Predicate<Cell<T>> predicate) {
        Cell<T> start = get(0, 0);
        Neighbour<T> result = predicate.test(start) ? new Neighbour<>(start, List.of()) : null;
        for (Neighbour<T> neighbour : neighbours(0, 0)) {
            if (predicate.test(neighbour.cell())) {
                if (result == null || result.distance() > neighbour.distance()) {
                    result = neighbour;
                }
            }
        }
        return result;
    }

    public Cell<T> findFirst(Predicate<Cell<T>> predicate) {
        Neighbour<T> neighbour = findFirstNeighbour(predicate);
        return neighbour != null ? neighbour.cell() : null;
    }

    public abstract Cell<T> get(int i, int j);

    public void update(int i, int j, Consumer<Cell<T>> consumer) {
        consumer.accept(value[i][j]);
    }

    public Cell<T>[][] value() {
        return value;
    }
}
