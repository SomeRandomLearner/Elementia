package characters;

public class Kayden extends GameCharacter{

    public Kayden(int maxHealth, int maxMana, int defense, int manaRecovery) {
        super("Kayden", maxHealth, maxMana, defense, manaRecovery, "/resources/Kayden.png");
        super.addNewSkill(SkillRegistry.getSkill("super_speed"));
        super.addNewSkill(SkillRegistry.getSkill("lightning_strike"));
        super.addNewSkill(SkillRegistry.getSkill("force_control"));
    }

    public Kayden(){
        super("Kayden", 100, 100, 20, 10, "/resources/Kayden.png");
        super.addNewSkill(SkillRegistry.getSkill("super_speed"));
        super.addNewSkill(SkillRegistry.getSkill("lightning_strike"));
        super.addNewSkill(SkillRegistry.getSkill("force_control"));
    }


}