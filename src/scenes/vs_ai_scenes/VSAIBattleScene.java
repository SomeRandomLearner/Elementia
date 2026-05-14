package scenes.vs_ai_scenes;

import characters.GameCharacter;
import logic.BattleEventListener;
import logic.Level;
import logic.LevelManager;
import logic.Skill;
import scenes.AbstractBattleScene;
import scenes.Elementia;
import scenes.Scenes;
import utils.CharacterView;
import utils.Utility;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class VSAIBattleScene extends AbstractBattleScene {

    public VSAIBattleScene(Elementia frame){
        super(frame);

        timerLabel.setFont(new Font("Arial", Font.BOLD, 30));
        timerLabel.setForeground(Color.WHITE);

        backButton.addActionListener(e -> {
            repaint();
            if(timer != null) timer.stop();
            if(enemyTurnTimer != null) enemyTurnTimer.stop();
            timer = enemyTurnTimer = null;

                bottomPanel.removeAll();
                setupBottomPanelLayout();
                bottomPanel.revalidate();
                bottomPanel.repaint();
                hasGameEnded = false;


            frame.showScreen(Scenes.VS_AI_CHARACTER_SELECT);
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
        winCounterLabel.setText("0 / 0");
        isFirstRound = true;
        roundNumber = 1;
        hasRoundEnded = false;
        hasGameEnded = false;
        selectedTarget = null;

        displayCharacterViews();
        setupBottomPanelLayout();
        centerPanel.revalidate();
        centerPanel.repaint();

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

                        ArrayList<GameCharacter> validTargets = new ArrayList<>();
                        for(GameCharacter character: battleLogic.getActivePlayer1Team()){
                            if(!character.isDead()){
                                validTargets.add(character);
                            }
                        }
                        if(validTargets.isEmpty()) return;

                        GameCharacter selectedTarget = validTargets.get(new Random().nextInt(validTargets.size()));
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

                timerCount = 15;
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
            public void onAttackResolved() {
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

                winCounterLabel.setText(
                        battleLogic.getWinCount(1) + " / " + battleLogic.getWinCount(2)
                );
                topPanel.repaint();

                JButton rematchBtn = Utility.createStyledButton(
                        "REMATCH",
                        new Color(60, 60, 120).brighter(),
                        new Color(40, 40, 80).darker()
                );

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
                    bottomPanel.add(player2SkillPanel, BorderLayout.EAST);

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
    public void setVSAIBattleSceneBackground(int backgroundNumber){
        ImageIcon newIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/LevelBackgrounds/LVL" + backgroundNumber + "_BG.png")));
        bgImage = newIcon.getImage();
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (bgImage != null)
            g.drawImage(bgImage, 0, 0, getWidth(), getHeight(), null);

        if (hasGameEnded) {
            if(gameWinner == 1) {
                displayMessage(g, "You Win!");
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