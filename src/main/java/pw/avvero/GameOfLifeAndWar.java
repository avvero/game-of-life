package pw.avvero;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

public class GameOfLifeAndWar {

    private final Board board;

    public GameOfLifeAndWar(Board board) {
        this.board = board;
//        initializeRandom(board.value(),
//                0, board.value().length / 3,
//                0, board.value()[0].length / 3,
//                1);
        initializeRandom(board.value(),
                board.value().length / 3 * 2, board.value().length,
                board.value()[0].length / 3 * 2, board.value()[0].length,
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
                if (board.value()[i][j] != 0) {
                    int v = board.value()[i][j];
                    if (n.size() < 2) {
                        next[i][j] = 0;
                    } else if (n.size() <= 10) {
                        next[i][j] = v;
                    } else {
                        next[i][j] = 0;
                    }
                } else {
                    if (n.size() == 3) {
                        Map<Integer, Long> grouped = n.stream().collect(groupingBy(triplet -> triplet[2], counting()));
                        Integer v = 0;
                        Long c = 0L;
                        for (Map.Entry<Integer, Long> entry : grouped.entrySet()) {
                            if (entry.getValue() > c) {
                                v = entry.getKey();
                                c = entry.getValue();
                            }
                        }
                        if (c >= 3) {
                            next[i][j] = v;
                        }
                    }
                }
            }
        }
        return next;
    }

    private void initializeRandom(int[][] board, int is, int ie, int js, int je, int value) {
        for (int i = is; i < ie; i++) {
            for (int j = js; j < je; j++) {
                board[i][j] = ThreadLocalRandom.current().nextBoolean() ? value : 0;
            }
        }
    }
}
