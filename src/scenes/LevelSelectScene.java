package scenes;

import characters.*;
import characters.Teams;
import characters.ZenStream;
import logic.Utility;

import javax.swing.*;

import characters.*;
import characters.Teams;
import logic.Utility;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Objects;

public class LevelSelectScene extends JPanel{

    private int selectedLevel = 1;
    private int completedLevels = 0;

    private final HashMap<Integer, Boolean> levelStatuses;
    private final JButton[] levelButtons;

    private Image bgImage;

    public LevelSelectScene(Elementia frame) {

        final byte NO_OF_LEVELS = 10;

        bgImage = new ImageIcon(
                Objects.requireNonNull(getClass().getResource("/resources/CharacterSelectBG.png"))
        ).getImage();

        levelStatuses = new HashMap<>();

        for(int i=1;i<=NO_OF_LEVELS;i++){
            levelStatuses.put(i,false);
        }

        setLayout(new BorderLayout());

        JLabel title = new JLabel("Level Select", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 32));
        title.setForeground(Color.WHITE);
        add(title, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(2,5,25,25));
        buttonPanel.setOpaque(false);

        levelButtons = new JButton[NO_OF_LEVELS];

        for(int i = 0; i < NO_OF_LEVELS; i++){

            levelButtons[i] = Utility.createButton("" + (i+1));

            levelButtons[i].setPreferredSize(new Dimension(120,120));

            levelButtons[i].setFont(new Font("Arial", Font.BOLD,18));
            levelButtons[i].setFocusPainted(false);

            buttonPanel.add(levelButtons[i]);
        }

        unlockLevels();


        levelButtons[0].addActionListener(e -> {

            selectedLevel = 1;

            while(Teams.getAlliedTeamCount() > 1)
                Teams.popAlliedTeam();

            if(Teams.getEnemyTeamCount() > 0)
                Teams.clearEnemyTeam();

            Teams.addToEnemyTeam(new ZenStream());

            frame.addBattleScene();
            frame.showScreen("Battle");
        });



        levelButtons[1].addActionListener(e -> {

            selectedLevel = 2;

            while(Teams.getAlliedTeamCount() > 1)
                Teams.popAlliedTeam();

            if(Teams.getEnemyTeamCount() > 0)
                Teams.clearEnemyTeam();

            Teams.addToEnemyTeam(new Ripper());

            frame.addBattleScene();
            frame.showScreen("Battle");
        });


        levelButtons[2].addActionListener(e -> {

            selectedLevel = 3;

            while(Teams.getAlliedTeamCount() > 1)
                Teams.popAlliedTeam();

            if(Teams.getEnemyTeamCount() > 0)
                Teams.clearEnemyTeam();

            Teams.addToEnemyTeam(new Aero());

            frame.addBattleScene();
            frame.showScreen("Battle");
        });



        levelButtons[3].addActionListener(e -> {

            selectedLevel = 4;

            while(Teams.getAlliedTeamCount() > 1)
                Teams.popAlliedTeam();

            if(Teams.getEnemyTeamCount() > 0)
                Teams.clearEnemyTeam();

            Teams.addToEnemyTeam(new Psalm());

            frame.addBattleScene();
            frame.showScreen("Battle");
        });



        levelButtons[4].addActionListener(e -> {

            selectedLevel = 5;

            while(Teams.getAlliedTeamCount() > 1)
                Teams.popAlliedTeam();

            if(Teams.getEnemyTeamCount() > 0)
                Teams.clearEnemyTeam();

            Teams.addToEnemyTeam(new Kaelis());

            frame.addBattleScene();
            frame.showScreen("Battle");
        });



        levelButtons[5].addActionListener(e -> {

            selectedLevel = 6;

            while(Teams.getAlliedTeamCount() > 1)
                Teams.popAlliedTeam();

            if(Teams.getEnemyTeamCount() > 0)
                Teams.clearEnemyTeam();

            Teams.addToEnemyTeam(new Kangel());

            frame.addBattleScene();
            frame.showScreen("Battle");
        });



        levelButtons[6].addActionListener(e -> {

            selectedLevel = 7;

            while(Teams.getAlliedTeamCount() > 1)
                Teams.popAlliedTeam();

            if(Teams.getEnemyTeamCount() > 0)
                Teams.clearEnemyTeam();

            Teams.addToEnemyTeam(new Maelor());

            frame.addBattleScene();
            frame.showScreen("Battle");
        });


        levelButtons[7].addActionListener(e -> {

            selectedLevel = 8;

            while(Teams.getAlliedTeamCount() > 1)
                Teams.popAlliedTeam();

            if(Teams.getEnemyTeamCount() > 0)
                Teams.clearEnemyTeam();

            Teams.addToEnemyTeam(new Veyrion());

            frame.addBattleScene();
            frame.showScreen("Battle");
        });



        levelButtons[8].addActionListener(e -> {

            selectedLevel = 9;

            while(Teams.getAlliedTeamCount() > 1)
                Teams.popAlliedTeam();

            if(Teams.getEnemyTeamCount() > 0)
                Teams.clearEnemyTeam();

            Teams.addToEnemyTeam(new Kayden());

            frame.addBattleScene();
            frame.showScreen("Battle");
        });


        levelButtons[9].addActionListener(e -> {

            selectedLevel = 10;

            while(Teams.getAlliedTeamCount() > 1)
                Teams.popAlliedTeam();

            ArrayList<GameCharacter> availableCharacters = GameCharacter.getAllCharacters();

            for(GameCharacter character : Teams.getAlliedTeam()){
                availableCharacters.remove(character);
            }

            while(Teams.getAlliedTeamCount() < 3){
                int randomInt = new Random().nextInt(availableCharacters.size());
                Teams.addToAlliedTeam(availableCharacters.get(randomInt));
            }

            if(Teams.getEnemyTeamCount() > 0)
                Teams.clearEnemyTeam();

            while(Teams.getEnemyTeamCount() < 3){
                int randomInt = new Random().nextInt(availableCharacters.size());
                Teams.addToEnemyTeam(availableCharacters.get(randomInt));
            }

            frame.addBattleScene();
            frame.showScreen("Battle");
        });

        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setOpaque(false);
        centerPanel.add(buttonPanel);

        add(centerPanel, BorderLayout.CENTER);

        JButton backButton = Utility.createButton("Back");

        backButton.addActionListener(e -> frame.showScreen("CharacterSelect"));

        add(backButton, BorderLayout.SOUTH);
    }

    public int getSelectedLevel(){
        return selectedLevel;
    }

    public void incrementCompletedLevels(){
        completedLevels++;
    }

    public void setLevelStatus(int levelNumber, boolean status){
        levelStatuses.replace(levelNumber,status);
    }

    public boolean getLevelStatus(int levelNumber){
        return levelStatuses.get(levelNumber);
    }

    protected void unlockLevels(){

        for(int i=0;i<levelButtons.length;i++){

            if(i <= completedLevels){

                levelButtons[i].setEnabled(true);
                levelButtons[i].setBackground(new Color(70,170,255));
                levelButtons[i].setForeground(Color.WHITE);

            }else{

                levelButtons[i].setEnabled(false);
                levelButtons[i].setBackground(new Color(70,70,70));
                levelButtons[i].setForeground(Color.LIGHT_GRAY);

            }
        }
    }

    @Override
    protected void paintComponent(Graphics g){

        super.paintComponent(g);

        if(bgImage != null){
            g.drawImage(bgImage,0,0,getWidth(),getHeight(),this);
        }
    }
}