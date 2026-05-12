package logic;

import characters.*;
import utils.Utility;

import java.util.ArrayList;

public class LevelManager {
    private static final int MAX_LEVELS = 10;
    private static int currentLevelNumber = 0;
    static int levelCount = 0;
    private static Level currentLevel = null;
    private static GameCharacter playerCharacter = null;

    private static ArrayList<Level> levels = new ArrayList<>(MAX_LEVELS);
    private static final ArrayList<GameCharacter> allCharacters = Utility.getAllCharacters();


    public static void setBossLevel(GameCharacter boss){
        Level bossLevel = levels.get(MAX_LEVELS-1);
        bossLevel.getAlliedTeam().clear();
        bossLevel.getEnemyTeam().clear();

        boss = switch(boss.getCharacterId()){
            case 1 -> new Aero(150, 120, 22, 25);
            case 2 -> new Kaelis(150, 120, 22, 25);
            case 3 -> new Kangel(150, 120, 22, 25);
            case 4 -> new Kayden(150, 120, 22, 25);
            case 5 -> new Maelor(150, 120, 22, 25);
            case 6 -> new Psalm(150, 120, 22, 25);
            case 7 -> new Ripper(150, 120, 22, 25);
            case 8 -> new Veyrion(150, 120, 22, 25);
            case 9 -> new ZenStream(150, 120, 22, 25);
            default -> throw new IllegalStateException("Unexpected value: " + boss.getCharacterId());
        };

        bossLevel.addToAllyTeam(playerCharacter);
        bossLevel.addToEnemyTeam(boss);
    }

    public static int getCurrentLevelNumber(){
        return currentLevelNumber;
    }

    public static void setCurrentLevelNumber(int currentLevelNumber){
        if(currentLevelNumber > MAX_LEVELS || currentLevelNumber <= 0) return;
        LevelManager.currentLevelNumber = currentLevelNumber;
        currentLevel = levels.get(currentLevelNumber-1);
    }

    public static Level getCurrentLevel(){
        return currentLevel;
    }

    public static void initLevels(){
        levels.clear();
        for(int i = 0; i < MAX_LEVELS; i++){

            levels.add(new Level());
            if (levels.size() > MAX_LEVELS) {
                levels.removeLast();
            }
            if(i == MAX_LEVELS-1){
                setBossLevel(playerCharacter);
                break;
            }
            else {
                ArrayList<GameCharacter> availableCharacters = new ArrayList<>();
                for (GameCharacter character : allCharacters) {
                    availableCharacters.add(character.clone());
                }


                levels.get(i).addToAllyTeam(playerCharacter);
                levels.get(i).addToEnemyTeam(availableCharacters.get(i));
            }
//            availableCharacters.remove(i);
//            Random random = new Random();
//            int randomNumber = random.nextInt(availableCharacters.size());
//            levels.get(i).addToEnemyTeam(availableCharacters.get(randomNumber).clone());
//            availableCharacters.remove(randomNumber);
//
//            randomNumber = random.nextInt(availableCharacters.size());
//            levels.get(i).addToAllyTeam(availableCharacters.get(randomNumber).clone());
        }
    }

        public static void refreshCurrentLevel(){
        if(currentLevelNumber == MAX_LEVELS) {
            setBossLevel(playerCharacter);
            return;
        }
        ArrayList<GameCharacter> availableCharacters = new ArrayList<>();

        for(GameCharacter character : allCharacters){
            availableCharacters.add(character.clone());
        }
        int currentLevelIndex = currentLevelNumber - 1;
        ArrayList<GameCharacter> alliedTeam = levels.get(currentLevelIndex).getAlliedTeam();
        ArrayList<GameCharacter> enemyTeam = levels.get(currentLevelIndex).getEnemyTeam();

        alliedTeam.clear();
        enemyTeam.clear();

        alliedTeam.add(playerCharacter);
        enemyTeam.add(availableCharacters.get(currentLevelIndex));


//        levels.get(currentLevelNumber-1).getAlliedTeam().removeLast();
//        levels.get(currentLevelNumber-1).getEnemyTeam().clear();
//
//        levels.get(currentLevelNumber-1).addToEnemyTeam(availableCharacters.get(currentLevelNumber-1).clone());
//        availableCharacters.remove(currentLevelNumber-1);

//        int randomIndex = ThreadLocalRandom.current().nextInt(availableCharacters.size());
//        levels.get(currentLevelNumber-1).addToEnemyTeam(availableCharacters.get(randomIndex).clone());
//        availableCharacters.remove(randomIndex);
//
//        randomIndex = ThreadLocalRandom.current().nextInt(availableCharacters.size());
//        levels.get(currentLevelNumber-1).addToAllyTeam(availableCharacters.get(randomIndex).clone());
    }

    public static int getMaxLevels(){
        return MAX_LEVELS;
    }

    public static void setCurrentPlayerCharacter(GameCharacter character){
        playerCharacter = character.clone();
        playerCharacter.setMaxHealth(playerCharacter.getMaxHP() + 90); // game too hard :(
        playerCharacter.setMaxMana(playerCharacter.getMaxMana() + 50);
    }
}

