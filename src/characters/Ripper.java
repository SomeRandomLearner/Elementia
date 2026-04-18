package characters;

import logic.Skill;
import logic.SkillRegistry;

import javax.swing.*;
import java.util.Objects;

public class Ripper extends GameCharacter{

    public Ripper(int maxHealth, int maxMana, int defense, int manaRecovery) {
        super("Ripper", maxHealth, maxMana, defense, manaRecovery);
        super.addNewSkill(SkillRegistry.getSkill("tackle"));
        super.addNewSkill(SkillRegistry.getSkill("ground_slam"));
        super.addNewSkill(SkillRegistry.getSkill("hardening_punch"));
        super.setCharacterImage("/resources/Ripper.png");
        ImageIcon[] animationFrames = new ImageIcon[3];
        animationFrames[0] = new ImageIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/effects/earth1.png"))).getImage());
        animationFrames[1] = new ImageIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/effects/earth2.png"))).getImage());
        animationFrames[2] = new ImageIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/effects/earth3.png"))).getImage());

        for(Skill skill : this.getSkills()){
            skill.setAnimationFrames(animationFrames);
        }
    }

    public Ripper(){
        this(80, 80, 30, 30);
    }
    @Override
    public GameCharacter clone(){
        return new Ripper(super.getMaxHP(), super.getMaxMana(), super.getDefense(), super.getManaRecovery());
    }
    @Override
    public String getImagePath(){
        return "/resources/Ripper.png";
    }
}