package characters;

import logic.Skill;
import logic.SkillRegistry;

import javax.swing.*;
import java.util.Objects;

public class Aero extends GameCharacter{

    public Aero(int maxHealth, int maxMana, int defense, int manaRecovery) {
        super("Aero", maxHealth, maxMana, defense, manaRecovery);
        super.addNewSkill(SkillRegistry.getSkill("zephyr_splash"));
        super.addNewSkill(SkillRegistry.getSkill("cyclone_fury"));
        super.addNewSkill(SkillRegistry.getSkill("aether_guard"));
        super.setCharacterImage("/resources/Aero.png");
        this.element = "Wind";
        ImageIcon[] animationFrames = new ImageIcon[3];
        animationFrames[0] = new ImageIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/effects/air1.png"))).getImage());
        animationFrames[1] = new ImageIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/effects/air2.png"))).getImage());
        animationFrames[2] = new ImageIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/effects/air3.png"))).getImage());

        for(Skill skill : this.getSkills()){
            skill.setAnimationFrames(animationFrames);
        }
    }

    public Aero(){
        this(100, 100, 20, 10);
    }

    @Override
    public GameCharacter clone(){
        return new Aero();
    }

    @Override
    public String getImagePath(){
        return "/resources/Aero.png";
    }
}