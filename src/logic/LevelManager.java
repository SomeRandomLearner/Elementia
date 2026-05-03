package logic;

import characters.*;

import java.util.ArrayList;

public class LevelManager {
    private static final int MAX_LEVELS = 10;
    static int levelCount = 0;
    private static int currentLevelNumber = 0;
    private static Level currentLevel = null;

    private static ArrayList<Level> levels = new ArrayList<>();

    static {
        for(int i = 0; i < MAX_LEVELS; i++){
            levels.add(new Level());
        }
        levels.get(0).addToEnemyTeam(new Aero());
        levels.get(1).addToEnemyTeam(new Kaelis());
        levels.get(2).addToEnemyTeam(new Kangel());
        levels.get(3).addToEnemyTeam(new Kayden());
        levels.get(4).addToEnemyTeam(new Maelor());
        levels.get(5).addToEnemyTeam(new Psalm());
        levels.get(6).addToEnemyTeam(new Ripper());
        levels.get(7).addToEnemyTeam(new Veyrion());
        levels.get(8).addToEnemyTeam(new ZenStream());
    }

    public static void setBossLevel(GameCharacter boss){
        if(levels.get(MAX_LEVELS - 1) != null) {
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

