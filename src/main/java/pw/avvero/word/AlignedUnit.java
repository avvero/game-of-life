package pw.avvero.word;

import pw.avvero.board.Cell;

import java.util.Objects;
import java.util.function.Predicate;

public class AlignedUnit extends Unit {

    public static Predicate<Cell<WordObject>> findEnemyAndFight(String selfAllegiance) {
        return cell -> cell.value instanceof AlignedUnit alignedUnit && !Objects.equals(alignedUnit.allegiance, selfAllegiance);
    }

    protected final String allegiance;

    public String getAllegiance() {
        return allegiance;
    }

    public AlignedUnit(String allegiance, Predicate<Cell<WordObject>> order) {
        super(order);
        this.allegiance = allegiance;
    }
}
