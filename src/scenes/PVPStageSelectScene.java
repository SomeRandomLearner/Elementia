package scenes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.Objects;

public class PVPStageSelectScene extends JPanel {
    private Image originalBackground;
    private JLabel backgroundLabel;
    private final JButton confirmButton;
    private final Font normalFont = new Font("Times New Roman", Font.PLAIN, 30);
    private int choice;

    ImageIcon chosenStageBackground = null;

    public PVPStageSelectScene(Elementia frame) {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();


        URL bgPath = getClass().getResource("/resources/Menu Bar.png");
        assert bgPath != null : "Menu Bar not found";
        ImageIcon bgIcon = new ImageIcon(bgPath);
        originalBackground = bgIcon.getImage();


        JLabel stageSelectLabel = new JLabel("Choose a Stage");
        stageSelectLabel.setFont(new Font("Times New Roman", Font.BOLD, 20));
        stageSelectLabel.setForeground(Color.WHITE);
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        add(stageSelectLabel);

        JPanel wrapperPanel = new JPanel(new GridLayout(3, 3));

        for (int i = 1; i <= 5; i++) {
            final int choiceNumber = i;
            ImageIcon selectBgIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/LevelBackgrounds/Level" + i + "Background.png")));
            JLabel backgroundLabel = new JLabel(new ImageIcon(selectBgIcon.getImage().getScaledInstance(180, 180, Image.SCALE_SMOOTH)));

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
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        add(confirmButton, gbc);

        confirmButton.addActionListener(e -> {
            frame.getPVPBattle().setPVPBattleSceneBackground(choice);
            // Reset all characters before the battle starts
            frame.getPVPBattle().startGame();
            frame.showScreen("PVPBattle");
        });

        gbc.gridy = 4;
        JButton backBtn = createStyledButton("Go Back");
        backBtn.addActionListener(e -> frame.showScreen("PVPCharacterSelect"));
        add(backBtn, gbc);
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
