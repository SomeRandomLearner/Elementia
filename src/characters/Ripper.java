package characters;

public class Ripper extends GameCharacter{

    public Ripper(int maxHealth, int maxMana, int attack, int defense, int manaRecovery) {
        super("Ripper", maxHealth, maxMana, attack, defense, manaRecovery, "/resources/Ripper.png");
        super.addNewSkill("Tackle", 0, 0, 1.0f);
        super.addNewSkill("Ground Slam", 40, 50, 1.0f);
        super.addNewSkill("Hardening Punch", 80, 100, 1.1f);
    }

    public Ripper(){
        super("Ripper", 100, 100, 20, 20, 10, "/resources/Ripper.png");
        super.addNewSkill("Tackle", 0, 0, 1.0f);
        super.addNewSkill("Ground Slam", 40, 50, 1.0f);
        super.addNewSkill("Hardening Punch", 80, 100, 1.1f);
    }


}