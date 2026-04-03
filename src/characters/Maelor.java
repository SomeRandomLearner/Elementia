package characters;

import logic.SkillRegistry;

public class Maelor extends GameCharacter{
    public Maelor(int maxHealth, int maxMana, int defense, int manaRecovery) {
        super("Maelor", maxHealth, maxMana, defense, manaRecovery);
        super.addNewSkill(SkillRegistry.getSkill("magnetic_pull"));
        super.addNewSkill(SkillRegistry.getSkill("repulse_field"));
        super.addNewSkill(SkillRegistry.getSkill("iron_storm"));
        super.setCharacterImage("/resources/Maelor.png");
    }
    public Maelor(){
        this(80,80,40, 10);
    }

    @Override
    public GameCharacter clone(){
        return new Maelor(super.getMaxHP(), super.getMaxMana(), super.getDefense(), super.getManaRecovery());
    }
}
