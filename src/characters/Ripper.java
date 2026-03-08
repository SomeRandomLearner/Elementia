package characters;

public class Ripper extends GameCharacter{

    public Ripper(int maxHealth, int maxMana, int defense, int manaRecovery) {
        super("Ripper", maxHealth, maxMana, defense, manaRecovery, "/resources/Ripper.png");
        super.addNewSkill(SkillRegistry.getSkill("tackle"));
        super.addNewSkill(SkillRegistry.getSkill("ground_slam"));
        super.addNewSkill(SkillRegistry.getSkill("hardening_punch"));
    }

    public Ripper(){
        super("Ripper", 100, 100, 20, 10, "/resources/Ripper.png");
        super.addNewSkill(SkillRegistry.getSkill("tackle"));
        super.addNewSkill(SkillRegistry.getSkill("ground_slam"));
        super.addNewSkill(SkillRegistry.getSkill("hardening_punch"));
    }


}