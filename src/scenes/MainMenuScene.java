package scenes;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;

public class MainMenuScene extends JPanel {
    Container con;
    JPanel titleNamePanel, buttonPanel, creditsPanel;
    JLabel titleNameLabel, backgroundLabel;
    Font titlefont = new Font("Times New Roman", Font.BOLD, 100);
    Font normalFont = new Font("Times New Roman", Font.PLAIN, 30);
    JButton startButton, exitButton, creditsButton;
    Image originalBackground;
    int height = 1000, width = 600;
    public MainMenuScene(Elementia frame) {
        URL bgPath = getClass().getResource("/resources/Menu Bar.png");
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
        titleNameLabel.setFont(titlefont);
        titleNamePanel.add(titleNameLabel);
        layeredPane.add(titleNamePanel, Integer.valueOf(1));

        buttonPanel = new JPanel(new GridLayout(3, 1, 0, 50));
        buttonPanel.setOpaque(false);

        creditsPanel = new JPanel();creditsPanel.setLayout(new BoxLayout(creditsPanel, BoxLayout.Y_AXIS));
        creditsPanel.setBackground(Color.BLACK);
        JLabel lblCredits = new JLabel("CREDITS");
        lblCredits.setForeground(Color.WHITE);

        lblCredits.setAlignmentX(Component.CENTER_ALIGNMENT);
        creditsPanel.add(lblCredits);

        JTextArea txtCredits = getCreditsArea();
        txtCredits.setAlignmentX(Component.CENTER_ALIGNMENT);
        creditsPanel.add(txtCredits);

        JButton btnClose = createStyledButton("Return");
        btnClose.setAlignmentX(Component.CENTER_ALIGNMENT);


        btnClose.addActionListener(e -> creditsPanel.setVisible(false));
        creditsPanel.setVisible(false);
        creditsPanel.add(btnClose);

        startButton = createStyledButton("START");
        startButton.addActionListener(e -> frame.showScreen("CharacterSelect"));

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
        JTextArea txtCredits = new JTextArea();
        txtCredits.setEditable(false);
        txtCredits.setBackground(new Color(30, 30, 40));
        txtCredits.setForeground(new Color(200, 200, 220));
        txtCredits.setFont(new Font("Courier New", Font.PLAIN, 11));
        txtCredits.setLineWrap(true);
        txtCredits.setWrapStyleWord(true);
        txtCredits.setMargin(new Insets(5, 5, 5, 5));
        txtCredits.setText("This game is non-commercial and for educational purposes only\nContributors:\nJoshua Raagas\nKaizen Gabriel Guiroy\nKangel Hermosilla\nMaria Mie Cadungog\nPsalmist Mae Guiroy\nVince Jayson");
        return txtCredits;
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