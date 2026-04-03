package characters;

import logic.SkillRegistry;

public class Ripper extends GameCharacter{

    public Ripper(int maxHealth, int maxMana, int defense, int manaRecovery) {
        super("Ripper", maxHealth, maxMana, defense, manaRecovery);
        super.addNewSkill(SkillRegistry.getSkill("tackle"));
        super.addNewSkill(SkillRegistry.getSkill("ground_slam"));
        super.addNewSkill(SkillRegistry.getSkill("hardening_punch"));
        super.setCharacterImage("/resources/Ripper.png");
    }

    public Ripper(){
        this(80, 80, 30, 30);
    }
    @Override
    public GameCharacter clone(){
        return new Ripper(super.getMaxHP(), super.getMaxMana(), super.getDefense(), super.getManaRecovery());
    }

}