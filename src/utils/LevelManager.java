package utils;

import characters.GameCharacter;

import java.util.ArrayList;

public class LevelManager {
    private Level currentLevel = null;

    public void setCurrentLevel(Level currentLevel){
        this.currentLevel = currentLevel;
    }
}

class Level{
    private int levelNumber;
    private ArrayList<GameCharacter> alliedTeam;
    private ArrayList<GameCharacter> enemyTeam;

    public Level(int levelNumber, ArrayList<GameCharacter> alliedTeam, ArrayList<GameCharacter> enemyTeam) {
        this.levelNumber = levelNumber;
        this.alliedTeam = alliedTeam;
        this.enemyTeam = enemyTeam;
    }
}
