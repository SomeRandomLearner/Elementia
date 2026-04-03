package characters;

import logic.SkillRegistry;

public class Kayden extends GameCharacter{

    public Kayden(int maxHealth, int maxMana, int defense, int manaRecovery) {
        super("Kayden", maxHealth, maxMana, defense, manaRecovery);
        super.addNewSkill(SkillRegistry.getSkill("super_speed"));
        super.addNewSkill(SkillRegistry.getSkill("lightning_strike"));
        super.addNewSkill(SkillRegistry.getSkill("force_control"));
        super.setCharacterImage("/resources/Kayden.png");
    }

    public Kayden(){
        this(90, 110, 20, 10);
    }

    @Override
    public GameCharacter clone(){
        return new Kayden(super.getMaxHP(), super.getMaxMana(), super.getDefense(), super.getManaRecovery());
    }
}