package scenes;

import utils.Utility;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;

public class ModeSelectScene extends JPanel {
    private Image backgroundImage;
    private Image arcadeIcon;
    private Image pvpIcon;

    public ModeSelectScene(Elementia frame) {
        setLayout(new BorderLayout());
        setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Load background
        URL bgPath = getClass().getResource("/resources/GameMode.png");
        if (bgPath != null) {
            backgroundImage = new ImageIcon(bgPath).getImage();
        }

        // Load icons
        URL arcadePath = getClass().getResource("/resources/Arcade.png");
        URL pvpPath = getClass().getResource("/resources/PVP.png");

        if (arcadePath != null) {
            arcadeIcon = new ImageIcon(arcadePath).getImage();
        }
        if (pvpPath != null) {
            pvpIcon = new ImageIcon(pvpPath).getImage();
        }

        // 🔥 SPECTACULAR TITLE
        JLabel modeSelectLabel = new JLabel("CHOOSE YOUR BATTLE MODE", SwingConstants.CENTER);
        modeSelectLabel.setFont(new Font("Arial Black", Font.BOLD, 64));
        modeSelectLabel.setForeground(new Color(255, 255, 255, 240));
        modeSelectLabel.setBorder(BorderFactory.createEmptyBorder(80, 0, 60, 0));

        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setOpaque(false);
        titlePanel.add(modeSelectLabel, BorderLayout.CENTER);

        // 🔥 PREMIUM ICON PANEL
        IconPanel iconPanel = new IconPanel(frame);
        iconPanel.setPreferredSize(new Dimension(1200, 600));

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setOpaque(false);
        centerPanel.add(iconPanel, BorderLayout.CENTER);

        // 🔥 BACK BUTTON - LOWER LEFT CORNER
        JButton backButton = createPremiumBackButton(frame);

        // 🔥 BOTTOM PANEL WITH BACK BUTTON POSITIONED LOWER LEFT
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setOpaque(false);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 60, 40));
        bottomPanel.add(backButton, BorderLayout.WEST);

        add(titlePanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    // 🔥 PREMIUM BACK BUTTON
    private JButton createPremiumBackButton(Elementia frame) {
        JButton backBtn = new JButton("← BACK") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // 🔥 GRADIENT BACKGROUND
                GradientPaint gradient = new GradientPaint(
                        0, 0, new Color(60, 60, 120).brighter(),
                        0, getHeight(), new Color(40, 40, 80).darker()
                );
                g2d.setPaint(gradient);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 25, 25);

                // 🔥 GLOW BORDER
                g2d.setColor(new Color(100, 100, 200, 150));
                g2d.setStroke(new BasicStroke(3));
                g2d.drawRoundRect(2, 2, getWidth()-5, getHeight()-5, 22, 22);

                // 🔥 INNER SHINE
                g2d.setColor(new Color(255, 255, 255, 40));
                g2d.fillRoundRect(4, 4, getWidth()-8, getHeight()-8, 20, 20);

                g2d.dispose();
                super.paintComponent(g);
            }
        };

        backBtn.setForeground(new Color(220, 220, 255));
        backBtn.setFont(new Font("Arial Black", Font.BOLD, 20));
        backBtn.setFocusPainted(false);
        backBtn.setContentAreaFilled(false);
        backBtn.setBorder(BorderFactory.createEmptyBorder(15, 30, 15, 30));
        backBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backBtn.setPreferredSize(new Dimension(160, 55));
        backBtn.addActionListener(e -> frame.showScreen(Scenes.MAIN_MENU));

        return backBtn;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }

    // ENHANCED ICON PANEL WITH BETTER EFFECTS
    private class IconPanel extends JPanel {
        private Elementia frame;
        private Rectangle arcadeBounds;
        private Rectangle pvpBounds;
        private boolean arcadeHovered = false;
        private boolean pvpHovered = false;

        public IconPanel(Elementia frame) {
            this.frame = frame;
            setOpaque(false);
            setPreferredSize(new Dimension(1200, 600));

            addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    checkClick(e.getX(), e.getY());
                }
            });

            addMouseMotionListener(new MouseAdapter() {
                @Override
                public void mouseMoved(MouseEvent e) {
                    checkHover(e.getX(), e.getY());
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    arcadeHovered = false;
                    pvpHovered = false;
                    setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                    repaint();
                }
            });
        }

        private void checkClick(int x, int y) {
            if (arcadeBounds != null && arcadeBounds.contains(x, y)) {
                frame.showScreen(Scenes.ARCADE_CHARACTER_SELECT);
            } else if (pvpBounds != null && pvpBounds.contains(x, y)) {
                frame.showScreen(Scenes.PVP_CHARACTER_SELECT);
            }
        }

        private void checkHover(int x, int y) {
            boolean wasArcadeHovered = arcadeHovered;
            boolean wasPvpHovered = pvpHovered;

            arcadeHovered = arcadeBounds != null && arcadeBounds.contains(x, y);
            pvpHovered = pvpBounds != null && pvpBounds.contains(x, y);

            if (arcadeHovered || pvpHovered) {
                setCursor(new Cursor(Cursor.HAND_CURSOR));
            } else {
                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }

            if (wasArcadeHovered != arcadeHovered || wasPvpHovered != pvpHovered) {
                repaint();
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int iconSize = 450;
            int gapBetweenIcons = 50;
            int startX = (getWidth() - (iconSize * 2 + gapBetweenIcons)) / 2;
            int y = (getHeight() - iconSize) / 2;

            // ARCADE ICON WITH ENHANCED HOVER EFFECTS
            if (arcadeIcon != null) {
                arcadeBounds = new Rectangle(startX, y, iconSize, iconSize);

                // BACKGROUND GLOW ON HOVER
                if (arcadeHovered) {
                    g2d.setColor(new Color(60, 40, 100, 100));
                    g2d.fillRoundRect(startX - 15, y - 15, iconSize + 30, iconSize + 30, 35, 35);
                }

                // SCALE & SHADOW EFFECT
                float scale = arcadeHovered ? 1.05f : 1.0f;
                int scaledSize = (int)(iconSize * scale);
                int offsetX = startX + (iconSize - scaledSize) / 2;
                int offsetY = y + (iconSize - scaledSize) / 2;

                // Shadow
                if (arcadeHovered) {
                    g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
                    g2d.drawImage(arcadeIcon, offsetX + 8, offsetY + 8, scaledSize, scaledSize, this);
                }

                // Main icon
                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, arcadeHovered ? 0.95f : 1.0f));
                g2d.drawImage(arcadeIcon, offsetX, offsetY, scaledSize, scaledSize, this);
            }

            //  PVP ICON WITH ENHANCED HOVER EFFECTS
            if (pvpIcon != null) {
                pvpBounds = new Rectangle(startX + iconSize + gapBetweenIcons, y, iconSize, iconSize);

                // BACKGROUND GLOW ON HOVER
                if (pvpHovered) {
                    g2d.setColor(new Color(60, 40, 100, 100));// Red glow
                    g2d.fillRoundRect(startX + iconSize + gapBetweenIcons - 15, y - 15, iconSize + 30, iconSize + 30, 35, 35);
                }

                //  SCALE & SHADOW EFFECT
                float scale = pvpHovered ? 1.05f : 1.0f;
                int scaledSize = (int)(iconSize * scale);
                int offsetX = startX + iconSize + gapBetweenIcons + (iconSize - scaledSize) / 2;
                int offsetY = y + (iconSize - scaledSize) / 2;

                // Shadow
                if (pvpHovered) {
                    g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
                    g2d.drawImage(pvpIcon, offsetX + 8, offsetY + 8, scaledSize, scaledSize, this);
                }

                // Main icon
                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, pvpHovered ? 0.95f : 1.0f));
                g2d.drawImage(pvpIcon, offsetX, offsetY, scaledSize, scaledSize, this);
            }

            g2d.dispose();
        }
    }
}