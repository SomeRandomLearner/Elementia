package utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Utility {
    private static final Font normalFont = new Font("Times New Roman", Font.PLAIN, 30);
    public static JButton createButton(String text) {
        JButton btn = new JButton(text);
        btn.setFocusPainted(false);
        btn.setOpaque(true);
        btn.setContentAreaFilled(true);
        btn.setForeground(Color.WHITE);
        btn.setBackground(Color.BLACK);
        btn.setFont(normalFont);
        btn.setMinimumSize(new Dimension(150, 40));

        final Color defaultColor = Color.BLACK;
        final Color hoverColor = new Color(70, 70, 70);

        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(hoverColor);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btn.setBackground(defaultColor);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                btn.setBackground(hoverColor.darker());
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                btn.setBackground(btn.contains(e.getPoint()) ? hoverColor : defaultColor);
            }
        });

        return btn;
    }
}
