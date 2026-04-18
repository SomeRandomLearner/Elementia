package scenes;

import characters.*;
import logic.BattleLogic;
import logic.Skill;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Objects;


public class PVPCharacterSelectScene extends JPanel {
    private Font normalFont = new Font("Times New Roman", Font.PLAIN, 30);

    private GameCharacter player1ChosenCharacter;
    private GameCharacter player2ChosenCharacter;
    
    private final boolean[] player1HasChosen = {false};
    private final boolean[] player2HasChosen = {false};
    private JLabel characterSelectLabel = null;
    private JButton confirmButton = null;

    public PVPCharacterSelectScene(Elementia frame){
        setLayout(new GridBagLayout());
        setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();

        characterSelectLabel = new JLabel("Player 1 Choose a Character");
        characterSelectLabel.setFont(new Font("Times New Roman", Font.BOLD, 20));
        characterSelectLabel.setForeground(Color.BLACK);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.gridheight = 1;
        add(characterSelectLabel, gbc);

        confirmButton = createStyledButton("Confirm");
        confirmButton.addActionListener(e -> {
            boolean isPVP = true;
            BattleLogic battleLogic = new BattleLogic(isPVP);

            battleLogic.resetCharacterChoices();
            battleLogic.addToTeam(1, player1ChosenCharacter);
            battleLogic.addToTeam(2, player2ChosenCharacter);

            frame.getPVPBattle().setBattleLogic(battleLogic);
            frame.showScreen("PVPStageSelect");
        });
        confirmButton.setEnabled(false);

        GameCharacter[] characters = {
                new Aero(), new Kaelis(), new Kangel(),
                new Kayden(), new Maelor(), new Psalm(),
                new Ripper(), new Veyrion(), new ZenStream()
        };

        JPanel wrapperPanel = new JPanel(new GridLayout(3,3));
        wrapperPanel.setOpaque(false);

        for(GameCharacter character : characters){
            wrapperPanel.add(createClickableCharacterPanel(character));
        }

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        add(wrapperPanel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridheight = 1;
        gbc.gridwidth = GridBagConstraints.REMAINDER;

        add(confirmButton, gbc);

        gbc.gridy = 5;
        JButton backBtn = createStyledButton("Return to Main Menu");
        backBtn.addActionListener(e -> frame.showScreen("MainMenu"));
        add(backBtn, gbc);
    }


    private ImageIcon getCharacterImageIcon(String path){
        return new ImageIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource(path))).getImage().getScaledInstance(180, 180, Image.SCALE_SMOOTH));
    }

    private JPanel createClickableCharacterPanel(GameCharacter character){
        JPanel newCharacterPanel = new JPanel(new BorderLayout());
        newCharacterPanel.add(new JLabel(getCharacterImageIcon(character.getImagePath())), BorderLayout.CENTER);
        newCharacterPanel.add(new JLabel(character.getName()), BorderLayout.SOUTH);
        newCharacterPanel.setOpaque(false);

        newCharacterPanel.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                if(!player1HasChosen[0]){
                    player1HasChosen[0] = true;
                    player1ChosenCharacter = character;
                    characterSelectLabel.setText("Player 2 Choose a Character");
                }
                else {
                    player2HasChosen[0] = true;
                    player2ChosenCharacter = character;
                    if(player1ChosenCharacter == player2ChosenCharacter) { // resolves duplicate character problems
                        player2ChosenCharacter = character.clone();
                        player2ChosenCharacter.removeAllSkills();
                        for(Skill skill : player1ChosenCharacter.getSkills()){
                            player2ChosenCharacter.addNewSkill(skill.clone());
                        }
                    }
                    characterSelectLabel.setText("All Ready!");
                    confirmButton.setEnabled(true);
                }
            }
        });
        return newCharacterPanel;
    }
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(Color.BLACK);
        button.setForeground(Color.WHITE);
        button.setFont(normalFont);
        button.setFocusPainted(false);
        return button;
    }
}
