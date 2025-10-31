package scenes;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PVPCharacterSelectScene extends JPanel {
    public PVPCharacterSelectScene(Elementia frame) {
        setLayout(new BorderLayout());
        setBackground(Color.BLACK);

        JPanel buttonPanel = null;



        JLabel title = new JLabel("Select your Character");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        add(title, BorderLayout.NORTH);

        add(buttonPanel, BorderLayout.CENTER);
    }
}
