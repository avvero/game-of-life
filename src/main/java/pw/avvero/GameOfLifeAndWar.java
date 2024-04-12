package pw.avvero;

import pw.avvero.board.Board;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

public class GameOfLifeAndWar implements Game{

    @Override
    public int calculate(int current, List<int[]> neighbours) {
        Map<Integer, Long> grouped = neighbours.stream().collect(groupingBy(triplet -> triplet[2], counting()));
        int[] top = top(grouped);
        if (current != 0) {
            if (neighbours.size() < 2) {
                return 0;
            } else if (top[1] <= 3) {
                return top[0];
            }
        } else {
            if (neighbours.size() == 3) {
                if (top[1] >= 2) {
                    return top[0];
                }
            }
        }
        return 0;
    }

    private int[] top(Map<Integer, Long> grouped) {
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
