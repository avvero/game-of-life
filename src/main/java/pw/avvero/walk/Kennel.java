package pw.avvero.walk;

import java.util.Arrays;
import java.util.LinkedList;

public class Kennel<T> implements WalkableCell.Factory<Hound<T>> {

    private final LinkedList<Hound<T>> hounds = new LinkedList<>();

    public Kennel(Hound<T>... hounds) {
        this.hounds.addAll(Arrays.asList(hounds));
    }

    @Override
    public Hound<T> get() {
        return !hounds.isEmpty() ? hounds.removeFirst() : null;
    }
}
