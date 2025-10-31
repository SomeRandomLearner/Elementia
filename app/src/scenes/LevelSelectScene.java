package scenes;
import characters.GameCharacter;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class LevelSelectScene extends JPanel{
    public LevelSelectScene(Elementia frame) {
        final byte NO_OF_LEVELS = 5;

        setLayout(new BorderLayout());
        setBackground(Color.BLACK);

        JLabel title = new JLabel("Level Select", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 28));
        title.setForeground(Color.WHITE);
        add(title, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton[] levelButtons = new JButton[NO_OF_LEVELS];// Manually edit accordingly
        for(int i = 0; i < NO_OF_LEVELS; i++){
            levelButtons[i] = Utility.createButton("" + (i+1));
            buttonPanel.add(levelButtons[i]);
        }

        ArrayList<GameCharacter> enemyTeam = new ArrayList<>(3);
        // Add three enemies at most;
        // Feel free to change the enemies or the number of levels

        //Level 1
        levelButtons[0].addActionListener(e -> {
            enemyTeam.add(new GameCharacter("Assassin", 100, 100, 30, 10, "/images/Aero.png"));
            frame.showScreen("VSAIBattle");
        });
        //Level 2
        levelButtons[1].addActionListener(e -> {
            enemyTeam.add(new GameCharacter("Assassin", 100, 100, 30, 10, "/images/Aero.png"));
            enemyTeam.add(new GameCharacter("Assassin", 100, 100, 30, 10, "/images/Aero.png"));
            frame.showScreen("VSAIBattle");
        });
        //Level 3
        levelButtons[2].addActionListener(e -> {
            enemyTeam.add(new GameCharacter("Aero", 100, 100, 30, 10, "/images/Aero.png"));
            enemyTeam.add(new GameCharacter("Assassin", 100, 100, 30, 10, "/images/Aero.png"));
            frame.showScreen("VSAIBattle");
        });
        //Level 4
        levelButtons[3].addActionListener(e -> {
            enemyTeam.add(new GameCharacter("Aero", 100, 100, 30, 10, "/images/Aero.png"));
            enemyTeam.add(new GameCharacter("Assassin", 100, 100, 30, 10, "/images/Aero.png"));
            enemyTeam.add(new GameCharacter("Assassin", 100, 100, 30, 10, "/images/Aero.png"));
            frame.showScreen("VSAIBattle");
        });
        //Level 5
        levelButtons[4].addActionListener(e -> {
            enemyTeam.add(new GameCharacter("Kayden", 100, 100, 30, 10, "/images/Aero.png"));
            enemyTeam.add(new GameCharacter("Psalm", 100, 100, 30, 10, "/images/Aero.png"));
            enemyTeam.add(new GameCharacter("ZnStream", 100, 100, 30, 10, "/images/Aero.png"));
            frame.showScreen("VSAIBattle");
        });
        // I can't help but feel like there is a better way to do this

        add(buttonPanel);
    }
}
