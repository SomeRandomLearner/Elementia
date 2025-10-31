package characters;

public class ZnStream extends GameCharacter{

    public ZnStream(int maxHealth, int maxMana, int attack, int defense) {
        super("ZnStream", maxHealth, maxMana, attack, defense, "/images/ZnStream.png");
        super.addNewSkill("Sling Water", 0, 0, 1.0f);
        super.addNewSkill("Liquify", 20, 20, 1.0f);
        super.addNewSkill("Water Takeover", 100, 10, 2.0f);
    }

    public ZnStream(){
        super("ZnStream", 100, 100, 20, 20, "/images/ZnStream.png");
        super.addNewSkill("Sling Water", 0, 0, 1.0f);
        super.addNewSkill("Liquify", 20, 20, 1.0f);
        super.addNewSkill("Water Takeover", 100, 10, 2.0f);
    }


}
