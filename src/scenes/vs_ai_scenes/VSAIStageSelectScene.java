package scenes.vs_ai_scenes;

import scenes.Elementia;
import scenes.Scenes;
import utils.Utility;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.Objects;

public class VSAIStageSelectScene extends JPanel {

    private Image originalBackground = null;
    private JButton confirmButton = Utility.createButton("Confirm");
    private int choice;

    public VSAIStageSelectScene(Elementia frame) {

        setLayout(new BorderLayout());


        URL bgPath = getClass().getResource("/resources/GameMode.png");
        if (bgPath != null) {
            ImageIcon bgIcon = new ImageIcon(bgPath);
            originalBackground = bgIcon.getImage();
        }

        JPanel topPanel = new JPanel();
        topPanel.setOpaque(false);
        topPanel.setBorder(BorderFactory.createEmptyBorder(40, 0, 20, 0));

        JLabel stageSelectLabel = new JLabel("CHOOSE A STAGE");
        stageSelectLabel.setFont(new Font("Segoe UI Black", Font.BOLD, 40));
        stageSelectLabel.setForeground(Color.WHITE);

        topPanel.add(stageSelectLabel);

        add(topPanel, BorderLayout.NORTH);

        JPanel centerWrapper = new JPanel(new GridBagLayout());
        centerWrapper.setOpaque(false);

        JPanel wrapperPanel = new JPanel(new GridLayout(2, 2, 20, 20));
        wrapperPanel.setOpaque(false);
        wrapperPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        for (int i = 1; i <= 4; i++) {

            final int choiceNumber = i;
            final boolean[] selected = {false};

            ImageIcon selectBgIcon = new ImageIcon(
                    Objects.requireNonNull(
                            getClass().getResource("/resources/LevelBackgrounds/LVL" + i + "_BG.png")
                    )
            );

            JLabel backgroundLabel = new JLabel(
                    new ImageIcon(selectBgIcon.getImage().getScaledInstance(280, 200, Image.SCALE_SMOOTH))
            );

            backgroundLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            backgroundLabel.setBorder(BorderFactory.createLineBorder(new Color(255, 255, 255, 60), 2));

            backgroundLabel.addMouseListener(new MouseAdapter() {

                @Override
                public void mouseEntered(MouseEvent e) {
                    if (selected[0]) return;

                    backgroundLabel.setBorder(
                            BorderFactory.createLineBorder(new Color(255, 215, 0, 180), 2)
                    );
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    if (selected[0]) return;

                    backgroundLabel.setBorder(
                            BorderFactory.createLineBorder(new Color(255, 255, 255, 60), 2)
                    );
                }

                @Override
                public void mouseClicked(MouseEvent e) {

                    // reset previous selection visuals
                    for (Component comp : wrapperPanel.getComponents()) {
                        if (comp instanceof JLabel label) {
                            label.setBorder(BorderFactory.createLineBorder(new Color(255, 255, 255, 60), 2));
                        }
                    }

                    choice = choiceNumber;
                    confirmButton.setEnabled(true);

                    selected[0] = true;

                    backgroundLabel.setBorder(
                            BorderFactory.createLineBorder(new Color(255, 215, 0), 3)
                    );
                }
            });

            wrapperPanel.add(backgroundLabel);
        }

        centerWrapper.add(wrapperPanel);
        add(centerWrapper, BorderLayout.CENTER);


        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 20));
        bottomPanel.setOpaque(false);

        JButton backButton = createStyledButton(
                "BACK",
                new Color(60, 60, 120).brighter(),
                new Color(40, 40, 80).darker()
        );
        backButton.addActionListener(e -> frame.showScreen(Scenes.VS_AI_CHARACTER_SELECT));

        confirmButton = createStyledButton(
                "CONFIRM",
                new Color(60, 60, 120).brighter(),
                new Color(40, 40, 80).darker()
        );

        confirmButton.setEnabled(false);

        confirmButton.addActionListener(e -> {
            frame.getVSAIBattle().setVSAIBattleSceneBackground(choice);
            frame.getVSAIBattle().startGame();
            frame.showScreen(Scenes.VS_AI_BATTLE);
            confirmButton.setEnabled(false);
        });

        bottomPanel.add(backButton);
        bottomPanel.add(confirmButton);

        add(bottomPanel, BorderLayout.SOUTH);
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

        button.setFont(new Font("Segoe UI Black", Font.BOLD, 18));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setOpaque(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(220, 60));

        return button;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (originalBackground != null) {
            g.drawImage(originalBackground, 0, 0, getWidth(), getHeight(), this);
        }
    }
}