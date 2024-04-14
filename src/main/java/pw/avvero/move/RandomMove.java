package pw.avvero.move;

import pw.avvero.State;
import pw.avvero.board.Board.Neighbour;
import pw.avvero.board.Cell;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class RandomMove implements State<Integer> {


    @Override
    public Runnable calculate(Cell<Integer> current, List<Neighbour<Integer>> neighbours) {
        if (current.value == 0) return null; // TODO
        List<Neighbour<Integer>> fields = neighbours.stream()
                .filter(neighbour -> neighbour.level() == 1 && neighbour.cell().value == 0) // TODO
                .toList();
        if (fields.isEmpty()) return null; //nowhere to go
        int id = ThreadLocalRandom.current().nextInt(fields.size());
        Neighbour<Integer> target = fields.get(id);
        return () -> {
            target.cell().value = current.value;
            current.value = 0; // TODO
        };
    }
}
