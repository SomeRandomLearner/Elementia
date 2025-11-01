import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

class GameCharacter {
    String name;
    int health;
    int maxHealth;
    int attackPower;
    int defense;
    String imagePath;

    public GameCharacter(String name, int health, int attackPower, int defense, String imagePath) {
        this.name = name;
        this.health = health;
        this.maxHealth = health;
        this.attackPower = attackPower;
        this.defense = defense;
        this.imagePath = imagePath;
    }

    public boolean isAlive() {
        return health > 0;
    }

    public int attack(GameCharacter enemy) {
        Random rand = new Random();
        int damage = (attackPower + rand.nextInt(6)) - enemy.defense;
        if (damage < 0) damage = 0;
        enemy.health -= damage;
        if (enemy.health < 0) enemy.health = 0;
        return damage;
    }

    public void heal() {
        Random rand = new Random();
        int healAmount = rand.nextInt(8) + 5;
        health += healAmount;
        if (health > maxHealth) health = maxHealth;
    }
}

public class BattleGUI_Team extends JFrame {

    private ArrayList<GameCharacter> heroes = new ArrayList<>();
    private GameCharacter enemy;

    private JPanel heroPanel;
    private JLabel enemyImageLabel, enemyLabel, battleLog;
    private JButton attackButton, healButton, nextHeroButton;

    private int currentHeroIndex = 0; // tracks which hero is taking the turn

    public BattleGUI_Team() {
        setTitle("⚔️ RPG Battle — Team Mode ⚔️");
        setSize(900, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout());

        // ===== Create heroes =====
        heroes.add(new GameCharacter("Aero", 50, 10, 3, "src/images/Aero.png"));
        heroes.add(new GameCharacter("Kayden", 45, 12, 2, "src/images/Kayden Break Temp.png"));
        heroes.add(new GameCharacter("Psalm", 55, 9, 4, "src/images/PsalmFire.png"));
        heroes.add(new GameCharacter("Ripper", 48, 11, 3, "src/images/Ripper Temp.png"));
        heroes.add(new GameCharacter("ZnStream", 40, 8, 2, "src/images/ZnStream.png"));

        // ===== Create enemy =====
        enemy = new GameCharacter("Dark Lord", 150, 10, 3, "src/images/ArgusLOL.png");

        // ===== Battlefield Layout =====
        JPanel battlefield = new JPanel(new GridLayout(1, 2));
        battlefield.setBackground(new Color(40, 40, 40));

        // LEFT SIDE (Heroes)
        heroPanel = new JPanel();
        heroPanel.setBackground(new Color(60, 80, 150));
        heroPanel.setBorder(BorderFactory.createTitledBorder("Heroes"));
        heroPanel.setLayout(new GridLayout(heroes.size(), 1)); // 5 rows for 5 heroes

        for (GameCharacter hero : heroes) {
            heroPanel.add(createCharacterPanel(hero));
        }

        // RIGHT SIDE (Enemy)
        JPanel enemyPanel = new JPanel(new BorderLayout());
        enemyPanel.setBackground(new Color(150, 50, 50));
        enemyPanel.setBorder(BorderFactory.createTitledBorder("Enemies"));

        enemyImageLabel = new JLabel();
        enemyImageLabel.setHorizontalAlignment(JLabel.CENTER);
        loadCharacterImage(enemyImageLabel, enemy.imagePath);

        enemyLabel = new JLabel(formatCharacter(enemy), JLabel.CENTER);
        enemyLabel.setForeground(Color.WHITE);
        enemyLabel.setFont(new Font("Consolas", Font.BOLD, 16));

        enemyPanel.add(enemyImageLabel, BorderLayout.CENTER);
        enemyPanel.add(enemyLabel, BorderLayout.SOUTH);

        battlefield.add(heroPanel);
        battlefield.add(enemyPanel);

        // ===== Battle Log =====
        battleLog = new JLabel("Battle Start! 5 Heroes vs " + enemy.name, JLabel.CENTER);
        battleLog.setFont(new Font("Consolas", Font.PLAIN, 14));
        battleLog.setOpaque(true);
        battleLog.setBackground(new Color(20, 20, 20));
        battleLog.setForeground(Color.WHITE);
        battleLog.setPreferredSize(new Dimension(900, 40));

        // ===== Buttons =====
        JPanel actionPanel = new JPanel(new FlowLayout());
        attackButton = new JButton("Attack");
        healButton = new JButton("Heal");
        nextHeroButton = new JButton("Next Hero");

        actionPanel.add(attackButton);
        actionPanel.add(healButton);
        actionPanel.add(nextHeroButton);

        add(battleLog, BorderLayout.NORTH);
        add(battlefield, BorderLayout.CENTER);
        add(actionPanel, BorderLayout.SOUTH);

        // ===== Event Handling =====
        attackButton.addActionListener(e -> heroAttack());
        healButton.addActionListener(e -> heroHeal());
        nextHeroButton.addActionListener(e -> nextHeroTurn());

        updateLabels();
        setVisible(true);
    }

    private JPanel createCharacterPanel(GameCharacter c) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(60, 80, 150));

        JLabel imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(JLabel.CENTER);
        loadCharacterImage(imageLabel, c.imagePath);

        JLabel infoLabel = new JLabel(formatCharacter(c), JLabel.CENTER);
        infoLabel.setForeground(Color.WHITE);
        infoLabel.setFont(new Font("Consolas", Font.BOLD, 14));

        panel.add(imageLabel, BorderLayout.CENTER);
        panel.add(infoLabel, BorderLayout.SOUTH);

        return panel;
    }

    private void loadCharacterImage(JLabel label, String path) {
        try {
            ImageIcon icon = new ImageIcon(path);
            Image scaled = icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            label.setIcon(new ImageIcon(scaled));
        } catch (Exception e) {
            label.setText("<html><center>[No Image]</center></html>");
            label.setForeground(Color.WHITE);
        }
    }

    private String formatCharacter(GameCharacter c) {
        return "<html><center>" + c.name + "<br>HP: " + c.health + "/" + c.maxHealth + "</center></html>";
    }

    private void heroAttack() {
        if (!enemy.isAlive()) return;
        GameCharacter hero = heroes.get(currentHeroIndex);
        if (!hero.isAlive()) {
            battleLog.setText(hero.name + " is down! Use Next Hero.");
            return;
        }

        int damage = hero.attack(enemy);
        battleLog.setText(hero.name + " attacked " + enemy.name + " for " + damage + " damage!");
        updateLabels();
        checkBattleStatus();

        if (enemy.isAlive()) enemyTurn();
    }

    private void heroHeal() {
        GameCharacter hero = heroes.get(currentHeroIndex);
        hero.heal();
        battleLog.setText(hero.name + " healed themselves!");
        updateLabels();
        enemyTurn();
    }

    private void nextHeroTurn() {
        currentHeroIndex = (currentHeroIndex + 1) % heroes.size();
        battleLog.setText("It’s now " + heroes.get(currentHeroIndex).name + "’s turn!");
    }

    private void enemyTurn() {
        if (!enemy.isAlive()) return;

        // Enemy attacks a random alive hero
        Random rand = new Random();
        ArrayList<GameCharacter> aliveHeroes = new ArrayList<>();
        for (GameCharacter h : heroes) if (h.isAlive()) aliveHeroes.add(h);

        if (aliveHeroes.isEmpty()) {
            battleLog.setText("💀 All heroes have fallen...");
            disableButtons();
            return;
        }

        GameCharacter target = aliveHeroes.get(rand.nextInt(aliveHeroes.size()));
        int damage = enemy.attack(target);
        battleLog.setText(enemy.name + " attacked " + target.name + " for " + damage + " damage!");
        updateLabels();

        checkBattleStatus();
    }

    private void updateLabels() {
        heroPanel.removeAll();
        for (GameCharacter hero : heroes) {
            heroPanel.add(createCharacterPanel(hero));
        }
        enemyLabel.setText(formatCharacter(enemy));
        heroPanel.revalidate();
        heroPanel.repaint();
    }

    private void checkBattleStatus() {
        boolean allDead = heroes.stream().noneMatch(GameCharacter::isAlive);
        if (allDead) {
            battleLog.setText("💀 All heroes were defeated...");
            disableButtons();
        } else if (!enemy.isAlive()) {
            battleLog.setText("🏆 The heroes defeated " + enemy.name + "!");
            disableButtons();
        }
    }

    private void disableButtons() {
        attackButton.setEnabled(false);
        healButton.setEnabled(false);
        nextHeroButton.setEnabled(false);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(BattleGUI_Team::new);
    }
}