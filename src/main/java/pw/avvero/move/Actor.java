package pw.avvero.move;

import java.util.function.Predicate;

public class Actor<T> implements WalkableCell.Walker<T> {

    public final int id;
    private Predicate<T> target;

    public Actor(int id, Predicate<T> target) {
        this.id = id;
        this.target = target;
    }

    @Override
    public boolean isTarget(T candidate) {
        if (candidate == null && target == null) return true;
        if (candidate == null) return false;
        return target.test(candidate);
    }
}
