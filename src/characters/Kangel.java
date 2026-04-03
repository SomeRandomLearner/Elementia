package characters;

import logic.SkillRegistry;

public class Kangel extends GameCharacter {
    public Kangel(int maxHealth, int maxMana, int defense, int manaRecovery) {
        super("Kangel", maxHealth, maxMana, defense, manaRecovery);
        super.addNewSkill(SkillRegistry.getSkill("blood_bind"));
        super.addNewSkill(SkillRegistry.getSkill("crimson_spear"));
        super.addNewSkill(SkillRegistry.getSkill("life_drain"));
        super.setCharacterImage("/resources/Kangel.png");
    }

    public Kangel() {
        this(120, 80,20,10);
    }

    @Override
    public GameCharacter clone() {
        return new Kangel(super.getMaxHP(), super.getMaxMana(), super.getDefense(), super.getManaRecovery());
    }
}