package characters;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

public abstract class GameCharacter {
    private String name;
    private int currentHealth;
    private int maxHealth;
    private int currentMana;
    private int maxMana;
    private int defense;
    private int manaRecovery;
    private boolean allyStatus = false;
    private boolean takingDamage;
    private boolean isDead;
    private BufferedImage characterImage;
    private int x;
    private int y;

    private Skill[] skills = new Skill[3];
    private int skillCount = 0;

    public GameCharacter(String name, int maxHealth, int maxMana, int defense, int manaRecovery) {
        this.name = name;
        this.maxHealth = maxHealth;
        this.currentHealth = maxHealth;
        this.maxMana = maxMana;
        this.currentMana = maxMana;
        this.defense = defense;
        this.manaRecovery = manaRecovery;
        isDead = false;
    }


    public boolean useSkill(Skill skill, GameCharacter target) {
        boolean skillCasted = false;
        if (skill == null || target == null) return false;

        if (this.currentMana < skill.getManaCost()) {
            return skillCasted;
        }

        this.currentMana -= skill.getManaCost();
        Random random = new Random();
        int damage = random.nextInt(skill.getMaxDamage() - skill.getMinDamage() + 1) + skill.getMinDamage();
        target.takeDamage(damage);

        skill.resetCooldownTimer();
        skillCasted = true;
        return skillCasted;
    }

    public abstract GameCharacter clone();


    public void addNewSkill(String id, String name, int manaCost, int minDamage, int maxDamage, int cooldown) {
        if (this.skillCount < 3) {
            this.skills[this.skillCount] = new Skill(id, name, manaCost, minDamage, maxDamage, cooldown);
            this.skillCount++;
        }
    }
    public void addNewSkill(Skill skill) {
        if (this.skillCount < 3) {
            this.skills[this.skillCount] = skill;
            this.skillCount++;
        }
    }
    public void addNewSkill() {
        if (this.skillCount < 3) {
            this.skills[this.skillCount] = SkillRegistry.getSkill("attack");
            this.skillCount++;
        }
    }

    public void removeSkill() {
        if (skillCount == 0) {
            System.out.println("No skills left to remove!");
            return;
        }
        this.skills[--this.skillCount] = null;
    }

    public void removeAllSkills() {
        for (int i = 0; i < skillCount; i++) {
            this.skills[i] = null;
        }
        skillCount = 0;
    }

    /**
     * Takes damage reduced by defense. HP cannot go below 0.
     */
    public void takeDamage(int damage) {
        int finalDamage = damage - this.defense;
        if (finalDamage > 0) {
            this.currentHealth -= finalDamage;
        }
        if (this.currentHealth <= 0) {
            this.currentHealth = 0;
            isDead = true;
            setCharacterImage("/resources/Grave.png");
        }
    }

    public boolean isDead() {
        return isDead;
    }

    public void setDead(boolean dead) {
        isDead = dead;
    }

    public enum Character{
        AERO,
        KAELIS,
        KANGEL,
        KAYDEN,
        MAELOR,
        PSALM,
        RIPPER,
        VEYRION,
        ZENSTREAM,
    };

    // ---------- Getters & Setters ----------
    public BufferedImage getImage() {
        return this.characterImage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCurrentHP() {
        return currentHealth;
    }

    public void setCurrentHP(int currentHealth) { this.currentHealth = currentHealth; }

    public int getMaxHP() {
        return maxHealth;
    }

    public int getCurrentMana() {
        return currentMana;
    }

    public void setCurrentMana(int currentMana) { this.currentMana = currentMana; }

    public int getMaxMana() {
        return maxMana;
    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public int getManaRecovery() { return manaRecovery; }
    public boolean isAlly() {
        return allyStatus;
    }

    public void setAllyStatus(boolean status) {
        this.allyStatus = status;
    }

    public Skill[] getSkills() {
        return this.skills;
    }

    public String[] getSkillNames() {
        String[] skillNames = new String[skillCount];
        int count = 0;
        for (Skill skill : skills) {
            if (skill != null) {
                skillNames[count++] = skill.getName();
            }
        }
        return skillNames;
    }

    public void recoverMana(){
        setCurrentMana(Math.min(currentMana + manaRecovery, maxMana));
    }

    public void setCharacterImage(BufferedImage characterImage) {
        this.characterImage = characterImage;
    }

    public void setCharacterImage(String imagePath) {
        try {
            InputStream imgStream = getClass().getResourceAsStream(imagePath); // Aero.png default for now; change later
            if (imgStream != null) {
                this.characterImage = ImageIO.read(imgStream);
            } else {
                System.err.println("Error: Couldn't find image at path: " + imagePath);
                this.characterImage = null;
            }
        } catch (IOException e) {
            System.err.println("Error loading image: " + imagePath);
            e.printStackTrace();
            this.characterImage = null;
        }
    }
    public int getX(){
        return x;
    }
    public void setX(int x){
        this.x = x;
    }
    public int getY(){
        return y;
    }
    public void setY(int y){
        this.y = y;
    }

}
