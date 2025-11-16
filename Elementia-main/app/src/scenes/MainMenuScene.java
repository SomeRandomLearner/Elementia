package scenes;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;

public class MainMenuScene extends JPanel {
    Container con;
    JPanel titleNamePanel, buttonPanel;
    JLabel titleNameLabel, backgroundLabel;
    Font titlefont = new Font("Times New Roman", Font.BOLD, 100);
    Font normalFont = new Font("Times New Roman", Font.PLAIN, 30);
    JButton startButton, humanVsHumanButton, exitButton;
    Image originalBackground;

    public MainMenuScene(Elementia frame) {
        URL bgPath = getClass().getResource("/resources/Menu Bar.png");
        assert bgPath != null : "Menu Bar not found";
        ImageIcon bgIcon = new ImageIcon(bgPath);
        originalBackground = bgIcon.getImage();
        backgroundLabel = new JLabel(new ImageIcon(originalBackground.getScaledInstance(1000, 600, Image.SCALE_SMOOTH)));
        backgroundLabel.setBounds(0, 0, 1000, 600);


        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setBounds(0, 0, 1000, 600);


        titleNamePanel = new JPanel();
        titleNamePanel.setOpaque(false);
        titleNameLabel = new JLabel("ELEMENTIA");
        titleNameLabel.setForeground(Color.BLACK);
        titleNameLabel.setFont(titlefont);
        titleNamePanel.add(titleNameLabel);
        layeredPane.add(titleNamePanel, Integer.valueOf(1));


        buttonPanel = new JPanel(new GridLayout(3, 1, 0, 15));
        buttonPanel.setOpaque(false);

        startButton = createStyledButton("START");
        startButton.addActionListener(e -> frame.showScreen("CharacterDisplay"));

        humanVsHumanButton = createStyledButton("HUMAN vs HUMAN");
        humanVsHumanButton.addActionListener(e -> frame.showScreen("PVPCharacterSelect"));

        exitButton = createStyledButton("EXIT");
        exitButton.addActionListener(e -> System.exit(0));

        buttonPanel.add(startButton);
        buttonPanel.add(humanVsHumanButton);
        buttonPanel.add(exitButton);
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


    private void updateLayout(Elementia frame, JLayeredPane layeredPane) {
        int width = frame.getWidth();
        int height = frame.getHeight();


        Image scaledImage = originalBackground.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        backgroundLabel.setIcon(new ImageIcon(scaledImage));
        backgroundLabel.setBounds(0, 0, width, height);
        layeredPane.setBounds(0, 0, width, height);


        int titleWidth = 700;
        int titleHeight = 150;
        int titleX = (width - titleWidth) / 2;
        int titleY = height / 8;
        titleNamePanel.setBounds(titleX, titleY, titleWidth, titleHeight);


        int buttonWidth = 350;
        int buttonHeight = 250;
        int buttonX = (width - buttonWidth) / 2;
        int buttonY = (height - buttonHeight) / 2 + 50;
        buttonPanel.setBounds(buttonX, buttonY, buttonWidth, buttonHeight);

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