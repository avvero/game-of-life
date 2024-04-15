package pw.avvero.move;

public class Actor<T> implements TraversalSpaceCell.Traversal<T> {

    public final int id;
    private T target;

    public Actor(int id, T target) {
        this.id = id;
        this.target = target;
    }

    @Override
    public boolean isTarget(T candidate) {
        if (candidate == null && target == null) return true;
        if (candidate == null) return false;
        return candidate.equals(target);
    }
}
