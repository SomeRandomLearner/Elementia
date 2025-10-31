package characters.heroes;

import characters.Person;

public class Psalm extends Person {

    public Psalm(String name, int maxHealth, int attack, int defense, int maxMana, int manaRecoveryRate) {
        super(name, maxHealth, attack, defense, maxMana, manaRecoveryRate);
    }

    public Psalm() {
        super("Psalm", 100, 20, 5, 100, 10);
    }


    @Override
    public void skill1(Person target) {
        int manaCost = getSkill1ManaCost();
        if (getCurrentMana() < manaCost) {
            System.out.println(getName() + " tried to use " + getSkill1Name() + " but doesn't have enough mana!");
            return;
        }

        useMana(manaCost);
        int totalDamage = (int) (getAttack() * 1.5);
        System.out.println(getName() + " unleashes Rapid Fire Punches on " + target.getName() + "!");
        target.takeDamage(totalDamage);
    }

    @Override
    public int getSkill1ManaCost() {
        return 20;
    }

    @Override
    public String getSkill1Name() {
        return "Rapid Fire Punch";
    }


    @Override
    public void skill2(Person target) {
        int manaCost = getSkill2ManaCost();
        if (getCurrentMana() < manaCost) {
            System.out.println(getName() + " tried to use " + getSkill2Name() + " but doesn't have enough mana!");
            return;
        }

        useMana(manaCost);
        int healAmount = 30;
        heal(healAmount);
        System.out.println(getName() + " waves the Healing Fan and restores " + healAmount + " HP!");
    }

    @Override
    public int getSkill2ManaCost() {
        return 25;
    }

    @Override
    public String getSkill2Name() {
        return "Healing Fan";
    }


    @Override
    public void skill3(Person target) {
        int manaCost = getSkill3ManaCost();
        if (getCurrentMana() < manaCost) {
            System.out.println(getName() + " tried to use " + getSkill3Name() + " but doesn't have enough mana!");
            return;
        }

        useMana(manaCost);
        int damage = (int) (getAttack() * 2.0);
        System.out.println(getName() + " performs blazing Fire Kicks on " + target.getName() + "!");
        target.takeDamage(damage);
    }

    @Override
    public int getSkill3ManaCost() {
        return 35;
    }

    @Override
    public String getSkill3Name() {
        return "Fire Kicks";
    }
}
