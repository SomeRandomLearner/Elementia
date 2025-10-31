package characters;

public abstract class Person {
    private String name;
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


    // --- Constructor ---
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


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(int newHealth) {
        this.maxHealth = newHealth;
        if (currentHealth > newHealth) currentHealth = newHealth;
    }

    public int getCurrentHealth() {
        return currentHealth;
    }

    public void setCurrentHealth(int newHealth) {
        this.currentHealth = Math.min(newHealth, maxHealth);
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int newAttack) {
        this.attack = newAttack;
    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public int getMaxMana() {
        return maxMana;
    }

    public void setMaxMana(int newMana) {
        this.maxMana = newMana;
        if (currentMana > newMana) currentMana = newMana;
    }

    public int getCurrentMana() {
        return currentMana;
    }

    public void setCurrentMana(int newMana) {
        this.currentMana = Math.min(newMana, maxMana);
    }

    public void recoverMana() {
        this.currentMana += this.manaRecoveryRate;
        if (this.currentMana > this.maxMana) this.currentMana = this.maxMana;
    }

    // --- Utility combat methods ---
    public void takeDamage(int damage) {
        int reducedDamage = Math.max(damage - defense, 0);
        currentHealth -= reducedDamage;
        if (currentHealth < 0) currentHealth = 0;
        System.out.println(name + " took " + reducedDamage + " damage! (HP: " + currentHealth + "/" + maxHealth + ")");
    }

    public void heal(int amount) {
        currentHealth += amount;
        if (currentHealth > maxHealth) currentHealth = maxHealth;
        System.out.println(name + " healed for " + amount + " HP! (HP: " + currentHealth + "/" + maxHealth + ")");
    }

    public void useMana(int amount) {
        currentMana -= amount;
        if (currentMana < 0) currentMana = 0;
        System.out.println(name + " used " + amount + " mana. (MP: " + currentMana + "/" + maxMana + ")");
    }
}
