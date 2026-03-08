package characters;

public class Aero extends GameCharacter{

    public Aero(int maxHealth, int maxMana, int defense, int manaRecovery) {
        super("Aero", maxHealth, maxMana, defense, manaRecovery, "/resources/Aero.png");
        super.addNewSkill(SkillRegistry.getSkill("zephyr_splash"));
        super.addNewSkill(SkillRegistry.getSkill("cyclone_fury"));
        super.addNewSkill(SkillRegistry.getSkill("aether_guard"));
    }

    public Aero(){
        super("Aero", 100, 100, 20, 10, "/resources/Aero.png");
        super.addNewSkill(SkillRegistry.getSkill("zephyr_splash"));
        super.addNewSkill(SkillRegistry.getSkill("cyclone_fury"));
        super.addNewSkill(SkillRegistry.getSkill("aether_guard"));
    }


}