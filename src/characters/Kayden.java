package characters;

public class Kayden extends GameCharacter{

    public Kayden(int maxHealth, int maxMana, int defense, int manaRecovery) {
        super("Kayden", maxHealth, maxMana, defense, manaRecovery);
        super.addNewSkill(SkillRegistry.getSkill("super_speed"));
        super.addNewSkill(SkillRegistry.getSkill("lightning_strike"));
        super.addNewSkill(SkillRegistry.getSkill("force_control"));
        super.setCharacterImage("/resources/Kayden.png");
    }

    public Kayden(){
        super("Kayden", 90, 110, 20, 10);
        super.addNewSkill(SkillRegistry.getSkill("super_speed"));
        super.addNewSkill(SkillRegistry.getSkill("lightning_strike"));
        super.addNewSkill(SkillRegistry.getSkill("force_control"));
        super.setCharacterImage("/resources/Kayden.png");
    }

}