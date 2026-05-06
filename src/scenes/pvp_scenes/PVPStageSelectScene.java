package scenes.pvp_scenes;

import scenes.Elementia;
import utils.Utility;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.Objects;

public class PVPStageSelectScene extends JPanel {

    private Image originalBackground = null;
    private JButton confirmButton = Utility.createButton("Confirm");
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


        JPanel wrapperPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        wrapperPanel.setOpaque(false);

        for (int i = 1; i <= 4; i++) {

            final int choiceNumber = i;

            ImageIcon selectBgIcon = new ImageIcon(
                    Objects.requireNonNull(
                            getClass().getResource("/resources/LevelBackgrounds/LVL" + i + "_BG.png")
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

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setOpaque(false);

        JButton backButton = Utility.createButton("Go Back");
        backButton.addActionListener(e -> {
            confirmButton.setEnabled(false);
            frame.showScreen("PVPCharacterSelect");
        });
        buttonPanel.add(backButton, gbc);


        confirmButton.setEnabled(false);

        confirmButton.addActionListener(e -> {
            frame.getPVPBattle().setPVPBattleSceneBackground(choice);
            frame.getPVPBattle().startGame();
            frame.showScreen("PVPBattle");
            confirmButton.setEnabled(false);
        });;

        buttonPanel.add(confirmButton);
        gbc.gridy = 2;
        add(buttonPanel, gbc);
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (originalBackground != null) {
            g.drawImage(originalBackground, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
