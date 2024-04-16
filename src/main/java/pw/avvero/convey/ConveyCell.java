package pw.avvero.convey;

import pw.avvero.board.Cell;

import java.util.concurrent.ThreadLocalRandom;

public class ConveyCell extends Cell<Integer> {

    public ConveyCell() {
        super(ThreadLocalRandom.current().nextBoolean() ? 1 : 0);
    }

    @Override
    public Runnable nextState() {
        long n = board.nearCells(this).stream().filter((c) -> c.value == 1).count();
        if (value != 0 && (n == 2 || n == 3)) {
            return null;
        } else if (n == 3) {
            return () -> value = 1;
        }
        return () -> value = 0;
    }
}
