package scenes.arcade_scenes;

import characters.*;
import logic.BattleLogic;
import scenes.Elementia;
import utils.CustomButton;
import logic.LevelManager;
import utils.Utility;

import javax.swing.*;

import java.awt.*;
import java.util.HashMap;
import java.util.Objects;

public class LevelSelectScene extends JPanel{
    private int completedLevels = 0;
    private GameCharacter selectedCharacter = null;

    private final HashMap<Integer, Boolean> levelStatuses;
    private final CustomButton[] levelButtons;

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

        levelButtons = new CustomButton[NO_OF_LEVELS];

        for(int i = 0; i < NO_OF_LEVELS; i++){
            levelButtons[i] = Utility.createButton("" + (i+1));
            levelButtons[i].setPreferredSize(new Dimension(120,120));
            levelButtons[i].setFont(new Font("Arial", Font.BOLD,18));
            levelButtons[i].setFocusPainted(false);

            int levelNumber = i + 1;
            levelButtons[i].addActionListener(e -> {
                if (selectedCharacter == null){
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
                frame.getArcadeBattle().displayCharacterViews();
                frame.getArcadeBattle().startGame();
                frame.showScreen("Battle");
            });
            buttonPanel.add(levelButtons[i]);
        }

        unlockLevels();


//        levelButtons[0].addActionListener(e -> {
//            selectedLevel = 1;
//
//            while(Teams.getAlliedTeamCount() > 1)
//                Teams.popAlliedTeam();
//
//            if(Teams.getEnemyTeamCount() > 0)
//                Teams.clearEnemyTeam();
//
//            Teams.addToEnemyTeam(new ZenStream());
//
//            frame.addBattleScene();
//            frame.showScreen("Battle");
//        });
//


        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setOpaque(false);
        centerPanel.add(buttonPanel);

        add(centerPanel, BorderLayout.CENTER);

        JButton backButton = Utility.createButton("Back");

        backButton.addActionListener(e -> frame.showScreen("ArcadeCharacterSelect"));

        add(backButton, BorderLayout.SOUTH);
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
                levelButtons[i].setDefaultBackgroundColor(new Color(0, 0, 139));
                levelButtons[i].setDefaultForegroundColor(Color.WHITE);
                levelButtons[i].setHoverColor(new Color(70,170,255));
                levelButtons[i].setBackgroundColorToDefault();
                levelButtons[i].setForegroundColorToDefault();
            }else{
                levelButtons[i].setEnabled(false);
                levelButtons[i].setDefaultBackgroundColor(new Color(70, 70, 70));
                levelButtons[i].setDefaultForegroundColor(Color.GRAY);
                levelButtons[i].setBackgroundColorToDefault();
                levelButtons[i].setForegroundColorToDefault();
            }
        }
    }

    public void setSelectedCharacter(GameCharacter character){
        this.selectedCharacter = character;
    }
    @Override
    protected void paintComponent(Graphics g){

        super.paintComponent(g);

        if(bgImage != null){
            g.drawImage(bgImage,0,0,getWidth(),getHeight(),this);
        }
    }
}