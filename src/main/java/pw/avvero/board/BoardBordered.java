package pw.avvero.board;

import pw.avvero.State;

public class BoardBordered extends Board {

    public BoardBordered(int x, int y, State state) {
        super(new Cell[x][y], state);
    }

    @Override
    public boolean exists(int i, int j) {
        if (i < 0 || i == value.length || j < 0 || j == value[i].length) return false;
        return value[i][j] != null;
    }
}
