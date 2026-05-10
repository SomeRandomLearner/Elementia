package characters;

import logic.Skill;
import logic.SkillRegistry;

import javax.swing.*;
import java.util.Objects;

public class Kaelis extends GameCharacter{
    public Kaelis(int maxHealth, int maxMana, int defense, int manaRecovery) {
        super("Kaelis", maxHealth, maxMana, defense, manaRecovery);
        setCharacterId(2);
        addNewSkill(SkillRegistry.getSkill("wolf_call"));
        addNewSkill(SkillRegistry.getSkill("hawk_sight"));
        addNewSkill(SkillRegistry.getSkill("beast_surge"));
        setCharacterImage("/resources/Kaelis.png");
        this.element = "Beast";

        ImageIcon[] animationFrames = new ImageIcon[3];
        animationFrames[0] = new ImageIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/effects/beast1.png"))).getImage());
        animationFrames[1] = new ImageIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/effects/beast2.png"))).getImage());
        animationFrames[2] = new ImageIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/effects/beast3.png"))).getImage());

        for(Skill skill : this.getSkills()){
            skill.setAnimationFrames(animationFrames);
        }
    }
    public Kaelis() { this(105, 70,  20, 15); }

    @Override
    public String getDescription() {
        return "Kaelis was raised on the edge of expanding civilization, where forests"+
        "were slowly replaced by roads and factories. She learned early how to survive"+
        "without relying on cities. Her connection with animals isn’t mystical—it's mutual"+
        "trust built over time. Through a rare resonance ability, she can synchronize with"+
        "beasts and call them to her side. survive without relying on cities."+
        "Her connection with animals isn’t mystical — it’s mutual trust built over time."+
        "Through a rare resonance ability, she can synchronize with beasts and call them"+
        "to her side.";
    }

    @Override
    public GameCharacter clone(){
        GameCharacter clone = new Kaelis(super.getMaxHP(), super.getMaxMana(), super.getDefense(), super.getManaRecovery());
        clone.replaceSkillsWithClone();
        return clone;
    }
    @Override
    public String getImagePath(){
        return "/resources/Kaelis.png";
    }
}
