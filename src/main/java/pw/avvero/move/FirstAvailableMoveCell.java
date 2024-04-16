package pw.avvero.move;

import pw.avvero.board.Cell;
import pw.avvero.board.Neighbour;

import java.util.List;

public class FirstAvailableMoveCell extends Cell<MoveTarget> {

    public FirstAvailableMoveCell(MoveTarget value) {
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
        return () -> new FirstWin().accept(this, fields.getFirst());
    }
}
