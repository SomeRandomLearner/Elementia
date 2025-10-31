package elementia.scenes;
import elementia.PersonView;
import characters.heroes.*;
import elementia.Elementia;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PVPCharacterSelectScene extends JPanel {
    public PVPCharacterSelectScene(Elementia frame) {
        setLayout(new BorderLayout());
        setBackground(Color.BLACK);

        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);

        PersonView aeroSprite = new PersonView(new Aero(), "Elementia-Psalm-s-creator-branch/app/src/images/Aero.png");
        PersonView kaydenSprite = new PersonView(new Kayden(), "Elementia-Psalm-s-creator-branch/app/src/images/Kayden Break Temp.png");
        PersonView psalmSprite = new PersonView(new Psalm(), "Elementia-Psalm-s-creator-branch/app/src/images/PsalmFire.png");
        PersonView znStreamSprite = new PersonView(new ZnStream(), "Elementia-Psalm-s-creator-branch/app/src/images/ZnStream.png");
        JLabel currentTarget = null;
        Border selectedBorder = BorderFactory.createLineBorder(Color.YELLOW, 3);
//        aeroSprite.addMouseListener(new MouseAdapter() {
//                                        @Override
//                                        public void mouseClicked(MouseEvent e) {
//                                            if (currentTarget != null) {
//                                                currentTarget.setBorder(null); // Deselect old target
//                                            }
//                                            aeroSprite.setBorder(selectedBorder); // Select new target
//                                            currentTarget = aeroSprite; // Update currentTarget
//                                        }
//                                    });
        gbc.gridx = 0;
        gbc.gridy = 0;
        buttonPanel.add(aeroSprite, gbc);
        gbc.gridx = 1;
        gbc.gridy = 0;
        buttonPanel.add(kaydenSprite, gbc);
        gbc.gridx = 2;
        gbc.gridy = 0;
        buttonPanel.add(psalmSprite, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        buttonPanel.add(znStreamSprite, gbc);


        JLabel title = new JLabel("Select your Character");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        add(title, BorderLayout.NORTH);

        add(buttonPanel, BorderLayout.CENTER);
    }
}
