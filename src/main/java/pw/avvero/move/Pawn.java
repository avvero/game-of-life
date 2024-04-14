package pw.avvero.move;

public class Pawn implements Movable {

    public final Integer id;

    public Pawn(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Pawn{"+ id +'}';
    }
}
