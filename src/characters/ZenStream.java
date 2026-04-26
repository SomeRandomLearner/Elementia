package characters;

import logic.Skill;
import logic.SkillRegistry;

import javax.swing.*;
import java.util.Objects;

public class ZenStream extends GameCharacter{
    public ZenStream(int maxHealth, int maxMana, int defense, int manaRecovery) {
        super("ZenStream", maxHealth, maxMana, defense, manaRecovery);
        addNewSkill(SkillRegistry.getSkill("sling_water"));
        addNewSkill(SkillRegistry.getSkill("liquify"));
        addNewSkill(SkillRegistry.getSkill("water_takeover"));
        setCharacterImage("/resources/ZenStream.png");
        this.element = "Water";

        ImageIcon[] animationFrames = new ImageIcon[3];
        animationFrames[0] = new ImageIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/effects/water1.png"))).getImage());
        animationFrames[1] = new ImageIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/effects/water2.png"))).getImage());
        animationFrames[2] = new ImageIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/effects/water3.png"))).getImage());

        for(Skill skill : this.getSkills()){
            skill.setAnimationFrames(animationFrames);
        }
    }

    public ZenStream(){
        this(80, 80, 30, 20);
    }
    @Override
    public GameCharacter clone(){
        return new ZenStream(super.getMaxHP(), super.getMaxMana(), super.getDefense(), super.getManaRecovery());
    }
    @Override
    public String getImagePath(){
        return "/resources/ZenStream.png";
    }

}
