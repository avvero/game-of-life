package pw.avvero;

import pw.avvero.board.Cell;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;
import static pw.avvero.board.Cell.ZERO;

public class GameOfWar2 implements State {

    @Override
    public Cell calculate(Cell current, Map<Integer, List<Cell>> neighbours) {
        Map<Integer, List<Cell>> groups = neighbours.get(1).stream()
                .filter(c -> c.value() != 0)
                .collect(groupingBy(Cell::value, toList()));
        if (current.value() != 0) {
            List<Cell> team = Optional.ofNullable(groups.get(current.value())).orElse(List.of());
            groups.remove(current.value());
            if (groups.isEmpty()) { // no enemies
                return current;     // current stays
            } else {
                return encounter(current, team, groups);
            }
        } else {
            if (neighbours.size() >= 2) {
                List<Cell> firstGroup = groups.entrySet().stream().findFirst().get().getValue();
                Cell first = firstGroup.get(0);
                groups.remove(first.value());
                if (!groups.isEmpty()) {
                    return encounter(first, firstGroup, groups);
                } else {
                    return first.acquire(current);
                }
            }
        }
        return ZERO.acquire(current); // empty
    }

    private Cell encounter(Cell current, List<Cell> team, Map<Integer, List<Cell>> enemies) {
        int currentDef = 1 + team.size();
        int currentPow = 1 + team.size();
        int currentDamage = Math.max(1, currentPow * ThreadLocalRandom.current().nextInt(0, 2));
        //
        List<Cell> biggestEnemy = biggest(enemies);
        Cell enemy = biggestEnemy.get(0);
        List<Cell> enemyTeam = biggestEnemy;
        int enemyDef = enemyTeam.size();
        int enemyPow = enemyTeam.size();
        int enemyDamage = Math.max(1, enemyPow * ThreadLocalRandom.current().nextInt(0, 2));

        if (currentDef - enemyDamage < 0 && enemyDef - currentDamage < 0) {
            return ZERO.acquire(current); // full death
        } else if ((currentDef - enemyDamage) > (enemyDef - currentDamage)) {
//            current.hit(enemyDamage / enemyTeam.size());
            return current;
        } else {
//            enemy.hit(currentDamage / (team.size() + 1));
            return enemy.acquire(current);
        }
    }

    private List<Cell> biggest(Map<Integer, List<Cell>> grouped) {
        if (grouped.isEmpty()) return null;
        int biggestSize = 0;
        List<Cell> biggestGroup = List.of();
        for (Map.Entry<Integer, List<Cell>> entry : grouped.entrySet()) {
            if (entry.getValue().size() > biggestSize) {
                biggestGroup = entry.getValue();
                biggestSize = biggestGroup.size();
            }
        }
        return biggestGroup;
    }
}
