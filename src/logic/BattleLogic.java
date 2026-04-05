package logic;

import characters.GameCharacter;

import java.util.*;

public class BattleLogic {
    private final int player1 = 1;
    private final int player2 = 2;

    private final List<GameCharacter> player1Team = new ArrayList<>();
    private final List<GameCharacter> player2Team = new ArrayList<>();

    private final List<GameCharacter> activePlayer1Team = new ArrayList<>();
    private final List<GameCharacter> activePlayer2Team = new ArrayList<>();

    private List<GameCharacter> currentTeam = null;
    private List<GameCharacter> opposingTeam = null;

    private Skill selectedSkill = null;
    private int currentPlayerTurn;
    private int currentCharacterTurn;
    private GameCharacter currentCharacter;
    private GameCharacter targetCharacter;

    private boolean isPlayer1FirstTurn;
    private boolean isPlayer2FirstTurn;
    private byte player1WinCount;
    private byte player2WinCount;
    private int previousRoundWinner;

    private BattleEventListener battleEventListener;

    public void startGame(){
        previousRoundWinner = 0;
        player1WinCount = player2WinCount = 0;
        resetActiveTeams(true);
        startRound();
    }

    private void startRound(){
        switch (previousRoundWinner){
            case player1 -> currentPlayerTurn = player2;
            case player2 -> currentPlayerTurn = player1;
            default -> currentPlayerTurn = new Random().nextBoolean() ? player1 : player2; // The first player is random.
        }

        resetActiveTeams(false);
        switch(currentPlayerTurn){
            case player1 -> {
                currentTeam = activePlayer1Team;
                opposingTeam = activePlayer2Team;
            }
            case player2 -> {
                currentTeam = activePlayer2Team;
                opposingTeam = activePlayer1Team;
            }
        }

        if(battleEventListener != null) battleEventListener.onRoundStarted(currentPlayerTurn);

        isPlayer1FirstTurn = isPlayer2FirstTurn = true;
        currentCharacterTurn = 0;
        startTurn();
    }

    private void startTurn(){
        if(currentCharacterTurn >= currentTeam.size()) {
            if (currentPlayerTurn == player1) {
                isPlayer1FirstTurn = false;
                currentPlayerTurn = player2;
                currentTeam = activePlayer2Team;
                opposingTeam = activePlayer1Team;
            } else {
                isPlayer2FirstTurn = false;
                currentPlayerTurn = player1;
                currentTeam = activePlayer1Team;
                opposingTeam = activePlayer2Team;
            }
            currentCharacterTurn = 0;
            startTurn();
            return;
        }

        currentCharacter = currentTeam.get(currentCharacterTurn);

        // skip current character if dead
        if (currentCharacter.isDead()) {
            currentCharacterTurn++;
            startTurn();
            return;
        }

        boolean isFirstTurn = (currentPlayerTurn == player1) ? isPlayer1FirstTurn : isPlayer2FirstTurn;

        if(!isFirstTurn){
            for(Skill skill : currentCharacter.getSkills()){
                skill.incrementCooldownTimer();
            }
        }
        currentCharacter.recoverMana();

        if(battleEventListener != null) battleEventListener.onTurnStarted(currentPlayerTurn, currentCharacter);
    }

    public void currentCharacterUseSkillOnTarget(){
        boolean success = currentCharacter.useSkill(selectedSkill, targetCharacter);
        if(success){
            selectedSkill = null;
            if(battleEventListener != null) {
                TurnResult result = new TurnResult(currentCharacter.getName(), targetCharacter.getName(), targetCharacter.isDead());
                battleEventListener.onAttackResolved(result);
            }

            if(isOpposingTeamAllDeadOrEmpty()){
                if(currentPlayerTurn == player1){
                    player1WinCount++;
                    previousRoundWinner = player1;
                }
                else{
                    player2WinCount++;
                    previousRoundWinner = player2;
                }
                if(battleEventListener != null) battleEventListener.onRoundEnded(previousRoundWinner);

                if(player1WinCount >= 3 || player2WinCount >= 3){
                    endGame();
                }
                else startRound();
            }
            else {
                currentCharacterTurn++;
                startTurn();
            }
        }
    }

    private boolean isOpposingTeamAllDeadOrEmpty(){
        boolean allDeadOrEmpty = true;
        for(GameCharacter character : opposingTeam){
            if(!character.isDead()){
                allDeadOrEmpty = false;
                break;
            }
        }
        return allDeadOrEmpty;
    }

    private void endGame(){
        int gameWinner = (player1WinCount >= 3) ? player1 : player2;
        battleEventListener.onGameEnded(gameWinner);
    }

    private void resetActiveTeams(boolean resetSkills){
        activePlayer1Team.clear();
        activePlayer2Team.clear();

        int count = 0;
        for(GameCharacter character : player1Team){
            activePlayer1Team.add(character.clone());
            if(resetSkills){
                for(Skill skill : activePlayer1Team.get(count++).getSkills()){
                    skill.resetCooldownTimer();
                }
            }
        }

        count = 0;
        for(GameCharacter character : player2Team){
            activePlayer2Team.add(character.clone());
            if(resetSkills){
                for(Skill skill : activePlayer2Team.get(count++).getSkills()){
                    skill.resetCooldownTimer();
                }
            }
        }

    }

    public void addToTeam(int playerNumber, GameCharacter character){
        switch(playerNumber) {
            case player1 -> player1Team.add(character);
            case player2 -> player2Team.add(character);
        }
    }

    public void addAllToTeam(int playerNumber, List<GameCharacter> characters){
        switch(playerNumber) {
            case player1 -> {
                player1Team.addAll(characters);
            }
            case player2 -> {
                player2Team.addAll(characters);
            }
        }
    }

    public void resetCharacterChoices(){
        player1Team.clear();
        player2Team.clear();
    }

    public void setTargetCharacter(GameCharacter character){
        targetCharacter = character;
    }

    public void setBattleEventListener(BattleEventListener battleEventListener){
        this.battleEventListener = battleEventListener;
    }

    public int getCurrentPlayerTurn(){ return currentPlayerTurn;}

    public GameCharacter getCurrentCharacter(){
        return currentCharacter;
    }

    public Skill getSelectedSkill(){
        return selectedSkill;
    }

    public int getWinCount(int playerNumber){
        return (playerNumber == player1)? player1WinCount : player2WinCount;
    }

    public List<GameCharacter> getCurrentTeam(){
        return currentTeam;
    }

    public List<GameCharacter> getOpposingTeam(){
        return opposingTeam;
    }

    public void setSelectedSkill(Skill selectedSkill){
        this.selectedSkill = selectedSkill;
    }

    public List<GameCharacter> getPlayer1Team() {
        return player1Team;
    }
    public List<GameCharacter> getPlayer2Team() {
        return player2Team;
    }

    public List<GameCharacter> getActivePlayer1Team() {
        return activePlayer1Team;
    }
    public List<GameCharacter> getActivePlayer2Team() {
        return activePlayer2Team;
    }
}