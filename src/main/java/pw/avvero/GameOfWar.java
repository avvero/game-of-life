package pw.avvero;

import pw.avvero.board.Board;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

public class GameOfWar implements State {

    @Override
    public Board.Cell calculate(int i, int j, Board.Cell current, List<Board.Cell> neighbours) {
        Map<Integer, Long> grouped = neighbours.stream().collect(groupingBy(cell -> cell.value, counting()));
        int[] top = top(grouped);
        if (current != null) {
            int team = Math.toIntExact(Optional.ofNullable(grouped.get(current.value)).orElse(0L));
            grouped.remove(current.value);
            int[] enemy = top(grouped);
            if (enemy == null) {
               return current;
            } else {
                int enemyValue = enemy[0];
                int enemySize = enemy[1];
                if (enemySize * ThreadLocalRandom.current().nextInt(0, 2) - 1 - team * ThreadLocalRandom.current().nextInt(0, 2) > 0) {
                    return new Board.Cell(i, j, enemyValue);
                } else {
                    return new Board.Cell(i, j, current.value);
                }
            }
        } else {
            if (neighbours.size() >= 3) {
                if (top[1] >= 2) {
                    return new Board.Cell(i, j, top[0]);
                }
            }
        }
        return null;
    }

    private int[] top(Map<Integer, Long> grouped) {
        if (grouped.isEmpty()) return null;
        int v = 0;
        int c = 0;
        for (Map.Entry<Integer, Long> entry : grouped.entrySet()) {
            if (entry.getValue() > c) {
                v = entry.getKey();
                c = Math.toIntExact(entry.getValue());
            }
        }
        return new int[]{v, c};
    }
}
