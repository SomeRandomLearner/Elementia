package scenes;
import characters.*;
import characters.Teams;
import characters.ZnStream;

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
            this.selectedLevel = 1;
            while(Teams.getAlliedTeamCount() > 1) Teams.popAlliedTeam(); // Clears non-support characters
            Teams.addToAlliedTeam(new Kayden());
            for(GameCharacter character: Teams.getAlliedTeam()) {
                character.setCurrentHP(character.getMaxHP());
                character.setCurrentMana(character.getMaxMana());
            }
            if(Teams.getEnemyTeamCount() > 0) Teams.clearEnemyTeam();
            Teams.addToEnemyTeam(new GameCharacter("Assassin", 100, 100, 30, 10, 10, "/resources/Aero.png"));
            Teams.getEnemyTeam()[0].addNewSkill();
            Teams.getEnemyTeam()[0].addNewSkill("Stab", 20, 20, 1.0f);
            Teams.getEnemyTeam()[0].addNewSkill("Execute", 70, 70, 1.0f);
            Teams.getEnemyTeam()[0].setCharacterImage("/resources/Assassin.png");
            frame.addVSAIBattleScene();
            frame.showScreen("VSAIBattle");
        });
        //Level 2
        levelButtons[1].addActionListener(e -> {
            this.selectedLevel = 2;
            while(Teams.getAlliedTeamCount() > 1) Teams.popAlliedTeam();
            Teams.addToAlliedTeam(new Aero());

            for(GameCharacter character: Teams.getAlliedTeam()) {
                character.setCurrentHP(character.getMaxHP());
                character.setCurrentMana(character.getMaxMana());
            }
            if(Teams.getEnemyTeamCount() > 0) Teams.clearEnemyTeam();
            Teams.addToEnemyTeam(new GameCharacter("Assassin"));
            Teams.getEnemyTeam()[0].addNewSkill();
            Teams.getEnemyTeam()[0].addNewSkill("Stab", 20, 20, 1.0f);
            Teams.getEnemyTeam()[0].addNewSkill("Execute", 70, 70, 1.0f);
            Teams.getEnemyTeam()[0].setCharacterImage("/resources/Assassin.png");
            Teams.addToEnemyTeam(new GameCharacter("Assassin"));
            Teams.getEnemyTeam()[1].addNewSkill();
            Teams.getEnemyTeam()[1].addNewSkill("Stab", 20, 20, 1.0f);
            Teams.getEnemyTeam()[1].addNewSkill("Execute", 70, 70, 1.0f);
            Teams.getEnemyTeam()[1].setCharacterImage("/resources/Assassin.png");
            frame.addVSAIBattleScene();
            frame.showScreen("VSAIBattle");
        });
        //Level 3
        levelButtons[2].addActionListener(e -> {
            this.selectedLevel = 3;
            while(Teams.getAlliedTeamCount() > 1) Teams.popAlliedTeam();
            Teams.addToAlliedTeam(new ZnStream());
            for(GameCharacter character: Teams.getAlliedTeam()) {
                character.setCurrentHP(character.getMaxHP());
                character.setCurrentMana(character.getMaxMana());
            }
            if(Teams.getEnemyTeamCount() > 0) Teams.clearEnemyTeam();
            Teams.addToEnemyTeam(new Aero());
            Teams.addToEnemyTeam(new GameCharacter("Assassin"));
            Teams.getEnemyTeam()[1].addNewSkill();
            Teams.getEnemyTeam()[1].addNewSkill("Stab", 20, 20, 1.0f);
            Teams.getEnemyTeam()[1].addNewSkill("Execute", 70, 70, 1.0f);
            Teams.getEnemyTeam()[1].setCharacterImage("/resources/Assassin.png");
            frame.addVSAIBattleScene();
            frame.showScreen("VSAIBattle");
        });
        //Level 4
        levelButtons[3].addActionListener(e -> {
            this.selectedLevel = 4;
            while(Teams.getAlliedTeamCount() > 1) Teams.popAlliedTeam();
            Teams.addToAlliedTeam(new Psalm());
            for(GameCharacter character: Teams.getAlliedTeam()) {
                character.setCurrentHP(character.getMaxHP());
                character.setCurrentMana(character.getMaxMana());
            }
            if(Teams.getEnemyTeamCount() > 0) Teams.clearEnemyTeam();
            Teams.addToEnemyTeam(new ZnStream());
            Teams.addToEnemyTeam(new Ripper());
            Teams.addToEnemyTeam(new GameCharacter("Assassin"));
            Teams.getEnemyTeam()[2].addNewSkill();
            Teams.getEnemyTeam()[2].addNewSkill("Stab", 20, 20, 1.0f);
            Teams.getEnemyTeam()[2].addNewSkill("Execute", 70, 70, 1.0f);
            Teams.getEnemyTeam()[2].setCharacterImage("/resources/Assassin.png");

            frame.addVSAIBattleScene();
            frame.showScreen("VSAIBattle");
        });
        //Level 5
        levelButtons[4].addActionListener(e -> {
            this.selectedLevel = 5;
            while(Teams.getAlliedTeamCount() > 1) Teams.popAlliedTeam();
            Teams.addToAlliedTeam(new Ripper());
            for(GameCharacter character: Teams.getAlliedTeam()) {
                character.setCurrentHP(character.getMaxHP());
                character.setCurrentMana(character.getMaxMana());
            }
            if(Teams.getEnemyTeamCount() > 0) Teams.clearEnemyTeam();
            Teams.addToEnemyTeam(new Kayden());
            Teams.addToEnemyTeam(new Psalm());
            Teams.addToEnemyTeam(new ZnStream());
            frame.addVSAIBattleScene();
            frame.showScreen("VSAIBattle");
        });

        JPanel centerPanel = new JPanel(new GridBagLayout());

        JButton backButton = Utility.createButton("Back");
        backButton.addActionListener(e -> frame.showScreen("MainMenu"));

        centerPanel.add(buttonPanel);
        add(centerPanel, BorderLayout.CENTER);
        add(backButton, BorderLayout.SOUTH);
    }
}
