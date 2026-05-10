package scenes.arcade_scenes;

import characters.*;
import logic.BattleLogic;
import scenes.Elementia;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Objects;

public class ArcadeCharacterSelectScene extends JPanel {

    private GameCharacter selectedCharacter;

    private boolean characterSelected = false;

    private JLabel titleLabel;
    private JButton confirmButton;

    private JLabel previewImage;
    private JLabel nameLabel, elementLabel, skill1, skill2, skill3;
    private JTextArea descriptionArea;

    private Image bgImage;

    private BattleLogic battleLogic;
    private Elementia frameRef;

    public ArcadeCharacterSelectScene(Elementia frame) {
        this.frameRef = frame;
        setLayout(new BorderLayout());

        battleLogic = new BattleLogic(false);

        loadBackground();
        initTop();
        initCenter();
        initBottom();
    }

    private void loadBackground() {
        try {
            bgImage = new ImageIcon(
                    Objects.requireNonNull(getClass().getResource("/resources/CharacterSelectBG.png"))
            ).getImage();
        } catch (Exception e) {
            bgImage = null;
        }
    }


    private void initTop() {
        titleLabel = new JLabel("SELECT YOUR CHARACTER", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI Black", Font.BOLD, 40));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(25, 0, 25, 0));

        JPanel top = new JPanel();
        top.setOpaque(false);
        top.add(titleLabel);

        add(top, BorderLayout.NORTH);
    }


    private void initCenter() {

        JPanel main = new JPanel(new BorderLayout());
        main.setOpaque(false);

        main.add(createCharacterGrid(), BorderLayout.WEST);
        main.add(createPreviewAndInfo(), BorderLayout.CENTER);

        add(main, BorderLayout.CENTER);
    }


    private JPanel createCharacterGrid() {

        GameCharacter[] characters = {
                new Aero(), new Kaelis(), new Kangel(),
                new Kayden(), new Maelor(), new Psalm(),
                new Ripper(), new Veyrion(), new ZenStream()
        };

        JPanel grid = new JPanel(new GridLayout(3, 3, 20, 20));
        grid.setOpaque(false);
        grid.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        for (GameCharacter c : characters) {
            grid.add(createCharacterCard(c, grid));
        }

        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setOpaque(false);
        wrapper.setPreferredSize(new Dimension(600, 800));
        wrapper.add(grid);

        return wrapper;
    }

    private JPanel createCharacterCard(GameCharacter character, JPanel grid) {

        JPanel card = new JPanel(new BorderLayout()) {

            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                boolean isSelected = getClientProperty("selected") == Boolean.TRUE;
                boolean isHovered = getClientProperty("hovered") == Boolean.TRUE;

                Color base = new Color(255, 255, 255, 35);
                Color hover = new Color(120, 180, 255, 80);
                Color selectedColor = new Color(255, 215, 0, 140);

                if (isSelected) {
                    g2.setColor(new Color(255, 255, 0, 100));
                    g2.setStroke(new BasicStroke(3));
                    g2.drawRoundRect(4, 4, getWidth() - 9, getHeight() - 9, 22, 22);
                } else if (isHovered) {
                    g2.setColor(hover);
                } else {
                    g2.setColor(base);
                }

                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 25, 25);

                g2.setColor(new Color(255, 255, 255, 120));
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 25, 25);

                g2.dispose();
                super.paintComponent(g);
            }
        };

        card.setOpaque(false);
        card.setPreferredSize(new Dimension(160, 160));
        card.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        JLabel img = new JLabel(getIcon(character.getImagePath()));
        img.setHorizontalAlignment(SwingConstants.CENTER);
        card.add(img, BorderLayout.CENTER);

        card.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {
                card.putClientProperty("hovered", true);
                card.repaint();
                updatePreview(character);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                card.putClientProperty("hovered", false);
                card.repaint();
            }

            @Override
            public void mouseClicked(MouseEvent e) {

                clearSelection(grid);

                selectedCharacter = character;
                characterSelected = true;

                card.putClientProperty("selected", true);
                card.repaint();

                confirmButton.setEnabled(true);
                titleLabel.setText("CHARACTER READY!");
            }
        });

        return card;
    }

    private void clearSelection(JPanel grid) {
        for (Component comp : grid.getComponents()) {
            if (comp instanceof JPanel panel) {
                panel.putClientProperty("selected", false);
                panel.repaint();
            }
        }
    }

    private JPanel createPreviewAndInfo() {

        JPanel panel = new JPanel(new GridLayout(1, 2, 30, 0));
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        panel.add(createPreviewPanel());
        panel.add(createInfoPanel());

        return panel;
    }


    private JPanel createPreviewPanel() {

        JPanel panel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                GradientPaint gp = new GradientPaint(
                        0, 0, new Color(0, 0, 0, 140),
                        0, getHeight(), new Color(20, 20, 60, 160)
                );

                g2.setPaint(gp);
                g2.fillRoundRect(10, 10, getWidth() - 20, getHeight() - 20, 30, 30);

                g2.setColor(new Color(120, 180, 255, 80));
                g2.drawRoundRect(10, 10, getWidth() - 20, getHeight() - 20, 30, 30);

                g2.dispose();
                super.paintComponent(g);
            }
        };

        panel.setOpaque(false);

        previewImage = new JLabel();
        previewImage.setHorizontalAlignment(SwingConstants.CENTER);

        panel.add(previewImage, BorderLayout.CENTER);

        return panel;
    }


    private JPanel createInfoPanel() {

        JPanel mainBox = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                GradientPaint gp = new GradientPaint(
                        0, 0, new Color(30, 20, 80, 200),
                        0, getHeight(), new Color(10, 30, 80, 200)
                );

                g2.setPaint(gp);
                g2.fillRoundRect(10, 10, getWidth() - 20, getHeight() - 20, 30, 30);

                g2.setColor(new Color(120, 100, 255, 80));
                g2.setStroke(new BasicStroke(3));
                g2.drawRoundRect(8, 8, getWidth() - 16, getHeight() - 16, 30, 30);

                g2.dispose();
                super.paintComponent(g);
            }
        };

        mainBox.setOpaque(false);
        mainBox.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));

        JPanel content = new JPanel();
        content.setOpaque(false);
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));


        nameLabel = createLabel("CHARACTER", 32, true);
        elementLabel = createLabel("ELEMENT", 18, false);
        elementLabel.setForeground(new Color(150, 255, 255));

        JPanel headerBox = createSubBox(new Color(0, 200, 255, 60));
        headerBox.add(nameLabel);
        headerBox.add(Box.createVerticalStrut(5));
        headerBox.add(elementLabel);

        // ===== SKILLS =====
        JLabel skillsTitle = createLabel("SKILLS", 20, true);
        skillsTitle.setForeground(new Color(255, 215, 0));

        skill1 = createLabel("", 14, false);
        skill2 = createLabel("", 14, false);
        skill3 = createLabel("", 14, false);

        JPanel skillsBox = createSubBox(new Color(255, 200, 100, 60));
        skillsBox.add(skillsTitle);
        skillsBox.add(Box.createVerticalStrut(10));
        skillsBox.add(skill1);
        skillsBox.add(skill2);
        skillsBox.add(skill3);

        // ===== DESCRIPTION =====
        JLabel descTitle = createLabel("DESCRIPTION", 18, true);
        descTitle.setForeground(new Color(255, 150, 255));

        descriptionArea = new JTextArea();
        descriptionArea.setOpaque(false);
        descriptionArea.setForeground(new Color(235, 235, 255));
        descriptionArea.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        descriptionArea.setEditable(false);

        JPanel descBox = createSubBox(new Color(150, 100, 255, 60));
        descBox.setLayout(new BorderLayout());
        descBox.add(descTitle, BorderLayout.NORTH);
        descBox.add(descriptionArea, BorderLayout.CENTER);

        // ===== ADD =====
        content.add(headerBox);
        content.add(Box.createVerticalStrut(18));
        content.add(skillsBox);
        content.add(Box.createVerticalStrut(18));
        content.add(descBox);

        mainBox.add(content, BorderLayout.CENTER);

        return mainBox;
    }

    // ================= FIXED (NO NULL) =================
    private JPanel createSubBox(Color color) {

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                g2.setColor(color);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);

                g2.setColor(new Color(255, 255, 255, 60));
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20);

                g2.dispose();
                super.paintComponent(g);
            }
        };

        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        return panel;
    }

    // ================= BOTTOM =================
    private void initBottom() {

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 20));
        bottom.setOpaque(false);

        JButton back = createStyledButton(
                "BACK",
                new Color(60, 60, 120).brighter(),
                new Color(40, 40, 80).darker()
        );

        back.addActionListener(e -> frameRef.showScreen("ModeSelect"));

        confirmButton = createStyledButton(
                "CONFIRM",
                new Color(60, 60, 120).brighter(),
                new Color(40, 40, 80).darker()
        );

        confirmButton.setEnabled(false);

        confirmButton.addActionListener(e -> {

            if (selectedCharacter == null) return;

            battleLogic.resetCharacterChoices();
            battleLogic.addToTeam(1, selectedCharacter);

            frameRef.getArcadeBattle().setBattleLogic(battleLogic);
            frameRef.showScreen("ArcadeBattleScene");
        });

        bottom.add(back);
        bottom.add(confirmButton);

        add(bottom, BorderLayout.SOUTH);
    }

    private JButton createStyledButton(String text, Color topColor, Color bottomColor) {

        JButton button = new JButton(text) {

            private boolean hovered = false;

            {
                addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseEntered(MouseEvent e) {
                        if (isEnabled()) {
                            hovered = true;
                            repaint();
                        }
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                        hovered = false;
                        repaint();
                    }
                });
            }

            @Override
            protected void paintComponent(Graphics g) {

                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);

                int shadow = hovered ? 10 : 6;

                // Shadow
                g2.setColor(new Color(0, 0, 0, 80));
                g2.fillRoundRect(
                        shadow / 2,
                        shadow / 2,
                        getWidth() - shadow,
                        getHeight() - shadow,
                        35,
                        35
                );

                // Gradient
                GradientPaint gp;

                if (hovered && isEnabled()) {
                    gp = new GradientPaint(
                            0, 0, topColor.brighter(),
                            0, getHeight(), bottomColor.brighter()
                    );
                } else {
                    gp = new GradientPaint(
                            0, 0, topColor,
                            0, getHeight(), bottomColor
                    );
                }

                g2.setPaint(gp);
                g2.fillRoundRect(
                        0,
                        0,
                        getWidth() - shadow,
                        getHeight() - shadow,
                        35,
                        35
                );

                // Border glow
                g2.setColor(new Color(255, 255, 255, hovered ? 180 : 100));
                g2.setStroke(new BasicStroke(2));
                g2.drawRoundRect(
                        1,
                        1,
                        getWidth() - shadow - 2,
                        getHeight() - shadow - 2,
                        35,
                        35
                );

                // Disabled overlay
                if (!isEnabled()) {
                    g2.setColor(new Color(0, 0, 0, 140));
                    g2.fillRoundRect(
                            0,
                            0,
                            getWidth() - shadow,
                            getHeight() - shadow,
                            35,
                            35
                    );
                }

                g2.dispose();
                super.paintComponent(g);
            }
        };

        button.setFont(new Font("Segoe UI Black", Font.BOLD, 20));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setOpaque(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(260, 70));

        return button;
    }

    // ================= LOGIC =================
    private void updatePreview(GameCharacter c) {

        previewImage.setIcon(new ImageIcon(
                new ImageIcon(Objects.requireNonNull(getClass().getResource(c.getImagePath())))
                        .getImage().getScaledInstance(400, 400, Image.SCALE_SMOOTH)
        ));

        nameLabel.setText(c.getName());
        elementLabel.setText(c.getElement());

        String[] s = c.getSkillNames();
        skill1.setText("• " + (s.length > 0 ? s[0] : ""));
        skill2.setText("• " + (s.length > 1 ? s[1] : ""));
        skill3.setText("• " + (s.length > 2 ? s[2] : ""));

        descriptionArea.setText(c.getDescription());
    }

    // ================= UTIL =================
    private JLabel createLabel(String text, int size, boolean bold) {
        JLabel l = new JLabel(text);
        l.setForeground(Color.WHITE);
        l.setFont(new Font("Segoe UI", bold ? Font.BOLD : Font.PLAIN, size));
        return l;
    }

    private ImageIcon getIcon(String path) {
        return new ImageIcon(
                new ImageIcon(Objects.requireNonNull(getClass().getResource(path)))
                        .getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH)
        );
    }

    // ================= BACKGROUND PAINT =================
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (bgImage != null) {
            g.drawImage(bgImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}