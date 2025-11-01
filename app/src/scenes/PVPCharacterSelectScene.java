package scenes;

import characters.CharacterView;
import characters.GameCharacter;
import characters.ZnStream;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PVPCharacterSelectScene extends JPanel {
    private final JLabel instruction;
    private GameCharacter chosen1;
    private GameCharacter chosen2;
    private boolean selectingPlayer1 = true;

    public PVPCharacterSelectScene(Elementia frame) {
        setLayout(new BorderLayout());
        setBackground(Color.DARK_GRAY);
        instruction = new JLabel("Player 1: Select your character", SwingConstants.CENTER);
        instruction.setForeground(Color.WHITE);
        instruction.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
        add(instruction, BorderLayout.NORTH);

        JPanel grid = new JPanel(new GridBagLayout());
        grid.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10,10,10,10);

        GameCharacter aero = new GameCharacter("Aero", 100, 100, 30, 10, "/images/Aero.png");
        CharacterView aeroView = new CharacterView(aero, "/images/Aero.png");

        GameCharacter kayden = new GameCharacter("Kayden", 100, 100, 30, 10, "/images/Aero.png");
        CharacterView kaydenView = new CharacterView(kayden, "/images/Kayden Break Temp.png");

        GameCharacter psalm = new GameCharacter("Psalm",100, 100, 30, 10, "/images/Aero.png");
        CharacterView psalmView = new CharacterView(psalm, "/images/PsalmFire.png");

        GameCharacter zn = new ZnStream();
        CharacterView znView = new CharacterView(zn, "/images/ZnStream.png");

        addSelectableView(grid, aeroView, aero, frame, 0,0);
        addSelectableView(grid, kaydenView, kayden, frame, 1,0);
        addSelectableView(grid, psalmView, psalm, frame, 0,1);
        addSelectableView(grid, znView, zn, frame, 1,1);

        add(grid, BorderLayout.CENTER);

        JPanel bottom = new JPanel();
        bottom.setOpaque(false);
        JButton startBtn = new JButton("Start PVP");
        startBtn.setEnabled(false);
        startBtn.addActionListener(e -> {
            if (chosen1 != null && chosen2 != null) {
                PVPBattleScene battle = new PVPBattleScene(frame);
                battle.setPlayer1(chosen1);
                battle.setPlayer2(chosen2);

                frame.getContentPane().removeAll();
                frame.getContentPane().add(battle);
                frame.revalidate();
                frame.repaint();
            }
        });

        JButton backBtn = new JButton("Back");
        backBtn.addActionListener(e -> {
//            frame.getContentPane().removeAll();
//            frame.revalidate();
//            frame.repaint();
            frame.showScreen("MainMenu");
        });

        bottom.add(startBtn);
        bottom.add(backBtn);
        add(bottom, BorderLayout.SOUTH);

        this.startButtonRef = startBtn;
    }

    private final JButton startButtonRef;

    private void addSelectableView(JPanel grid, CharacterView view, GameCharacter hero, JFrame frame, int gridx, int gridy) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = gridx;
        gbc.gridy = gridy;
        gbc.insets = new Insets(8,8,8,8);

        view.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (selectingPlayer1) {
                    chosen1 = hero;
                    instruction.setText("Player 2: Select your character");
                    selectingPlayer1 = false;
                } else {
                    chosen2 = hero;
                    instruction.setText("Both chosen. Click Start PVP");
                }
                if (chosen1 != null && chosen2 != null) {
                    startButtonRef.setEnabled(true);
                }
            }
        });

        grid.add(view, gbc);
    }
}
