package characters;

import java.util.ArrayList;
import java.util.Arrays;

public class Teams {
    private static final int MAX_TEAM_SIZE = 3;

    private static final ArrayList<GameCharacter> alliedTeam = new ArrayList<>();
    private static final ArrayList<GameCharacter> enemyTeam = new ArrayList<>();
    private static int alliedTeamCount = 0;
    private static int enemyTeamCount = 0;

    public static void addToAlliedTeam(GameCharacter character) {
        if (alliedTeamCount >= MAX_TEAM_SIZE || character == null) return;
        character.setAllyStatus(true);
        alliedTeamCount++;
        alliedTeam.add(character);
    }

    public static void addToEnemyTeam(GameCharacter character) {
        if (enemyTeamCount >= MAX_TEAM_SIZE || character == null) return;
        character.setAllyStatus(false);
        enemyTeamCount++;
        enemyTeam.add(character);
    }

    public static void clearAlliedTeam() {
        alliedTeam.clear();
        alliedTeamCount = 0;
    }

    public static GameCharacter popAlliedTeam() {
        return alliedTeam.removeLast();
    }


    public static void clearEnemyTeam() {
        enemyTeam.clear();
        enemyTeamCount = 0;
    }

    // --- Getters ---
    public static ArrayList<GameCharacter> getAlliedTeam() {
        return alliedTeam;
    }

    public static ArrayList<GameCharacter> getEnemyTeam() {
        return enemyTeam;
    }

    public static int getAlliedTeamCount() {
        return alliedTeamCount;
    }

    public static int getEnemyTeamCount() {
        return enemyTeamCount;
    }
}
