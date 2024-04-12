package pw.avvero.seed;

import pw.avvero.board.Board;
import pw.avvero.board.Cell;

import java.util.function.Supplier;

public interface Seed {

    void initialize(Board board, int is, int ie, int js, int je, Supplier<Cell> factory);

}
