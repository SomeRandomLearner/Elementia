package characters;

import logic.SkillRegistry;

public class Veyrion extends GameCharacter{
    public Veyrion(int maxHealth, int maxMana, int defense, int manaRecovery) {
        super("Veyrion", maxHealth, maxMana, defense, manaRecovery);
        super.addNewSkill(SkillRegistry.getSkill("shadow_step"));
        super.addNewSkill(SkillRegistry.getSkill("dark_grasp"));
        super.addNewSkill(SkillRegistry.getSkill("night_veil"));
        super.setCharacterImage("/resources/Veyrion.png");
    }
    public Veyrion(){
        this(80,80,50,6);
    }

    @Override
    public GameCharacter clone(){
        return new Veyrion(super.getMaxHP(), super.getMaxMana(), super.getDefense(), super.getManaRecovery());
    }
}
