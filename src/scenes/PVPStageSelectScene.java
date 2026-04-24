package scenes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.Objects;

public class PVPStageSelectScene extends JPanel {

    private Image originalBackground;
    private final JButton confirmButton;
    private final Font normalFont = new Font("Times New Roman", Font.PLAIN, 30);
    private int choice;

    public PVPStageSelectScene(Elementia frame) {

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();


        URL bgPath = getClass().getResource("/resources/GameMode.png");
        if (bgPath != null) {
            ImageIcon bgIcon = new ImageIcon(bgPath);
            originalBackground = bgIcon.getImage();
        }


        JLabel stageSelectLabel = new JLabel("Choose a Stage");
        stageSelectLabel.setFont(new Font("Times New Roman", Font.BOLD, 28));
        stageSelectLabel.setForeground(Color.WHITE);

        gbc.gridy = 0;
        add(stageSelectLabel, gbc);


        JPanel wrapperPanel = new JPanel(new GridLayout(3, 3, 10, 10));
        wrapperPanel.setOpaque(false);

        for (int i = 1; i <= 5; i++) {

            final int choiceNumber = i;

            ImageIcon selectBgIcon = new ImageIcon(
                    Objects.requireNonNull(
                            getClass().getResource("/resources/LevelBackgrounds/Level" + i + "Background.png")
                    )
            );

            JLabel backgroundLabel = new JLabel(
                    new ImageIcon(selectBgIcon.getImage().getScaledInstance(250, 180, Image.SCALE_SMOOTH))
            );

            backgroundLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    choice = choiceNumber;
                    confirmButton.setEnabled(true);
                }
            });

            wrapperPanel.add(backgroundLabel);
        }

        gbc.gridy = 1;
        add(wrapperPanel, gbc);

        confirmButton = createStyledButton("Confirm");
        confirmButton.setEnabled(false);

        confirmButton.addActionListener(e -> {
            frame.getPVPBattle().setPVPBattleSceneBackground(choice);
            frame.getPVPBattle().startGame();
            frame.showScreen("PVPBattle");
        });

        gbc.gridy = 2;
        add(confirmButton, gbc);

        JButton backBtn = createStyledButton("Go Back");
        backBtn.addActionListener(e -> frame.showScreen("PVPCharacterSelect"));

        gbc.gridy = 3;
        add(backBtn, gbc);
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (originalBackground != null) {
            g.drawImage(originalBackground, 0, 0, getWidth(), getHeight(), this);
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
}
