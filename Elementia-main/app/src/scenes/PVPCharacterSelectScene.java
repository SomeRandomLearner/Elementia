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
            // Create fresh character instances with full HP and mana
            GameCharacter player1 = createCharacterInstance(chosen1);
            GameCharacter player2 = createCharacterInstance(chosen2);
            Teams.addToAlliedTeam(player1);
            Teams.addToEnemyTeam(player2);
            frame.addPVPBattleScene();
            frame.showScreen("PVPBattle");
        });

        JButton backButton = Utility.createButton("Back");
        backButton.addActionListener(e -> {
            resetSelection();
            frame.showScreen("MainMenu");
        });

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

    private GameCharacter createCharacterInstance(GameCharacter template) {
        GameCharacter fresh;
        if (template instanceof Aero) {
            fresh = new Aero();
        } else if (template instanceof Kayden) {
            fresh = new Kayden();
        } else if (template instanceof Psalm) {
            fresh = new Psalm();
        } else if (template instanceof Ripper) {
            fresh = new Ripper();
        } else if (template instanceof ZnStream) {
            fresh = new ZnStream();
        } else {
            fresh = new Aero(); // Default fallback
        }
        return fresh;
    }

    private void resetSelection() {
        chosen1 = null;
        chosen2 = null;
        selectingPlayer1 = true;
        startButton.setEnabled(false);
        instruction.setText("Player 1: Select your character");
    }
}
