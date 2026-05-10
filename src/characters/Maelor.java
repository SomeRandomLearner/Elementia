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
        return "Maelor grew up in a heavily industrialized state where metal powered"+
        "everything — cities, weapons, infrastructure. His magnetic abilities manifested"+
        "early and were quickly exploited for military development. Rather than remain a"+
        "state asset, he disappeared. Now he operates independently, taking contracts that"+
        "align with his own logic. He values control — of himself, of his environment, and"+
        "especially of any battlefield he steps into. He doesn’t fight emotionally. He fights"+
        "strategically.";
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
