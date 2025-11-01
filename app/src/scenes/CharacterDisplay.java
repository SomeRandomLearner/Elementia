package scenes;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;

public class CharacterDisplay extends JPanel {

    // GUI components
    private JLabel characterImageLabel;
    private JTextArea storylineArea;
    private JLabel skillInfoLabel;
    private JLabel[] skillIcons;

    // Example data
    private String characterName = "Aero — The Wind Element";
    private String storyline = """
            Born on the cliffs where the winds howl endlessly,
            Aero was chosen to wield the unseen strengths of the skies.
            With powers that protect and guide, she uses her elemental
            wind to support her allies and turn the tide of battlefield.
            """;

    private String[] skillNames = {"Zephyr Splash", "Cyclone Furry", "Aether Guard"};
    private String[] skillDescriptions = {
            "Strike with a high-speed burst of wind and water knocking enemies off balance.",
            "Spin into a violent cyclone, dragging foes in before blasting them away.",
            "Deploy a swirling wind shield that blocks attacks and restores stamina on release."
    };

    private String[] skillImagePaths = {"skill1.png", "skill2.png", "skill3.png"};

    public CharacterDisplay(Elementia frame) {
        setSize(900, 550);
        setLayout(new BorderLayout(10, 10));

        JLabel nameLabel = new JLabel(characterName, SwingConstants.CENTER);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 24));
        add(nameLabel, BorderLayout.NORTH);

        JPanel mainPanel = new JPanel(new GridLayout(1, 2, 10, 10));


        JPanel leftPanel = new JPanel(new BorderLayout());
        characterImageLabel = new JLabel("CHARACTER IMAGE", SwingConstants.CENTER);
        characterImageLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        characterImageLabel.setFont(new Font("Arial", Font.BOLD, 16));
        characterImageLabel.setOpaque(true);
        characterImageLabel.setBackground(Color.LIGHT_GRAY);

        URL aeroPath = getClass().getResource("/images/Aero.png");
        assert aeroPath != null : "Aero image not found";
        ImageIcon icon = new ImageIcon(aeroPath);
        if (icon.getIconWidth() > 0) {
            Image img = icon.getImage().getScaledInstance(100, 200, Image.SCALE_SMOOTH);
            characterImageLabel.setIcon(new ImageIcon(img));
            characterImageLabel.setText("");
        }
        leftPanel.add(characterImageLabel, BorderLayout.CENTER);


        JPanel rightPanel = new JPanel(new BorderLayout(10, 10));

        //Kaizen is editing here
        JButton nextBtn = Utility.createButton("Next");
        JButton backBtn = Utility.createButton("Back");
        JButton selectBtn = Utility.createButton("Select");
        selectBtn.addActionListener(e -> {
            frame.showScreen("LevelSelect");
        });
        storylineArea = new JTextArea(storyline);
        storylineArea.setWrapStyleWord(true);
        storylineArea.setLineWrap(true);
        storylineArea.setEditable(false);
        storylineArea.setBorder(BorderFactory.createTitledBorder("The Character's Story"));
        storylineArea.setFont(new Font("Arial", Font.PLAIN, 16));
        storylineArea.setForeground(Color.BLACK);
        rightPanel.add(new JScrollPane(storylineArea), BorderLayout.NORTH);

        JPanel bottomRightPanel = new JPanel(new BorderLayout(5, 5));

        JPanel skillsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        skillIcons = new JLabel[3];

        for (int i = 0; i < 3; i++) {
            skillIcons[i] = new JLabel("", SwingConstants.CENTER);
            skillIcons[i].setPreferredSize(new Dimension(135, 135)); // Bigger square
            skillIcons[i].setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
            skillIcons[i].setOpaque(true);
            skillIcons[i].setBackground(new Color(230, 230, 230));
            skillIcons[i].setFont(new Font("Arial", Font.BOLD, 12));


            ImageIcon skillIcon = new ImageIcon(skillImagePaths[i]);
            if (skillIcon.getIconWidth() > 0) {
                Image skillImg = skillIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                skillIcons[i].setIcon(new ImageIcon(skillImg));
            } else {
                skillIcons[i].setText(skillNames[i]); // fallback text
            }

            int index = i;
            skillIcons[i].addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    skillInfoLabel.setText(skillDescriptions[index]);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    skillInfoLabel.setText("Hover over a skill to see info...");
                }
            });

            skillsPanel.add(skillIcons[i]);
        }

        // Skill info label
        skillInfoLabel = new JLabel("Hover over a skill to see info...", SwingConstants.CENTER);
        skillInfoLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        skillInfoLabel.setBorder(BorderFactory.createEmptyBorder(10, 20, 90, 20));

        bottomRightPanel.add(skillsPanel, BorderLayout.CENTER);
        bottomRightPanel.add(skillInfoLabel, BorderLayout.SOUTH);

        rightPanel.add(bottomRightPanel, BorderLayout.CENTER);

        mainPanel.add(leftPanel);
        mainPanel.add(rightPanel);
        add(mainPanel, BorderLayout.CENTER);


        add(nextBtn, BorderLayout.EAST);
        add(backBtn, BorderLayout.WEST);
        add(selectBtn, BorderLayout.SOUTH);
        setVisible(true);
    }
}
