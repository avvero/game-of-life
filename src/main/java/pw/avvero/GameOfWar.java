package pw.avvero;

import pw.avvero.board.Board;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

public class GameOfWar implements State {

    @Override
    public Board.Cell calculate(Board.Cell current, List<Board.Cell> neighbours) {
        Map<Integer, List<Board.Cell>> groups = neighbours.stream().collect(groupingBy(cell -> cell.value, toList()));
        if (current.value != 0) {
            List<Board.Cell> team = Optional.ofNullable(groups.get(current.value)).orElse(List.of());
            groups.remove(current.value);
            if (groups.isEmpty()) { // no enemies
                return new Board.Cell(current.i, current.j, current.value);     // current stays
            } else {
                return encounter(current, team, groups);
            }
        } else {
            if (neighbours.size() >= 2) {
                List<Board.Cell> biggest = biggest(groups);
                if (biggest != null && biggest.size() >= 2) {
                    return new Board.Cell(current.i, current.j, biggest.get(0).value);
                }
            }
        }
        return new Board.Cell(current.i, current.j, 0);
    }

    private Board.Cell encounter(Board.Cell current, List<Board.Cell> team, Map<Integer, List<Board.Cell>> enemies) {
        List<Board.Cell> biggestEnemy = biggest(enemies);

        int enemyValue = biggestEnemy.get(0).value;
        int enemySize = biggestEnemy.size();
        if (enemySize * ThreadLocalRandom.current().nextInt(0, 2) - 1 - team.size() * ThreadLocalRandom.current().nextInt(0, 2) > 0) {
            return new Board.Cell(current.i, current.j, enemyValue);
        } else {
            return new Board.Cell(current.i, current.j, current.value);
        }
    }

    private List<Board.Cell> biggest(Map<Integer, List<Board.Cell>> grouped) {
        if (grouped.isEmpty()) return null;
        int biggestSize = 0;
        List<Board.Cell> biggestGroup = List.of();
        for (Map.Entry<Integer, List<Board.Cell>> entry : grouped.entrySet()) {
            if (entry.getValue().size() > biggestSize) {
                biggestGroup = entry.getValue();
                biggestSize = biggestGroup.size();
            }
        }
        return biggestGroup;
    }
}
