package pw.avvero.word;

public class Pell extends DamageableUnit implements Mortal {

    public Pell(int health) {
        super(null);
        this.health = health;
    }

    @Override
    public WordObject remains() {
        return new Tomb();
    }
}
