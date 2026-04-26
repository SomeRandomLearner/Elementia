package characters;

import logic.Skill;
import logic.SkillRegistry;

import javax.swing.*;
import java.util.Objects;

public class Maelor extends GameCharacter{
    public Maelor(int maxHealth, int maxMana, int defense, int manaRecovery) {
        super("Maelor", maxHealth, maxMana, defense, manaRecovery);
        addNewSkill(SkillRegistry.getSkill("magnetic_pull"));
        addNewSkill(SkillRegistry.getSkill("repulse_field"));
        addNewSkill(SkillRegistry.getSkill("iron_storm"));
        setCharacterImage("/resources/Maelor.png");
        this.element = "Magnetism";

        ImageIcon[] animationFrames = new ImageIcon[3];
        animationFrames[0] = new ImageIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/effects/magnet1.png"))).getImage());
        animationFrames[1] = new ImageIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/effects/magnet2.png"))).getImage());
        animationFrames[2] = new ImageIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/effects/magnet3.png"))).getImage());

        for(Skill skill : this.getSkills()){
            skill.setAnimationFrames(animationFrames);
        }
    }
    public Maelor(){
        this(80,80,40, 10);
    }

    @Override
    public GameCharacter clone(){
        return new Maelor(super.getMaxHP(), super.getMaxMana(), super.getDefense(), super.getManaRecovery());
    }
    @Override
    public String getImagePath(){
        return "/resources/Maelor.png";
    }
}
