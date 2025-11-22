package scenes;

import characters.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class PVPCharacterSelectScene extends JPanel {

    private final JLabel instruction;
    private GameCharacter chosen1;
    private GameCharacter chosen2;
    private boolean selectingPlayer1 = true;
    private final JButton startButton;
    private final Elementia frame;

    public PVPCharacterSelectScene(Elementia frame) {
        this.frame = frame;

        setLayout(new BorderLayout());
        setBackground(Color.DARK_GRAY);

        instruction = new JLabel("Player 1: Select your character", SwingConstants.CENTER);
        instruction.setForeground(Color.WHITE);
        instruction.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
        add(instruction, BorderLayout.NORTH);

        JPanel grid = new JPanel(new GridBagLayout());
        grid.setOpaque(false);

        try {
            BufferedImage aeroImg = ImageIO.read(getClass().getResource("/resources/Aero.png"));
            BufferedImage kaydenImg = ImageIO.read(getClass().getResource("/resources/Kayden Break Temp.png"));
            BufferedImage psalmImg = ImageIO.read(getClass().getResource("/resources/PsalmFire.png"));
            BufferedImage ripperImg = ImageIO.read(getClass().getResource("/resources/Ripper.png"));
            BufferedImage znImg = ImageIO.read(getClass().getResource("/resources/ZnStream.png"));

            // Create characters
            addSelectableView(grid, new CharacterView(new Aero(), aeroImg), new Aero(), 0, 0);
            addSelectableView(grid, new CharacterView(new Kayden(), kaydenImg), new Kayden(), 1, 0);
            addSelectableView(grid, new CharacterView(new Psalm(), psalmImg), new Psalm(), 2, 0);
            addSelectableView(grid, new CharacterView(new Ripper(), ripperImg), new Ripper(), 3, 0);
            addSelectableView(grid, new CharacterView(new ZnStream(), znImg), new ZnStream(), 4, 0);

        } catch (IOException e) {
            e.printStackTrace();
        }

        add(grid, BorderLayout.CENTER);

        JPanel bottom = new JPanel();
        bottom.setOpaque(false);

        startButton = Utility.createButton("Start PVP");
        startButton.setEnabled(false);
        startButton.addActionListener(e -> {
            Teams.clearAlliedTeam(); // Prevents unintended events
            Teams.clearEnemyTeam();
            Teams.addToAlliedTeam(chosen1);
            Teams.addToEnemyTeam(chosen2);
            frame.addPVPBattleScene();
            frame.showScreen("PVPBattle");
        });

        JButton backButton = Utility.createButton("Back");
        backButton.addActionListener(e -> frame.showScreen("MainMenu"));

        bottom.add(startButton);
        bottom.add(backButton);
        add(bottom, BorderLayout.SOUTH);
    }

    private void addSelectableView(JPanel grid, CharacterView view, GameCharacter hero, int gridx, int gridy) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = gridx;
        gbc.gridy = gridy;
        gbc.insets = new Insets(10, 10, 10, 10);

        view.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (selectingPlayer1) {
                    chosen1 = hero;
                    instruction.setText("Player 2: Select your character");
                    selectingPlayer1 = false;
                } else {
                    chosen2 = hero;
                    instruction.setText("Both players have chosen. Click to start PVP");
                }

                if (chosen1 != null && chosen2 != null) {
                    startButton.setEnabled(true);
                }
            }
        });

        grid.add(view, gbc);
    }
}
