package pw.avvero.board;

public class BoardBordered extends Board {

    public BoardBordered(int x, int y) {
        super(new int[x][y]);
    }

    @Override
    public boolean exists(int i, int j) {
        if (i < 0 || i == value.length || j < 0 || j == value[i].length) return false;
        return value[i][j] != 0;
    }
}
