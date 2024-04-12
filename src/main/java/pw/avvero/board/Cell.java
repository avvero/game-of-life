package pw.avvero.board;

import java.util.concurrent.ThreadLocalRandom;

public class Cell {
    public static final Cell ZERO = new Cell(0, 0, 0);
    private int i;
    private int j;
    private int value;
    private int age;
    private int deathAge;

    public int value() {
        return value;
    }

    private Cell() {
    }

    Cell(int i, int j, int value) {
        this.i = i;
        this.j = j;
        this.value = value;
        this.age = 0;
        this.deathAge = 10 + 10 * ThreadLocalRandom.current().nextInt(0, 10);
    }

    public static Cell of(int value) {
        return new Cell(-1, -1, value);
    }

    public static Cell insteadOf(Cell current, Cell next) {
        return new Cell(current.i, current.j, next.value);
    }

    public Cell nextCycle() {
        Cell cell = new Cell();
        cell.i = this.i;
        cell.j = this.j;
        cell.value = this.value;
        cell.age = this.age + 1;
        cell.deathAge = this.age;
        return cell;
    }

    @Override
    public String toString() {
        return Integer.toString(value);
    }
}
