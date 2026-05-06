package characters;

import logic.Skill;
import logic.SkillRegistry;

import javax.swing.*;
import java.util.Objects;

public class Psalm extends GameCharacter{
    public Psalm(int maxHealth, int maxMana, int defense, int manaRecovery) {
        super("Psalm", maxHealth, maxMana, defense, manaRecovery);
        setCharacterId(6);
        addNewSkill(SkillRegistry.getSkill("rapid_punch"));
        addNewSkill(SkillRegistry.getSkill("healing_fan"));
        addNewSkill(SkillRegistry.getSkill("fire_kick"));
        this.element = "Fire";

        ImageIcon[] animationFrames = new ImageIcon[3];
        animationFrames[0] = new ImageIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/effects/fire1.png"))).getImage());
        animationFrames[1] = new ImageIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/effects/fire2.png"))).getImage());
        animationFrames[2] = new ImageIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/effects/fire3.png"))).getImage());

        for(Skill skill : this.getSkills()){
            skill.setAnimationFrames(animationFrames);
        }
        super.setCharacterImage("/resources/Psalm.png");
    }
    public Psalm(){
        this(90, 110, 20, 10);
    }

    @Override
    public String getDescription() {
        return "Psalm, the Arcane Scholar, weaves complex spells of pure magic. " +
                "Her intellect and arcane mastery bend reality to her will.";
    }

    @Override
    public GameCharacter clone(){
        GameCharacter clone = new Psalm(super.getMaxHP(), super.getMaxMana(), super.getDefense(), super.getManaRecovery());
        clone.replaceSkillsWithClone();
        return clone;
    }
    @Override
    public String getImagePath(){
        return "/resources/Psalm.png";
    }
}