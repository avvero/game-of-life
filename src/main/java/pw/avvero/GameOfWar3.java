package pw.avvero;

import pw.avvero.board.Cell;
import pw.avvero.seed.RoleFactory;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;
import static pw.avvero.board.Cell.ZERO;

public class GameOfWar3 implements State {

    @Override
    public Cell calculate(Cell current, Map<Integer, List<Cell>> neighbours) {
        Map<Integer, List<Cell>> groups = Optional.ofNullable(neighbours.get(1)).orElse(List.of())
                .stream().collect(groupingBy(Cell::value, toList()));
        if (current.value() != 0) {
            List<Cell> team = Optional.ofNullable(groups.get(current.value())).orElse(List.of());
            groups.remove(current.value());
            if (groups.isEmpty()) { // no enemies
                return current;     // current stays
            } else {
                return encounter(current, team, groups);
            }
        } else {
            if (Optional.ofNullable(neighbours.get(1)).orElse(List.of()).size() >= 2) {
                List<Cell> biggest = biggest(groups);
                if (biggest != null && biggest.size() >= 2) { // new
//                    return biggest.get(0).acquire(current);
                    return Cell.of(biggest.get(0).value(), RoleFactory.get());
                }
            }
        }
        return ZERO.acquire(current); // empty
    }

    private Cell encounter(Cell current, List<Cell> team, Map<Integer, List<Cell>> enemiesGroups) {
        int health = current.getRole().health();
        int defence = current.getRole().defence();
        Cell firstEnemy = null;
        for (List<Cell> enemies : enemiesGroups.values()) {
            for(Cell enemy : enemies) {
                firstEnemy = firstEnemy != null ? firstEnemy : enemy;
                int enemyMight = ThreadLocalRandom.current().nextInt(0, enemy.getRole().strength());
                health -= calculateDamage(enemy.getRole().strength(), defence, 0.3, 10, 0);
            }
        }
        if (health <= 0) {
            return ZERO.acquire(current);
        } else {
            return current;
        }
    }

    private int calculateDamage(int attack, int defense, double critChance, double critMultiplier, int fireDamage) {
        int baseDamage = Math.max(0, attack - defense);
        if (ThreadLocalRandom.current().nextDouble() < critChance) {
            baseDamage *= critMultiplier;
        }
        baseDamage += fireDamage; // Пример добавления специального урона от оружия
        return baseDamage;
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
