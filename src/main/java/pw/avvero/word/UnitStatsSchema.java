package pw.avvero.word;

public class UnitStatsSchema {

    public record Stats(int health, int range) {
    }

    public static Stats getBalance(Unit unit) {
        return switch (unit) {
            //health, range
            case Pell   u -> new Stats(1, 1);
            case Knight u -> new Stats(5, 1);
            case Archer u -> new Stats(1, 10);
            default -> new Stats(1, 1);
        };
    }
}
