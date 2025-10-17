package elementia.scenes;
import elementia.*;

import javax.swing.*;
import java.awt.*;

public class MainMenuScene extends JPanel {
    public MainMenuScene(Elementia frame) {// Should only be Elementia
        setLayout(new BorderLayout());
        setBackground(Color.BLACK);

        JLabel title = new JLabel("Main Menu", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 32));
        title.setForeground(Color.WHITE);
        add(title, BorderLayout.CENTER);

        JPanel buttons = new JPanel(new GridLayout(3,1));
        JButton startBtn = Utility.createButton("Start");
        JButton pvpBtn = Utility.createButton("Human VS Human");
        JButton exitBtn = Utility.createButton("Exit");

        startBtn.addActionListener(e -> frame.showScreen("CharacterSelect"));
        pvpBtn.addActionListener(e -> frame.showScreen("PVPBattle"));
        exitBtn.addActionListener(e -> System.exit(0));

        buttons.add(startBtn);
        buttons.add(pvpBtn);
        buttons.add(exitBtn);

        add(buttons, BorderLayout.SOUTH);
    }

}
