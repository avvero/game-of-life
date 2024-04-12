package pw.avvero.seed;

import pw.avvero.board.Cell;

import java.util.concurrent.ThreadLocalRandom;

public class RoleFactory {

    public static Cell.Role get() {
        return switch (ThreadLocalRandom.current().nextInt(0, 3)) {
            case 0 -> new Cell.Role(" ⛶", 30, 3, 10, 0, 0, 0);  // tank
            case 1 -> new Cell.Role(" <", 10, 20, 3, 0.9, 3, 0);  // ranger
            default -> new Cell.Role(" |", 20, 10, 5, 0.3, 2, 0); // fighter
        };
    }

}
