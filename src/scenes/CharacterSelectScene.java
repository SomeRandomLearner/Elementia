package scenes;

import characters.*;

import javax.swing.*;
import java.awt.*;

public class CharacterSelectScene extends JPanel {


    public CharacterSelectScene(Elementia frame) {


        setLayout(new BorderLayout());
        setBackground(Color.DARK_GRAY);

        JLabel titleLabel = new JLabel("Select Your Character", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(titleLabel, BorderLayout.NORTH);

        JPanel characterPanel = new JPanel(new GridLayout(1, 5, 15, 10));
        characterPanel.setBackground(Color.DARK_GRAY);
        characterPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 40, 40));


        CharacterInfo[] characters = {
                new CharacterInfo(
                        "<html><center>Aero<br>The Wind Element</center></html>",
                        "Born on the cliffs where the winds howl endlessly, Aero commands the freedom of the skies.",
                        new String[]{"Zephyr Slash", "Cyclone Fury", "Aether Guard"},
                        new String[]{
                                "A slicing wave of compressed wind blows forward.",
                                "A spinning vortex pulls enemies inward.",
                                "A wind barrier forms and deflects attacks."
                        },
                        "images/characters/AERO/AERO.png",
                        new String[]{
                                "FIRE KICK.png",
                                "FIRE PUNCH.png",
                                "HEALING FAN.png"
                        },
                        "AERO"
                ),

                new CharacterInfo(
                        "<html><center>Kayden<br>The Lightning Element</center></html>",
                        "Forged in volcanic fury, Ignis burns to protect allies with unyielding flame.",
                        new String[]{"LIGHTNING STRIKE", "FORCE CONTROL", "SUPER SPEED"},
                        new String[]{
                                "Kayden dashes at lightning velocity.",
                                "A concentrated bolt shoots from his hands.",
                                "Electrifies the area, pushing or pulling foes."
                        },
                        "images/characters/KAYDEN/KAYDEN.png",
                        new String[]{
                                "LIGHTNING STRIKE.png",
                                "FORCE CONTROL.png",
                                "SUPER SPEED.png"
                        },
                        "KAYDEN"
                ),

                new CharacterInfo(
                        "<html><center>Psalm<br>The Fire Element</center></html>",
                        "A guardian of melody and light, Psalm burns with radiant flame.",
                        new String[]{"FIRE KICK", "HEALING FAN", "FIRE PUNCH"},
                        new String[]{
                                "A fiery combo hits multiple enemies.",
                                "A warm flame heals nearby allies.",
                                "A flaming kick sends enemies flying."
                        },
                        "images/characters/PSALM/PSALM.png",
                        new String[]{
                                "FIRE KICK.png",
                                "HEALING FAN.png",
                                "FIRE PUNCH.png"
                        },
                        "PSALM"
                ),

                new CharacterInfo(
                        "<html><center>Ripper<br>The Shadow Element</center></html>",
                        "A silent hunter cloaked in darkness.",
                        new String[]{"Night Slash", "Phantom Step", "Void Strike"},
                        new String[]{
                                "Strikes from the shadows.",
                                "Moves unseen, evading danger.",
                                "Unleashes a void-powered slash."
                        },
                        "images/characters/RIPPER/RIPPER.png",
                        new String[]{
                                "FIRE KICK.png",
                                "FIRE PUNCH.png",
                                "HEALING FAN.png"
                        },
                        "RIPPER"
                ),

                new CharacterInfo(
                        "<html><center>ZnStream<br>The Water Element</center></html>",
                        "Raised beneath the tides, ZnStream channels the deep ocean.",
                        new String[]{"Wave Crush", "Tidal Prison", "Ocean's Grace"},
                        new String[]{
                                "ZnStream dissolves into water to evade damage.",
                                "Traps enemies in a swirling whirlpool.",
                                "Heals and strengthens allies nearby."
                        },
                        "images/characters/ZNSTREAM/ZNSTREAM.png",
                        new String[]{
                                "LIQUIFY.png",
                                "WATER SLINGSHOT.png",
                                "WATER TAKEOVER.png"
                        },
                        "ZNSTREAM"
                )
        };

        for (int i = 0; i < characters.length; i++) {

            CharacterInfo ch = characters[i];

            JPanel card = new JPanel(new BorderLayout());
            card.setBackground(new Color(100, 100, 100));
            card.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
            card.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

            JLabel nameLabel = new JLabel(ch.getName(), SwingConstants.CENTER);
            nameLabel.setForeground(Color.WHITE);
            nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));


            ImageIcon icon = null;
            try {
                String fullPath = System.getProperty("user.dir") + "/" + ch.getImagePath();
                java.io.File file = new java.io.File(fullPath);

                if (file.exists()) {
                    Image img = new ImageIcon(fullPath).getImage()
                            .getScaledInstance(550, 550, Image.SCALE_SMOOTH);
                    icon = new ImageIcon(img);
                } else {
                    System.out.println("⚠ Image MISSING: " + file.getAbsolutePath());
                }
            } catch (Exception ex) {
                System.out.println("⚠ Error loading image: " + ch.getImagePath());
            }

            JLabel imageLabel = (icon != null)
                    ? new JLabel(icon, SwingConstants.CENTER)
                    : new JLabel("[Image Missing]", SwingConstants.CENTER);

            imageLabel.setForeground(Color.WHITE);
            imageLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            card.add(imageLabel, BorderLayout.CENTER);
            card.add(nameLabel, BorderLayout.SOUTH);

            int index = i;
            card.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent e) {
                    if(Teams.getAlliedTeamCount() > 0) Teams.clearAlliedTeam();
                    switch(index){
                        case 0 -> Teams.addToAlliedTeam(new Aero());
                        case 1 -> Teams.addToAlliedTeam(new Kayden());
                        case 2 -> Teams.addToAlliedTeam(new Psalm());
                        case 3 -> Teams.addToAlliedTeam(new Ripper());
                        case 4 -> Teams.addToAlliedTeam(new ZnStream());
                    }
                    frame.addCharacterDisplayScene(characters[index]);
                    frame.showScreen("CharacterDisplay");
                }

                @Override
                public void mouseEntered(java.awt.event.MouseEvent e) {
                    card.setBackground(new Color(70, 130, 180));
                }

                @Override
                public void mouseExited(java.awt.event.MouseEvent e) {
                    card.setBackground(new Color(100, 100, 100));
                }
            });

            characterPanel.add(card);
        }

        add(characterPanel, BorderLayout.CENTER);
    }
}


