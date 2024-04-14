package pw.avvero.move;

import pw.avvero.board.Cell;

import java.util.function.BiConsumer;

public class FirstWin extends Flip {
    @Override
    public void accept(Cell<MoveTarget> source, Cell<MoveTarget> destination) {
        if (destination.value != null) return; // acquired already
        super.accept(source, destination);
    }
}
