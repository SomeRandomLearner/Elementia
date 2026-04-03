package logic;

import characters.GameCharacter;

public interface BattleEventListener {
    void onTurnStarted(int currentPlayerTurn, GameCharacter currentCharacter);
    void onAttackResolved(TurnResult result);
    void onRoundStarted(int currentPlayerTurn);
    void onRoundEnded(int winningPlayer);
    void onGameEnded(int winningPlayer);
}
