package characters;

import logic.SkillRegistry;

public class Kaelis extends GameCharacter{
    public Kaelis(int maxHealth, int maxMana, int defense, int manaRecovery) {
        super("Kaelis", maxHealth, maxMana, defense, manaRecovery);
        super.addNewSkill(SkillRegistry.getSkill("wolf_call"));
        super.addNewSkill(SkillRegistry.getSkill("hawk_sight"));
        super.addNewSkill(SkillRegistry.getSkill("beast_surge"));
        super.setCharacterImage("/resources/Kaelis.png");
    }

    public Kaelis(){
        this(130, 80, 30, 10);
    }

    @Override
    public GameCharacter clone(){
        return new Kaelis(super.getMaxHP(), super.getMaxMana(), super.getDefense(), super.getManaRecovery());
    }
}
