package characters;

public class Assassin extends Person {
    public Assassin(int maxHealth, int attack, int defense, int maxMana, int manaRecoveryRate){
        super("Assassin", maxHealth, attack, defense, maxMana, manaRecoveryRate);
    }

    public Assassin() {
        super("Assassin",80, 20,0,60,10);
    }

    // Assassin.java

    @Override
    public String getSkill1Name() { return "Strike";}
    protected int skill1ManaCost = 0;
    @Override
    public int getSkill1ManaCost() { return skill1ManaCost;}

    @Override
    public void skill1(Person target) {
        this.setCurrentMana(this.getCurrentMana() - this.skill1ManaCost);
        target.setCurrentHealth(target.getCurrentHealth() - this.getAttack());
    }

    @Override
    public String getSkill2Name() {return "Hit & Run";}
    protected int skill2ManaCost = 20;
    @Override
    public int getSkill2ManaCost() {return skill2ManaCost;}

    @Override
    public void skill2(Person target) {
        this.setCurrentMana(this.getCurrentMana() - this.skill2ManaCost);
        skill1(target);
        dodgeNext = true;
    }
    private boolean dodgeNext;

    @Override
    public String getSkill3Name() {return "Execute";}
    @Override
    public int getSkill3ManaCost() {return skill3ManaCost;}
    protected int skill3ManaCost = 40;

    @Override
    public void skill3(Person target){
        skill1(target);
        if(target.getCurrentHealth() > 0 && target.getMaxHealth()/target.getCurrentHealth() >= 10){
            this.setCurrentMana(this.getCurrentMana() - this.skill3ManaCost);
            target.setCurrentHealth(0);
        }
    }

    @Override
    public void setCurrentHealth(int newHealth){
        if(dodgeNext) {
            dodgeNext = false;
            return;
        }
        else this.currentHealth = newHealth;
    }
}
