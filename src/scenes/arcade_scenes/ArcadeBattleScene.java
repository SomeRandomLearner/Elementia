package scenes.arcade_scenes;

import characters.GameCharacter;
import logic.BattleEventListener;
import logic.Skill;
import logic.TurnResult;
import logic.Level;
import logic.LevelManager;
import scenes.AbstractBattleScene;
import scenes.Elementia;
import utils.CharacterView;
import utils.Utility;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class ArcadeBattleScene extends AbstractBattleScene {

    public ArcadeBattleScene(Elementia frame){
        super(frame);

        // ✅ TIMER STYLE FIX (APPLIED ONCE)
        timerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        timerLabel.setForeground(Color.WHITE);

        backButton.addActionListener(e -> {
            repaint();
            if(enemyTurnTimer != null) enemyTurnTimer.stop();
            timer = enemyTurnTimer = null;

            if(hasGameEnded){
                bottomPanel.removeAll();
                setupBottomPanelLayout();
                bottomPanel.revalidate();
                bottomPanel.repaint();
                hasGameEnded = false;
            }

            frame.showScreen("LevelSelect");
            frame.getLevelSelect().unlockLevels();
        });
    }

    private void setupBottomPanelLayout() {
        bottomPanel.removeAll();

        JPanel mainBottomContainer = new JPanel(new BorderLayout());
        mainBottomContainer.setOpaque(false);

        JPanel upperSection = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        upperSection.setOpaque(false);
        upperSection.add(timerPanel);

        JPanel centerSection = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        centerSection.setOpaque(false);
        centerSection.add(player1SkillPanel);

        mainBottomContainer.add(upperSection, BorderLayout.NORTH);
        mainBottomContainer.add(centerSection, BorderLayout.CENTER);

        bottomPanel.add(mainBottomContainer, BorderLayout.CENTER);
    }

    @Override
    public void startGame(){
        setBattleSceneBackground((LevelManager.getCurrentLevelNumber() % 4) + 1);

        winCounterLabel.setText("0 / 0");
        isFirstRound = true;
        roundNumber = 1;
        hasRoundEnded = false;
        hasGameEnded = false;
        selectedTarget = null;

        displayCharacterViews();
        setupBottomPanelLayout();

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
                        player1SkillPanel.add(getSkillButton(currentCharacter, skill));
                    }

                    player1SkillPanel.revalidate();
                    player1SkillPanel.repaint();

                } else {
                    enemyTurnTimer = new Timer(2000, e -> {
                        Random random = new Random();
                        selectedTarget = battleLogic.getActivePlayer1Team()
                                .get(random.nextInt(battleLogic.getActivePlayer1Team().size()));

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

                        CharacterView view = characterToViewMap.get(selectedTarget);
                        view.setSelectedSkill(selectedSkill);
                        view.playSkillAnimation();

                        ((Timer)e.getSource()).stop();
                    });
                    enemyTurnTimer.start();

                    currentTurnLabel.setText("Enemies' Turn!");
                    currentTurnLabel.setForeground(Color.BLUE);
                    player1SkillPanel.setVisible(false);
                }

                setupBottomPanelLayout();
                bottomPanel.revalidate();
                bottomPanel.repaint();

                if(currentPlayerTurn == 1){
                    setAllComponents(leftPanel, false);
                    setAllComponents(rightPanel, true);
                } else {
                    setAllComponents(leftPanel, true);
                    setAllComponents(rightPanel, false);
                }

                rightPanel.repaint();
                leftPanel.repaint();

                timerCount = 4;
                timerLabel.setText(String.valueOf(timerCount));

                if(currentPlayerTurn == 1) {
                    timerPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
                    timerPanel.setVisible(true);
                } else {
                    timerPanel.setVisible(false);
                }

                timerPanel.revalidate();

                if (timer != null && timer.isRunning()) {
                    timer.stop();
                }

                timer = new Timer(1000, e -> {
                    if(timerCount > 0){
                        timerCount--;
                        timerLabel.setText(String.valueOf(timerCount));
                    } else {
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
                setupBottomPanelLayout();
                leftPanel.revalidate();
                rightPanel.revalidate();
                leftPanel.repaint();
                rightPanel.repaint();
            }

            @Override
            public void onRoundStarted(int currentPlayerTurn) {
                if(isFirstRound) {
                    displayCharacterViews();
                    isFirstRound = false;
                    return;
                }

                Timer roundDelayTimer = new Timer(600, e->{
                    displayCharacterViews();
                    ((Timer)e.getSource()).stop();
                });
                roundDelayTimer.start();
            }

            @Override
            public void onRoundEnded(int winningPlayer) {
                roundWinner = winningPlayer;
                hasRoundEnded = true;
                winCounterLabel.setText(
                        battleLogic.getWinCount(1) + " / " + battleLogic.getWinCount(2)
                );

                topPanel.repaint();
                centerPanel.revalidate();
                centerPanel.repaint();
                repaint();

                Timer roundMessageTimer = new Timer(2000, e -> {
                    roundNumber++;
                    hasRoundEnded = false;
                    repaint();
                    setupBottomPanelLayout();
                    ((Timer)e.getSource()).stop();
                });

                roundMessageTimer.start();
            }

            @Override
            public void onGameEnded(int winningPlayer) {
                gameWinner = winningPlayer;
                hasGameEnded = true;
                player1SkillPanel.removeAll();

                if(gameWinner != 1){
                    frame.getLevelSelect().decrementHeart();
                }

                JButton rematchBtn = Utility.createButton("Rematch");

                JPanel buttonWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 20));
                buttonWrapper.setOpaque(false);
                buttonWrapper.setBorder(new EmptyBorder(0, 0, 40, 0));
                buttonWrapper.add(rematchBtn);

                JPanel mainBottomContainer = new JPanel(new BorderLayout());
                mainBottomContainer.setOpaque(false);
                mainBottomContainer.setBorder(new EmptyBorder(40, 0, 40, 0));
                mainBottomContainer.add(buttonWrapper, BorderLayout.CENTER);

                bottomPanel.removeAll();
                bottomPanel.add(mainBottomContainer, BorderLayout.CENTER);

                rematchBtn.addActionListener(e -> {
                    bottomPanel.removeAll();
                    setupBottomPanelLayout();
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

        if (bgImage != null)
            g.drawImage(bgImage, 0, 0, getWidth(), getHeight(), null);

        if (hasGameEnded) {
            if(gameWinner == 1) {
                displayMessage(g, "You Win!");
                Level currentLevel = LevelManager.getCurrentLevel();
                if(!currentLevel.getIsCleared()) {
                    frame.getLevelSelect().incrementCompletedLevels();
                    frame.getLevelSelect().unlockLevels();
                    currentLevel.setIsCleared(true);
                }
            } else {
                displayMessage(g, "You Lose!");
            }
        } else if(hasRoundEnded) {
            if(roundWinner == 1)
                displayMessage(g, "You Win Round " + roundNumber);
            else
                displayMessage(g, "You Lost Round " + roundNumber);
        }
    }
}