package pw.avvero.move;

import pw.avvero.board.Cell;
import pw.avvero.board.Neighbour;

import java.util.List;

public class FirstAvailableEnemyMoveCell extends Cell<MoveTarget> {

    public FirstAvailableEnemyMoveCell(MoveTarget value) {
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
        Cell<MoveTarget> destination = fields.get(0);
        return () -> {
            MoveTarget destinationValue = destination.value;
            destination.value = this.value;
            this.value = destinationValue;
        };
    }

}
