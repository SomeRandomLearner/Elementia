package elementia.scenes;

import elementia.PersonView;
import characters.Person;
import characters.heroes.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PVPSetupScene extends JPanel {
    private final JLabel instruction;
    private Person chosen1;
    private Person chosen2;
    private boolean selectingPlayer1 = true;

    public PVPSetupScene(JFrame frame) {
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

        Person aero = new Aero();
        PersonView aeroView = new PersonView(aero, "Elementia-Psalm-s-creator-branch/app/src/images/Aero.png");

        Person kayden = new Kayden();
        PersonView kaydenView = new PersonView(kayden, "Elementia-Psalm-s-creator-branch/app/src/images/Kayden Break Temp.png");

        Person psalm = new Psalm();
        PersonView psalmView = new PersonView(psalm, "Elementia-Psalm-s-creator-branch/app/src/images/PsalmFire.png");

        Person zn = new ZnStream();
        PersonView znView = new PersonView(zn, "Elementia-Psalm-s-creator-branch/app/src/images/ZnStream.png");

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
            frame.getContentPane().removeAll();
            frame.revalidate();
            frame.repaint();
        });

        bottom.add(startBtn);
        bottom.add(backBtn);
        add(bottom, BorderLayout.SOUTH);

        this.startButtonRef = startBtn;
    }

    private final JButton startButtonRef;

    private void addSelectableView(JPanel grid, PersonView view, Person hero, JFrame frame, int gridx, int gridy) {
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
