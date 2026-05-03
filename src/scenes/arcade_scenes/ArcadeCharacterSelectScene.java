package scenes.arcade_scenes;

import characters.*;
import logic.LevelManager;
import scenes.Elementia;
import utils.Utility;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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

        // 🔥 BACKGROUND
        bgImage = new ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/CharacterSelectBG.png"))).getImage();

        // 🔥 SPECTACULAR TITLE
        JLabel title = new JLabel("SELECT YOUR CHARACTER", SwingConstants.CENTER);
        title.setFont(new Font("Arial Black", Font.BOLD, 40));
        title.setForeground(new Color(255, 255, 255, 240));
        title.setBorder(BorderFactory.createEmptyBorder(25, 0, 20, 0));

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);
        topPanel.add(title, BorderLayout.CENTER);

        // 🔥 PREMIUM ICONS
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

        // 🔥 EPIC RIGHT PANEL
        JPanel rightPanel = createEpicRightPanel();
        rightPanel.setPreferredSize(new Dimension(850, 850));

        // 🔥 GLOSSY BUTTONS
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 20));
        bottomPanel.setOpaque(false);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 30, 0));

        confirmButton = createPremiumButton("CONFIRM", new Color(0, 200, 255));
        confirmButton.setEnabled(false);
        confirmButton.setPreferredSize(new Dimension(220, 55));

        JButton backBtn = createPremiumButton("BACK", new Color(100, 100, 255));
        backBtn.setPreferredSize(new Dimension(120, 55));

        confirmButton.addActionListener(e -> {
            frame.getLevelSelect().setSelectedCharacter(chosenCharacter);
            LevelManager.setBossLevel(chosenCharacter);
            frame.showScreen("LevelSelect");
        });

        backBtn.addActionListener(e -> frame.showScreen("ModeSelect"));

        bottomPanel.add(confirmButton);
        bottomPanel.add(backBtn);

        add(topPanel, BorderLayout.NORTH);
        add(leftPanel, BorderLayout.WEST);
        add(rightPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    // 🔥 PREMIUM RIGHT PANEL
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

        // 🔥 GLOW EFFECT CONTAINER
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

        // 🔥 VERTICALLY CENTERED CONTENT
        JPanel content = new JPanel();
        content.setOpaque(false);
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setAlignmentY(CENTER_ALIGNMENT);

        // 🔥 HERO NAME - MASSIVE
        nameLabel = createSpectacularLabel("CHAMPION", 44, Color.WHITE, true);
        nameLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 25, 0));

        // 🔥 ELEMENT TYPE
        elementLabel = createSpectacularLabel("ELEMENT", 26, new Color(150, 255, 255), false);
        elementLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 35, 0));

        // 🔥 SKILLS SECTION
        JLabel skillsTitle = createSpectacularLabel("SKILLS", 24, new Color(255, 215, 0), true);
        skillsTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));

        skill1 = createSpectacularLabel("PRIMARY", 18, Color.WHITE, false);
        skill2 = createSpectacularLabel("SECONDARY", 18, Color.WHITE, false);
        skill3 = createSpectacularLabel("ULTIMATE", 18, new Color(255, 100, 100), true);


        JLabel loreTitle = createSpectacularLabel("CHARACTER DESCRIPTIION", 24, new Color(255, 150, 255), true);
        loreTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 12, 0));

        descriptionArea = new JTextArea("Hover over champions to reveal their power...");
        styleDescriptionArea(descriptionArea);


        content.add(Box.createVerticalGlue());
        content.add(nameLabel);
        content.add(elementLabel);
        content.add(Box.createVerticalStrut(20));
        content.add(skillsTitle);
        content.add(skill1);
        content.add(Box.createVerticalStrut(8));
        content.add(skill2);
        content.add(Box.createVerticalStrut(8));
        content.add(skill3);
        content.add(Box.createVerticalStrut(30));
        content.add(loreTitle);
        content.add(descriptionArea);
        content.add(Box.createVerticalGlue());

        mainPanel.add(content, BorderLayout.CENTER);
        return mainPanel;
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
            if (lockedCharacter == null) updatePreview(character);
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
                        .getImage().getScaledInstance(680, 780, Image.SCALE_SMOOTH)
        ));

        nameLabel.setText(temp.getName().toUpperCase());
        elementLabel.setText("The " + temp.getElement() + " Element");

        String[] skills = temp.getSkillNames();
        skill1.setText((skills.length > 0 ? skills[0] : "PRIMARY ATTACK"));
        skill2.setText((skills.length > 1 ? skills[1] : "POWER STRIKE"));
        skill3.setText((skills.length > 2 ? skills[2] : "ULTIMATE"));

        try {
            String desc = temp.getDescription();
            descriptionArea.setText(desc != null && !desc.trim().isEmpty() ?
                    desc : temp.getName() + " - Legend of the " + temp.getElement());
        } catch (Exception e) {
            descriptionArea.setText(temp.getName() + " - Epic " + temp.getElement() + " Warrior");
        }
    }

    // 🔥 UTILITY METHODS
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
        area.setPreferredSize(new Dimension(380, 120));
        area.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
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