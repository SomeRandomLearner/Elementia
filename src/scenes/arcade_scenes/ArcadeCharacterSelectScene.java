package scenes.arcade_scenes;

import characters.*;
import logic.LevelManager;
import scenes.Elementia;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.Objects;

public class ArcadeCharacterSelectScene extends JPanel {
    private final ImageIcon aeroImgIcon, kaelisImgIcon, kangelImgIcon, kaydenImgIcon;
    private final ImageIcon psalmImgIcon, maelorImgIcon, ripperImgIcon, veyrionImgIcon, zenStreamImgIcon;

    private GameCharacter chosenCharacter;
    private GameCharacter lockedCharacter = null;

    private JButton confirmButton;
    private Image bgImage;

    private JLabel previewImage, nameLabel, elementLabel, skill1, skill2, skill3;
    private JTextArea descriptionArea;

    public ArcadeCharacterSelectScene(Elementia frame) {
        setLayout(new BorderLayout());

        // BACKGROUND
        bgImage = new ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/CharacterSelectBG.png"))).getImage();

        // TITLE
        JLabel title = new JLabel("SELECT YOUR CHARACTER", SwingConstants.CENTER);
        title.setFont(new Font("Arial Black", Font.BOLD, 40));
        title.setForeground(new Color(255, 255, 255, 240));
        title.setBorder(BorderFactory.createEmptyBorder(25, 0, 20, 0));

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);
        topPanel.add(title, BorderLayout.CENTER);

        // ICONS
        aeroImgIcon = getIcon("/resources/Aero.png");
        kaelisImgIcon = getIcon("/resources/Kaelis.png");
        kangelImgIcon = getIcon("/resources/Kangel.png");
        kaydenImgIcon = getIcon("/resources/Kayden.png");
        maelorImgIcon = getIcon("/resources/Maelor.png");
        psalmImgIcon = getIcon("/resources/Psalm.png");
        ripperImgIcon = getIcon("/resources/Ripper.png");
        veyrionImgIcon = getIcon("/resources/Veyrion.png");
        zenStreamImgIcon = getIcon("/resources/ZenStream.png");

        // 🔥 LUXURY CHARACTER GRID
        JPanel grid = new JPanel(new GridLayout(3, 3, 25, 25));
        grid.setOpaque(false);
        grid.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        grid.add(createPremiumCard(GameCharacter.Character.AERO, aeroImgIcon));
        grid.add(createPremiumCard(GameCharacter.Character.KAELIS, kaelisImgIcon));
        grid.add(createPremiumCard(GameCharacter.Character.KANGEL, kangelImgIcon));
        grid.add(createPremiumCard(GameCharacter.Character.KAYDEN, kaydenImgIcon));
        grid.add(createPremiumCard(GameCharacter.Character.MAELOR, maelorImgIcon));
        grid.add(createPremiumCard(GameCharacter.Character.PSALM, psalmImgIcon));
        grid.add(createPremiumCard(GameCharacter.Character.RIPPER, ripperImgIcon));
        grid.add(createPremiumCard(GameCharacter.Character.VEYRION, veyrionImgIcon));
        grid.add(createPremiumCard(GameCharacter.Character.ZENSTREAM, zenStreamImgIcon));

        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setOpaque(false);
        leftPanel.setPreferredSize(new Dimension(780, 850));
        leftPanel.add(grid, BorderLayout.CENTER);

        // RIGHT PANEL
        JPanel rightPanel = createEpicRightPanel();
        rightPanel.setPreferredSize(new Dimension(850, 850));

        // BUTTONS
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setOpaque(false);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 30, 40));

        confirmButton = createPremiumButton("CONFIRM", new Color(40, 40, 80).darker());
        confirmButton.setEnabled(false);
        confirmButton.setPreferredSize(new Dimension(300, 55));

        JButton backBtn = createPremiumButton("← BACK", new Color(40, 40, 80).darker());
        backBtn.setPreferredSize(new Dimension(250, 55));

        confirmButton.addActionListener(e -> {
            frame.getLevelSelect().setSelectedCharacter(chosenCharacter);
            LevelManager.setBossLevel(chosenCharacter.clone());

            File currentDataFile = new File("data/player_data.txt");
            if(!currentDataFile.getParentFile().exists()){
                currentDataFile.getParentFile().mkdirs();
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(currentDataFile, false),1024)){
                Instant timeStarted = Instant.now();
                writer.write(timeStarted.toString());
            } catch (IOException ex) {
                ex.printStackTrace();
            }


            frame.getLevelSelect().resetProgress(); // I don't get it, but it only works when there are two of them.
            frame.getLevelSelect().resetProgress();
            LevelManager.initLevels();
            frame.showScreen("LevelSelect");
        });

        backBtn.addActionListener(e -> frame.showScreen("ModeSelect"));

        bottomPanel.add(backBtn, BorderLayout.WEST);
        bottomPanel.add(confirmButton, BorderLayout.EAST);

        add(topPanel, BorderLayout.NORTH);
        add(leftPanel, BorderLayout.WEST);
        add(rightPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }
    // PANEL
    private JPanel createEpicRightPanel() {
        JPanel panel = new JPanel(new GridLayout(1, 2, 40, 0));
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createEmptyBorder(30, 35, 30, 35));

        // MASSIVE HERO PREVIEW
        panel.add(createHeroPreviewPanel());

        // SPECTACULAR INFO PANEL
        panel.add(createSpectacularInfoPanel());

        return panel;
    }

    private JPanel createHeroPreviewPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);

        //GLOW EFFECT CONTAINER
        previewImage = new JLabel();
        previewImage.setHorizontalAlignment(SwingConstants.CENTER);
        previewImage.setPreferredSize(new Dimension(680, 780));
        previewImage.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(previewImage, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createSpectacularInfoPanel() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setOpaque(false);

        // VERTICALLY CENTERED CONTENT
        JPanel content = new JPanel();
        content.setOpaque(false);
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setAlignmentY(CENTER_ALIGNMENT);

        // MAIN INFO CONTAINER WITH SEMI-TRANSPARENT BOX
        JPanel mainInfoBox = createMainInfoBox();

        // HERO NAME - MASSIVE
        nameLabel = createSpectacularLabel("CHARACTER", 44, Color.WHITE, true);
        nameLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 25, 0));

        // ELEMENT TYPE
        elementLabel = createSpectacularLabel("ELEMENT", 26, new Color(150, 255, 255), false);
        elementLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        // SKILLS SECTION
        JLabel skillsTitle = createSpectacularLabel("SKILLS", 24, new Color(255, 215, 0), true);
        skillsTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));

        skill1 = createSpectacularLabel("PRIMARY", 18, Color.WHITE, false);
        skill2 = createSpectacularLabel("SECONDARY", 18, Color.WHITE, false);
        skill3 = createSpectacularLabel("ULTIMATE", 18, Color.WHITE, true);

        JLabel loreTitle = createSpectacularLabel("CHARACTER DESCRIPTION", 24, new Color(255, 150, 255), true);
        loreTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 12, 0));

        // DESCRIPTION BOX
        JPanel descriptionBox = createDescriptionBox();
        descriptionArea = new JTextArea("Hover over champions to reveal their power...");
        styleDescriptionArea(descriptionArea);
        descriptionBox.add(descriptionArea, BorderLayout.CENTER);

        // ELEMENT PANEL (Name + Element)
        JPanel elementPanel = createElementPanel(nameLabel, elementLabel);

        // ADD ALL TO MAIN INFO BOX
        mainInfoBox.add(elementPanel, BorderLayout.NORTH);
        mainInfoBox.add(createSkillsPanel(skillsTitle, skill1, skill2, skill3), BorderLayout.CENTER);
        mainInfoBox.add(descriptionBox, BorderLayout.SOUTH);

        content.add(Box.createVerticalGlue());
        content.add(mainInfoBox);
        content.add(Box.createVerticalGlue());

        mainPanel.add(content, BorderLayout.CENTER);
        return mainPanel;
    }

    // MAIN INFO BOX - WRAPS EVERYTHING (Name, Element, Skills, Description)
    private JPanel createMainInfoBox() {
        JPanel box = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Semi-transparent gradient background (25% opacity)
                GradientPaint gradient = new GradientPaint(
                        0, 0, new Color(20, 15, 50, 90), // Top: Deep space purple
                        0, getHeight(), new Color(10, 20, 60, 90) // Bottom: Cosmic blue
                );
                g2d.setPaint(gradient);
                g2d.fillRoundRect(12, 12, getWidth()-24, getHeight()-24, 25, 25);

                // Premium outer glow
                g2d.setColor(new Color(100, 80, 200, 60));
                g2d.setStroke(new BasicStroke(3));
                g2d.drawRoundRect(8, 8, getWidth()-16, getHeight()-16, 28, 28);

                // Inner highlight border
                g2d.setColor(new Color(150, 120, 255, 80));
                g2d.setStroke(new BasicStroke(1.5f));
                g2d.drawRoundRect(15, 15, getWidth()-30, getHeight()-30, 22, 22);

                g2d.dispose();
            }
        };
        box.setOpaque(false);
        box.setBorder(BorderFactory.createEmptyBorder(25, 30, 25, 30));
        box.setPreferredSize(new Dimension(400, 720));
        box.setAlignmentX(Component.CENTER_ALIGNMENT);
        return box;
    }

    private JPanel createElementPanel(JLabel nameLabel, JLabel elementLabel) {
        JPanel elementBox = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Cyan-tinted semi-transparent background (20% opacity)
                g2d.setColor(new Color(0, 180, 255, 70));
                g2d.fillRoundRect(8, 8, getWidth()-16, getHeight()-16, 22, 22);

                // Cyan glow border
                g2d.setColor(new Color(0, 220, 255, 90));
                g2d.setStroke(new BasicStroke(2.5f));
                g2d.drawRoundRect(10, 10, getWidth()-20, getHeight()-20, 20, 20);

                g2d.dispose();
            }
        };
        elementBox.setOpaque(false);
        elementBox.setBorder(BorderFactory.createEmptyBorder(20, 25, 25, 25));
        elementBox.setPreferredSize(new Dimension(330, 160));

        JPanel elementContent = new JPanel(new BorderLayout());
        elementContent.setOpaque(false);
        elementContent.add(nameLabel, BorderLayout.NORTH);
        elementContent.add(elementLabel, BorderLayout.SOUTH);

        elementBox.add(elementContent, BorderLayout.CENTER);
        return elementBox;
    }

    // SKILLS PANEL WITH ITS OWN MINI-BOX
    private JPanel createSkillsPanel(JLabel skillsTitle, JLabel skill1, JLabel skill2, JLabel skill3) {
        JPanel skillsBox = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Gold-tinted semi-transparent background (20% opacity)
                g2d.setColor(new Color(255, 200, 100, 70));
                g2d.fillRoundRect(8, 8, getWidth()-16, getHeight()-16, 18, 18);

                // Golden border glow
                g2d.setColor(new Color(255, 215, 0, 100));
                g2d.setStroke(new BasicStroke(2));
                g2d.drawRoundRect(10, 10, getWidth()-20, getHeight()-20, 16, 16);

                g2d.dispose();
            }
        };
        skillsBox.setOpaque(false);
        skillsBox.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));
        skillsBox.setPreferredSize(new Dimension(330, 180));

        // Skills content panel
        JPanel skillsContent = new JPanel(new GridLayout(4, 1, 0, 8));
        skillsContent.setOpaque(false);
        skillsContent.add(skillsTitle);
        skillsContent.add(skill1);
        skillsContent.add(skill2);
        skillsContent.add(skill3);

        skillsBox.add(skillsContent, BorderLayout.CENTER);
        return skillsBox;
    }

    // DESCRIPTION BOX
    private JPanel createDescriptionBox() {
        JPanel box = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Semi-transparent gradient background (25% opacity)
                GradientPaint gradient = new GradientPaint(
                        0, 0, new Color(60, 40, 100, 100), // Top-left: dark purple
                        0, getHeight(), new Color(40, 60, 120, 100) // Bottom: dark blue
                );
                g2d.setPaint(gradient);
                g2d.fillRoundRect(8, 8, getWidth()-16, getHeight()-16, 20, 20);

                // Subtle inner glow border
                g2d.setColor(new Color(120, 100, 200, 80));
                g2d.setStroke(new BasicStroke(2));
                g2d.drawRoundRect(10, 10, getWidth()-20, getHeight()-20, 18, 18);

                g2d.dispose();
            }
        };
        box.setOpaque(false);
        box.setBorder(BorderFactory.createEmptyBorder(15, 25, 15, 25));
        box.setPreferredSize(new Dimension(330, 140));
        box.setAlignmentX(Component.CENTER_ALIGNMENT);
        return box;
    }

    private JPanel createPremiumCard(GameCharacter.Character character, ImageIcon icon) {
        JPanel card = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(new Color(0, 150, 255, 30));
                g2d.fillRoundRect(8, 8, getWidth()-16, getHeight()-16, 25, 25);
                g2d.dispose();
            }
        };
        card.setPreferredSize(new Dimension(200, 200));
        card.setOpaque(false);
        card.setCursor(new Cursor(Cursor.HAND_CURSOR));
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(255, 255, 255, 120), 3, true),
                BorderFactory.createEmptyBorder(12, 12, 12, 12)
        ));

        JLabel img = new JLabel(icon);
        img.setHorizontalAlignment(SwingConstants.CENTER);
        img.setVerticalAlignment(SwingConstants.CENTER);
        card.add(img, BorderLayout.CENTER);

        card.addMouseListener(new PremiumMouseAdapter(character));
        return card;
    }

    private class PremiumMouseAdapter extends MouseAdapter {
        private final GameCharacter.Character character;

        PremiumMouseAdapter(GameCharacter.Character character) {
            this.character = character;
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            if (lockedCharacter == null)
                updatePreview(character);
            ((JPanel) e.getSource()).setBorder(BorderFactory.createLineBorder(new Color(0, 220, 255, 220), 4, true));
        }

        @Override
        public void mouseExited(MouseEvent e) {
            JPanel card = (JPanel) e.getSource();
            if (lockedCharacter != getCharacterInstance(character)) {
                card.setBorder(BorderFactory.createLineBorder(new Color(255, 255, 255, 120), 3, true));
            }
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            chosenCharacter = getCharacterInstance(character);
            lockedCharacter = chosenCharacter;
            confirmButton.setEnabled(true);
            updatePreview(character);
        }
    }

    private void updatePreview(GameCharacter.Character character) {
        GameCharacter temp = getCharacterInstance(character);
        previewImage.setIcon(new ImageIcon(
                new ImageIcon(Objects.requireNonNull(getClass().getResource(temp.getImagePath())))
                        .getImage().getScaledInstance(680, 600, Image.SCALE_SMOOTH)
        ));

        nameLabel.setText(temp.getName().toUpperCase());
        elementLabel.setText("The " + temp.getElement() + " Element");

        String[] skills = temp.getSkillNames();
        skill1.setText((skills.length > 0 ? skills[0] : "PRIMARY ATTACK"));
        skill2.setText((skills.length > 1 ? skills[1] : "POWER STRIKE"));
        skill3.setText((skills.length > 2 ? skills[2] : "ULTIMATE"));

        try {
            String desc = temp.getDescription();
            descriptionArea.setText(desc != null && !desc.trim().isEmpty() ? desc : temp.getName() + " - Legend of the " + temp.getElement());
        } catch (Exception e) {
            descriptionArea.setText(temp.getName() + " - Epic " + temp.getElement() + " Warrior");
        }
    }

    // UTILITY METHODS
    private GameCharacter getCharacterInstance(GameCharacter.Character character) {
        return switch (character) {
            case AERO -> new Aero();
            case KAELIS -> new Kaelis();
            case KANGEL -> new Kangel();
            case KAYDEN -> new Kayden();
            case MAELOR -> new Maelor();
            case PSALM -> new Psalm();
            case RIPPER -> new Ripper();
            case VEYRION -> new Veyrion();
            case ZENSTREAM -> new ZenStream();
            default -> null;
        };
    }

    private JLabel createSpectacularLabel(String text, int size, Color color, boolean shadow) {
        JLabel label = new JLabel(text);
        label.setForeground(color);
        label.setFont(new Font("Arial Black", Font.BOLD, size));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        if (shadow) {
            label.setBorder(BorderFactory.createEmptyBorder(4, 4, 0, 0));
        }
        return label;
    }

    private void styleDescriptionArea(JTextArea area) {
        area.setOpaque(false);
        area.setForeground(new Color(240, 240, 255));
        area.setFont(new Font("Georgia", Font.ITALIC, 16));
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setEditable(false);
        area.setBorder(null);
        area.setBackground(null);
        area.setAlignmentX(Component.CENTER_ALIGNMENT);
        area.setPreferredSize(new Dimension(300, 100));
        area.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
    }

    private JButton createPremiumButton(String text, Color color) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                GradientPaint gradient = new GradientPaint(0, 0, color.brighter(), 0, getHeight(), color.darker());
                g2d.setPaint(gradient);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);

                g2d.setColor(Color.WHITE);
                g2d.setStroke(new BasicStroke(2));
                g2d.drawRoundRect(1, 1, getWidth()-3, getHeight()-3, 20, 20);

                super.paintComponent(g);
                g2d.dispose();
            }
        };
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial Black", Font.BOLD, 16));
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setBorder(BorderFactory.createEmptyBorder(12, 30, 12, 30));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    private ImageIcon getIcon(String path) {
        return new ImageIcon(
                new ImageIcon(Objects.requireNonNull(getClass().getResource(path)))
                        .getImage().getScaledInstance(160, 160, Image.SCALE_SMOOTH)
        );
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (bgImage != null) {
            g.drawImage(bgImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}