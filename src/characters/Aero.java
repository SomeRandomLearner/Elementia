package characters;

import logic.SkillRegistry;

public class Aero extends GameCharacter{

    public Aero(int maxHealth, int maxMana, int defense, int manaRecovery) {
        super("Aero", maxHealth, maxMana, defense, manaRecovery);
        super.addNewSkill(SkillRegistry.getSkill("zephyr_splash"));
        super.addNewSkill(SkillRegistry.getSkill("cyclone_fury"));
        super.addNewSkill(SkillRegistry.getSkill("aether_guard"));
        super.setCharacterImage("/resources/Aero.png");
    }

    public Aero(){
        super("Aero", 100, 100, 20, 10);
        super.addNewSkill(SkillRegistry.getSkill("zephyr_splash"));
        super.addNewSkill(SkillRegistry.getSkill("cyclone_fury"));
        super.addNewSkill(SkillRegistry.getSkill("aether_guard"));
        super.setCharacterImage("/resources/Aero.png");
    }

    @Override
    public GameCharacter clone(){
        return new Aero();
    }


}