package characters;
public class Skill{
    String name;
    int manaCost;
    int attackUp;
    float multiplier;

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
}