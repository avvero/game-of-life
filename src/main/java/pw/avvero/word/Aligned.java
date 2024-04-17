package pw.avvero.word;

import pw.avvero.board.Cell;

import java.util.Objects;
import java.util.function.Predicate;

public interface Aligned {

    static Predicate<Cell<WordObject>> findEnemyAndFight(String selfAllegiance) {
        return cell -> cell.value instanceof Aligned aligned && !Objects.equals(aligned.getAllegiance(), selfAllegiance);
    }

    String getAllegiance();

}
