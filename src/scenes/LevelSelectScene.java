package scenes;
import characters.*;
import characters.Teams;
import characters.ZenStream;
import logic.Utility;

import javax.swing.*;
import java.awt.*;

public class LevelSelectScene extends JPanel{
    static int selectedLevel = 1;
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

        // Adds three enemies at most;
        // Feel free to change the enemies or the number of levels

        //Level 1
        levelButtons[0].addActionListener(e -> {
            selectedLevel = 1;
            while(Teams.getAlliedTeamCount() > 1) Teams.popAlliedTeam(); // Clears non-support characters
            Teams.addToAlliedTeam(new Kayden());
            if(Teams.getEnemyTeamCount() > 0) Teams.clearEnemyTeam();
            Teams.addToEnemyTeam(new Kayden());
            frame.addBattleScene();
            frame.showScreen("Battle");
        });
        //Level 2
        levelButtons[1].addActionListener(e -> {
            selectedLevel = 2;
            while(Teams.getAlliedTeamCount() > 1) Teams.popAlliedTeam();
            Teams.addToAlliedTeam(new Aero());

            if(Teams.getEnemyTeamCount() > 0) Teams.clearEnemyTeam();
            Teams.addToEnemyTeam(new Ripper());
            Teams.addToEnemyTeam(new Ripper());
            frame.addBattleScene();
            frame.showScreen("Battle");
        });
        //Level 3
        levelButtons[2].addActionListener(e -> {
            selectedLevel = 3;
            while(Teams.getAlliedTeamCount() > 1) Teams.popAlliedTeam();
            Teams.addToAlliedTeam(new ZenStream());
            if(Teams.getEnemyTeamCount() > 0) Teams.clearEnemyTeam();
            Teams.addToEnemyTeam(new Aero());

            frame.addBattleScene();
            frame.showScreen("Battle");
        });
        //Level 4
        levelButtons[3].addActionListener(e -> {
            selectedLevel = 4;
            while(Teams.getAlliedTeamCount() > 1) Teams.popAlliedTeam();
            Teams.addToAlliedTeam(new Psalm());
            if(Teams.getEnemyTeamCount() > 0) Teams.clearEnemyTeam();
            Teams.addToEnemyTeam(new ZenStream());
            Teams.addToEnemyTeam(new Ripper());

            frame.addBattleScene();
            frame.showScreen("Battle");
        });
        //Level 5
        levelButtons[4].addActionListener(e -> {
            selectedLevel = 5;
            while(Teams.getAlliedTeamCount() > 1) Teams.popAlliedTeam();
            Teams.addToAlliedTeam(new Ripper());
            if(Teams.getEnemyTeamCount() > 0) Teams.clearEnemyTeam();
            Teams.addToEnemyTeam(new Kayden());
            Teams.addToEnemyTeam(new Psalm());
            Teams.addToEnemyTeam(new ZenStream());
            frame.addBattleScene();
            frame.showScreen("Battle");
        });

        JPanel centerPanel = new JPanel(new GridBagLayout());

        JButton backButton = Utility.createButton("Back");
        backButton.addActionListener(e -> frame.showScreen("CharacterSelect"));

        centerPanel.add(buttonPanel);
        add(centerPanel, BorderLayout.CENTER);
        add(backButton, BorderLayout.SOUTH);
    }
}
