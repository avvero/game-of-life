package pw.avvero;

import pw.avvero.board.Board.Neighbour;
import pw.avvero.board.Cell;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CombatEnvironment {

    public List<Cell> fields = new ArrayList<>();
    public List<Cell> closeTeam = new ArrayList<>();
    public List<Neighbour> rangeTeam = new ArrayList<>();
    public Map<Integer, List<Cell>> closeEnemyGroups = new HashMap<>();
    public Map<Integer, List<Neighbour>> rangedEnemyGroups = new HashMap<>();
    public List<Cell> enemies = new ArrayList<>();

    public static CombatEnvironment calculate(Cell current, List<Neighbour> neighbours) {
        CombatEnvironment combatEnvironment = new CombatEnvironment();
        for (Neighbour neighbour : neighbours) {
            if (neighbour.cell().value() == 0) {
                if (neighbour.level() == 1) {
                    combatEnvironment.fields.add(neighbour.cell());
                }
                continue;
            }
            if (neighbour.cell().getRole().range() >= neighbour.level()) {
                if (current != null && current.value() == neighbour.cell().value()) {
                    if (neighbour.level() == 1) {
                        combatEnvironment.closeTeam.add(neighbour.cell());
                    } else if (neighbour.level() > 1) {
                        combatEnvironment.rangeTeam.add(neighbour);
                    } else {
                        throw new UnsupportedOperationException();
                    }
                } else {
                    combatEnvironment.enemies.add(neighbour.cell());
                    if (neighbour.level() == 1) {
                        combatEnvironment.closeEnemyGroups.computeIfAbsent(neighbour.cell().value(), k -> new ArrayList<>())
                                .add(neighbour.cell());
                    } else if (neighbour.level() > 1) {
                        combatEnvironment.rangedEnemyGroups.computeIfAbsent(neighbour.cell().value(), k -> new ArrayList<>())
                                .add(neighbour);
                    } else {
                        throw new UnsupportedOperationException();
                    }
                }
            }
        }
        return combatEnvironment;
    }

}
