package pw.avvero.board;

import pw.avvero.State;

public class BoardInfinity extends Board {

    public BoardInfinity(int x, int y, State state) {
        super(new Cell[x][y], state);
    }

    @Override
    public boolean exists(int i, int j) {
        if (i < 0) {
            i = value.length + i;
        }
        if (i == value.length) {
            i = 0;
        }
        if (j < 0) {
            j = value[i].length + j;
        }
        if (j == value[i].length) {
            j = 0;
        }
        return value[i][j] != null;
    }
}
