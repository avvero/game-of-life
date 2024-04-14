package pw.avvero.gol;

import pw.avvero.board.Cell;
import pw.avvero.board.Neighbour;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class ConveyCell extends Cell<Integer> {

    public ConveyCell() {
        this.value = ThreadLocalRandom.current().nextBoolean() ? 1 : 0;
    }

    @Override
    public Runnable nextState(List<Neighbour<Integer>> neighbours) {
        int n = neighbours.stream().filter(neighbour -> neighbour.level() == 1 && neighbour.cell().value == 1).toList().size();
        if (value != 0 && (n == 2 || n == 3)) {
            return null;
        } else if (n == 3) {
            return () -> value = 1;
        }
        return () -> value = 0;
    }
}
