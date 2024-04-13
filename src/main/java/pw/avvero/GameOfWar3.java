package pw.avvero;

import pw.avvero.board.Board;
import pw.avvero.board.Cell;
import pw.avvero.seed.RoleFactory;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

import static java.util.stream.Collectors.groupingBy;
import static pw.avvero.board.Cell.ZERO;

public class GameOfWar3 implements State {

    @Override
    public Cell calculate(Cell current, List<Board.Neighbour> neighbours) {
        CombatEnvironment combatEnvironment = CombatEnvironment.calculate(current, neighbours);
        if (current.value() != 0) {
            if (combatEnvironment.closeEnemyGroups.isEmpty()) { // no close enemies
                return current;     // current stays
            } else {
                return encounter(current, combatEnvironment);
            }
        } else {
            if (combatEnvironment.closeEnemyGroups.size() >= 2) {
                Cell claim = combatEnvironment.closeEnemyGroups.entrySet().stream().findFirst().get().getValue().get(0).acquire(current); // todo first claim?
                return calculate(claim, neighbours);
            } else if (combatEnvironment.closeEnemyGroups.size() == 1
                    && combatEnvironment.closeEnemyGroups.entrySet().stream().findFirst().get().getValue().size() > 2
                    && combatEnvironment.enemies.size() > 3) {
                Cell claim = combatEnvironment.closeEnemyGroups.entrySet().stream().findFirst().get().getValue().get(0).acquire(current);
                return claim.acquire(current); // todo first acquire?
            } else {
                return current;
            }
        }
    }

    private Cell encounter(Cell current, CombatEnvironment combatEnvironment) {
        int health = current.getRole().health();
        int defence = current.getRole().defence();
        Cell firstEnemy = null;
        for(Cell enemy : combatEnvironment.enemies) {
            health -= calculateDamage(enemy.getRole().strength(), defence, enemy.getRole().critChance(),
                    enemy.getRole().critMultiplier(), enemy.getRole().fireDamage());
            if (firstEnemy == null && health <= 0) {
                firstEnemy = enemy;
            }
        }
        if (health <= 0) {
            return firstEnemy.acquire(current);
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
