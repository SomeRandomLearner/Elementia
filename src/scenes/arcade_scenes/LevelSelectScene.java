package scenes.arcade_scenes;

import characters.*;
import logic.BattleLogic;
import scenes.Elementia;
import utils.CustomButton;
import logic.LevelManager;
import utils.Utility;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Instant;
import java.util.HashMap;
import java.util.Objects;

public class LevelSelectScene extends JPanel {
    private int completedLevels = 0;
    private GameCharacter selectedCharacter = null;
    private final HashMap<Integer, Boolean> levelStatuses;
    private final CustomButton[] levelButtons;
    private Image bgImage;
    private final byte NO_OF_LEVELS = 10;
    private boolean hasInputtedLeaderboardScore;
    private Elementia frame;

    public LevelSelectScene(Elementia frame) {
        this.frame = frame;
        bgImage = new ImageIcon(
                Objects.requireNonNull(getClass().getResource("/resources/CharacterSelectBG.png"))
        ).getImage();

        hasInputtedLeaderboardScore = false;
        levelStatuses = new HashMap<>();
        for (int i = 1; i <= NO_OF_LEVELS; i++) {
            levelStatuses.put(i, false);
        }

        setLayout(new BorderLayout());

        // ================= TITLE =================
        JLabel title = new JLabel("Level Select", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 32));
        title.setForeground(Color.WHITE);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);
        topPanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 10, 0));
        topPanel.add(title, BorderLayout.CENTER);
        add(topPanel, BorderLayout.NORTH);

        // ================= LEVEL BUTTONS =================
        JPanel buttonPanel = new JPanel(new GridLayout(2, 5, 25, 25));
        buttonPanel.setOpaque(false);

        levelButtons = new CustomButton[NO_OF_LEVELS];
        for (int i = 0; i < NO_OF_LEVELS; i++) {
            levelButtons[i] = Utility.createButton("" + (i + 1));
            levelButtons[i].setPreferredSize(new Dimension(120, 120));
            levelButtons[i].setFont(new Font("Arial", Font.BOLD, 18));
            levelButtons[i].setFocusPainted(false);

            int levelNumber = i + 1;
            levelButtons[i].addActionListener(e -> {
                if (selectedCharacter == null) {
                    System.out.println("ERROR in level select");
                    return;
                }

                LevelManager.setCurrentLevelNumber(levelNumber);
                boolean isPVP = false;
                BattleLogic battleLogic = new BattleLogic(isPVP);
                battleLogic.resetCharacterChoices();
                battleLogic.addToTeam(1, selectedCharacter);
                battleLogic.addAllToTeam(2, (LevelManager.getCurrentLevel()).getEnemyTeam());

                frame.getArcadeBattle().setBattleLogic(battleLogic);
                frame.getArcadeBattle().startGame();
                frame.showScreen("Battle");
            });

            buttonPanel.add(levelButtons[i]);
        }

        unlockLevels();

        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setOpaque(false);
        centerPanel.add(buttonPanel);
        add(centerPanel, BorderLayout.CENTER);

        // ================= BACK BUTTON (LOWER LEFT FIX) =================
        JButton backButton = Utility.createButton("Back");

        // 🔥 SIZE + STYLE
        backButton.setPreferredSize(new Dimension(200, 50));
        backButton.setFont(new Font("Arial", Font.BOLD, 16));
        backButton.setFocusPainted(false);

        // 🎯 LEFT-ALIGNED PANEL
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
        bottomPanel.setOpaque(false);

        // 📐 spacing (left + bottom padding)
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 0));
        bottomPanel.add(backButton);

        backButton.addActionListener(e -> frame.showScreen("ArcadeCharacterSelect"));
        add(bottomPanel, BorderLayout.SOUTH);
    }

    // ================= LOGIC =================
    public void incrementCompletedLevels() {
        completedLevels++;
    }

    public void setLevelStatus(int levelNumber, boolean status) {
        levelStatuses.replace(levelNumber, status);
    }

    public boolean getLevelStatus(int levelNumber) {
        return levelStatuses.get(levelNumber);
    }

    void unlockLevels() {
        if(!hasInputtedLeaderboardScore && completedLevels == NO_OF_LEVELS){
            try(BufferedWriter writer = new BufferedWriter(new FileWriter("data/player_data.txt", true))){
                writer.write("," + Instant.now());
            } catch (IOException e) {
                e.printStackTrace();
            }
            hasInputtedLeaderboardScore = true;
            frame.getLeaderboardNameInput().displayTimes();
            frame.showScreen("LeaderboardNameInput");
            System.out.println("VALID");
        }

        for (int i = 0; i < levelButtons.length; i++) {
            if (i <= completedLevels) {
                levelButtons[i].setEnabled(true);
                levelButtons[i].setDefaultBackgroundColor(new Color(0, 0, 139));
                levelButtons[i].setDefaultForegroundColor(Color.WHITE);
                levelButtons[i].setHoverColor(new Color(70, 170, 255));
                levelButtons[i].setBackgroundColorToDefault();
                levelButtons[i].setForegroundColorToDefault();
            } else {
                levelButtons[i].setEnabled(false);
                levelButtons[i].setDefaultBackgroundColor(new Color(70, 70, 70));
                levelButtons[i].setDefaultForegroundColor(Color.GRAY);
                levelButtons[i].setBackgroundColorToDefault();
                levelButtons[i].setForegroundColorToDefault();
            }
        }
    }

    public void setSelectedCharacter(GameCharacter character) {
        this.selectedCharacter = character;
    }

    // ================= BACKGROUND =================
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (bgImage != null) {
            g.drawImage(bgImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}