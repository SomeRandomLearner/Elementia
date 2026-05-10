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
    public Psalm() { this(60,  120, 5,  15); }

    @Override
    public String getDescription() {
        return "A woman with an unknown lineage seeking the truth of her past."+
        "She travels distant lands to uncover any clues there are of her true self. "+
                "She practiced daily making her physically strong in order to prepare for the trials " +
        "and tribulations she is guaranteed to face. She thought that mastering her"+
        "element on her own wasn't enough so she decided to enroll in CIT-U where"+
        "she can sharpen her skills.";
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