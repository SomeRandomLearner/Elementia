package logic;

import characters.*;
import jdk.jshell.execution.Util;
import utils.Utility;

import java.util.ArrayList;

public class LevelManager {
    private static final int MAX_LEVELS = 10;
    static int levelCount = 0;
    private static int currentLevelNumber = 0;
    private static Level currentLevel = null;

    private static ArrayList<Level> levels = new ArrayList<>();

    static {
        ArrayList<GameCharacter> allCharacters = Utility.getAllCharacters();
        for(int i = 0; i < MAX_LEVELS-1; i++){
            levels.add(new Level());
            levels.get(i).addToEnemyTeam(allCharacters.get(i).clone());
        }
    }

    public static void setBossLevel(GameCharacter boss){

            if (levels.size() >= MAX_LEVELS) {
                levels.remove(MAX_LEVELS - 1);
                levelCount--;
            }

        GameCharacter upgradedBoss = switch(boss.getCharacterId()){
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

        upgradedBoss.replaceSkillsWithClone();
        Level level = new Level();
        level.addToEnemyTeam(upgradedBoss);
        levels.add(level);
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
}

