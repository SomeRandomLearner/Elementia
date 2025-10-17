package elementia.scenes;
import characters.*;
import characters.Person;
import characters.heroes.*;
import elementia.*;

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

        ArrayList<Person> enemyTeam = new ArrayList<>(3);
        // Add three enemies at most;
        // Feel free to change the enemies or the number of levels

        //Level 1
        levelButtons[0].addActionListener(e -> {
            enemyTeam.add(new Assassin());
            frame.showScreen("VSAIBattle");
        });
        //Level 2
        levelButtons[1].addActionListener(e -> {
            enemyTeam.add(new Assassin());
            enemyTeam.add(new Assassin());
            frame.showScreen("VSAIBattle");
        });
        //Level 3
        levelButtons[2].addActionListener(e -> {
            enemyTeam.add(new Aero());
            enemyTeam.add(new Assassin());
            frame.showScreen("VSAIBattle");
        });
        //Level 4
        levelButtons[3].addActionListener(e -> {
            enemyTeam.add(new Ripper());
            enemyTeam.add(new Assassin());
            enemyTeam.add(new Assassin());
            frame.showScreen("VSAIBattle");
        });
        //Level 5
        levelButtons[4].addActionListener(e -> {
            enemyTeam.add(new Kayden());
            enemyTeam.add(new Psalm());
            enemyTeam.add(new ZnStream());
            frame.showScreen("VSAIBattle");
        });
        // I can't help but feel like there is a better way to do this

        add(buttonPanel);
    }
}
