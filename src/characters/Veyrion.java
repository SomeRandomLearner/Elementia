package characters;

import logic.Skill;
import logic.SkillRegistry;

import javax.swing.*;
import java.util.Objects;

public class Veyrion extends GameCharacter{
    public Veyrion(int maxHealth, int maxMana, int defense, int manaRecovery) {
        super("Veyrion", maxHealth, maxMana, defense, manaRecovery);
        super.addNewSkill(SkillRegistry.getSkill("shadow_step"));
        super.addNewSkill(SkillRegistry.getSkill("dark_grasp"));
        super.addNewSkill(SkillRegistry.getSkill("night_veil"));
        super.setCharacterImage("/resources/Veyrion.png");
        ImageIcon[] animationFrames = new ImageIcon[3];
        animationFrames[0] = new ImageIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/effects/shadow1.png"))).getImage());
        animationFrames[1] = new ImageIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/effects/shadow2.png"))).getImage());
        animationFrames[2] = new ImageIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/effects/shadow3.png"))).getImage());

        for(Skill skill : this.getSkills()){
            skill.setAnimationFrames(animationFrames);
        }
    }
    public Veyrion(){
        this(80,80,50,6);
    }

    @Override
    public GameCharacter clone(){
        return new Veyrion(super.getMaxHP(), super.getMaxMana(), super.getDefense(), super.getManaRecovery());
    }
    @Override
    public String getImagePath(){
        return "/resources/Veyrion.png";
    }
}
