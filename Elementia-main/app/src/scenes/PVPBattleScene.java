package scenes;

import characters.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class PVPBattleScene extends JPanel {
    private Image leftBgImage;
    private Image rightBgImage;
    private GameCharacter currentActor;
    private Skill selectedSkill = null;

    private JPanel alliesPanel, enemiesPanel, leftSkillPanel, rightSkillPanel;
    private JLabel resultLabel;
    private BattleLog battleLog;

    private int currentAllyIndex = 0;
    private int currentEnemyIndex = 0;
    private boolean allyTurn = true;
    private boolean battleEnded = false;

    public PVPBattleScene(Elementia frame) {
        setLayout(new GridLayout(1, 2));

        try {
            leftBgImage = ImageIO.read(getClass().getResource("/resources/LevelBackgrounds/Level5Background.png"));
            rightBgImage = ImageIO.read(getClass().getResource("/resources/LevelBackgrounds/Level5Backgroundmirror.png"));
        } catch (IOException e) {
            System.err.println("Background images not found!");
        }

        JPanel leftSide = new JPanel(new BorderLayout());
        leftSide.setOpaque(false);

        JButton backBtn = Utility.createButton("Back");
        backBtn.addActionListener(e -> frame.showScreen("PVPCharacterSelect"));
        leftSide.add(backBtn, BorderLayout.NORTH);

        alliesPanel = new JPanel(new GridBagLayout());
        alliesPanel.setOpaque(false);
        leftSide.add(alliesPanel, BorderLayout.CENTER);

        leftSkillPanel = new JPanel(new FlowLayout());
        leftSkillPanel.setOpaque(false);
        leftSide.add(leftSkillPanel, BorderLayout.SOUTH);

        JPanel rightSide = new JPanel(new BorderLayout());
        rightSide.setOpaque(false);

        enemiesPanel = new JPanel(new GridBagLayout());
        enemiesPanel.setOpaque(false);
        rightSide.add(enemiesPanel, BorderLayout.CENTER);

        rightSkillPanel = new JPanel(new FlowLayout());
        rightSkillPanel.setOpaque(false);
        rightSide.add(rightSkillPanel, BorderLayout.SOUTH);

        resultLabel = new JLabel("", SwingConstants.CENTER);
        resultLabel.setFont(new Font("Arial", Font.BOLD, 36));
        resultLabel.setForeground(Color.WHITE);
        resultLabel.setVisible(false);

        setLayout(new OverlayLayout(this));
        add(resultLabel);
        add(new JPanel(new GridLayout(1, 2)) {{
            setOpaque(false);
            add(leftSide);
            add(rightSide);
        }});

        // Initialize battle log
        battleLog = new BattleLog();
        battleLog.setPreferredSize(new Dimension(300, 150));
        setLayout(new BorderLayout());
        add(battleLog, BorderLayout.SOUTH);

        updateCharacterPanels();
        nextTurn();
    }

    private void nextTurn() {
        if (battleEnded) return;

        GameCharacter[] allies = Teams.getAlliedTeam();
        GameCharacter[] enemies = Teams.getEnemyTeam();

        if (isTeamDead(allies)) {
            showResult(false);
            return;
        }
        if (isTeamDead(enemies)) {
            showResult(true);
            return;
        }

        if (allyTurn) {
            while (currentAllyIndex < allies.length &&
                    (allies[currentAllyIndex] == null || allies[currentAllyIndex].getCurrentHP() <= 0)) {
                currentAllyIndex++;
            }

            if (currentAllyIndex >= allies.length) {
                allyTurn = false;
                currentAllyIndex = 0;
                nextTurn();
                return;
            }

            GameCharacter actor = allies[currentAllyIndex];
            actor.setCurrentMana(Math.min(actor.getCurrentMana() + actor.getManaRecovery(), actor.getMaxMana()));
            battleLog.addTurn(actor.getName() + " (Left)");
            showSkillsForActor(actor, leftSkillPanel, true);
        } else {
            while (currentEnemyIndex < enemies.length &&
                    (enemies[currentEnemyIndex] == null || enemies[currentEnemyIndex].getCurrentHP() <= 0)) {
                currentEnemyIndex++;
            }

            if (currentEnemyIndex >= enemies.length) {
                allyTurn = true;
                currentEnemyIndex = 0;
                nextTurn();
                return;
            }

            GameCharacter actor = enemies[currentEnemyIndex];
            actor.setCurrentMana(Math.min(actor.getCurrentMana() + actor.getManaRecovery(), actor.getMaxMana()));
            battleLog.addTurn(actor.getName() + " (Right)");
            showSkillsForActor(actor, rightSkillPanel, false);
        }
    }

    private boolean isTeamDead(GameCharacter[] team) {
        for (GameCharacter ch : team)
            if (ch != null && ch.getCurrentHP() > 0)
                return false;
        return true;
    }

    private void addCharacters(JPanel panel, GameCharacter[] team, boolean isAlly) {
        panel.removeAll();
        for (GameCharacter ch : team) {
            if (ch == null) continue;
            CharacterView view = new CharacterView(ch, ch.getImage());
            view.setClickListener(clicked -> {
                if (selectedSkill != null && currentActor != null) {
                    handleSkillUse(currentActor, selectedSkill, clicked);
                }
            });
            panel.add(view);
        }
        panel.revalidate();
        panel.repaint();
    }

    private void handleSkillUse(GameCharacter user, Skill skill, GameCharacter target) {
        boolean success = user.useSkill(skill, target);
        if (success && target.getCurrentHP() <= 0) {
            removeCharacterFromTeam(target, target.isAlly() ? Teams.getAlliedTeam() : Teams.getEnemyTeam());
            battleLog.addDefeated(target.getName());
        } else if (success) {
            int damage = (int) ((user.getAttack() + skill.getAttackUp()) * skill.getMultiplier()) - target.getDefense();
            if (damage < 0) damage = 0;
            battleLog.addSkillUse(user.getName(), skill.getName(), target.getName(), damage);
        }

        updateCharacterPanels();
        selectedSkill = null;

        if (allyTurn) currentAllyIndex++;
        else currentEnemyIndex++;

        nextTurn();
    }

    private void showSkillsForActor(GameCharacter actor, JPanel skillPanel, boolean isAlly) {
        skillPanel.removeAll();
        if (actor == null) return;

        currentActor = actor;

        for (Skill skill : actor.getSkills()) {
            if (skill == null) continue;
            JButton skillButton = new JButton(skill.getName());
            skillButton.setFocusPainted(false);
            skillButton.setBackground(isAlly ? new Color(70, 110, 220) : new Color(220, 90, 90));
            skillButton.setForeground(Color.WHITE);
            skillButton.setEnabled(actor.getCurrentMana() >= skill.getManaCost());
            skillButton.addActionListener(e -> selectedSkill = skill);
            skillPanel.add(skillButton);
        }

        if (isAlly) {
            rightSkillPanel.removeAll();
        } else {
            leftSkillPanel.removeAll();
        }

        skillPanel.revalidate();
        skillPanel.repaint();
        updateCharacterPanels();
    }

    private void removeCharacterFromTeam(GameCharacter target, GameCharacter[] team) {
        for (int i = 0; i < team.length; i++) {
            if (team[i] == target) {
                team[i] = null;
                break;
            }
        }
    }

    private void showResult(boolean playerWon) {
        battleEnded = true;
        resultLabel.setText(playerWon ? "Player 1 Wins!" : "Player 2 Wins!");
        resultLabel.setForeground(playerWon ? new Color(80, 255, 80) : new Color(255, 80, 80));
        resultLabel.setVisible(true);
        resultLabel.repaint();

        battleLog.addResult(playerWon ? "Player 1 WINS!" : "Player 2 WINS!");

        leftSkillPanel.removeAll();
        rightSkillPanel.removeAll();
        revalidate();
        repaint();
    }

    private void updateCharacterPanels() {
        addCharacters(alliesPanel, Teams.getAlliedTeam(), true);
        addCharacters(enemiesPanel, Teams.getEnemyTeam(), false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int midX = getWidth() / 2;
        if (leftBgImage != null) g.drawImage(leftBgImage, 0, 0, midX, getHeight(), null);
        if (rightBgImage != null) g.drawImage(rightBgImage, midX, 0, midX, getHeight(), null);
    }
}
