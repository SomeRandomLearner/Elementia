package characters;

import logic.SkillRegistry;

public class Psalm extends GameCharacter{

    public Psalm(int maxHealth, int maxMana, int defense, int manaRecovery) {
        super("Psalm", maxHealth, maxMana, defense, manaRecovery);
        super.addNewSkill(SkillRegistry.getSkill("rapid_punch"));
        super.addNewSkill(SkillRegistry.getSkill("healing_fan"));
        super.addNewSkill(SkillRegistry.getSkill("fire_kick"));
        super.setCharacterImage("/resources/Psalm.png");
    }
    public Psalm(){
        this(90, 110, 20, 10);
    }
    @Override
    public GameCharacter clone(){
        return new Psalm(super.getMaxHP(), super.getMaxMana(), super.getDefense(), super.getManaRecovery());
    }
}