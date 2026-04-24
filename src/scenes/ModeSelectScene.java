package scenes;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.Objects;

public class ModeSelectScene extends JPanel {

    private Image backgroundImage;
    private Font normalFont = new Font("Times New Roman", Font.PLAIN, 30);

    public ModeSelectScene(Elementia frame) {

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        URL bgPath = getClass().getResource("/resources/GameMode.png");
        if (bgPath != null) {
            backgroundImage = new ImageIcon(bgPath).getImage();
        }

        JLabel modeSelectLabel = new JLabel("Choose a Mode");
        modeSelectLabel.setFont(new Font("Times New Roman", Font.BOLD, 40));
        modeSelectLabel.setForeground(Color.WHITE);
        modeSelectLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 0));
        buttonPanel.setOpaque(false);

        JButton arcadeButton = createStyledButton("Arcade");
        arcadeButton.addActionListener(e -> frame.showScreen("CharacterSelect"));

        JButton pvpButton = createStyledButton("Player vs Player");
        pvpButton.addActionListener(e -> frame.showScreen("PVPCharacterSelect"));

        buttonPanel.add(arcadeButton);
        buttonPanel.add(pvpButton);

        add(Box.createVerticalStrut(200));
        add(modeSelectLabel);
        add(Box.createVerticalStrut(80));
        add(buttonPanel);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
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
