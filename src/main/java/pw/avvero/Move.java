package pw.avvero;

import pw.avvero.board.Board;
import pw.avvero.board.Cell;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class Move implements State {
    @Override
    public Cell calculate(Cell current, List<Board.Neighbour> neighbours, Map<String, LinkedList<Cell>> claims, Map<String, LinkedList<Cell>> nextClaims) {
        if (current.value() == 0) {
            LinkedList<Cell> claim = claims.get(current.toString());
            if (claim != null && !claim.isEmpty()) {
                return claim.removeFirst().acquire(current);
            } else {
                return current;
            }
        }
        CombatEnvironment combatEnvironment = CombatEnvironment.calculate(current, neighbours);
        if (!combatEnvironment.fields.isEmpty()) {
            int id = ThreadLocalRandom.current().nextInt(combatEnvironment.fields.size());
            Cell field = combatEnvironment.fields.get(id);
            nextClaims.computeIfAbsent(field.toString(), k -> new LinkedList<>()).add(current);
            return Cell.zero().acquire(current); // leaves
        } else {
            return current;
        }
    }
}
