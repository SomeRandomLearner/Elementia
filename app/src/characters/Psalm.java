package characters;

public class Psalm extends GameCharacter{

    public Psalm(int maxHealth, int maxMana, int attack, int defense, int manaRecovery) {
        super("Psalm", maxHealth, maxMana, attack, defense, manaRecovery,"/resources/PsalmFire.png");
        super.addNewSkill("Rapid Punch", 0, 0, 1.0f);
        super.addNewSkill("Healing Fan", 20, 20, 1.0f);
        super.addNewSkill("Fire Kick", 100, 10, 2.0f);
    }

    public Psalm(){
        super("Psalm", 100, 100, 20, 20, 10, "/resources/PsalmFire.png");
        super.addNewSkill("Rapid Punch", 0, 0, 1.0f);
        super.addNewSkill("Healing Fan", -20, 0, 0.5f);
        super.addNewSkill("Fire Kick", 100, 100, 1.0f);
    }


}