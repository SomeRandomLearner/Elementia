package characters;

public class Kayden extends GameCharacter{

    public Kayden(int maxHealth, int maxMana, int attack, int defense, int manaRecovery) {
        super("Kayden", maxHealth, maxMana, attack, defense, manaRecovery, "/resources/Kayden.png");
        super.addNewSkill("Super Speed", 0, 0, 1.0f);
        super.addNewSkill("Lightning Strike", this.getMaxMana(), 20, 3.0f);
        super.addNewSkill("Force Control", -20, 0, 0.5f);
    }

    public Kayden(){
        super("Kayden", 100, 100, 20, 20, 10, "/resources/Kayden.png");
        super.addNewSkill("Super Speed", 0, 0, 1.0f);
        super.addNewSkill("Lightning Strike", this.getMaxMana(), 20, 3.0f);
        super.addNewSkill("Force Control", -20, 0, 0.5f);
    }


}