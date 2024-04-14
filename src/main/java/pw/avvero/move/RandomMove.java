package pw.avvero.move;

import pw.avvero.State;
import pw.avvero.board.Neighbour;
import pw.avvero.board.Cell;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class RandomMove implements State<RandomMove.MoveTarget> {

    public interface MoveTarget {

    }

    public interface Movable extends MoveTarget {

    }

    public interface Immovable extends MoveTarget {

    }

    @Override
    public Runnable calculate(Cell<MoveTarget> current, List<Neighbour<MoveTarget>> neighbours) {
        if (current.value instanceof Immovable) return null;
        List<Cell<MoveTarget>> fields = neighbours.stream()
                .filter(neighbour -> neighbour.level() == 1 && neighbour.cell().value instanceof Immovable) // TODO
                .map(Neighbour::cell)
                .toList();
        if (fields.isEmpty()) return null; //nowhere to go
        int id = ThreadLocalRandom.current().nextInt(fields.size());
        Cell<MoveTarget> target = fields.get(id);
        return () -> {
            MoveTarget old = target.value;
            target.value = current.value;
            current.value = old;
        };
    }
}
