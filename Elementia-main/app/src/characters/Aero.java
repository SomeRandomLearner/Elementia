package characters;

public class Aero extends GameCharacter{

    public Aero(int maxHealth, int maxMana, int attack, int defense, int manaRecovery) {
        super("Aero", maxHealth, maxMana, attack, defense, manaRecovery,"/resources/Aero.png");
        super.addNewSkill("Zeyphyr Splash", 0, 0, 1.0f);
        super.addNewSkill("Cyclone Fury", 60, 5, 3.0f);
        super.addNewSkill("Aether Guard", -40, 0, 0.0f);
    }

    public Aero(){
        super("Aero", 100, 100, 20, 20, 10, "/resources/Aero.png");
        super.addNewSkill("Zeyphyr Splash", 0, 0, 1.0f);
        super.addNewSkill("Cyclone Fury", 60, 5, 3.0f);
        super.addNewSkill("Aether Guard", -40, 0, 0.0f);
    }


}