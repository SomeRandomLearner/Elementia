package characters;

import logic.Skill;
import logic.SkillRegistry;

import javax.swing.*;
import java.util.Objects;

public class Aero extends GameCharacter{
    public Aero(int maxHealth, int maxMana, int defense, int manaRecovery) {
        super("Aero", maxHealth, maxMana, defense, manaRecovery);
        setCharacterId(1);
        addNewSkill(SkillRegistry.getSkill("zephyr_splash"));
        addNewSkill(SkillRegistry.getSkill("cyclone_fury"));
        addNewSkill(SkillRegistry.getSkill("aether_guard"));
        setCharacterImage("/resources/Aero.png");
        this.element = "Wind";

        ImageIcon[] animationFrames = new ImageIcon[3];
        animationFrames[0] = new ImageIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/effects/air1.png"))).getImage());
        animationFrames[1] = new ImageIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/effects/air2.png"))).getImage());
        animationFrames[2] = new ImageIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/effects/air3.png"))).getImage());

        for(Skill skill : this.getSkills()){
            skill.setAnimationFrames(animationFrames);
        }
    }
    public Aero() { this(80,  150, 10, 20); }
    @Override
    public String getDescription() {
        return "Born on the cliffs where the winds howl endlessly, Aero was chosen to wield the unseen strengths of the skies. " +
                "With powers that protect and guide, she uses her elemental wind to support her allies and turn the tide of battlefield.";
    }

    @Override
    public GameCharacter clone(){
        GameCharacter clone = new Aero(super.getMaxHP(), super.getMaxMana(), super.getDefense(), super.getManaRecovery());
        clone.replaceSkillsWithClone();
        return clone;
    }

    @Override
    public String getImagePath(){
        return "/resources/Aero.png";
    }
}