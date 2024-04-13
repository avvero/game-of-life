package pw.avvero.seed;

import pw.avvero.board.Cell;

import java.util.concurrent.ThreadLocalRandom;

public class RoleFactory {

    public static final Cell.Role TANK = new Cell.Role(" ⛶", 1, 30, 3, 10, 0, 0, 0);
    public static final Cell.Role FGTR = new Cell.Role(" |", 1, 20, 10, 5, 0.3, 2, 0);
    public static final Cell.Role MAGE = new Cell.Role(" *", 3, 5, 0, 3, 0.1, 3, 10);
    public static final Cell.Role RANG = new Cell.Role(" <", 3, 5, 10, 3, 0.1, 3, 0);
    public static final Cell.Role PAWN = new Cell.Role(" !", 1, 1, 1, 0, 0f, 0f, 0);

    public static Cell.Role get() {
        return switch (ThreadLocalRandom.current().nextInt(0, 7)) {
            case 0 -> TANK;  // tank
            case 1 -> RANG;  // ranger
            case 2 -> MAGE;  // mag
            default -> FGTR; // fighter
        };
    }

}
