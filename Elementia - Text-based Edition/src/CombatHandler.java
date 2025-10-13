import characters.*;
import characters.Person;
import characters.heroes.Aero;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class CombatHandler {

    public CombatHandler() {
        Scanner scanner = new Scanner(System.in);
        Aero player = new Aero();
        Assassin enemy = new Assassin();
        List<Person> players = new ArrayList<>(List.of(player, enemy));

        Collections.shuffle(players);
        for (Person p : players) {
            System.out.println(p.name + " enters the battlefield!");
        }

        do{
            for (Person p : players) {
                if(p.getCurrentHealth() <= 0) continue;
                System.out.println("[" + p.name + "'s turn!]\n");
                Person target = null;
                boolean valid = false;
                while (!valid) {
                    try {
                        int i = 1;
                        int skips = 0;
                        for (Person l : players) {
                            if(l == p){
                                skips++;
                                continue;
                            }
                            System.out.println("[" + i + "] " + l.name + " " + l.getCurrentHealth() + "/" + l.getMaxHealth() + "hp " + l.getCurrentMana() + "/" + l.getMaxMana() + "mp");
                            i++;
                        }
                        System.out.print("Select a target: ");
                        int targetInput = scanner.nextInt();
                        target = players.get(targetInput - 1 + skips);
                        valid = true;
                    } catch (Exception e) {
                        System.out.println("Invalid target. Try again.");
                        scanner.nextLine();
                    }
                }
                valid = false;
                while(!valid) {
                    System.out.println("[1] " + p.getSkill1Name() + " [2] " + p.getSkill2Name() + " [3] " + p.getSkill3Name());
                    System.out.println(p.getCurrentMana() + "/" + p.getMaxMana() + "mp");
                    System.out.print("Select a skill: ");
                    int skill = scanner.nextInt();
                    try {
                        switch (skill) {
                            case 1 -> p.skill1(target);
                            case 2 -> p.skill2(target);
                            case 3 -> p.skill3(target);
                            default -> {
                                System.out.println("Invalid");
                                continue;
                            }
                        }
                        valid = true;
                    } catch (Exception e) {
                        System.out.println(e);

                    }
                }
            }


            player.recoverMana();
            enemy.recoverMana();
            System.out.print("\033[H\033[2J");
            System.out.flush();
        }while(player.getCurrentHealth() > 0 && enemy.getCurrentHealth() > 0);

        if(player.getCurrentHealth() <= 0) System.out.println(player.name + " wins!");
        else System.out.println(enemy.name + " wins!");
    }


}
