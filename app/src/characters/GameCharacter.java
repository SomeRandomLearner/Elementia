package characters;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.InputStream;

public class GameCharacter{
    String name;
    int currentHealth;
    int maxHealth;
    int currentMana;
    int maxMana;
    int attack;
    int defense;
    BufferedImage characterImage;

    public Skill[] skills = new Skill[3];
    int skillCount = 0;
    public GameCharacter(String name, int maxHealth, int maxMana, int attack, int defense, String imagePath){
        this.name = name;
        this.maxHealth = maxHealth;
        this.currentHealth = maxHealth;
        this.maxMana = maxMana;
        this.currentMana = maxMana;
        this.attack = attack;
        this.defense = defense;

        try {
            InputStream imgStream = getClass().getResourceAsStream(imagePath);
            
            if(imgStream != null){
                this.characterImage = ImageIO.read(imgStream);
            }else{
                System.err.println("Error: Couldn't find image at path: " + imagePath);
                this.characterImage = null;
            }
        }catch(IOException e){
            System.err.println("Error loading image: " + imagePath);
            e.printStackTrace();
            this.characterImage = null;
        }
    }

    public boolean useSkill(Skill skill, GameCharacter target){
        if(this.currentMana - skill.getManaCost() < 0){
            return false;
        }
        else{
            this.currentMana -= skill.getManaCost();
            target.takeDamage((int)((this.attack + skill.getAttackUp()) * skill.getMultiplier()));
            return true;
        }
    }

    public void addNewSkill(String name, int manaCost, int attackUp, float multiplier){
        if(this.skillCount < 3){
            this.skills[this.skillCount] = new Skill(name, manaCost, attackUp, multiplier);
            this.skillCount++;
        }
    }

//    public void addSelfTargetingSkill(String name, int manaCost, int healthUp, int defenseUp){
//        if(this.skillCount < 3) {
//            this.currentHealth += healthUp;
//            if (this.currentHealth > this.maxHealth) this.currentHealth = this.maxHealth;
//            this.defense += defenseUp;
//        }
//    }

    public void removeSkill(){
        if(skillCount == 0){
            System.out.print("No skills left to remove!");
            return;
        }
        this.skills[--this.skillCount] = null;
    }

    public void removeAllSkills(){
        for(int i = 0; i < skillCount; i++)
            this.skills[i] = null;
        skillCount = 0;
    }

    public void takeDamage(int damage){
        int finalDamage = damage - this.defense;
        if(finalDamage > 0){
            this.currentHealth -= finalDamage;
        }
        if(this.currentHealth < 0){
            this.currentHealth = 0;
        }
    }
    
    public BufferedImage getImage() {
        return this.characterImage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCurrentHealth() {
        return currentHealth;
    }

    public void setCurrentHealth(int currentHealth) {
        this.currentHealth = currentHealth;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    public int getCurrentMana() {
        return currentMana;
    }

    public void setCurrentMana(int currentMana) {
        this.currentMana = currentMana;
    }

    public int getMaxMana() {
        return maxMana;
    }

    public void setMaxMana(int maxMana) {
        this.maxMana = maxMana;
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
}