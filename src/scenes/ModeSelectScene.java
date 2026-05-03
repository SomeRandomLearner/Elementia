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
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
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

        JLabel modeSelectLabel = new JLabel("Choose a Mode");
        modeSelectLabel.setFont(new Font("Times New Roman", Font.BOLD, 80));
        modeSelectLabel.setForeground(Color.WHITE);
        modeSelectLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Icon panel with hover effects
        IconPanel iconPanel = new IconPanel(frame);
        iconPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // ADJUSTED SPACING
        add(Box.createVerticalStrut(150));        // Reduced top spacing
        add(modeSelectLabel);
        add(Box.createVerticalStrut(50));        // Increased spacing between title and icons
        add(iconPanel);
        add(Box.createVerticalGlue());            // Push content up, fill remaining space
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }

    // Custom panel for clickable icons with hover effects
    private class IconPanel extends JPanel {
        private Elementia frame;
        private Rectangle arcadeBounds;
        private Rectangle pvpBounds;
        private boolean arcadeHovered = false;
        private boolean pvpHovered = false;

        public IconPanel(Elementia frame) {
            this.frame = frame;
            setOpaque(false);
            setPreferredSize(new Dimension(500, 500)); // FIXED: Proper size for 500x500 icons + spacing

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
                frame.showScreen("ArcadeCharacterSelect");
            } else if (pvpBounds != null && pvpBounds.contains(x, y)) {
                frame.showScreen("PVPCharacterSelect");
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

            int iconSize = 500;
            int gapBetweenIcons = 0;  // FIXED: Proper gap between icons
            int startX = (getWidth() - (iconSize * 2 + gapBetweenIcons)) / 2;
            int y = (getHeight() - iconSize) / 2;

            // Draw Arcade icon
            if (arcadeIcon != null) {
                arcadeBounds = new Rectangle(startX, y, iconSize, iconSize);
                float alpha = arcadeHovered ? 0.8f : 1.0f;
                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
                g2d.drawImage(arcadeIcon, startX, y, iconSize, iconSize, this);
            }

            // Draw PVP icon
            if (pvpIcon != null) {
                pvpBounds = new Rectangle(startX + iconSize + gapBetweenIcons, y, iconSize, iconSize);
                float alpha = pvpHovered ? 0.8f : 1.0f;
                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
                g2d.drawImage(pvpIcon, startX + iconSize + gapBetweenIcons, y, iconSize, iconSize, this);
            }

            g2d.dispose();
        }
    }
}