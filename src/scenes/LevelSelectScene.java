package scenes;
import characters.*;
import characters.Teams;
import characters.ZenStream;
import logic.Utility;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import characters.GameCharacter;

import static characters.GameCharacter.Character.*;

public class LevelSelectScene extends JPanel{
    private int selectedLevel = 1;
    private int completedLevels = 0;
    private final HashMap<Integer, Boolean> levelStatuses;
    private final JButton[] levelButtons;

    public LevelSelectScene(Elementia frame) {
        final byte NO_OF_LEVELS = 10;

        levelStatuses = new HashMap<>();
        levelStatuses.put(1, false);
        levelStatuses.put(2, false);
        levelStatuses.put(3, false);
        levelStatuses.put(4, false);
        levelStatuses.put(5, false);
        levelStatuses.put(6, false);
        levelStatuses.put(7, false);
        levelStatuses.put(8, false);
        levelStatuses.put(9, false);
        levelStatuses.put(10, false);

        setLayout(new BorderLayout());
        setBackground(Color.BLACK);

        JLabel title = new JLabel("Level Select", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 28));
        title.setForeground(Color.WHITE);
        add(title, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(2,5,20,20));
        levelButtons = new JButton[NO_OF_LEVELS];// Manually edit accordingly
        for(int i = 0; i < NO_OF_LEVELS; i++){
            levelButtons[i] = Utility.createButton("" + (i+1));
            buttonPanel.add(levelButtons[i]);
        }

        // Adds three enemies at most;
        // Feel free to change the enemies or the number of levels

        unlockLevels();

        //Level 1
        levelButtons[0].addActionListener(e -> {
            selectedLevel = 1;
            while(Teams.getAlliedTeamCount() > 1) Teams.popAlliedTeam(); // Clears non-support characters
            if(Teams.getEnemyTeamCount() > 0) Teams.clearEnemyTeam();
            Teams.addToEnemyTeam(new ZenStream());

            frame.addBattleScene();
            frame.showScreen("Battle");
        });
        //Level 2
        levelButtons[1].addActionListener(e -> {
            selectedLevel = 2;
            while(Teams.getAlliedTeamCount() > 1) Teams.popAlliedTeam();

            if(Teams.getEnemyTeamCount() > 0) Teams.clearEnemyTeam();
            Teams.addToEnemyTeam(new Ripper());
            frame.addBattleScene();
            frame.showScreen("Battle");
        });
        //Level 3
        levelButtons[2].addActionListener(e -> {
            selectedLevel = 3;
            while(Teams.getAlliedTeamCount() > 1) Teams.popAlliedTeam();

            if(Teams.getEnemyTeamCount() > 0) Teams.clearEnemyTeam();
            Teams.addToEnemyTeam(new Aero());

            frame.addBattleScene();
            frame.showScreen("Battle");
        });
        //Level 4
        levelButtons[3].addActionListener(e -> {
            selectedLevel = 4;
            while(Teams.getAlliedTeamCount() > 1) Teams.popAlliedTeam();

            if(Teams.getEnemyTeamCount() > 0) Teams.clearEnemyTeam();
            Teams.addToEnemyTeam(new Psalm());

            frame.addBattleScene();
            frame.showScreen("Battle");
        });
        //Level 5
        levelButtons[4].addActionListener(e -> {
            selectedLevel = 5;
            while(Teams.getAlliedTeamCount() > 1) Teams.popAlliedTeam();

            if(Teams.getEnemyTeamCount() > 0) Teams.clearEnemyTeam();
            Teams.addToEnemyTeam(new Kaelis());
            frame.addBattleScene();
            frame.showScreen("Battle");
        });
        //Level 6
        levelButtons[5].addActionListener(e -> {
            selectedLevel = 6;
            while(Teams.getAlliedTeamCount() > 1) Teams.popAlliedTeam();

            if(Teams.getEnemyTeamCount() > 0) Teams.clearEnemyTeam();
            Teams.addToEnemyTeam(new Kangel());
            frame.addBattleScene();
            frame.showScreen("Battle");
        });
        //Level 7
        levelButtons[6].addActionListener(e -> {
            selectedLevel = 7;
            while(Teams.getAlliedTeamCount() > 1) Teams.popAlliedTeam();

            if(Teams.getEnemyTeamCount() > 0) Teams.clearEnemyTeam();
            Teams.addToEnemyTeam(new Maelor());
            frame.addBattleScene();
            frame.showScreen("Battle");
        });
        //Level 8
        levelButtons[7].addActionListener(e -> {
            selectedLevel = 8;
            while(Teams.getAlliedTeamCount() > 1) Teams.popAlliedTeam();

            if(Teams.getEnemyTeamCount() > 0) Teams.clearEnemyTeam();
            Teams.addToEnemyTeam(new Veyrion());
            frame.addBattleScene();
            frame.showScreen("Battle");
        });
        //Level 9
        levelButtons[8].addActionListener(e -> {
            selectedLevel = 9;
            while(Teams.getAlliedTeamCount() > 1) Teams.popAlliedTeam();

            if(Teams.getEnemyTeamCount() > 0) Teams.clearEnemyTeam();
            Teams.addToEnemyTeam(new Kayden());
            frame.addBattleScene();
            frame.showScreen("Battle");
        });
        //Level 10
        levelButtons[9].addActionListener(e -> {
            selectedLevel = 10;
            while(Teams.getAlliedTeamCount() > 1) Teams.popAlliedTeam();

            ArrayList<GameCharacter> availableCharacters = GameCharacter.getAllCharacters();
            for(GameCharacter character : Teams.getAlliedTeam()){
                availableCharacters.remove(character);
            }
            while(Teams.getAlliedTeamCount() < 3){
                int randomInt = new Random().nextInt(availableCharacters.size());
                Teams.addToAlliedTeam(Teams.getAlliedTeam().get(randomInt));
            }



            if(Teams.getEnemyTeamCount() > 0) Teams.clearEnemyTeam();

            while(Teams.getEnemyTeamCount() < 3){
                int randomInt = new Random().nextInt(availableCharacters.size());
                Teams.addToEnemyTeam(Teams.getEnemyTeam().get(randomInt));
            }
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

    public int getSelectedLevel(){
        return selectedLevel;
    }

    public void incrementCompletedLevels(){
        completedLevels++;
    }

    public void setLevelStatus(int levelNumber, boolean status){
        levelStatuses.replace(levelNumber, status);
    }

    public boolean getLevelStatus(int levelNumber){
        return levelStatuses.get(levelNumber);
    }

    protected void unlockLevels(){
        for(int i = 0; i < levelButtons.length; i++){
            levelButtons[i].setEnabled(i <= completedLevels);
        }
    }
}
