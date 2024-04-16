package pw.avvero.walk;

import pw.avvero.board.Cell;

import java.util.function.Predicate;

public class Hound<T> implements WalkableCell.Walker<T> {

    public final int id;
    private final Predicate<T> target;

    public Hound(int id, Predicate<T> target) {
        this.id = id;
        this.target = target;
    }

    @Override
    public boolean isTarget(Cell<T> cell) {
        T candidate = cell.value;
        if (candidate == null && target == null) return true;
        if (candidate == null) return false;
        return target.test(candidate);
    }
}
