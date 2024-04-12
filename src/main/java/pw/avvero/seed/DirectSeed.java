package pw.avvero.seed;

import pw.avvero.board.Board;
import pw.avvero.board.Cell;

public class DirectSeed implements Seed {
    @Override
    public void initialize(Board board, int is, int ie, int js, int je, Cell cell) {
        for (int i = is; i < ie; i++) {
            for (int j = js; j < je; j++) {
                board.set(i, j, cell);
            }
        }
    }
}
