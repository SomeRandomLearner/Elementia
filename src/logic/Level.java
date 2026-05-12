package logic;

import characters.GameCharacter;

import java.util.ArrayList;

public class Level{
    private boolean isCleared = false;
    private final int levelNumber;
    private final ArrayList<GameCharacter> enemyTeam;
    private final ArrayList<GameCharacter> allyTeam;

    public Level(){;
        this.levelNumber = ++LevelManager.levelCount;
        enemyTeam = new ArrayList<>();
        allyTeam = new ArrayList<>();
    }

    public ArrayList<GameCharacter> getEnemyTeam(){
        return enemyTeam;
    }
    public ArrayList<GameCharacter> getAlliedTeam(){
        return allyTeam;
    }
    void addToEnemyTeam(GameCharacter character){
        enemyTeam.add(character.clone());
    }
    void addToAllyTeam(GameCharacter character){
        allyTeam.add(character);
    }

    public int getLevelNumber(){
        return levelNumber;
    }

    public boolean getIsCleared(){
        return isCleared;
    }

    public void setIsCleared(boolean isCleared){
        this.isCleared = isCleared;
    }
}