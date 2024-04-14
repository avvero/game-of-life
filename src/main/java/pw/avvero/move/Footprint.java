package pw.avvero.move;

public class Footprint<T> implements Immovable {

    T value;

    public Footprint(T value) {
        this.value = value;
    }
}
