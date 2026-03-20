package scenes;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class ModeSelectScene extends JPanel {
    Image originalBackground;
    JPanel wrapperPanel;
    JPanel buttonPanel;
    JLabel backgroundLabel;
    Font normalFont = new Font("Times New Roman", Font.PLAIN, 30);

    public ModeSelectScene(Elementia frame){
        setLayout(new GridLayout(2,1));
        wrapperPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        URL bgPath = getClass().getResource("/resources/Menu Bar.png"); // change later
        assert bgPath != null : "Menu Bar not found";
        ImageIcon bgIcon = new ImageIcon(bgPath);
        originalBackground = bgIcon.getImage();
        backgroundLabel = new JLabel(new ImageIcon(originalBackground));

        JLabel modeSelectLabel = new JLabel("Choose a Mode");
        Font font = new Font("Times New Roman", Font.BOLD, 40);
        modeSelectLabel.setFont(font);

        buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setSize(400, 600);

        JButton arcadeButton = createStyledButton("Arcade");
        arcadeButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        arcadeButton.addActionListener(e -> frame.showScreen("CharacterSelect"));

        JButton pvpButton = createStyledButton("Player vs Player");
        pvpButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        pvpButton.addActionListener(e -> frame.showScreen("PVPCharacterSelect"));

        wrapperPanel.add(modeSelectLabel, gbc);
        buttonPanel.add(arcadeButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        buttonPanel.add(pvpButton);

        add(wrapperPanel);
        add(buttonPanel);
        // add(backButton);


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
