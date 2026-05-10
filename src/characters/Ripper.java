package characters;

import logic.Skill;
import logic.SkillRegistry;

import javax.swing.*;
import java.util.Objects;

public class Ripper extends GameCharacter{

    public Ripper(int maxHealth, int maxMana, int defense, int manaRecovery) {
        super("Ripper", maxHealth, maxMana, defense, manaRecovery);
        setCharacterId(7);
        addNewSkill(SkillRegistry.getSkill("tackle"));
        addNewSkill(SkillRegistry.getSkill("ground_slam"));
        addNewSkill(SkillRegistry.getSkill("hardening_punch"));
        setCharacterImage("/resources/Ripper.png");
        this.element = "Earth";

        ImageIcon[] animationFrames = new ImageIcon[3];
        animationFrames[0] = new ImageIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/effects/earth1.png"))).getImage());
        animationFrames[1] = new ImageIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/effects/earth2.png"))).getImage());
        animationFrames[2] = new ImageIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/effects/earth3.png"))).getImage());

        for(Skill skill : this.getSkills()){
            skill.setAnimationFrames(animationFrames);
        }
    }

    public Ripper()    { this(120, 50,  30, 10); }

    @Override
    public String getDescription() {
        return "Ripper, the Blood Berserker, thrives on carnage and destruction. " +
                "His savage fury grows stronger with every wound he inflicts.";
    }

    @Override
    public GameCharacter clone(){
        GameCharacter clone = new Ripper(super.getMaxHP(), super.getMaxMana(), super.getDefense(), super.getManaRecovery());
        clone.replaceSkillsWithClone();
        return clone;
    }
    @Override
    public String getImagePath(){
        return "/resources/Ripper.png";
    }
}