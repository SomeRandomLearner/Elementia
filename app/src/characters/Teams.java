package characters;

public class Teams {
    static GameCharacter[] alliedTeam = new GameCharacter[3];
    static GameCharacter[] enemyTeam = new GameCharacter[3];
    static int alliedTeamCount = 0;
    static int enemyTeamCount = 0;

    public static void addToAlliedTeam(GameCharacter character){
        if(alliedTeamCount >= 3) return;
        alliedTeam[alliedTeamCount++] = character;
    }

    public static void clearAlliedTeam(){
        for(int i = 0; i < alliedTeamCount; i++){
            alliedTeam[i] = null;
        }
        alliedTeamCount = 0;
    }

    public static void addToEnemyTeam(GameCharacter character){
        if(enemyTeamCount >= 3) return;
        enemyTeam[enemyTeamCount++] = character;
    }

    public static void clearEnemyTeam(){
        for(int i = 0; i < enemyTeamCount; i++){
            enemyTeam[i] = null;
        }
        enemyTeamCount = 0;
    }

}
