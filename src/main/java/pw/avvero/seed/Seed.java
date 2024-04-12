package pw.avvero.seed;

import pw.avvero.board.Board;

public interface Seed {

    void initialize(Board board, int is, int ie, int js, int je, int value);

}
