package pw.avvero.word;

public class Pell extends DamageableUnit implements Mortal {

    public Pell(int health) {
        super(health, null);
    }

    @Override
    public WordObject remains() {
        return new Tomb();
    }
}
