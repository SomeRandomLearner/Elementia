package scenes;

import characters.CharacterView;
import characters.GameCharacter;
import logic.Skill;
import logic.BattleEventListener;
import logic.BattleLogic;
import logic.TurnResult;
import utils.Utility;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Objects;

public class PVPBattleScene extends JPanel {
    private JPanel topPanel;
    private JPanel centerPanel;
    private AnimatedPanel leftPanel;
    private AnimatedPanel rightPanel;
    private JPanel bottomPanel;
    private JPanel topLeftPanel;
    private JPanel topCenterPanel;
    private JPanel topRightPanel;
    private JPanel player1SkillPanel;
    private JPanel player2SkillPanel;
    private JPanel timerPanel;

    private JLabel currentTurnLabel;
    private JLabel winCounterLabel;
    private JLabel timerLabel;

    private Font normalFont = new Font("Times New Roman", Font.PLAIN, 32);
    private ImageIcon bgIcon = null;
    private Image bgImage;

    private BattleLogic battleLogic;
    private GameCharacter selectedTarget;

    private int roundNumber;
    private int roundWinner;
    private boolean hasRoundEnded;
    private int gameWinner;
    private boolean hasGameEnded;

    private Timer timer;
    private int timerCount;
    public PVPBattleScene(Elementia frame) {
        setLayout(new BorderLayout());

        bgIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/LevelBackgrounds/Level1Background.png"))); // default background image
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

        JButton backBtn = Utility.createButton("Back");
        backBtn.addActionListener(e -> {
            repaint();
            frame.showScreen("PVPCharacterSelect");
        });
        currentTurnLabel = new JLabel();
        currentTurnLabel.setFont(normalFont);

        topLeftPanel.add(backBtn);
        topCenterPanel.setBackground(Color.BLACK);
        topCenterPanel.add(currentTurnLabel);

        winCounterLabel = new JLabel("0 / 0"); // starts at zero for now
        winCounterLabel.setForeground(Color.WHITE);

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

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (bgImage != null) g.drawImage(bgImage, 0, 0, getWidth(), getHeight(), null);

        if (hasGameEnded) {
            displayMessage(g, "Player " + gameWinner + " Wins!");
        }
        else if(hasRoundEnded) {
            displayMessage(g, "Player " + roundWinner + " Wins Round " + roundNumber);
        }
    }


    public void startGame(){
        winCounterLabel.setText("0 / 0");
        roundNumber = 1;
        hasRoundEnded = false;
        hasGameEnded = false;
        selectedTarget = null;
        displayCharacterViews();

        if(battleLogic == null){
            System.out.println("Error: BattleLogic is null.");
            return;
        }
        battleLogic.setBattleEventListener(new BattleEventListener() {
            @Override
            public void onTurnStarted(int currentPlayerTurn, GameCharacter currentCharacter) {
                currentTurnLabel.setText("Player " + currentPlayerTurn + "'s Turn");
                if(currentPlayerTurn == 1){
                    currentTurnLabel.setForeground(Color.RED);

                    player1SkillPanel.setVisible(true);
                    player2SkillPanel.setVisible(false);

                    player1SkillPanel.removeAll();
                    for(Skill skill : currentCharacter.getSkills()){
                        if(skill == null) break;
                        player1SkillPanel.add(getJButton(currentCharacter, skill));
                    }

                    player1SkillPanel.revalidate();
                    player1SkillPanel.repaint();
                }
                else{
                    currentTurnLabel.setForeground(Color.BLUE);

                    player2SkillPanel.setVisible(true);
                    player1SkillPanel.setVisible(false);

                    player2SkillPanel.removeAll();
                    for(Skill skill : currentCharacter.getSkills()){
                        if(skill == null) break;
                        player2SkillPanel.add(getJButton(currentCharacter, skill));
                    }

                    player2SkillPanel.revalidate();
                    player2SkillPanel.repaint();
                }
                bottomPanel.revalidate();
                bottomPanel.repaint();

                if(currentPlayerTurn == 1){
                    setAllComponents(leftPanel, false);
                    setAllComponents(rightPanel, true);
                }
                else{
                    setAllComponents(leftPanel, true);
                    setAllComponents(rightPanel, false);
                }
                rightPanel.repaint();
                leftPanel.repaint();

                timerCount = 4;
                timerLabel.setText(String.valueOf(timerCount));
                if(currentPlayerTurn == 1) timerPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
                else timerPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

                timerPanel.revalidate();

                if (timer != null && timer.isRunning()) {
                    timer.stop();
                }
                timer = new Timer(1000, e -> {
                    if(timerCount > 0){
                        timerCount--;
                        timerLabel.setText(String.valueOf(timerCount));
                    }
                    else{
                        ((Timer)e.getSource()).stop();
                        battleLogic.nextTurn();
                    }
                });
                timer.start();

            }

            @Override
            public void onAttackResolved(TurnResult result) {
                if (timer != null && timer.isRunning()) {
                    timer.stop();
                }
                leftPanel.revalidate();
                rightPanel.revalidate();
                leftPanel.repaint();
                rightPanel.repaint();
            }

            @Override
            public void onRoundStarted(int currentPlayerTurn) {
                displayCharacterViews();
            }

            @Override
            public void onRoundEnded(int winningPlayer) {
                roundWinner = winningPlayer;
                hasRoundEnded = true;
                winCounterLabel.setText(battleLogic.getWinCount(1) + " / " + battleLogic.getWinCount(2));


                topPanel.repaint();
                centerPanel.revalidate();
                centerPanel.repaint();

                repaint();
                Timer roundMessageTimer;

                roundMessageTimer = new Timer(2000, e -> {
                    roundNumber++;
                    hasRoundEnded = false;
                    repaint();
                    ((Timer)e.getSource()).stop();
                });
                roundMessageTimer.start();
            }

            @Override
            public void onGameEnded(int winningPlayer) {
                gameWinner = winningPlayer;
                hasGameEnded = true;

                leftPanel.removeAll();
                rightPanel.removeAll();

                JButton rematchBtn = Utility.createButton("Rematch");

                JPanel buttonWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 20));
                buttonWrapper.setOpaque(false);
                buttonWrapper.setBorder(new EmptyBorder(0, 0, 40, 0));
                buttonWrapper.add(rematchBtn);

                bottomPanel.removeAll();
                bottomPanel.add(buttonWrapper, BorderLayout.CENTER);

                rematchBtn.addActionListener(e -> {
                    bottomPanel.removeAll();
                    bottomPanel.add(player1SkillPanel, BorderLayout.WEST);
                    bottomPanel.add(timerPanel, BorderLayout.CENTER);
                    bottomPanel.add(player2SkillPanel, BorderLayout.EAST);

                    bottomPanel.revalidate();
                    bottomPanel.repaint();
                    hasGameEnded = false;
                    startGame();
                });

                revalidate();
                repaint();
            }
        });

        battleLogic.startGame();
        setAllComponents(leftPanel, false);
        setAllComponents(rightPanel, false);
    }

    private void handleClick(GameCharacter target){
        if (target == null || battleLogic.getSelectedSkill() == null || battleLogic.getCurrentTeam().contains(target)) return;

        battleLogic.setTargetCharacter(target);
        int currentPlayerTurn = battleLogic.getCurrentPlayerTurn();
        if(currentPlayerTurn == 1){
            rightPanel.setSelectedSkill(battleLogic.getSelectedSkill());
            rightPanel.playSkillAnimation();
        }
        else{
            leftPanel.setSelectedSkill(battleLogic.getSelectedSkill());
            leftPanel.playSkillAnimation();
        }
        battleLogic.currentCharacterUseSkillOnTarget();

        battleLogic.setSelectedSkill(null);
        selectedTarget = null;

        rightPanel.repaint();
        leftPanel.repaint();
    }

    public static void setAllComponents(Container container, boolean setTo) {
        for (Component component : container.getComponents()) {
            component.setEnabled(setTo);
            if (component instanceof Container) {
                setAllComponents((Container) component, setTo);
            }
        }
    }

    private JButton getJButton(GameCharacter currentCharacter, Skill skill) {
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
        });
        return skillButton;
    }

    public void setPVPBattleSceneBackground(int backgroundNumber){
        ImageIcon newIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/LevelBackgrounds/Level" + backgroundNumber + "Background.png")));
        bgImage = newIcon.getImage();
        repaint();
    }

    private void displayCharacterViews(){
        leftPanel.removeAll();
        rightPanel.removeAll();

        for(GameCharacter character : battleLogic.getActivePlayer1Team()){
            CharacterView view = new CharacterView(character);
            view.setClickListener(e -> {
                selectedTarget = character;
                handleClick(selectedTarget);
            });
            leftPanel.add(view);
        }
        for(GameCharacter character : battleLogic.getActivePlayer2Team()){
            CharacterView view = new CharacterView(character);
            view.setClickListener(e -> {
                selectedTarget = character;
                handleClick(selectedTarget);
            });
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

    private void displayMessage(Graphics g, String message){
        g.setFont(new Font("Times New Roman", Font.BOLD, 48));
        g.setColor(Color.WHITE);
        FontMetrics metrics = g.getFontMetrics(g.getFont());
        int x = (getWidth() - metrics.stringWidth(message)) / 2;
        int y = (getHeight() - metrics.getHeight()) / 2 + metrics.getAscent();
        g.drawString(message, x, y);
    }
}
