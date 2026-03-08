package characters;

public class Psalm extends GameCharacter{

    public Psalm(int maxHealth, int maxMana, int defense, int manaRecovery) {
        super("Psalm", maxHealth, maxMana, defense, manaRecovery, "/resources/Psalm.png");
        super.addNewSkill(SkillRegistry.getSkill("rapid_punch"));
        super.addNewSkill(SkillRegistry.getSkill("healing_fan"));
        super.addNewSkill(SkillRegistry.getSkill("fire_kick"));
    }

    public Psalm(){
        super("Psalm", 100, 100, 20, 10, "/resources/Psalm.png");
        super.addNewSkill(SkillRegistry.getSkill("rapid_punch"));
        super.addNewSkill(SkillRegistry.getSkill("healing_fan"));
        super.addNewSkill(SkillRegistry.getSkill("fire_kick"));
    }


}