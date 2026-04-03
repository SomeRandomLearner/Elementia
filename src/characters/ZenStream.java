package characters;

import logic.SkillRegistry;

public class ZenStream extends GameCharacter{
    public ZenStream(int maxHealth, int maxMana, int defense, int manaRecovery) {
        super("ZenStream", maxHealth, maxMana, defense, manaRecovery);
        super.addNewSkill(SkillRegistry.getSkill("sling_water"));
        super.addNewSkill(SkillRegistry.getSkill("liquify"));
        super.addNewSkill(SkillRegistry.getSkill("water_takeover"));
        super.setCharacterImage("/resources/ZenStream.png");
    }

    public ZenStream(){
        this(80, 80, 30, 20);
    }
    @Override
    public GameCharacter clone(){
        return new ZenStream(super.getMaxHP(), super.getMaxMana(), super.getDefense(), super.getManaRecovery());
    }

}
