package pw.avvero.seed;

import pw.avvero.board.Board;
import pw.avvero.board.Cell;

public interface Seed {

    void initialize(Board board, int is, int ie, int js, int je, Cell cell);

}
