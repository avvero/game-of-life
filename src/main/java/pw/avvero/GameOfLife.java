package pw.avvero;

import java.util.List;

public class GameOfLife implements State {

    @Override
    public int calculate(int current, List<int[]> neighbours) {
        int n = neighbours.size();
        if (current != 0 && (n == 2 || n == 3)) {
            return current;
        } else if (n == 3) {
            return 1;
        }
        return 0;
    }
}
