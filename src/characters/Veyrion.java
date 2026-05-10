package characters;

import logic.Skill;
import logic.SkillRegistry;

import javax.swing.*;
import java.util.Objects;

public class Veyrion extends GameCharacter{
    public Veyrion(int maxHealth, int maxMana, int defense, int manaRecovery) {
        super("Veyrion", maxHealth, maxMana, defense, manaRecovery);
        setCharacterId(8);
        addNewSkill(SkillRegistry.getSkill("shadow_step"));
        addNewSkill(SkillRegistry.getSkill("dark_grasp"));
        addNewSkill(SkillRegistry.getSkill("night_veil"));
        setCharacterImage("/resources/Veyrion.png");
        this.element = "Shadow";

        ImageIcon[] animationFrames = new ImageIcon[3];
        animationFrames[0] = new ImageIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/effects/shadow1.png"))).getImage());
        animationFrames[1] = new ImageIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/effects/shadow2.png"))).getImage());
        animationFrames[2] = new ImageIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/effects/shadow3.png"))).getImage());

        for(Skill skill : this.getSkills()){
            skill.setAnimationFrames(animationFrames);
        }
    }
    public Veyrion()   { this(70,  100, 10, 20); }

    @Override
    public String getDescription() {
        return "Veyrion’s abilities surfaced during adolescence — shadows"+
        "reacting to his presence, stretching beyond natural limits. Rather than fear it, he"+
        "studied it. He later discovered shadow energy is simply the absence of light"+
        "manipulated at high density. To him, it’s a discipline — not superstition. He works"+
        "best alone, prefers short engagements, and avoids prolonged conflict. His goal"+
        "isn’t domination. It’s efficiency.";
    }

    @Override
    public GameCharacter clone(){
        GameCharacter clone = new Veyrion(super.getMaxHP(), super.getMaxMana(), super.getDefense(), super.getManaRecovery());
        clone.replaceSkillsWithClone();
        return clone;
    }
    @Override
    public String getImagePath(){
        return "/resources/Veyrion.png";
    }
}
