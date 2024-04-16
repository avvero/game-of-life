package pw.avvero.unit;

import pw.avvero.board.Cell;

import java.util.function.Supplier;

public class FactoryCell<T> extends Cell<T> {

    private final Supplier<T> factory;

    public FactoryCell(T value, Supplier<T> factory) {
        super(value);
        this.factory = factory;
    }

    @Override
    public Runnable nextState() {
        Cell<T> nearestEmptyCell = board.nearCells(this).stream().filter(this::isAvailable).findFirst().orElse(null);
        if (nearestEmptyCell == null) return null;      // no empty cell to create unit
        return () -> {
            if (!isAvailable(nearestEmptyCell)) return; // no empty cell to create unit
            nearestEmptyCell.value = factory.get();
        };
    }

    private boolean isAvailable(Cell<T> cell) {
        return cell.value == null; // todo check access, maybe we should create method to check it's empty
    }
}
