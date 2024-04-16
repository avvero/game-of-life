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
    public Runnable nextState() {
        if (this.value == null) return null;

        Neighbour<MoveTarget> enemy = findClosesEnemy(board.neighbours(this));
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
        Neighbour<MoveTarget> enemy = null;
        for (Neighbour<MoveTarget> neighbour : neighbours) {
            if (neighbour.cell().value != null && neighbour.cell().value instanceof Pawn) {
                if (enemy == null || enemy.level() > neighbour.level()) {
                    enemy = neighbour;
                }
            }
        }
        return enemy;
    }
}
