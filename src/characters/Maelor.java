package characters;

import logic.Skill;
import logic.SkillRegistry;

import javax.swing.*;
import java.util.Objects;

public class Maelor extends GameCharacter{
    public Maelor(int maxHealth, int maxMana, int defense, int manaRecovery) {
        super("Maelor", maxHealth, maxMana, defense, manaRecovery);
        setCharacterId(5);
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
    public Maelor()    { this(95,  90,  25, 15); }

    @Override
    public String getDescription() {
        return "Maelor, the Earth Warden, commands the unbreakable might of stone " +
                "and soil. His defenses are impenetrable, his strikes devastating.";
    }

    @Override
    public GameCharacter clone(){
        GameCharacter clone = new Maelor(super.getMaxHP(), super.getMaxMana(), super.getDefense(), super.getManaRecovery());
        clone.replaceSkillsWithClone();
        return clone;
    }
    @Override
    public String getImagePath(){
        return "/resources/Maelor.png";
    }
}
