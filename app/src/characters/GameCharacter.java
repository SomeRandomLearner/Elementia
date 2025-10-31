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

    Skill[] skills = new Skill[3];
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

    

    public void addNewSkill(String name, int manaCost, int attackUp, float multiplier){
        if(this.skillCount < 3){
            this.skills[this.skillCount] = new Skill(name, manaCost, attackUp, multiplier);
            this.skillCount++;
        }
    }

    public void removeSkill(){
        if(skillCount == 0){
            System.out.print("No skills left to remove!");
            return;
        }
        this.skills[--this.skillCount] = null;
    }

    public void removeAllSkill(){
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
}