package logic;

import characters.GameCharacter;

import java.util.ArrayList;

public class Level{
    private boolean isCleared = false;
    private final ArrayList<GameCharacter> enemyTeam;
    private final ArrayList<GameCharacter> allyTeam;

    public Level(){;
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
        enemyTeam.add(character);
    }
    void addToAllyTeam(GameCharacter character){
        allyTeam.add(character);
    }

    public boolean getIsCleared(){
        return isCleared;
    }

    public void setIsCleared(boolean isCleared){
        this.isCleared = isCleared;
    }
}