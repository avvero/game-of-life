package pw.avvero.move;

import pw.avvero.board.Cell;

import java.util.function.BiConsumer;

public class Flip implements BiConsumer<Cell<MoveTarget>, Cell<MoveTarget>> {
    @Override
    public void accept(Cell<MoveTarget> source, Cell<MoveTarget> destination) {
        MoveTarget destinationValue = destination.value;
        destination.value = source.value;
        source.value = destinationValue;
    }
}
