package characters;

import logic.Skill;
import logic.SkillRegistry;

import javax.swing.*;
import java.util.Objects;

public class Kangel extends GameCharacter {
    public Kangel(int maxHealth, int maxMana, int defense, int manaRecovery) {
        super("Kangel", maxHealth, maxMana, defense, manaRecovery);
        super.addNewSkill(SkillRegistry.getSkill("blood_bind"));
        super.addNewSkill(SkillRegistry.getSkill("crimson_spear"));
        super.addNewSkill(SkillRegistry.getSkill("life_drain"));
        super.setCharacterImage("/resources/Kangel.png");
        ImageIcon[] animationFrames = new ImageIcon[3];
        animationFrames[0] = new ImageIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/effects/blood1.png"))).getImage());
        animationFrames[1] = new ImageIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/effects/blood2.png"))).getImage());
        animationFrames[2] = new ImageIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/effects/blood3.png"))).getImage());

        for(Skill skill : this.getSkills()){
            skill.setAnimationFrames(animationFrames);
        }
    }

    public Kangel() {
        this(120, 80,20,10);
    }

    @Override
    public GameCharacter clone() {
        return new Kangel(super.getMaxHP(), super.getMaxMana(), super.getDefense(), super.getManaRecovery());
    }
    @Override
    public String getImagePath(){
        return "/resources/Kangel.png";
    }
}