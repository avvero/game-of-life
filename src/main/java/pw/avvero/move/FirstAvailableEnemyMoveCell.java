package pw.avvero.move;

import pw.avvero.board.Cell;
import pw.avvero.board.Neighbour;

import java.util.List;

public class FirstAvailableEnemyMoveCell extends Cell<MoveTarget> {

    public FirstAvailableEnemyMoveCell(MoveTarget value) {
        super(value);
    }

    @Override
    public Runnable nextState(List<Neighbour<MoveTarget>> neighbours) {
        if (this.value == null) return null;

        Neighbour<MoveTarget> enemy = findClosesEnemy(neighbours);
        if (enemy == null) return null;
        return () -> {
            Cell<MoveTarget> destination = enemy.path().get(0);
            destination.value = this.value;
            this.value = null;
        };
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
