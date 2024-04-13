package pw.avvero.seed;

import pw.avvero.board.Cell;

import java.util.concurrent.ThreadLocalRandom;

public class RoleFactory {

    public static final Cell.Role TANK = new Cell.Role(" ⛶", 1, 30, 3, 10, 0, 0, 0);
    public static final Cell.Role FIGHTER = new Cell.Role(" |", 1, 20, 10, 5, 0.3, 2, 0);
    public static final Cell.Role MAGE = new Cell.Role(" *", 5, 5, 0, 3, 0.9, 3, 30);

    public static Cell.Role get() {
        return switch (ThreadLocalRandom.current().nextInt(0, 7)) {
            case 0 -> TANK;  // tank
            case 1 -> new Cell.Role(" <", 2, 10, 20, 3, 0.9, 3, 0);  // ranger
            case 2 -> MAGE;  // mag
            default -> FIGHTER; // fighter
        };
    }

}
