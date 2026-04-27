package scenes;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;

public class MainMenuScene extends JPanel {
    Container con;
    JPanel titleNamePanel, buttonPanel, creditsPanel;
    JLabel titleNameLabel, backgroundLabel;
    Font normalFont = new Font("Times New Roman", Font.PLAIN, 30);
    JButton startButton, exitButton, creditsButton;
    Image originalBackground;
    int width = 620, height = 1000;
    public MainMenuScene(Elementia frame) {
        URL bgPath = getClass().getResource("/resources/ELEMENTIA.png");
        assert bgPath != null : "Menu Bar not found";
        ImageIcon bgIcon = new ImageIcon(bgPath);
        originalBackground = bgIcon.getImage();
        backgroundLabel = new JLabel(new ImageIcon(originalBackground.getScaledInstance(width, height, Image.SCALE_SMOOTH)));
        backgroundLabel.setBounds(0, 0, width, height);


        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setBounds(0, 0, width, height);


        titleNamePanel = new JPanel();
        titleNamePanel.setOpaque(false);
        titleNameLabel = new JLabel("ELEMENTIA");
        titleNameLabel.setForeground(Color.WHITE);
        titleNamePanel.add(titleNameLabel);
        layeredPane.add(titleNamePanel, Integer.valueOf(1));

        buttonPanel = new JPanel(new GridLayout(3, 1, 0, 20));
        buttonPanel.setOpaque(false);

        creditsPanel = new JPanel();
        creditsPanel.setLayout(new BoxLayout(creditsPanel, BoxLayout.Y_AXIS));
        creditsPanel.setBackground(Color.BLACK);
        JLabel creditsLabel = new JLabel("CREDITS");
        creditsLabel.setForeground(Color.WHITE);

        creditsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        creditsPanel.add(creditsLabel);

        JTextArea creditsTextArea = getCreditsArea();
        creditsTextArea.setAlignmentX(Component.CENTER_ALIGNMENT);
        creditsPanel.add(creditsTextArea);

        JButton closeButton = createStyledButton("Return");
        closeButton.setAlignmentX(Component.CENTER_ALIGNMENT);


        closeButton.addActionListener(e -> creditsPanel.setVisible(false));
        creditsPanel.setVisible(false);
        creditsPanel.add(closeButton);

        startButton = createStyledButton("START");
        startButton.addActionListener(e -> frame.showScreen("ModeSelect"));

        creditsButton = createStyledButton("CREDITS");
        creditsButton.addActionListener(e -> creditsPanel.setVisible(true));

        exitButton = createStyledButton("EXIT");
        exitButton.addActionListener(e -> System.exit(0));

        buttonPanel.add(startButton);
        buttonPanel.add(creditsButton);
        buttonPanel.add(exitButton);

        layeredPane.add(creditsPanel, Integer.valueOf(2));
        layeredPane.add(buttonPanel, Integer.valueOf(1));
        layeredPane.add(backgroundLabel, Integer.valueOf(0));
        this.setLayout(new BorderLayout());
        this.add(layeredPane, BorderLayout.CENTER);

        updateLayout(frame, layeredPane);


        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                updateLayout(frame, layeredPane);
            }
        });
    }

    private static JTextArea getCreditsArea() {
        JTextArea creditsTextArea = new JTextArea();
        creditsTextArea.setEditable(false);
        creditsTextArea.setBackground(new Color(30, 30, 40));
        creditsTextArea.setForeground(new Color(200, 200, 220));
        creditsTextArea.setFont(new Font("Courier New", Font.PLAIN, 11));
        creditsTextArea.setLineWrap(true);
        creditsTextArea.setWrapStyleWord(true);
        creditsTextArea.setMargin(new Insets(5, 5, 5, 5));
        creditsTextArea.setText(
                "This game is non-commercial and for educational purposes only\n" +
                "Contributors:\n" +
                "Joshua Raagas\n" +
                "Kaizen Gabriel Guiroy\n" +
                "Kangel Hermosilla\n" +
                "Maria Mie Cadungog\n" +
                "Psalmist Mae Guiroy\n" +
                "Vince Jayson\n\n\n" +
                "Basic Attack Sound by freesound_CrunchpixStudio");
        return creditsTextArea;
    }


    private void updateLayout(Elementia frame, JLayeredPane layeredPane) {
        int width = frame.getWidth();
        int height = frame.getHeight();

        Image scaledImage = originalBackground.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        backgroundLabel.setIcon(new ImageIcon(scaledImage));
        backgroundLabel.setBounds(0, 0, width, height);
        layeredPane.setBounds(0, 0, width, height);

        int buttonWidth = 200;
        int buttonHeight = 200;
        int buttonX = (width - buttonWidth) / 2;
        int buttonY = (height - buttonHeight)/ 2 + 150;
        buttonPanel.setBounds(buttonX, buttonY, buttonWidth, buttonHeight);

        int creditsWidth = 720;
        int creditsHeight = 400;
        creditsPanel.setBounds((width - creditsWidth)/2, (height - creditsHeight)/2, creditsWidth, creditsHeight);

        frame.revalidate();
        frame.repaint();
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