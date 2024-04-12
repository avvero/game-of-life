package pw.avvero;

import pw.avvero.board.Board;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

public class GameOfLifeAndWar {

    private final Board board;

    public GameOfLifeAndWar(Board board) {
        this.board = board;
        initializeRandom(board.value(),
                0, board.value().length / 2,
                0, board.value()[0].length / 2,
                1);
        initializeRandom(board.value(),
                board.value().length / 2, board.value().length,
                board.value()[0].length / 2, board.value()[0].length,
                -1);
    }

    public void newCycle() {
        this.board.update(nextGeneration());
    }

    private int[][] nextGeneration() {
        int[][] next = new int[board.value().length][board.value()[0].length];
        for (int i = 0; i < board.value().length; i++) {
            for (int j = 0; j < board.value()[i].length; j++) {
                List<int[]> n = board.neighbours(i, j);
                Map<Integer, Long> grouped = n.stream().collect(groupingBy(triplet -> triplet[2], counting()));
                int[] top = top(grouped);
                if (board.value()[i][j] != 0) {
                    int v = board.value()[i][j];
                    if (n.size() < 2) {
                        next[i][j] = 0;
                    } else if (top[1] <= 3) {
                        next[i][j] = top[0];
                    }
                } else {
                    if (n.size() == 3) {
                        if (top[1] >= 2) {
                            next[i][j] = top[0];
                        }
                    }
                }
            }
        }
        return next;
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

    private void initializeRandom(int[][] board, int is, int ie, int js, int je, int value) {
        for (int i = is; i < ie; i++) {
            for (int j = js; j < je; j++) {
                board[i][j] = ThreadLocalRandom.current().nextBoolean() ? value : 0;
            }
        }
    }
}
