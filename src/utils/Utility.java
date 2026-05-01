package utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Utility {
    private static final Font normalFont = new Font("Times New Roman", Font.PLAIN, 30);

    public static CustomButton createButton(String text, Color defaultBackgroundColor, Color defaultForegroundColor, Color hoverColor) {
        CustomButton btn = new CustomButton(text, defaultBackgroundColor, defaultForegroundColor, hoverColor);
        btn.setFocusPainted(false);
        btn.setOpaque(true);
        btn.setContentAreaFilled(true);
        btn.setDefaultBackgroundColor(defaultBackgroundColor);
        btn.setDefaultForegroundColor(defaultForegroundColor);
        btn.setFont(normalFont);
        btn.setMinimumSize(new Dimension(150, 40));


        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if(btn.isEnabled()) btn.setBackground(btn.getHoverColor());
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
                if(btn.contains(e.getPoint())) btn.setBackground(btn.getHoverColor());
                else btn.setBackgroundColorToDefault();
            }
        });
        return btn;
    }

    public static CustomButton createButton(String text) {
        return createButton(text, Color.BLACK, Color.WHITE, new Color(70,70,70));
    }
}
