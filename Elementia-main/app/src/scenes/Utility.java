package scenes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Utility {
    public static JPanel createScene(String text, Color bg) {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Arial", Font.BOLD, 28));
        panel.setBackground(bg);
        panel.add(label, BorderLayout.CENTER);
        return panel;
    }

    public static JButton createButton(String text) {
        JButton btn = new JButton(text);
        btn.setFocusPainted(false);
        btn.setOpaque(true);
        btn.setContentAreaFilled(true);
        btn.setForeground(Color.WHITE);
        btn.setBackground(Color.BLACK);
        btn.setFont(btn.getFont().deriveFont(Font.BOLD, 16f));
        btn.setPreferredSize(new Dimension(150, 40));

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
