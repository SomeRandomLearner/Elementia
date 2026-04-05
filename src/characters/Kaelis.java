package characters;

import logic.Skill;
import logic.SkillRegistry;

import javax.swing.*;
import java.util.Objects;

public class Kaelis extends GameCharacter{
    public Kaelis(int maxHealth, int maxMana, int defense, int manaRecovery) {
        super("Kaelis", maxHealth, maxMana, defense, manaRecovery);
        super.addNewSkill(SkillRegistry.getSkill("wolf_call"));
        super.addNewSkill(SkillRegistry.getSkill("hawk_sight"));
        super.addNewSkill(SkillRegistry.getSkill("beast_surge"));
        super.setCharacterImage("/resources/Kaelis.png");
        ImageIcon[] animationFrames = new ImageIcon[3];
        animationFrames[0] = new ImageIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/effects/beast1.png"))).getImage());
        animationFrames[1] = new ImageIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/effects/beast2.png"))).getImage());
        animationFrames[2] = new ImageIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/effects/beast3.png"))).getImage());

        for(Skill skill : this.getSkills()){
            skill.setAnimationFrames(animationFrames);
        }
    }

    public Kaelis(){
        this(130, 80, 30, 10);
    }

    @Override
    public GameCharacter clone(){
        return new Kaelis(super.getMaxHP(), super.getMaxMana(), super.getDefense(), super.getManaRecovery());
    }
}
