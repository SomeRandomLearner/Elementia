package scenes;

import characters.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Random;

public class VSAIBattleScene extends JPanel {
    private Image leftBgImage;
    private Image rightBgImage;
    private GameCharacter currentActor;
    private Skill selectedSkill = null;

    private JPanel alliesPanel, enemiesPanel, skillsPanel;
    private JLabel resultLabel; // for "You Won!" / "You Lost!"

    private int currentAllyIndex = 0;
    private boolean allyTurn = true;
    private boolean battleEnded = false;

    public VSAIBattleScene(Elementia frame) {
        setLayout(new GridLayout(1, 2));

        int level = LevelSelectScene.selectedLevel;
        try {
            leftBgImage = ImageIO.read(getClass().getResource("/resources/LevelBackgrounds/Level" + level + "Background.png"));
            rightBgImage = ImageIO.read(getClass().getResource("/resources/LevelBackgrounds/Level" + level + "Backgroundmirror.png"));
        } catch (IOException e) {
            System.err.println("Background images not found!");
        }

        JPanel leftSide = new JPanel(new BorderLayout());
        leftSide.setOpaque(false);

        JButton backBtn = Utility.createButton("Back");
        backBtn.addActionListener(e -> frame.showScreen("LevelSelect"));
        leftSide.add(backBtn, BorderLayout.NORTH);

        alliesPanel = new JPanel(new GridBagLayout());
        alliesPanel.setOpaque(false);
        leftSide.add(alliesPanel, BorderLayout.CENTER);

        skillsPanel = new JPanel(new FlowLayout());
        skillsPanel.setOpaque(false);
        leftSide.add(skillsPanel, BorderLayout.SOUTH);

        JPanel rightSide = new JPanel(new BorderLayout());
        rightSide.setOpaque(false);
        enemiesPanel = new JPanel(new GridBagLayout());
        enemiesPanel.setOpaque(false);
        rightSide.add(enemiesPanel, BorderLayout.CENTER);

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
            // Skip defeated allies
            while (currentAllyIndex < allies.length &&
                    (allies[currentAllyIndex] == null || allies[currentAllyIndex].getCurrentHP() <= 0)) {
                currentAllyIndex++;
            }

            if (currentAllyIndex >= allies.length) {
                allyTurn = false;
                currentAllyIndex = 0;
                SwingUtilities.invokeLater(this::startEnemyPhase);
                return;
            }

            GameCharacter actor = allies[currentAllyIndex];
            actor.setCurrentMana(Math.min(actor.getCurrentMana() + actor.getManaRecovery(), actor.getMaxMana()));
            showSkillsForActor(actor);
        } else {
            SwingUtilities.invokeLater(this::startEnemyPhase);
        }
    }

    private void startEnemyPhase() {
        GameCharacter[] enemies = Teams.getEnemyTeam();
        GameCharacter[] allies = Teams.getAlliedTeam();

        java.util.List<GameCharacter> actingEnemies = new java.util.ArrayList<>();
        for (GameCharacter e : enemies)
            if (e != null && e.getCurrentHP() > 0) {
                e.setCurrentMana(Math.min(e.getCurrentMana() + e.getManaRecovery(), e.getMaxMana()));
                actingEnemies.add(e);
            }

        if (actingEnemies.isEmpty()) {
            allyTurn = true;
            currentAllyIndex = 0;
            nextTurn();
            return;
        }

        final int delayMs = 700;
        final int[] index = {0};
        Timer timer = new Timer(delayMs, null);

        timer.addActionListener(evt -> {
            if (index[0] >= actingEnemies.size()) {
                timer.stop();
                allyTurn = true;
                currentAllyIndex = 0;
                updateCharacterPanels();
                nextTurn();
                return;
            }

            GameCharacter enemy = actingEnemies.get(index[0]);
            if (enemy == null || enemy.getCurrentHP() <= 0) {
                index[0]++;
                return;
            }

            GameCharacter target = getRandomLivingCharacter(allies);
            if (target == null) {
                showResult(false);
                timer.stop();
                return;
            }

            Skill[] skills = enemy.getSkills();
            java.util.List<Skill> available = new java.util.ArrayList<>();
            if (skills != null) for (Skill s : skills) if (s != null) available.add(s);

            if (available.isEmpty()) {
                index[0]++;
                return;
            }

            Skill skill = available.get(new Random().nextInt(available.size()));
            boolean success = enemy.useSkill(skill, target);

            if (success && target.getCurrentHP() <= 0) {
                removeCharacterFromTeam(target, allies);
            }

            updateCharacterPanels();
            index[0]++;
        });

        timer.setInitialDelay(0);
        timer.start();
    }

    private boolean isTeamDead(GameCharacter[] team) {
        for (GameCharacter ch : team)
            if (ch != null && ch.getCurrentHP() > 0)
                return false;
        return true;
    }

    private GameCharacter getRandomLivingCharacter(GameCharacter[] team) {
        java.util.List<GameCharacter> alive = new java.util.ArrayList<>();
        for (GameCharacter c: team)
            if (c != null && c.getCurrentHP() > 0)
                alive.add(c);
        if (alive.isEmpty()) return null;
        return alive.get(new Random().nextInt(alive.size()));
    }

    private void addCharacters(JPanel panel, GameCharacter[] team, boolean isAlly) {
        panel.removeAll();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);

        for (GameCharacter ch : team) {
            if (ch == null) continue;

            CharacterView view = new CharacterView(ch, ch.getImage());
            view.setClickListener(clicked -> {
                if (!isAlly) handleEnemyClick(clicked);
            });

            panel.add(view, gbc);
            gbc.gridy++;
        }
    }

    private void handleEnemyClick(GameCharacter enemy) {
        if (currentActor == null || selectedSkill == null) return;

        boolean success = currentActor.useSkill(selectedSkill, enemy);
        if (success && enemy.getCurrentHP() <= 0) {
            removeCharacterFromTeam(enemy, Teams.getEnemyTeam());
        }

        updateCharacterPanels();
        selectedSkill = null;
        currentAllyIndex++;
        nextTurn();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int midX = getWidth() / 2;
        if (leftBgImage != null) g.drawImage(leftBgImage, 0, 0, midX, getHeight(), null);
        if (rightBgImage != null) g.drawImage(rightBgImage, midX, 0, midX, getHeight(), null);
    }

    private void updateCharacterPanels() {
        addCharacters(alliesPanel, Teams.getAlliedTeam(), true);
        addCharacters(enemiesPanel, Teams.getEnemyTeam(), false);
        alliesPanel.revalidate();
        enemiesPanel.revalidate();
        alliesPanel.repaint();
        enemiesPanel.repaint();
    }

    private void showSkillsForActor(GameCharacter actor) {
        skillsPanel.removeAll();

        if (actor != null && actor.isAlly()) {
            currentActor = actor;

            for (Skill skill : actor.getSkills()) {
                if (skill == null) continue;
                JButton skillButton = new JButton(skill.getName());
                skillButton.setFocusPainted(false);
                skillButton.setBackground(new Color(70, 110, 220));
                skillButton.setForeground(Color.WHITE);
                if (actor.getCurrentMana() < skill.getManaCost()) {
                    skillButton.setEnabled(false);
                }
                skillButton.addActionListener(e -> selectedSkill = skill);
                skillsPanel.add(skillButton);
            }
        }
        skillsPanel.revalidate();
        skillsPanel.repaint();
        updateCharacterPanels();
    }

    private void removeCharacterFromTeam(GameCharacter target, GameCharacter[] team) {
        for (int i = 0; i < team.length; i++)
            if (team[i] == target) {
                team[i] = null;
                break;
            }
    }

    private void showResult(boolean playerWon) {
        battleEnded = true;
        resultLabel.setText(playerWon ? "You Won!" : "You Lost!");
        resultLabel.setForeground(playerWon ? new Color(80, 255, 80) : new Color(255, 80, 80));
        resultLabel.setVisible(true);
        resultLabel.repaint();

        skillsPanel.removeAll();
        skillsPanel.revalidate();
        skillsPanel.repaint();
    }
}
