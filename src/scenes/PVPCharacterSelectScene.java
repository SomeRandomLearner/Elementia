package scenes;

import characters.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Objects;


public class PVPCharacterSelectScene extends JPanel {
    Font normalFont = new Font("Times New Roman", Font.PLAIN, 30);

    ImageIcon aeroImgIcon;
    ImageIcon kaydenImgIcon;
    ImageIcon psalmImgIcon;
    ImageIcon ripperImgIcon;
    ImageIcon zenStreamImgIcon;
    // add other characters here later
    private GameCharacter player1ChosenCharacter;
    private GameCharacter player2ChosenCharacter;
    
    final boolean[] player1HasChosen = {false};
    final boolean[] player2HasChosen = {false};
    JLabel characterSelectLabel = null;
    JButton confirmButton = null;

    public PVPCharacterSelectScene(Elementia frame){
        setLayout(new GridBagLayout());
        setBackground(Color.BLACK);

        GridBagConstraints gbc = new GridBagConstraints();

        characterSelectLabel = new JLabel("Player 1 Choose a Character");
        characterSelectLabel.setFont(new Font("Times New Roman", Font.BOLD, 20));
        characterSelectLabel.setForeground(Color.WHITE);

        aeroImgIcon = new ImageIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/Aero.png"))).getImage().getScaledInstance(180, 180, Image.SCALE_SMOOTH));
        kaydenImgIcon = new ImageIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/Kayden.png"))).getImage().getScaledInstance(180, 180, Image.SCALE_SMOOTH));
        psalmImgIcon = new ImageIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/Psalm.png"))).getImage().getScaledInstance(180, 180, Image.SCALE_SMOOTH));
        ripperImgIcon = new ImageIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/Ripper.png"))).getImage().getScaledInstance(180, 180, Image.SCALE_SMOOTH));
        zenStreamImgIcon = new ImageIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/ZenStream.png"))).getImage().getScaledInstance(180, 180, Image.SCALE_SMOOTH));


        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.gridheight = 1;
        add(characterSelectLabel, gbc);

        confirmButton = createStyledButton("Confirm");
        confirmButton.addActionListener(e -> {
            frame.getPVPBattle().addToPlayer1Team(player1ChosenCharacter);
            frame.getPVPBattle().addToPlayer2Team(player2ChosenCharacter);
            frame.showScreen("PVPStageSelect");
        });
        confirmButton.setEnabled(false);


        JLabel aeroImgLabel = getImgLabel(GameCharacter.Character.AERO, aeroImgIcon);
        aeroImgLabel.setPreferredSize(new Dimension(180,180));
        JLabel kaydenImgLabel = getImgLabel(GameCharacter.Character.KAYDEN, kaydenImgIcon);
        kaydenImgLabel.setPreferredSize(new Dimension(180,180));
        JLabel psalmImgLabel = getImgLabel(GameCharacter.Character.PSALM, psalmImgIcon);
        psalmImgLabel.setPreferredSize(new Dimension(180,180));
        JLabel ripperImgLabel = getImgLabel(GameCharacter.Character.RIPPER, ripperImgIcon);
        ripperImgLabel.setPreferredSize(new Dimension(180,180));
        JLabel zenStreamImgLabel = getImgLabel(GameCharacter.Character.ZENSTREAM, zenStreamImgIcon);
        zenStreamImgLabel.setPreferredSize(new Dimension(180,180));

        JPanel wrapperPanel = new JPanel(new GridLayout(3,3));

        wrapperPanel.add(aeroImgLabel);
        wrapperPanel.add(kaydenImgLabel);
        wrapperPanel.add(psalmImgLabel);
        wrapperPanel.add(ripperImgLabel);
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
    }


    private JLabel getImgLabel(GameCharacter.Character characterName, ImageIcon imgIcon) {
        JLabel imgLabel = new JLabel(imgIcon);
        imgLabel.setFont(new Font("Times New Roman", Font.BOLD, 20));
        imgLabel.setForeground(Color.WHITE);
        final GameCharacter chosenCharacter = switch (characterName){
            case AERO -> new Aero();
//            case KAELIS -> new Kaelis();
//            case KANGEL -> new Kangel();
            case KAYDEN -> new Kayden();
//            case MAELOR -> new Maelor();
            case PSALM -> new Psalm();
            case RIPPER -> new Ripper();
//            case VEYRION -> new Veyrion;
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
        return imgLabel;
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
