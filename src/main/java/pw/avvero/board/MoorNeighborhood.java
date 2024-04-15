package pw.avvero.board;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MoorNeighborhood<T> implements Neighborhood<T> {

    @Override
    public List<Neighbour<T>> neighbours(Board<T> board, int i, int j) {
        List<Neighbour<T>> result = new ArrayList<>();
        //
        boolean[][] visited = new boolean[board.x][board.y];
        LinkedList<Object[]> tovisit = new LinkedList<>();
        tovisit.add(new Object[]{0, i, j, new ArrayList<>()});
        while (!tovisit.isEmpty()) {
            Object[] target = tovisit.removeFirst();
            int level = (Integer) target[0], ti = (Integer) target[1], tj = (Integer) target[2];
            List<Cell<T>> path = (List<Cell<T>>) target[3];
            //
            if (visited[ti][tj]) continue;
            visited[ti][tj] = true;
            if (level > 0) {
                result.add(new Neighbour<>(board.get(ti, tj), path));
            }
            for (int x = -1; x <= 1; x++) {
                for (int y = -1; y <= 1; y++) {
                    if (x == 0 && y == 0) continue; // current
                    int ni = ti + x, nj = tj + y;
                    if (board.exists(ni, nj) && !visited[ni][nj]) {
                        ArrayList<Cell<T>> subpath = new ArrayList<>(path);
                        subpath.add(board.get(ni, nj));
                        tovisit.add(new Object[]{level + 1, ni, nj, subpath});
                    }
                }
            }
        }
        return result;
    }
}
