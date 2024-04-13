package pw.avvero.board;

import java.util.concurrent.ThreadLocalRandom;

public class Cell {

    public static final Cell ZERO = new Cell(-1, -1, 0, 0, 0, new Role("  ", 0, 0, 0, 0, 0, 0, 0));
    private int i = -1;
    private int j = -1;
    private int value;
    private int age;
    private int deathAge;
    private Role role = null;

    public static record Role(String sign,
                              int range,
                              int health,
                              int strength,
                              int defence,
                              double critChance,
                              double critMultiplier,
                              int fireDamage) {
    }

    public int value() {
        return value;
    }

    public Role getRole() {
        return role;
    }

    private Cell(int i, int j, int value, int age, int deathAge, Role role) {
        this.i = i;
        this.j = j;
        this.value = value;
        this.age = age;
        this.deathAge = deathAge; //10 + 10 * ThreadLocalRandom.current().nextInt(0, 10);
        this.role = role;
    }

    public static Cell of(int value, Role role) {
        return new Cell(-1, -1, value, 0, 10 + 10 * ThreadLocalRandom.current().nextInt(0, 10), role);
    }

    public Cell acquire(Cell old) {
        return new Cell(old.i, old.j, this.value, this.age, this.deathAge, this.role);
    }

    public Cell nextCycle() {
        int incAge = 0;//1;
        Cell next = new Cell(this.i, this.j, this.value, this.age + incAge, this.deathAge, this.role);
        if (next.isAlive()) {
            return next;
        } else {
            return ZERO.acquire(next);
        }
    }

    Cell acquire(int i, int j) { // dirty
        this.i = i;
        this.j = j;
        return this;
    }

    public boolean isAlive() {
        if (age >= deathAge) return false;
        if (role.health < 0) return false;
        return true;
    }

    @Override
    public String toString() {
        return Integer.toString(value);
    }
}
