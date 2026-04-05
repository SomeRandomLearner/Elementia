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

    private ImageIcon aeroImgIcon;
    private ImageIcon kaelisImgIcon;
    private ImageIcon kangelImgIcon;
    private ImageIcon kaydenImgIcon;
    private ImageIcon psalmImgIcon;
    private ImageIcon maelorImgIcon;
    private ImageIcon ripperImgIcon;
    private ImageIcon veyrionImgIcon;
    private ImageIcon zenStreamImgIcon;

    private GameCharacter player1ChosenCharacter;
    private GameCharacter player2ChosenCharacter;
    
    private final boolean[] player1HasChosen = {false};
    private final boolean[] player2HasChosen = {false};
    private JLabel characterSelectLabel = null;
    private JButton confirmButton = null;

    public PVPCharacterSelectScene(Elementia frame){
        setLayout(new GridBagLayout());
        setBackground(Color.BLACK);

        GridBagConstraints gbc = new GridBagConstraints();

        characterSelectLabel = new JLabel("Player 1 Choose a Character");
        characterSelectLabel.setFont(new Font("Times New Roman", Font.BOLD, 20));
        characterSelectLabel.setForeground(Color.WHITE);

        aeroImgIcon = getCharacterImageIcon("/resources/Aero.png");
        kaelisImgIcon = getCharacterImageIcon("/resources/Kaelis.png");
        kangelImgIcon = getCharacterImageIcon("/resources/Kangel.png");
        kaydenImgIcon = getCharacterImageIcon("/resources/Kayden.png");
        maelorImgIcon = getCharacterImageIcon("/resources/Maelor.png");
        psalmImgIcon = getCharacterImageIcon("/resources/Psalm.png");
        ripperImgIcon = getCharacterImageIcon("/resources/Ripper.png");
        veyrionImgIcon = getCharacterImageIcon("/resources/Veyrion.png");
        zenStreamImgIcon = getCharacterImageIcon("/resources/ZenStream.png");


        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.gridheight = 1;
        add(characterSelectLabel, gbc);

        confirmButton = createStyledButton("Confirm");
        confirmButton.addActionListener(e -> {
            BattleLogic battleLogic = new BattleLogic();

            battleLogic.resetCharacterChoices();
            battleLogic.addToTeam(1, player1ChosenCharacter);
            battleLogic.addToTeam(2, player2ChosenCharacter);

            frame.getPVPBattle().setBattleLogic(battleLogic);
            frame.showScreen("PVPStageSelect");
        });
        confirmButton.setEnabled(false);


        JLabel aeroImgLabel = getImgLabel(GameCharacter.Character.AERO, aeroImgIcon);
        JLabel kaelisImgLabel = getImgLabel(GameCharacter.Character.KAELIS, kaelisImgIcon);
        JLabel kangelImgLabel = getImgLabel(GameCharacter.Character.KANGEL, kangelImgIcon);
        JLabel kaydenImgLabel = getImgLabel(GameCharacter.Character.KAYDEN, kaydenImgIcon);
        JLabel maelorImgLabel = getImgLabel(GameCharacter.Character.MAELOR, maelorImgIcon);
        JLabel psalmImgLabel = getImgLabel(GameCharacter.Character.PSALM, psalmImgIcon);
        JLabel ripperImgLabel = getImgLabel(GameCharacter.Character.RIPPER, ripperImgIcon);
        JLabel veyrionImgLabel = getImgLabel(GameCharacter.Character.VEYRION, veyrionImgIcon);
        JLabel zenStreamImgLabel = getImgLabel(GameCharacter.Character.ZENSTREAM, zenStreamImgIcon);

        JPanel wrapperPanel = new JPanel(new GridLayout(3,3));

        wrapperPanel.add(aeroImgLabel);
        wrapperPanel.add(kaelisImgLabel);
        wrapperPanel.add(kangelImgLabel);
        wrapperPanel.add(kaydenImgLabel);
        wrapperPanel.add(maelorImgLabel);
        wrapperPanel.add(psalmImgLabel);
        wrapperPanel.add(ripperImgLabel);
        wrapperPanel.add(veyrionImgLabel);
        wrapperPanel.add(zenStreamImgLabel);


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


    private JLabel getImgLabel(GameCharacter.Character characterName, ImageIcon imgIcon) {
        JLabel imgLabel = new JLabel(imgIcon);
        imgLabel.setFont(new Font("Times New Roman", Font.BOLD, 20));
        imgLabel.setForeground(Color.WHITE);
        final GameCharacter chosenCharacter = switch (characterName){
            case AERO -> new Aero();
            case KAELIS -> new Kaelis();
            case KANGEL -> new Kangel();
            case KAYDEN -> new Kayden();
            case MAELOR -> new Maelor();
            case PSALM -> new Psalm();
            case RIPPER -> new Ripper();
            case VEYRION -> new Veyrion( );
            case ZENSTREAM -> new ZenStream();
            default -> null;
        };
        imgLabel.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                if(!player1HasChosen[0]){
                    player1HasChosen[0] = true;
                    player1ChosenCharacter = chosenCharacter;
                    characterSelectLabel.setText("Player 2 Choose a Character");
                }
                else {
                    player2HasChosen[0] = true;
                    player2ChosenCharacter = chosenCharacter;
                    if(player1ChosenCharacter == player2ChosenCharacter) { // resolves duplicate character problems
                        player2ChosenCharacter = chosenCharacter.clone();
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
        imgLabel.setPreferredSize(new Dimension(180,180));
        return imgLabel;
    }


    private ImageIcon getCharacterImageIcon(String path){
        return new ImageIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource(path))).getImage().getScaledInstance(180, 180, Image.SCALE_SMOOTH));
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
