package pw.avvero.move;

import pw.avvero.board.Cell;
import pw.avvero.board.Neighbour;

import java.util.List;
import java.util.function.BiFunction;

public class FirstAvailableMoveCell extends Cell<MoveTarget> {

    public FirstAvailableMoveCell(MoveTarget value) {
        super(value);
    }

    @Override
    public Runnable nextState(Integer i, Integer j, BiFunction<Integer, Integer, List<Neighbour<MoveTarget>>> findNeighbour) {
        if (this.value == null) return null;

        List<Cell<MoveTarget>> fields = findNeighbour.apply(i, j).stream()
                .filter(neighbour -> neighbour.cell().value == null)
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
