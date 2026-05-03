package scenes;

import characters.GameCharacter;
import logic.BattleEventListener;
import logic.Skill;
import logic.TurnResult;
import utils.Level;
import utils.LevelManager;
import utils.Utility;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Random;

public class ArcadeBattleScene extends BattleScene{
    public ArcadeBattleScene(Elementia frame){
        super(frame);
        backBtn.addActionListener(e -> {
            repaint();
            if(hasGameEnded){
                bottomPanel.removeAll();
                bottomPanel.add(player1SkillPanel, BorderLayout.WEST);
                bottomPanel.add(timerPanel, BorderLayout.CENTER);

                bottomPanel.revalidate();
                bottomPanel.repaint();
                hasGameEnded = false;
            }
            frame.showScreen("LevelSelect");
        });
    }

    @Override
    public void startGame(){
        winCounterLabel.setText("0 / 0");
        roundNumber = 1;
        hasRoundEnded = false;
        hasGameEnded = false;
        selectedTarget = null;
        displayCharacterViews();

        if(battleLogic == null){
            System.out.println("Error: BattleLogic is null.");
            return;
        }
        battleLogic.setBattleEventListener(new BattleEventListener() {
            @Override
            public void onTurnStarted(int currentPlayerTurn, GameCharacter currentCharacter) {
                if(currentPlayerTurn == 1){
                    currentTurnLabel.setText("Your Turn!");
                    currentTurnLabel.setForeground(Color.RED);

                    player1SkillPanel.setVisible(true);

                    player1SkillPanel.removeAll();
                    for(Skill skill : currentCharacter.getSkills()){
                        if(skill == null) break;
                        player1SkillPanel.add(getJButton(currentCharacter, skill));
                    }

                    player1SkillPanel.revalidate();
                    player1SkillPanel.repaint();
                }
                else{
                    Timer enemyTurnTimer = new Timer(2000, e -> {
                        Random random = new Random();
                        selectedTarget = battleLogic.getActivePlayer1Team().get(random.nextInt(battleLogic.getActivePlayer1Team().size()));
                        battleLogic.setTargetCharacter(selectedTarget);

                        Skill[] validSkills = new Skill[3];
                        int count = 0;
                        for(Skill skill: currentCharacter.getSkills()){
                            if(skill.isOnCooldown()) continue;
                            if(currentCharacter.getCurrentMana() >= skill.getManaCost()){
                                validSkills[count++] = skill;
                            }
                        }

                        Skill selectedSkill = validSkills[random.nextInt(count)];
                        battleLogic.setSelectedSkill(selectedSkill);
                        leftPanel.setSelectedSkill(selectedSkill);

                        battleLogic.currentCharacterUseSkillOnTarget();
                        leftPanel.playSkillAnimation();

                        ((Timer)e.getSource()).stop();
                    });
                    enemyTurnTimer.start();
                    currentTurnLabel.setText("Enemies' Turn!");
                    currentTurnLabel.setForeground(Color.BLUE);
                    player1SkillPanel.setVisible(false);
                }
                bottomPanel.revalidate();
                bottomPanel.repaint();

                if(currentPlayerTurn == 1){
                    setAllComponents(leftPanel, false);
                    setAllComponents(rightPanel, true);
                }
                else{
                    setAllComponents(leftPanel, true);
                    setAllComponents(rightPanel, false);
                }
                rightPanel.repaint();
                leftPanel.repaint();

                timerCount = 4;
                timerLabel.setText(String.valueOf(timerCount));
                if(currentPlayerTurn == 1) {
                    timerPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
                    timerPanel.setVisible(true);
                }
                else timerPanel.setVisible(false);

                timerPanel.revalidate();

                if (timer != null && timer.isRunning()) {
                    timer.stop();
                }
                timer = new Timer(1000, e -> {
                    if(timerCount > 0){
                        timerCount--;
                        timerLabel.setText(String.valueOf(timerCount));
                    }
                    else{
                        ((Timer)e.getSource()).stop();
                        battleLogic.nextTurn();
                    }
                });
                if(currentPlayerTurn == 1) timer.start();

            }

            @Override
            public void onAttackResolved(TurnResult result) {
                if (timer != null && timer.isRunning()) {
                    timer.stop();
                }
                leftPanel.revalidate();
                rightPanel.revalidate();
                leftPanel.repaint();
                rightPanel.repaint();
            }

            @Override
            public void onRoundStarted(int currentPlayerTurn) {
                displayCharacterViews();
            }

            @Override
            public void onRoundEnded(int winningPlayer) {
                roundWinner = winningPlayer;
                hasRoundEnded = true;
                winCounterLabel.setText(battleLogic.getWinCount(1) + " / " + battleLogic.getWinCount(2));


                topPanel.repaint();
                centerPanel.revalidate();
                centerPanel.repaint();

                repaint();
                Timer roundMessageTimer;

                roundMessageTimer = new Timer(2000, e -> {
                    roundNumber++;
                    hasRoundEnded = false;
                    repaint();
                    ((Timer)e.getSource()).stop();
                });
                roundMessageTimer.start();
            }

            @Override
            public void onGameEnded(int winningPlayer) {
                gameWinner = winningPlayer;
                hasGameEnded = true;

                leftPanel.removeAll();
                rightPanel.removeAll();

                JButton rematchBtn = Utility.createButton("Rematch");

                JPanel buttonWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 20));
                buttonWrapper.setOpaque(false);
                buttonWrapper.setBorder(new EmptyBorder(0, 0, 40, 0));
                buttonWrapper.add(rematchBtn);

                bottomPanel.removeAll();
                bottomPanel.add(buttonWrapper, BorderLayout.CENTER);

                rematchBtn.addActionListener(e -> {
                    bottomPanel.removeAll();
                    bottomPanel.add(player1SkillPanel, BorderLayout.WEST);
                    bottomPanel.add(timerPanel, BorderLayout.CENTER);

                    bottomPanel.revalidate();
                    bottomPanel.repaint();
                    hasGameEnded = false;
                    startGame();
                });

                revalidate();
                repaint();
            }
        });

        battleLogic.startGame();
        setAllComponents(leftPanel, false);
        setAllComponents(rightPanel, false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (bgImage != null) g.drawImage(bgImage, 0, 0, getWidth(), getHeight(), null);

        if (hasGameEnded) {
            if(gameWinner == 1) {
                displayMessage(g, "You Win!");
                Level currentLevel = LevelManager.getCurrentLevel();
                if(!currentLevel.getIsCleared()) {
                    frame.getLevelSelect().incrementCompletedLevels();
                    frame.getLevelSelect().unlockLevels();
                    currentLevel.setIsCleared(true);
                }
            }
            else displayMessage(g, "You Lose!");
        }
        else if(hasRoundEnded) {
            if(roundWinner == 1) displayMessage(g, "You Win Round " + roundNumber);
            else displayMessage(g, "You Lost Round" + roundNumber);
        }
    }
}
