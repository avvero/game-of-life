package pw.avvero.move;

import pw.avvero.board.Cell;

import java.util.function.BiFunction;

public class Flip implements BiFunction<Cell<MoveTarget>, Cell<MoveTarget>, Cell<MoveTarget>> {
    @Override
    public Cell<MoveTarget> apply(Cell<MoveTarget> source, Cell<MoveTarget> target) {
        return null;
    }
}
