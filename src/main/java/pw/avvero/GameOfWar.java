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
    public int calculate(int current, List<Board.Cell> neighbours) {
        Map<Integer, Long> grouped = neighbours.stream().collect(groupingBy(cell -> cell.value, counting()));
        int[] top = top(grouped);
        if (current != 0) {
            int team = Math.toIntExact(Optional.ofNullable(grouped.get(current)).orElse(0L));
            grouped.remove(current);
            int[] enemy = top(grouped);
            if (enemy == null) {
               return current;
            } else {
                int enemyValue = enemy[0];
                int enemySize = enemy[1];
                if (enemySize * ThreadLocalRandom.current().nextInt(0, 2) - 1 - team * ThreadLocalRandom.current().nextInt(0, 2) > 0) {
                    return enemyValue;
                } else {
                    return current;
                }
            }
        } else {
            if (neighbours.size() >= 3) {
                if (top[1] >= 2) {
                    return top[0];
                }
            }
        }
        return 0;
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
