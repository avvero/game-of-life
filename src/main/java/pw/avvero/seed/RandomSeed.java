package pw.avvero.seed;

import pw.avvero.board.Board;

import java.util.concurrent.ThreadLocalRandom;

public class RandomSeed implements Seed {
    @Override
    public void initialize(Board board, int is, int ie, int js, int je, int value) {
        for (int i = is; i < ie; i++) {
            for (int j = js; j < je; j++) {
                if (ThreadLocalRandom.current().nextBoolean()) {
                    board.value()[i][j] = new Board.Cell(i, j, value);
                }
            }
        }
    }
}
