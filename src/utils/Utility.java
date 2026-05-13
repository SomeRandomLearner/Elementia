package utils;

import characters.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class Utility {

    private static final Font normalFont = new Font("Arial", Font.BOLD, 18);

    public static CustomButton createButton(
            String text,
            Color defaultBackgroundColor,
            Color defaultForegroundColor,
            Color hoverColor
    ) {

        CustomButton btn = new CustomButton(text, defaultBackgroundColor, defaultForegroundColor, hoverColor);


        btn.setFont(normalFont);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(true);
        btn.setOpaque(true);

        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));


        btn.setPreferredSize(new Dimension(180, 45));
        btn.setMinimumSize(new Dimension(150, 40));


        btn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));


        btn.setBackground(defaultBackgroundColor);
        btn.setForeground(defaultForegroundColor);

        btn.setDefaultBackgroundColor(defaultBackgroundColor);
        btn.setDefaultForegroundColor(defaultForegroundColor);

        btn.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {
                if (btn.isEnabled()) {
                    btn.setBackground(hoverColor);
                    btn.setForeground(Color.WHITE);
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btn.setBackgroundColorToDefault();
                btn.setForegroundColorToDefault();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                btn.setBackground(btn.getBackground().darker());
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (!btn.isEnabled()) return;

                if (btn.contains(e.getPoint())) {
                    btn.setBackground(hoverColor);
                } else {
                    btn.setBackgroundColorToDefault();
                }
            }
        });

        return btn;
    }

    public static CustomButton createButton(String text) {
        return createButton(
                text,
                new Color(25, 25, 25),
                Color.WHITE,
                new Color(70, 130, 180)
        );
    }

    public static CustomButton createButton(){
        return createButton("");
    }

    public static ArrayList<GameCharacter> getAllCharacters(){
        ArrayList<GameCharacter> allCharactersArray = new ArrayList<>();
        allCharactersArray.add(new Aero());
        allCharactersArray.add(new Kaelis());
        allCharactersArray.add(new Kangel());
        allCharactersArray.add(new Kayden());
        allCharactersArray.add(new Maelor());
        allCharactersArray.add(new Psalm());
        allCharactersArray.add(new Ripper());
        allCharactersArray.add(new Veyrion());
        allCharactersArray.add(new ZenStream());
        return allCharactersArray;
    }

    public static JButton createStyledButton(String text, Color topColor, Color bottomColor) {

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

                g2.setColor(new Color(0, 0, 0, 80));
                g2.fillRoundRect(
                        shadow / 2,
                        shadow / 2,
                        getWidth() - shadow,
                        getHeight() - shadow,
                        35,
                        35
                );

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
}