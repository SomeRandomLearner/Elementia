package logic;

/**
 * @param targetDied private final int damageDealt;
 */
public record TurnResult(String attackerName, String targetName, boolean targetDied) {

}