package characters;

public abstract class Person {
    public String name;
    private int maxHealth;
    protected int currentHealth;
    private int attack;
    private int defense;
    private int maxMana;
    private int currentMana;
    private int manaRecoveryRate;

    public abstract void skill1(Person target);
    public abstract int getSkill1ManaCost();
    public abstract String getSkill1Name();

    public abstract void skill2(Person target);
    public abstract int getSkill2ManaCost();
    public abstract String getSkill2Name();

    public abstract void skill3(Person target);
    public abstract int getSkill3ManaCost();
    public abstract String getSkill3Name();




    public Person(String name, int maxHealth, int attack, int defense, int maxMana, int manaRecoveryRate) {
        this.name = name;
        this.maxHealth = maxHealth;
        this.currentHealth = maxHealth;
        this.attack = attack;
        this.defense = defense;
        this.maxMana = maxMana;
        this.currentMana = maxMana;
        this.manaRecoveryRate = manaRecoveryRate;
    }

//    public void setTurnOrder(byte turnOrder){
//        this.turnOrder = turnOrder;
//    }
//    public byte getTurnOrder(){
//        return this.turnOrder;
//    }

    public void setMaxHealth(int newHealth){
        this.maxHealth = newHealth;
        if(currentHealth > newHealth) currentHealth = newHealth;
    }

    public int getMaxHealth() {
        return this.maxHealth;
    }

    public void setCurrentHealth(int newHealth){
        this.currentHealth = newHealth;
    }

    public int getCurrentHealth(){
        return this.currentHealth;
    }

    public void setAttack(int newAttack){
        this.attack = newAttack;
    }

    public int getAttack(){
        return attack;
    }

    public void setMaxMana(int newMana){
        this.maxMana = newMana;
        if(currentMana > newMana) currentMana = newMana;
    }

    public int getMaxMana() {
        return this.maxMana;
    }

    public void setCurrentMana(int newMana){
        this.currentMana = newMana;
    }

    public int getCurrentMana(){
        return this.currentMana;
    }

    public void recoverMana(){
        this.currentMana += this.manaRecoveryRate;
        if(this.currentMana > this.maxMana) this.currentMana = this.maxMana;
    }
}
