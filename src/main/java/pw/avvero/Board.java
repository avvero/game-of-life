package pw.avvero;

public abstract class Board {

    protected int[][] value;

    public Board(int[][] value) {
        this.value = value;
    }

    public int[][] value() {
        return value;
    }

    void update(int[][] value) {
        this.value = value;
    }

    abstract boolean exists(int i, int j);

    public int neighbours(int i, int j) {
        int result = 0;
        for (int x = -1; x < 2; x++) {
            for (int y = -1; y < 2; y++) {
                if (x == 0 && y == 0) continue;
                result += exists(i + x, j + y) ? 1 : 0;
            }
        }
        return result;
    }
}
