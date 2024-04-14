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
    public Runnable nextState(List<Neighbour<MoveTarget>> neighbours) {
        if (this.value == null) return null;

        List<Cell<MoveTarget>> fields = neighbours.stream()
                .filter(neighbour -> neighbour.level() == 1 && neighbour.cell().value == null)
                .map(Neighbour::cell)
                .toList();
        if (fields.isEmpty()) return null; //nowhere to go
        Cell<MoveTarget> destination = fields.get(ThreadLocalRandom.current().nextInt(fields.size()));
        return () -> {
            MoveTarget destinationValue = destination.value;
            destination.value = this.value;
            this.value = destinationValue;
        };
    }

}
