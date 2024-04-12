package pw.avvero.board;

import pw.avvero.Game;

public class BoardBordered extends Board {

    public BoardBordered(int x, int y, Game game) {
        super(new int[x][y], game);
    }

    @Override
    public boolean exists(int i, int j) {
        if (i < 0 || i == value.length || j < 0 || j == value[i].length) return false;
        return value[i][j] != 0;
    }
}
