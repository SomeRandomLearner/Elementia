package characters;

import logic.Skill;
import logic.SkillRegistry;

import javax.swing.*;
import java.util.Objects;

public class Kayden extends GameCharacter{

    public Kayden(int maxHealth, int maxMana, int defense, int manaRecovery) {
        super("Kayden", maxHealth, maxMana, defense, manaRecovery);
        setCharacterId(4);
        addNewSkill(SkillRegistry.getSkill("super_speed"));
        addNewSkill(SkillRegistry.getSkill("lightning_strike"));
        addNewSkill(SkillRegistry.getSkill("force_control"));
        setCharacterImage("/resources/Kayden.png");
        this.element = "Lightning";

        ImageIcon[] animationFrames = new ImageIcon[3];
        animationFrames[0] = new ImageIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/effects/lightning1.png"))).getImage());
        animationFrames[1] = new ImageIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/effects/lightning2.png"))).getImage());
        animationFrames[2] = new ImageIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/effects/lightning3.png"))).getImage());

        for(Skill skill : this.getSkills()){
            skill.setAnimationFrames(animationFrames);
        }
    }

    public Kayden() { this(75,  120, 15, 10); }

    @Override
    public String getDescription() {
        return "Kayden, the Shadow Assassin, strikes from the darkness with lethal " +
                "precision. No target escapes his silent, deadly embrace.";
    }

    @Override
    public GameCharacter clone(){
        GameCharacter clone = new Kayden(super.getMaxHP(), super.getMaxMana(), super.getDefense(), super.getManaRecovery());
        clone.replaceSkillsWithClone();
        return clone;
    }
    @Override
    public String getImagePath(){
        return "/resources/Kayden.png";
    }
}