package characters;

import logic.Skill;
import logic.SkillRegistry;

import javax.swing.*;
import java.util.Objects;

public class Kangel extends GameCharacter {
    public Kangel(int maxHealth, int maxMana, int defense, int manaRecovery) {
        super("Kangel", maxHealth, maxMana, defense, manaRecovery);
        setCharacterId(3);
        addNewSkill(SkillRegistry.getSkill("blood_bind"));
        addNewSkill(SkillRegistry.getSkill("crimson_spear"));
        addNewSkill(SkillRegistry.getSkill("life_drain"));
        setCharacterImage("/resources/Kangel.png");
        this.element = "Blood";

        ImageIcon[] animationFrames = new ImageIcon[3];
        animationFrames[0] = new ImageIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/effects/blood1.png"))).getImage());
        animationFrames[1] = new ImageIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/effects/blood2.png"))).getImage());
        animationFrames[2] = new ImageIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/effects/blood3.png"))).getImage());

        for(Skill skill : this.getSkills()){
            skill.setAnimationFrames(animationFrames);
        }
    }

    public Kangel() { this(110, 80,  15, 15); }

    @Override
    public String getDescription() {
        return "Kangel was trained in a secluded medical order known for advanced healing"+
        "techniques. During a regional conflict, she discovered that the same knowledge used to"+
        "preserve life could also manipulate it. When her order refused to interfere in the war, she"+
        "left. She doesn’t see her power as forbidden or cursed. To her, it’s simply biology pushed"+
        "further than others are willing to go. She fights efficiently, wastes nothing, and treats battle"+
        "like surgery — precise and controlled. She doesn’t enjoy violence. She just understands"+
                "it.";
    }

    @Override
    public GameCharacter clone() {
        GameCharacter clone = new Kangel(super.getMaxHP(), super.getMaxMana(), super.getDefense(), super.getManaRecovery());
        clone.replaceSkillsWithClone();
        return clone;
    }
    @Override
    public String getImagePath(){
        return "/resources/Kangel.png";
    }
}