package pw.avvero.gol;

import pw.avvero.State;
import pw.avvero.board.Board;
import pw.avvero.board.Board.Neighbour;
import pw.avvero.board.Cell;

import java.util.List;

public class GameOfLife implements State<Integer> {

    @Override
    public Runnable calculate(Cell<Integer> current, List<Neighbour<Integer>> neighbours) {
        int n = neighbours.stream().filter(neighbour -> neighbour.level() == 1 && neighbour.cell().value == 1).toList().size();
        if (current.value != 0 && (n == 2 || n == 3)) {
            return null;
        } else if (n == 3) {
            return () -> current.value = 1;
        }
        return () -> current.value = 0;
    }
}
