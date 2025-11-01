package scenes;

import characters.GameCharacter;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class VSAIBattleScene extends JPanel {
    private final List<GameCharacter> allies = new ArrayList<>();
    private final List<GameCharacter> enemies = new ArrayList<>();
    private GameCharacter selectedTarget;
    private GameCharacter currentActor;

    private JPanel alliesPanel;
    private JPanel enemiesPanel;
    private JPanel skillsPanel;
    private JLabel battleLog;
    private JFrame frame;

    public VSAIBattleScene(Elementia frame) {
        this.frame = frame;
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel title = new JLabel("Battle vs AI", SwingConstants.CENTER);
        title.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 24));
        add(title, BorderLayout.NORTH);

        JPanel battleField = new JPanel(new GridLayout(1, 2, 20, 0));

        alliesPanel = new JPanel(new GridLayout(0, 1, 8, 8));
        alliesPanel.setBorder(BorderFactory.createTitledBorder("Allies"));
        battleField.add(alliesPanel);

        enemiesPanel = new JPanel(new GridLayout(0, 1, 8, 8));
        enemiesPanel.setBorder(BorderFactory.createTitledBorder("Enemies"));
        battleField.add(enemiesPanel);

        add(battleField, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout(10, 10));
        battleLog = new JLabel("Battle starts!", SwingConstants.CENTER);
        battleLog.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 14));
        bottomPanel.add(battleLog, BorderLayout.NORTH);

        skillsPanel = new JPanel(new GridLayout(1, 3, 10, 10));
        skillsPanel.setBorder(BorderFactory.createTitledBorder("Skills"));
        bottomPanel.add(skillsPanel, BorderLayout.CENTER);

        JButton backBtn = new JButton("Back to Menu");
        backBtn.addActionListener(e -> {
            frame.showScreen("MainMenu");
        });

        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        controlPanel.add(backBtn);
        bottomPanel.add(controlPanel, BorderLayout.SOUTH);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    public void setBattleGroups(List<GameCharacter> allyGroup, List<GameCharacter> enemyGroup) {
        allies.clear();
        enemies.clear();
        allies.addAll(allyGroup);
        enemies.addAll(enemyGroup);

        refreshPanels();
        startNextTurn();
    }

    private void refreshPanels() {
        alliesPanel.removeAll();
        enemiesPanel.removeAll();

        for (GameCharacter ally : allies) {
            JButton btn = new JButton(ally.getName() + " | HP: " + ally.getCurrentHealth() + "/" + ally.getMaxHealth());
            btn.setBackground(new Color(180, 220, 255));
            btn.addActionListener(e -> selectTarget(ally));
            alliesPanel.add(btn);
        }

        for (GameCharacter enemy : enemies) {
            JButton btn = new JButton(enemy.getName() + " | HP: " + enemy.getCurrentHealth() + "/" + enemy.getMaxHealth());
            btn.setBackground(new Color(255, 180, 180));
            btn.addActionListener(e -> selectTarget(enemy));
            enemiesPanel.add(btn);
        }

        revalidate();
        repaint();
    }

    private void startNextTurn() {
        currentActor = getNextActor();
        if (currentActor == null) {
            battleLog.setText("Battle finished!");
            skillsPanel.removeAll();
            revalidate();
            repaint();
            return;
        }

        battleLog.setText("It's " + currentActor.getName() + "'s turn!");
        setupSkillButtons(currentActor);
    }

    private GameCharacter getNextActor() {
        for (GameCharacter p : allies)
            if (p.getCurrentHealth() > 0)
                return p;
        for (GameCharacter e : enemies)
            if (e.getCurrentHealth() > 0)
                return e;
        return null;
    }

    private void setupSkillButtons(GameCharacter actor) {
        skillsPanel.removeAll();

        // If no skills defined, show a basic "Attack" for now
        if (actor.skills == null || actor.skills.length == 0) {
            JButton attackBtn = new JButton("Attack");
            attackBtn.addActionListener(e -> useSkill(actor, 0));
            skillsPanel.add(attackBtn);
        } else {
            for (int i = 0; i < actor.skills.length; i++) {
                JButton skillBtn = new JButton(actor.skills[i].getName());
                int skillNum = i;
                skillBtn.addActionListener(e -> useSkill(actor, skillNum));
                skillsPanel.add(skillBtn);
            }
        }

        revalidate();
        repaint();
    }

    private void selectTarget(GameCharacter target) {
        selectedTarget = target;
        battleLog.setText("Target selected: " + target.getName());
    }

    private void useSkill(GameCharacter actor, int skillNum) {
        if (selectedTarget == null) {
            JOptionPane.showMessageDialog(this, "Select a target first!");
            return;
        }

        if (actor.getCurrentHealth() <= 0) return;

        try {
            actor.useSkill(actor.skills[0], selectedTarget);
            battleLog.setText(actor.getName() + " attacked " + selectedTarget.getName() + "!");
            refreshPanels();
            startNextTurn();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Invalid move!");
        }
    }
}
