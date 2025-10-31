package characters.heroes;
import characters.Person;

public class ZnStream extends Person {


    public ZnStream(String name, int maxHealth, int attack, int defense, int maxMana, int manaRecoveryRate) {
        super(name, maxHealth, attack, defense, maxMana, manaRecoveryRate);
    }

    public ZnStream() {
        super("Aero",100, 20,5,100,10);
    }

    public void skill1(Person target) {

    }

    public int getSkill1ManaCost() {
        return 0;
    }

    public String getSkill1Name() {
        return null;
    }

    public void skill2(Person target) {

    }

    public int getSkill2ManaCost() {
        return 0;
    }

    public String getSkill2Name() {
        return null;
    }

    public void skill3(Person target) {

    }

    public int getSkill3ManaCost() {
        return 0;
    }

    public String getSkill3Name() {
        return null;
    }
}
