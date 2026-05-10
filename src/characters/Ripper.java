package characters;

import logic.Skill;
import logic.SkillRegistry;

import javax.swing.*;
import java.util.Objects;

public class Ripper extends GameCharacter{

    public Ripper(int maxHealth, int maxMana, int defense, int manaRecovery) {
        super("Ripper", maxHealth, maxMana, defense, manaRecovery);
        setCharacterId(7);
        addNewSkill(SkillRegistry.getSkill("tackle"));
        addNewSkill(SkillRegistry.getSkill("ground_slam"));
        addNewSkill(SkillRegistry.getSkill("hardening_punch"));
        setCharacterImage("/resources/Ripper.png");
        this.element = "Earth";

        ImageIcon[] animationFrames = new ImageIcon[3];
        animationFrames[0] = new ImageIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/effects/earth1.png"))).getImage());
        animationFrames[1] = new ImageIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/effects/earth2.png"))).getImage());
        animationFrames[2] = new ImageIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/effects/earth3.png"))).getImage());

        for(Skill skill : this.getSkills()){
            skill.setAnimationFrames(animationFrames);
        }
    }

    public Ripper()    { this(120, 50,  30, 10); }

    @Override
    public String getDescription() {
        return "Ripper is an Earth Elementalist and a wise ruler from another realm"+
        "who governed with peace, balance, and harmony, ensuring the land and its"+
        "creatures thrived. However, years later something unexpected happened"+
        "during the cataclysmic event from the outsiders, his realm was destroyed, the"+
        "lands were disrupted, and worst of all, the innocent peoples and other creatures"+
        "who lived peacefully were all killed by them. The destruction left scars on"+
        "Ripper, leaving him very devastated losing his sanity. And now, he roams to"+
        "every realm and desires for vengeance against those outsiders who were"+
        "responsible on destroying his realm. Don't let his friendly yet intimidating look"+
        "fool you! He can be a real menace for those who try to harm him";
    }

    @Override
    public GameCharacter clone(){
        GameCharacter clone = new Ripper(super.getMaxHP(), super.getMaxMana(), super.getDefense(), super.getManaRecovery());
        clone.replaceSkillsWithClone();
        return clone;
    }
    @Override
    public String getImagePath(){
        return "/resources/Ripper.png";
    }
}