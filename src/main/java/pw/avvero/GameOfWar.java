package pw.avvero;

import pw.avvero.board.Cell;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;
import static pw.avvero.board.Cell.ZERO;

public class GameOfWar implements State {

    @Override
    public Cell calculate(Cell current, Map<Integer, List<Cell>> neighbours) {
        Map<Integer, List<Cell>> groups = neighbours.get(1).stream().collect(groupingBy(Cell::value, toList()));
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
                List<Cell> biggest = biggest(groups);
                if (biggest != null && biggest.size() >= 2) { // new
                    return biggest.get(0).acquire(current);
                }
            }
        }
        return ZERO.acquire(current); // empty
    }

    private Cell encounter(Cell current, List<Cell> team, Map<Integer, List<Cell>> enemies) {
        List<Cell> biggestEnemy = biggest(enemies);

        Cell enemy = biggestEnemy.get(0);
        int enemySize = biggestEnemy.size();
        if (enemySize * ThreadLocalRandom.current().nextInt(0, 2) - 1 - team.size() * ThreadLocalRandom.current().nextInt(0, 2) > 0) {
            return enemy.acquire(current);
        } else {
            return current;
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
