package pw.avvero.move;

import pw.avvero.board.Cell;
import pw.avvero.board.Neighbour;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class RandomMoveCell extends Cell<MoveTarget> {

    public RandomMoveCell(MoveTarget value) {
        super(value);
    }

    @Override
    public Runnable nextState() {
        if (this.value == null) return null;

        List<Cell<MoveTarget>> fields = board.neighbours(this).stream()
                .filter(neighbour -> neighbour.level() == 1 && neighbour.cell().value == null)
                .map(Neighbour::cell)
                .toList();
        if (fields.isEmpty()) return null; //nowhere to go
        return () -> new Flip().accept(this, fields.get(ThreadLocalRandom.current().nextInt(fields.size())));
    }

}
