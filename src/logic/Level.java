package logic;

import characters.GameCharacter;

import java.util.ArrayList;

public class Level{
    private boolean isCleared = false;
    private final int levelNumber;
    private ArrayList<GameCharacter> enemyTeam;

    public Level(){
        this.levelNumber = ++LevelManager.levelCount;
        enemyTeam = new ArrayList<>();
    }

    public ArrayList<GameCharacter> getEnemyTeam(){
        return enemyTeam;
    }
    void addToEnemyTeam(GameCharacter character){
        enemyTeam.add(character);
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