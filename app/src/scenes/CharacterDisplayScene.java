package scenes;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class CharacterDisplayScene extends JPanel {

    public CharacterDisplayScene(Elementia frame, CharacterInfo character) {

        setLayout(null);
        setBackground(new Color(20, 20, 20));

        ImageIcon icon = null;
        String fullPath = System.getProperty("user.dir") + "/" + character.getImagePath();
        File file = new File(fullPath);

        if (file.exists()) {
            Image img = new ImageIcon(fullPath).getImage();
            Image scaledImg = img.getScaledInstance(500, 570, Image.SCALE_SMOOTH);
            icon = new ImageIcon(scaledImg);
        } else {
            System.out.println("⚠ Image not found: " + file.getAbsolutePath());
        }

        JLabel characterImage;
        if (icon != null) {
            characterImage = new JLabel(icon);
        } else {
            characterImage = new JLabel("[Character Image]");
            characterImage.setForeground(Color.WHITE);
            characterImage.setHorizontalAlignment(SwingConstants.CENTER);
        }

        characterImage.setBounds(20, 20, 400, 570);
        characterImage.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        add(characterImage);

        JLabel nameLabel = new JLabel(character.getName());
        nameLabel.setBounds(580, 10, 590, 80);
        nameLabel.setForeground(Color.WHITE);
        nameLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        add(nameLabel);

        JTextArea storyline = new JTextArea(character.getStoryline());
        storyline.setBounds(450, 100, 500, 200);
        storyline.setLineWrap(true);
        storyline.setWrapStyleWord(true);
        storyline.setEditable(false);
        storyline.setBackground(Color.BLACK);
        storyline.setForeground(Color.WHITE);
        storyline.setFont(new Font("Serif", Font.PLAIN, 16));
        add(storyline);

        JLabel skillNameLabel = new JLabel("");
        skillNameLabel.setBounds(450, 490, 600, 30);
        skillNameLabel.setForeground(Color.WHITE);
        skillNameLabel.setFont(new Font("Sans Serif", Font.BOLD, 18));
        add(skillNameLabel);

        JLabel skillDescription = new JLabel("Hover over a skill to see its description.");
        skillDescription.setBounds(450, 510, 600, 30);
        skillDescription.setForeground(Color.WHITE);
        add(skillDescription);

        String[] skillNames = character.getSkills();
        String[] skillDescriptions = character.getSkillDescriptions();
        String[] skillImages = character.getSkillImages();
        String characterFolder = character.getFolderName();

        int x = 450;

        for (int i = 0; i < skillImages.length; i++) {

            JPanel skillPanel = new JPanel(null);
            skillPanel.setBounds(x, 320, 160, 160);
            skillPanel.setBackground(new Color(40, 40, 40));
            skillPanel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));

            JLabel skillImgLabel = new JLabel();
            skillImgLabel.setBounds(0, 0, 160, 160);

            String imagePath = System.getProperty("user.dir")
                    + "/images/characters/" + characterFolder + "/skills/" + skillImages[i];

            File imgFile = new File(imagePath);

            if (imgFile.exists()) {
                Image img = new ImageIcon(imagePath).getImage();
                Image scaled = img.getScaledInstance(160, 160, Image.SCALE_SMOOTH);
                skillImgLabel.setIcon(new ImageIcon(scaled));
            } else {
                System.out.println("Skill IMAGE NOT FOUND: " + imagePath);
            }

            skillPanel.add(skillImgLabel);

            int index = i;

            skillPanel.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseEntered(java.awt.event.MouseEvent e) {
                    skillNameLabel.setText(skillNames[index]);
                    skillDescription.setText(skillDescriptions[index]);
                }

                @Override
                public void mouseExited(java.awt.event.MouseEvent e) {
                    skillNameLabel.setText("");
                    skillDescription.setText("Hover over a skill to see its description.");
                }
            });

            add(skillPanel);
            x += 170;
        }

        JButton continueButton = new JButton("Continue");
        continueButton.setBounds(450, 600, 500, 40);
        continueButton.setBackground(new Color(60, 60, 60));
        continueButton.setForeground(Color.WHITE);
        continueButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        continueButton.addActionListener(e -> {
            frame.showScreen("LevelSelect");
        });
        add(continueButton);

        JButton backButton = new JButton("Change Character");
        backButton.setBounds(20, 600, 400, 40);
        backButton.setBackground(new Color(60, 60, 60));
        backButton.setForeground(Color.WHITE);
        backButton.setFont(new Font("SansSerif", Font.BOLD, 14));

        backButton.addActionListener(e -> {
            frame.showScreen("CharacterSelect");
        });

        add(backButton);
    }
}
