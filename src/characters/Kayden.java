package characters;

import logic.Skill;
import logic.SkillRegistry;

import javax.swing.*;
import java.util.Objects;

public class Kayden extends GameCharacter{

    public Kayden(int maxHealth, int maxMana, int defense, int manaRecovery) {
        super("Kayden", maxHealth, maxMana, defense, manaRecovery);
        super.addNewSkill(SkillRegistry.getSkill("super_speed"));
        super.addNewSkill(SkillRegistry.getSkill("lightning_strike"));
        super.addNewSkill(SkillRegistry.getSkill("force_control"));
        super.setCharacterImage("/resources/Kayden.png");
        ImageIcon[] animationFrames = new ImageIcon[3];
        animationFrames[0] = new ImageIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/effects/lightning1.png"))).getImage());
        animationFrames[1] = new ImageIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/effects/lightning2.png"))).getImage());
        animationFrames[2] = new ImageIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/effects/lightning3.png"))).getImage());

        for(Skill skill : this.getSkills()){
            skill.setAnimationFrames(animationFrames);
        }
    }

    public Kayden(){
        this(90, 110, 20, 10);
    }

    @Override
    public GameCharacter clone(){
        return new Kayden(super.getMaxHP(), super.getMaxMana(), super.getDefense(), super.getManaRecovery());
    }
    @Override
    public String getImagePath(){
        return "/resources/Kayden.png";
    }
}