//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import elementia.Elementia;

import javax.swing.*;


public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Elementia().setVisible(true);
        });

//            Scanner scanner = new Scanner(System.in);
//            ArrayList<Person> alliedTeam = new ArrayList<>();
//            ArrayList<Person> enemyTeam = new ArrayList<>();
//
//        System.out.println("Choose your team (maximum of three):");
//            int input;
//            do{
//                System.out.println("[1] Aero");
//                System.out.println("[2] Assassin");
//                System.out.println("[0] Close");
//
//
//                input = scanner.nextInt();
//                switch(input){
//                    case 1:
//                        alliedTeam.add(new Aero());
//                        break;
//                    case 2:
//                        alliedTeam.add(new Assassin());
//                        break;
//                }
//                if(alliedTeam.isEmpty()){
//                    System.out.println("Choose at least one character.");
//                    input = -1;
//                }
//            }while(input != 0 && alliedTeam.size() < 3);
//
//            System.out.print("Lever select (1-5): ");
//
//            do{
//                input = scanner.nextInt();
//                switch(input){
//                    case 1:
//                        enemyTeam.add(new Assassin());
//                        break;
//                    case 2:
//                        enemyTeam.add(new Assassin());
//                        enemyTeam.add(new Assassin());
//                        break;
//                    case 3:
//                        enemyTeam.add(new Aero());
//                        enemyTeam.add(new Assassin());
//                }
//
//            }while(input < 1 || input > 5);
//
//
//            new CombatHandler2(alliedTeam, enemyTeam);
//
    }
}
