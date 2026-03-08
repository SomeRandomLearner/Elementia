package characters;

public class Skill{
    private String id;
    private String name;
    private int manaCost;
    private int minDamage;
    private int maxDamage;
    private int cooldown;
    private int cooldownTimer;


    public Skill(String id, String name, int manaCost, int minDamage, int maxDamage, int cooldown){
        this.id = id;
        this.name = name;
        this.manaCost = manaCost;
        this.minDamage = minDamage;
        this.maxDamage = maxDamage;
        this.cooldown = cooldown;
        cooldownTimer = 0;
    }

    public String getId(){
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) { this.name = name; }

    public int getManaCost() {
        return manaCost;
    }

    public void setManaCost(int manaCost) {
        this.manaCost = manaCost;
    }

    public int getMinDamage() {
        return minDamage;
    }

    public void setMinDamage(int minDamage) { this.minDamage = minDamage; }

    public int getMaxDamage() {
        return maxDamage;
    }

    public void setMaxDamage(int maxDamage) {
        this.maxDamage = maxDamage;
    }

    public int getCooldown() { return cooldown; }

    public void setCooldown(int cooldown) {
        this.cooldown = cooldown;
    }

    public int getCooldownTimer() {
        return cooldownTimer;
    }

    public void incrementCooldownTimer(){ cooldownTimer++; }

    public void resetCooldownTimer() { cooldownTimer = 0; }
}