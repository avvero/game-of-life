package pw.avvero.gol;

import pw.avvero.board.Cell;
import pw.avvero.board.Neighbour;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.BiFunction;

public class ConveyCell extends Cell<Integer> {

    public ConveyCell() {
        super(ThreadLocalRandom.current().nextBoolean() ? 1 : 0);
    }

    @Override
    public Runnable nextState(Integer i, Integer j, BiFunction<Integer, Integer, List<Neighbour<Integer>>> findNeighbour) {
        int n = findNeighbour.apply(i, j).stream().filter(neighbour -> neighbour.cell().value == 1).toList().size();
        if (value != 0 && (n == 2 || n == 3)) {
            return null;
        } else if (n == 3) {
            return () -> value = 1;
        }
        return () -> value = 0;
    }
}
