import characters.Person;

import java.util.*;

public class CombatHandler2 {

    public CombatHandler2(ArrayList<Person> allies, ArrayList<Person> enemies){
        Collections.shuffle(allies);
        Collections.shuffle(enemies);
        Scanner scanner = new Scanner(System.in);
        for(Person p : allies){
            System.out.print(p.name + " ");
        }
        System.out.println("\nVERSUS");
        for(Person p : enemies){
            System.out.print(p.name + " ");
        }
        System.out.println();
        do{
            for(Person p : allies){
                System.out.println("-------------------------------------");
                System.out.printf("%s's turn! [%d/%d] hp, [%d/%d] mp\n", p.name, p.getCurrentHealth(), p.getMaxHealth(), p.getCurrentMana(), p.getMaxMana());

                System.out.println("Choose a skill:");
                System.out.printf("(1) %s [%d mp] ", p.getSkill1Name(), p.getSkill1ManaCost());
                System.out.printf("(2) %s [%d mp] ", p.getSkill2Name(), p.getSkill2ManaCost());
                System.out.printf("(3) %s [%d mp] ", p.getSkill3Name(), p.getSkill3ManaCost());
                System.out.print("\nSkill: ");
                int chosenSkill = 0;
                do{
                    chosenSkill = scanner.nextInt();
                }while(chosenSkill < 1 || chosenSkill > 3);
                int i = 1;
                for(Person enemy : enemies){
                    System.out.printf("(%d) %s [%d/%d] hp, [%d/%d] mp\n", i, enemy.name, enemy.getCurrentHealth(), enemy.getMaxHealth(), enemy.getCurrentMana(), enemy.getMaxMana());
                    i++;
                }
                System.out.println("Choose a target:");

                int chosenTarget = 0;
                do{
                    chosenTarget = scanner.nextInt();
                }while(chosenTarget  < 1 || chosenTarget > enemies.size());
                Person target = enemies.get(chosenTarget - 1);
                switch(chosenSkill){
                    case 1 -> {
                        p.skill1(target);
                        deadTargetHandler(enemies, target);
                        System.out.printf(p.name + " uses " + p.getSkill1Name() + " on " + target.name + "\n");
                    }
                    case 2 -> {
                        p.skill2(target);
                        System.out.printf(p.name + " uses " + p.getSkill2Name() + " on " + target.name + "\n");
                        deadTargetHandler(enemies, target);
                    }
                    case 3 -> {
                        p.skill3(target);
                        System.out.printf(p.name + " uses " + p.getSkill3Name() + " on " + target.name + "\n");
                        deadTargetHandler(enemies, target);
                    }
                }
                p.recoverMana();
            }

            //Enemies turn
            for(Person p : enemies) {
                System.out.printf("%s's turn! [%d/%d] hp, [%d/%d] mp\n", p.name, p.getCurrentHealth(), p.getMaxHealth(), p.getCurrentMana(), p.getMaxMana());

                int chosenTarget = (int) (Math.random() * allies.size());
                Person target = allies.get(chosenTarget);
                int chosenSkill = (int) (Math.random() * 3) + 1;
                switch (chosenSkill) {
                    case 1 -> {
                        p.skill1(target);
                        System.out.printf(p.name + " uses " + p.getSkill1Name() + " on " + target.name + "\n");
                        deadTargetHandler(allies, target);
                    }
                    case 2 -> {
                        p.skill2(target);
                        System.out.printf(p.name + " uses " + p.getSkill2Name() + " on " + target.name + "\n");
                        deadTargetHandler(allies, target);
                    }
                    case 3 -> {
                        p.skill3(target);
                        System.out.printf(p.name + " uses " + p.getSkill3Name() + " on " + target.name + "\n");
                        deadTargetHandler(allies, target);
                    }
                }
                p.recoverMana();
            }
        }while(aliveExists(allies) && aliveExists(enemies));
        if(aliveExists(allies)) System.out.print("YOU WIN!");
        else System.out.print("YOU LOSE! \uD83D\uDE02\uD83D\uDE02\uD83D\uDE02\uD83D\uDE02\uD83D\uDE02\uD83D\uDE02");
    }

    protected boolean aliveExists(ArrayList<Person> team){
        byte length = (byte)team.size();
        boolean oneAlive = false;
        for(int i = 0; i < length; i++){
            if((team.get(i)).getCurrentHealth() > 0){
                oneAlive = true;
                break;
            }
        }
        return oneAlive;
    }

    protected void deadTargetHandler(ArrayList<Person> targetAllegiance, Person target){
        if(target.getCurrentHealth() <= 0){
            System.out.println(target.name + "has fallen!");
            targetAllegiance.remove(target);
        }
    }
}
