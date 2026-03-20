package characters;

public class ZenStream extends GameCharacter{

    public ZenStream(int maxHealth, int maxMana, int defense, int manaRecovery) {
        super("ZenStream", maxHealth, maxMana, defense, manaRecovery);
        super.addNewSkill(SkillRegistry.getSkill("sling_water"));
        super.addNewSkill(SkillRegistry.getSkill("liquify"));
        super.addNewSkill(SkillRegistry.getSkill("water_takeover"));
        super.setCharacterImage("/resources/ZenStream.png");
    }

    public ZenStream(){
        super("ZenStream", 80, 80, 30, 20);
        super.addNewSkill(SkillRegistry.getSkill("sling_water"));
        super.addNewSkill(SkillRegistry.getSkill("liquify"));
        super.addNewSkill(SkillRegistry.getSkill("water_takeover"));
        super.setCharacterImage("/resources/ZenStream.png");
    }
    @Override
    public GameCharacter clone(){
        return new ZenStream();
    }

}
