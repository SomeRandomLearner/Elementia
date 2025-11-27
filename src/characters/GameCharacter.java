package characters;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.InputStream;

public class GameCharacter {
    private String name;
    private int currentHealth;
    private int maxHealth;
    private int currentMana;
    private int maxMana;
    private int attack;
    private int defense;
    private int manaRecovery;
    private boolean allyStatus = false;
    private BufferedImage characterImage;

    private Skill[] skills = new Skill[3];
    private int skillCount = 0;

    public GameCharacter(String name, int maxHealth, int maxMana, int attack, int defense, int manaRecovery, String imagePath) {
        this.name = name;
        this.maxHealth = maxHealth;
        this.currentHealth = maxHealth;
        this.maxMana = maxMana;
        this.currentMana = maxMana;
        this.attack = attack;
        this.defense = defense;
        this.manaRecovery = manaRecovery;

        try {
            InputStream imgStream = getClass().getResourceAsStream(imagePath);
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
    public GameCharacter(String name) {
        this.name = name;
        this.maxHealth = 100;
        this.currentHealth = maxHealth;
        this.maxMana = 100;
        this.currentMana = maxMana;
        this.attack = 30;
        this.defense = 10;
        this.manaRecovery = 10;

        try {
            InputStream imgStream = getClass().getResourceAsStream("/resources/Aero.png"); // Aero.png default for now; change later
            if (imgStream != null) {
                this.characterImage = ImageIO.read(imgStream);
            } else {
                System.err.println("Error: Couldn't find image at path: " + "/resources/Aero.png");
                this.characterImage = null;
            }
        } catch (IOException e) {
            System.err.println("Error loading image: " + "/resources/Aero.png");
            e.printStackTrace();
            this.characterImage = null;
        }
        this.addNewSkill();
        this.addNewSkill("Strike Barrage", 50, 40, 1.0f);
        this.addNewSkill("Hail Mary", this.maxMana, 0, 3.0f);
    }


    public boolean useSkill(Skill skill, GameCharacter target) {
        boolean skillCasted = false;
        if (skill == null || target == null) return false;

        if (this.currentMana < skill.getManaCost()) {
            return skillCasted;
        }

        this.currentMana -= skill.getManaCost();
        int damage = (int) ((this.attack + skill.getAttackUp()) * skill.getMultiplier());
        target.takeDamage(damage);
        skillCasted = true;
        return skillCasted;
    }


    public void addNewSkill(String name, int manaCost, int attackUp, float multiplier) {
        if (this.skillCount < 3) {
            this.skills[this.skillCount] = new Skill(name, manaCost, attackUp, multiplier);
            this.skillCount++;
        }
    }
    public void addNewSkill() {
        if (this.skillCount < 3) {
            this.skills[this.skillCount] = new Skill();
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
        if (this.currentHealth < 0) {
            this.currentHealth = 0;
        }
    }

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

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
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

}
