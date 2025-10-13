package characters.heroes;
import characters.Person;
public class Aero extends Person {
    public final String name = "Aero";
    private int airStacks = 0;

    public Aero(int maxHealth, int attack, int defense, int maxMana, int manaRecoveryRate){
        super("Aero", maxHealth, attack, defense, maxMana, manaRecoveryRate);
    }

    public Aero() {
        super("Aero",100, 20,5,100,10);
    }

    @Override public String getSkill1Name() { return "Speedy Strike";}
    private int skill1ManaCost = 0;
    @Override
    public int getSkill1ManaCost() {return skill1ManaCost;}

    @Override
    public void skill1(Person target){
        this.setCurrentMana(this.getCurrentMana() - this.skill1ManaCost);
        target.setCurrentHealth(target.getCurrentHealth() - this.getAttack());
        this.airStacks++;
        if(airStacks >= 3){
            skill1(target);
            airStacks -= 3;
        }
    }

    @Override public String getSkill2Name() { return "Windy Speedy Up";}
    private int skill2ManaCost = 20;
    @Override
    public int getSkill2ManaCost() {return skill2ManaCost;}

    @Override
    public void skill2(Person target){
        if(this.getCurrentMana() - this.skill2ManaCost < 0) return;
        this.setCurrentMana(this.getCurrentMana() - this.skill2ManaCost);
        //To-be-added extra functionality with turn order intended in the future
        this.airStacks += 3;
    }

    @Override public String getSkill3Name() { return "Motorcycle Crash";}
    private int skill3ManaCost = 100;
    @Override
    public int getSkill3ManaCost() {return skill3ManaCost;}
    @Override
    public void skill3(Person target){
        if(this.getCurrentMana() - this.skill3ManaCost < 0) return;
        this.setCurrentMana(this.getCurrentMana() - this.skill3ManaCost);

        target.setCurrentHealth(target.getCurrentHealth() - (this.getAttack() * airStacks));
        this.airStacks = 0;
    }



}
