package scenes;

import utils.CharacterView;
import characters.GameCharacter;
import logic.Skill;
import logic.BattleLogic;
import utils.Utility;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.Random;

public abstract class AbstractBattleScene extends JPanel {
    protected Elementia frame;

    protected JPanel topPanel;
    protected JPanel centerPanel;
    protected AnimatedPanel leftPanel;
    protected AnimatedPanel rightPanel;
    protected JPanel bottomPanel;
    protected JPanel topLeftPanel;
    protected JPanel topCenterPanel;
    protected JPanel topRightPanel;
    protected JPanel player1SkillPanel;
    protected JPanel player2SkillPanel;
    protected JPanel timerPanel;

    protected JLabel currentTurnLabel;
    protected JLabel winCounterLabel;
    protected JLabel timerLabel;

    protected Timer enemyTurnTimer;
    protected JButton backButton;

    protected Font normalFont = new Font("Times New Roman", Font.PLAIN, 32);
    protected ImageIcon bgIcon;
    protected Image bgImage;

    protected BattleLogic battleLogic;
    protected GameCharacter selectedTarget;

    protected int roundNumber;
    protected int roundWinner;
    protected boolean isFirstRound;
    protected boolean hasRoundEnded;
    protected int gameWinner;
    protected boolean hasGameEnded;

    protected Timer timer;
    protected int timerCount;

    protected HashMap<GameCharacter, CharacterView> characterToViewMap = new HashMap<>();

    public AbstractBattleScene(Elementia frame) {
        this.frame = frame;
        setLayout(new BorderLayout());

        bgIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/LevelBackgrounds/LVL1_BG.png"))); // default background image
        bgImage = bgIcon.getImage();

        JPanel wrapperPanel = new JPanel(new BorderLayout());
        wrapperPanel.setOpaque(false); // must be set to false in order to remove grey background of the panel.

        timerLabel = initTimerLabel();

        topPanel = new JPanel(new GridLayout(1,3));
        topPanel.setPreferredSize(new Dimension(0, 60));
        topPanel.setOpaque(false);
        centerPanel = new JPanel(new GridLayout(1,2));
        centerPanel.setOpaque(false);
        bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setOpaque(false);

        topLeftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 6));
        topLeftPanel.setOpaque(false);
        topCenterPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 10));
//        topCenterPanel.setOpaque(false);
        topRightPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        topRightPanel.setOpaque(false);
        JPanel leftWrapperPanel = new JPanel(new GridBagLayout());
        leftWrapperPanel.setOpaque(false);
        JPanel rightWrapperPanel = new JPanel(new GridBagLayout());
        rightWrapperPanel.setOpaque(false);

        // FlowLayout.RIGHT and LEFT make the characters come as close as possible to the enemy
        leftPanel = new AnimatedPanel();
        leftPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 20, 6));
        leftPanel.setOpaque(false);
        rightPanel = new AnimatedPanel();
        rightPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 6));
        rightPanel.setOpaque(false);

        leftWrapperPanel.add(leftPanel);
        rightWrapperPanel.add(rightPanel);

        player1SkillPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 6));
        player1SkillPanel.setOpaque(false);
        player1SkillPanel.setBorder(new EmptyBorder(0,10,0,10));

        player2SkillPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 6));
        player2SkillPanel.setOpaque(false);
        player2SkillPanel.setBorder(new EmptyBorder(0,10,0,10));

        // wrapper panels for easy centering
        centerPanel.add(leftWrapperPanel);
        centerPanel.add(rightWrapperPanel);

        timerPanel = new JPanel(new FlowLayout());
        timerPanel.setOpaque(false);
        timerPanel.add(timerLabel);

        bottomPanel.add(player1SkillPanel, BorderLayout.WEST);
        bottomPanel.add(timerPanel, BorderLayout.CENTER);
        bottomPanel.add(player2SkillPanel, BorderLayout.EAST);

        backButton = Utility.createButton("Back");

        currentTurnLabel = new JLabel();
        currentTurnLabel.setFont(normalFont);

        topLeftPanel.add(backButton);
        topCenterPanel.setBackground(Color.BLACK);
        topCenterPanel.add(currentTurnLabel);

        winCounterLabel = new JLabel("0 / 0"); // starts at zero for now
        winCounterLabel.setForeground(Color.WHITE);
        winCounterLabel.setFont(new Font("Times New Roman", Font.BOLD, 24));

        topRightPanel.add(winCounterLabel);

        topPanel.add(topLeftPanel);
        topPanel.add(topCenterPanel);
        topPanel.add(topRightPanel);
        wrapperPanel.add(topPanel, BorderLayout.NORTH);
        wrapperPanel.add(centerPanel, BorderLayout.CENTER);
        wrapperPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(wrapperPanel);
        repaint();
    }

    private JLabel initTimerLabel() {
        JLabel label = new JLabel();
        label.setFont(normalFont);
        return label;
    }


    public abstract void startGame();

    public static void setAllComponents(Container container, boolean setTo) {
        for (Component component : container.getComponents()) {
            component.setEnabled(setTo);
            if (component instanceof Container) {
                setAllComponents((Container) component, setTo);
            }
        }
    }

    protected JButton getSkillButton(GameCharacter currentCharacter, Skill skill) {
        JButton skillButton = new JButton(skill.getName());
        skillButton.setFocusPainted(false);
        skillButton.setBackground(new Color(70, 110, 220));
        skillButton.setForeground(Color.WHITE);
        if (currentCharacter.getCurrentMana() < skill.getManaCost() || skill.getCooldown() > skill.getCooldownTimer()) {
            skillButton.setEnabled(false);
            skillButton.setBackground(Color.DARK_GRAY);
            if(skill.getCooldown() > skill.getCooldownTimer()) skillButton.setText(skill.getName() + " " + (skill.getCooldown() - skill.getCooldownTimer()));
        }
        skillButton.addActionListener(e -> {
            battleLogic.setSelectedSkill(skill);
            ArrayList<GameCharacter> opponentTeam = (ArrayList<GameCharacter>) battleLogic.getOpposingTeam();
            if(!opponentTeam.isEmpty()) {
                GameCharacter targetCharacter = opponentTeam.get(new Random().nextInt(opponentTeam.size()));
                battleLogic.setTargetCharacter(targetCharacter);
                battleLogic.currentCharacterUseSkillOnTarget();
                CharacterView view = characterToViewMap.get(targetCharacter);
                if(view != null) {
                    view.setSelectedSkill(skill);
                    view.playSkillAnimation();
                }
            }
        });
        return skillButton;
    }

    public void setBattleSceneBackground(int backgroundNumber){
        ImageIcon newIcon;
        if(new Random().nextInt(2) == 0) newIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/LevelBackgrounds/Level" + backgroundNumber + "BackgroundAlternate.png")));
        else newIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/LevelBackgrounds/Level" + backgroundNumber + "Background.png")));

        bgImage = newIcon.getImage();
        repaint();
    }

    protected void displayCharacterViews(){
        leftPanel.removeAll();
        rightPanel.removeAll();

        for(GameCharacter character : battleLogic.getActivePlayer1Team()){
            CharacterView view = new CharacterView(character);
            characterToViewMap.put(character, view);
            leftPanel.add(view);
        }
        for(GameCharacter character : battleLogic.getActivePlayer2Team()){
            CharacterView view = new CharacterView(character);
            characterToViewMap.put(character, view);
            rightPanel.add(view);
        }

        leftPanel.revalidate();
        leftPanel.repaint();
        rightPanel.revalidate();
        rightPanel.repaint();
    }

    public void setBattleLogic(BattleLogic battleLogic) {
        this.battleLogic = battleLogic;
    }

    protected void displayMessage(Graphics g, String message){
        g.setFont(new Font("Times New Roman", Font.BOLD, 48));
        g.setColor(Color.WHITE);
        FontMetrics metrics = g.getFontMetrics(g.getFont());
        int x = (getWidth() - metrics.stringWidth(message)) / 2;
        int y = (getHeight() - metrics.getHeight()) / 2 + metrics.getAscent();
        g.drawString(message, x, y);
    }
}
