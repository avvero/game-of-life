package pw.avvero.move;

import pw.avvero.board.Cell;
import pw.avvero.board.Neighbour;

import java.util.List;
import java.util.function.BiFunction;

public class FirstAvailableEnemyMoveCell extends Cell<MoveTarget> {

    public FirstAvailableEnemyMoveCell(MoveTarget value) {
        super(value);
    }

    @Override
    public Runnable nextState(Integer i, Integer j, BiFunction<Integer, Integer, List<Neighbour<MoveTarget>>> findNeighbour) {
        if (this.value == null) return null;

        Neighbour<MoveTarget> enemy = findClosesEnemy(findNeighbour.apply(i, j));
        if (enemy == null) return null;
        if (enemy.level() > 1) {
            return () -> new FirstWin().accept(this, enemy.path().get(0));
        } else {
            return () -> {
                if (this.value == null) return; // killed already
                enemy.path().get(0).value = null; // do kill
            };
        }
    }

    private Neighbour<MoveTarget> findClosesEnemy(List<Neighbour<MoveTarget>> neighbours) {
        for (Neighbour<MoveTarget> neighbour : neighbours) {
            if (neighbour.cell().value != null && neighbour.cell().value instanceof Pawn) {
                return neighbour;
            }
        }
        return null;
    }
}
