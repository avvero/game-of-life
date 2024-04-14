package pw.avvero.move;

import pw.avvero.board.Cell;
import pw.avvero.board.Neighbour;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class RandomMoveCell extends Cell<RandomMoveCell.MoveTarget> {

    public RandomMoveCell() {
        super(new Field() {
        });
    }

    @Override
    public Runnable nextState(List<Neighbour<MoveTarget>> neighbours) {
        if (this instanceof Immovable) return null;
        List<Cell<MoveTarget>> fields = neighbours.stream()
                .filter(neighbour -> neighbour.level() == 1 && neighbour.cell().value instanceof Field)
                .map(Neighbour::cell)
                .toList();
        if (fields.isEmpty()) return null; //nowhere to go
        int id = ThreadLocalRandom.current().nextInt(fields.size());
        Cell<MoveTarget> target = fields.get(id);
        return () -> {
            MoveTarget old = target.value;
            target.value = this.value;
            this.value = old;
        };
    }

    public interface MoveTarget {

    }

    public interface Movable extends MoveTarget {

    }

    public interface Immovable extends MoveTarget {

    }

    public interface Field extends Immovable {

    }

    public interface Pawn extends Movable {
    }

}
