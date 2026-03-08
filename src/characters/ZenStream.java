package characters;

public class ZenStream extends GameCharacter{

    public ZenStream(int maxHealth, int maxMana, int defense, int manaRecovery) {
        super("ZenStream", maxHealth, maxMana, defense, manaRecovery, "/resources/ZenStream.png");
        super.addNewSkill(SkillRegistry.getSkill("sling_water"));
        super.addNewSkill(SkillRegistry.getSkill("liquify"));
        super.addNewSkill(SkillRegistry.getSkill("water_takeover"));
    }

    public ZenStream(){
        super("ZenStream", 100, 100, 20, 10, "/resources/ZenStream.png");
        super.addNewSkill(SkillRegistry.getSkill("sling_water"));
        super.addNewSkill(SkillRegistry.getSkill("liquify"));
        super.addNewSkill(SkillRegistry.getSkill("water_takeover"));
    }


}
