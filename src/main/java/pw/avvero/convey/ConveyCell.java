package pw.avvero.convey;

import pw.avvero.board.Cell;

import java.util.concurrent.ThreadLocalRandom;

public class ConveyCell extends Cell<Integer> {

    public ConveyCell() {
        super(ThreadLocalRandom.current().nextBoolean() ? 1 : 0);
    }

    @Override
    public Runnable nextState() {
        int n = board.neighbours(this).stream().filter(neighbour -> neighbour.cell().value == 1).toList().size();
        if (value != 0 && (n == 2 || n == 3)) {
            return null;
        } else if (n == 3) {
            return () -> value = 1;
        }
        return () -> value = 0;
    }
}
