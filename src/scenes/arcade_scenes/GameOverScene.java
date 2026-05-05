package scenes.arcade_scenes;

import jdk.jshell.execution.Util;
import scenes.Elementia;
import utils.Utility;

import javax.swing.*;
import java.awt.*;

public class GameOverScene extends JPanel {
    public GameOverScene(Elementia frame){
        setLayout(new BorderLayout());

        JLabel gameOverLabel = new JLabel("GAME OVER");
        gameOverLabel.setOpaque(false);
        gameOverLabel.setFont(new Font("Times New Roman", Font.BOLD, 40));
        JPanel gameOverPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();


        JButton backButton = Utility.createButton("BACK");

        backButton.addActionListener(e -> {
            frame.showScreen("MainMenu");
        });

        gameOverPanel.add(gameOverLabel, gbc);
        add(gameOverPanel, BorderLayout.CENTER);
        add(backButton, BorderLayout.SOUTH);
    }
}
