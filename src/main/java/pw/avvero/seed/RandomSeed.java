package pw.avvero.seed;

import java.util.concurrent.ThreadLocalRandom;

public class RandomSeed implements Seed {
    @Override
    public void initialize(int[][] board, int is, int ie, int js, int je, int value) {
        for (int i = is; i < ie; i++) {
            for (int j = js; j < je; j++) {
                board[i][j] = ThreadLocalRandom.current().nextBoolean() ? value : 0;
            }
        }
    }
}
