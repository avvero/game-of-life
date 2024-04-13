package pw.avvero.seed;

import pw.avvero.board.Board;
import pw.avvero.board.Cell;

import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Supplier;

public class RandomSeed implements Seed {
    @Override
    public void initialize(Board board, int is, int ie, int js, int je, Supplier<Cell> factory) {
        for (int i = is; i < ie; i++) {
            for (int j = js; j < je; j++) {
                if (ThreadLocalRandom.current().nextBoolean()) {
                    board.set(i, j, factory);
                } else {
                    board.set(i, j, () -> Cell.zero());
                }
            }
        }
    }
}
