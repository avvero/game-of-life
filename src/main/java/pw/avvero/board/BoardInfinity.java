package pw.avvero.board;

import pw.avvero.Game;

public class BoardInfinity extends Board {

    public BoardInfinity(int x, int y, Game game) {
        super(new int[x][y], game);
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
        return value[i][j] != 0;
    }
}
