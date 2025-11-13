package characters;

import java.util.ArrayList;
import java.util.Arrays;

public class Teams {
    private static final int MAX_TEAM_SIZE = 3;

    private static GameCharacter[] alliedTeam = new GameCharacter[MAX_TEAM_SIZE];
    private static GameCharacter[] enemyTeam = new GameCharacter[MAX_TEAM_SIZE];
    private static int alliedTeamCount = 0;
    private static int enemyTeamCount = 0;

    public static void addToAlliedTeam(GameCharacter character) {
        if (alliedTeamCount >= MAX_TEAM_SIZE || character == null) return;
        character.setAllyStatus(true);
        alliedTeam[alliedTeamCount++] = character;
    }

    public static void addToEnemyTeam(GameCharacter character) {
        if (enemyTeamCount >= MAX_TEAM_SIZE || character == null) return;
        character.setAllyStatus(false);
        enemyTeam[enemyTeamCount++] = character;
    }

    public static void clearAlliedTeam() {
        Arrays.fill(alliedTeam, null);
        alliedTeamCount = 0;
    }

    public static GameCharacter popAlliedTeam() {
        GameCharacter result = alliedTeam[alliedTeamCount];
        alliedTeam[alliedTeamCount--] = null;
        return result;
    }


    public static void clearEnemyTeam() {
        Arrays.fill(enemyTeam, null);
        enemyTeamCount = 0;
    }

    // --- Getters ---
    public static GameCharacter[] getAlliedTeam() {
        return Arrays.copyOf(alliedTeam, alliedTeamCount);
    }

    public static GameCharacter[] getEnemyTeam() {
        return Arrays.copyOf(enemyTeam, enemyTeamCount);
    }

    public static int getAlliedTeamCount() {
        return alliedTeamCount;
    }

    public static int getEnemyTeamCount() {
        return enemyTeamCount;
    }

    // --- (Optional) Helpers for battle logic ---
    /*
    public static GameCharacter[] getAliveAllies() {
        ArrayList<GameCharacter> alive = new ArrayList<>();
        for (int i = 0; i < alliedTeamCount; i++) {
            if (alliedTeam[i] != null && alliedTeam[i].getCurrentHP() > 0) {
                alive.add(alliedTeam[i]);
            }
        }
        return alive.toArray(new GameCharacter[0]);
    }

    public static GameCharacter[] getAliveEnemies() {
        ArrayList<GameCharacter> alive = new ArrayList<>();
        for (int i = 0; i < enemyTeamCount; i++) {
            if (enemyTeam[i] != null && enemyTeam[i].getCurrentHP() > 0) {
                alive.add(enemyTeam[i]);
            }
        }
        return alive.toArray(new GameCharacter[0]);
    }
    */
}
