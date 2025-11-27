package characters;
public class Skill{
    private String name;
    private int manaCost;
    private int attackUp;
    private float multiplier;

    public Skill(String name, int manaCost, int attackUp, float multiplier){
        this.name = name;
        this.manaCost = manaCost;
        this.attackUp = attackUp;
        this.multiplier = multiplier;
    }

    public Skill(){
        this.name = "Attack";
        this.manaCost = 0;
        this.attackUp = 0; //0; no damage increase
        this.multiplier = 1.0f; //1.0 is the normal damage multiplier; no change
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getManaCost() {
        return manaCost;
    }

    public void setManaCost(int manaCost) {
        this.manaCost = manaCost;
    }

    public int getAttackUp() {
        return attackUp;
    }

    public void setAttackUp(int attackUp) {
        this.attackUp = attackUp;
    }

    public float getMultiplier() {
        return multiplier;
    }

    public void setMultiplier(float multiplier) {
        this.multiplier = multiplier;
    }
}