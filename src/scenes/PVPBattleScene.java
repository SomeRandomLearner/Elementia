package scenes;

import characters.CharacterView;
import characters.GameCharacter;
import characters.Skill;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class PVPBattleScene extends JPanel {
    JPanel topPanel;
    JPanel centerPanel;
    JPanel leftPanel;
    JPanel rightPanel;
    JPanel bottomPanel;
    JPanel skillPanel;

    JLabel currentTurnLabel;
    JLabel winCounterLabel;

    Font normalFont = new Font("Times New Roman", Font.PLAIN, 30);
    ImageIcon bgIcon = null;
    Image bgImage;

    ArrayList<GameCharacter> player1Team = new ArrayList<GameCharacter>();
    ArrayList<GameCharacter> player2Team = new ArrayList<GameCharacter>();
    ArrayList<GameCharacter> currentTeam = null;
    ArrayList<GameCharacter> opposingTeam = null;
    Skill selectedSkill = null;
    byte currentPlayerTurn;
    byte currentCharacterTurn;
    GameCharacter currentCharacter;
    boolean isPlayer1FirstTurn;
    boolean isPlayer2FirstTurn;
    byte player1WinCount;
    byte player2WinCount;

    int height = 1000, width = 600;
    public PVPBattleScene(Elementia frame) {
        bgIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/LevelBackgrounds/Level1Background.png"))); // default background image
        bgImage = bgIcon.getImage();

        JPanel wrapperPanel = new JPanel(new BorderLayout());
        wrapperPanel.setPreferredSize(new Dimension(frame.getWidth(), frame.getHeight()));
        wrapperPanel.setOpaque(false); // must be set to false in order to remove grey background of the panel.

        topPanel = new JPanel(new GridLayout(1,3));
        topPanel.setPreferredSize(new Dimension(0, 60));
        topPanel.setOpaque(false);
        centerPanel = new JPanel(new GridLayout(1,2));
        centerPanel.setOpaque(false);
        bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 6));
        bottomPanel.setPreferredSize(new Dimension(0, 100));
        bottomPanel.setOpaque(false);

        JPanel topLeftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 6));
        topLeftPanel.setOpaque(false);
        JPanel topCenterPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        topCenterPanel.setOpaque(false);
        JPanel topRightPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        topRightPanel.setOpaque(false);
        JPanel leftWrapperPanel = new JPanel(new GridBagLayout());
        leftWrapperPanel.setOpaque(false);
        JPanel rightWrapperPanel = new JPanel(new GridBagLayout());
        rightWrapperPanel.setOpaque(false);

        // FlowLayout.RIGHT and LEFT make the characters come as close as possible to the enemy
        leftPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 6));
        leftPanel.setOpaque(false);
        rightPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 6));
        rightPanel.setOpaque(false);

        leftWrapperPanel.add(leftPanel);
        rightWrapperPanel.add(rightPanel);

        skillPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 6));
        skillPanel.setOpaque(false);

        // wrapper panels for easy centering
        centerPanel.add(leftWrapperPanel);
        centerPanel.add(rightWrapperPanel);

        bottomPanel.add(skillPanel);

        JButton backBtn = Utility.createButton("Back");
        backBtn.addActionListener(e -> {
            resetPlayerTeams();
            repaint();
            frame.showScreen("PVPCharacterSelect");
        });
        currentTurnLabel = new JLabel();
        currentTurnLabel.setForeground(Color.WHITE);
        topLeftPanel.add(backBtn);
        topCenterPanel.add(currentTurnLabel);

        player1WinCount = player2WinCount = 0;
        winCounterLabel = new JLabel(player1WinCount + " / " + player2WinCount);
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

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (bgImage != null) g.drawImage(bgImage, 0, 0, getWidth(), getHeight(), null);
    }


    public void startGame(){
        isPlayer1FirstTurn = true;
        isPlayer2FirstTurn = true;

        currentPlayerTurn = (byte)(new Random().nextInt(2));
        initializeCharacters();
        setAllComponents(leftPanel, false);
        setAllComponents(rightPanel, false);

        startTurn();
    }
    public void initializeCharacters(){
        for(GameCharacter character : player1Team){
            character.setCurrentHP(character.getMaxHP());
            character.setCurrentMana(character.getMaxMana());
            for(Skill skill : character.getSkills()){
                skill.resetCooldownTimer();
            }
            CharacterView view = new CharacterView(character, character.getImage());
            view.setClickListener(this::handleClick);
            view.setEnabled(false);
            leftPanel.add(view);
        }
        for(GameCharacter character : player2Team){
            character.setCurrentHP(character.getMaxHP());
            character.setCurrentMana(character.getMaxMana());
            for(Skill skill : character.getSkills()){
                skill.resetCooldownTimer();
            }
            CharacterView view = new CharacterView(character, character.getImage());
            view.setClickListener(this::handleClick);
            view.setEnabled(false);
            rightPanel.add(view);
        }
    }

    private void startTurn(){
        setAllComponents(leftPanel, false);
        setAllComponents(rightPanel, false);

        rightPanel.repaint();
        leftPanel.repaint();

        currentTeam = (currentPlayerTurn == 0)? player1Team : player2Team;
        opposingTeam = (currentPlayerTurn == 0)? player2Team : player1Team;

        if(currentPlayerTurn == 0) bottomPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 6));
        else bottomPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 6));
        this.currentCharacter = currentTeam.get(currentCharacterTurn);
        switch (currentPlayerTurn) {
            case 0 -> {
                currentTurnLabel.setText("Player 1's " + player1Team.get(currentCharacterTurn).getName() + "'s Turn");
                if (!isPlayer1FirstTurn) {
                    for (Skill skill : currentCharacter.getSkills()) {
                        if (skill != null) skill.incrementCooldownTimer();
                    }
                    currentCharacter.setCurrentMana(Math.min(currentCharacter.getCurrentMana() + currentCharacter.getManaRecovery(), currentCharacter.getMaxMana()));
                }
            }
            case 1 -> {
                currentTurnLabel.setText("Player 2's " + player2Team.get(currentCharacterTurn).getName() + "'s Turn");
                if (!isPlayer2FirstTurn) {
                    for (Skill skill : currentCharacter.getSkills()) {
                        if (skill != null) skill.incrementCooldownTimer();
                    }
                    currentCharacter.setCurrentMana(Math.min(currentCharacter.getCurrentMana() + currentCharacter.getManaRecovery(), currentCharacter.getMaxMana()));
                }
            }
            default -> System.out.println("Invalid player turn!");
        }

        skillPanel.removeAll();
        for(Skill skill : currentCharacter.getSkills()){
            if(skill == null) break;
            skillPanel.add(getJButton(currentCharacter, skill));
        }
        skillPanel.revalidate();
        skillPanel.repaint();
    }

    private void nextTurn(){
        if(hasWinner()){
            System.out.println("Player " + (currentPlayerTurn + 1) + " wins!");
            if(currentPlayerTurn == 0) player1WinCount++;
            else player2WinCount++;

            winCounterLabel.setText(player1WinCount + " / " + player2WinCount);

            return;
        }
        if(currentCharacterTurn >= currentTeam.size() - 1) {
            if(currentPlayerTurn == 0) isPlayer1FirstTurn = false;
            else isPlayer2FirstTurn = false;

            currentPlayerTurn = (currentPlayerTurn == 0)? (byte) 1 : 0;
            currentCharacterTurn = 0;
        }
        else {
            currentCharacterTurn++;
        }
        startTurn();
    }

    private void handleClick(GameCharacter target){
        if (target == null || selectedSkill == null || currentTeam.contains(target)) return;

        boolean success = currentCharacter.useSkill(selectedSkill, target);
        if(success){
            selectedSkill.resetCooldownTimer();
            if (target.getCurrentHP() <= 0) {
                opposingTeam.remove(target);
            }
        }
        selectedSkill = null;

        rightPanel.repaint();
        leftPanel.repaint();
        nextTurn();
    }

    public boolean hasWinner(){
        return player1Team.isEmpty() || player2Team.isEmpty();
    }

    public static void setAllComponents(Container container, boolean setTo) {
        for (Component component : container.getComponents()) {
            component.setEnabled(setTo);
            if (component instanceof Container) {
                setAllComponents((Container) component, setTo);
            }
        }
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(Color.BLACK);
        button.setForeground(Color.WHITE);
        button.setFont(normalFont);
        button.setFocusPainted(false);
        return button;
    }

    private JButton getJButton(GameCharacter actor, Skill skill) {
        JButton skillButton = new JButton(skill.getName());
        skillButton.setFocusPainted(false);
        skillButton.setBackground(new Color(70, 110, 220));
        skillButton.setForeground(Color.WHITE);
        if (actor.getCurrentMana() < skill.getManaCost() || skill.getCooldown() > skill.getCooldownTimer()) {
            skillButton.setEnabled(false);
            skillButton.setBackground(Color.DARK_GRAY);
            if(skill.getCooldown() > skill.getCooldownTimer()) skillButton.setText(skill.getName() + " " + (skill.getCooldown() - skill.getCooldownTimer()));
        }
        skillButton.addActionListener(e -> {
            selectedSkill = skill;

            JPanel targetPanel = (currentPlayerTurn == 0) ? rightPanel : leftPanel;
            setAllComponents(targetPanel, true);
        });
        return skillButton;
    }

    public void addToPlayer1Team(GameCharacter character){
        if(player1Team.size() < 3)
            player1Team.add(character);
    }

    public void addToPlayer2Team(GameCharacter character){
        if(player2Team.size() < 3)
            player2Team.add(character);
    }

    public void resetPlayerTeams(){
        player1Team.clear();
        player2Team.clear();
    }
    public void setPVPBattleSceneBackground(int backgroundNumber){
        ImageIcon newIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/LevelBackgrounds/Level" + backgroundNumber + "Background.png")));
        bgImage = newIcon.getImage();
        repaint();
    }

}
