package characters;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class CharacterView extends JPanel {
    private final GameCharacter character;
    private final BufferedImage image;
    private OnClickListener clickListener;
    private boolean isHovered = false;

    public interface OnClickListener {
        void onClick(GameCharacter character);
    }

    public CharacterView(GameCharacter character, BufferedImage image) {
        this.character = character;
        this.image = image;

        setOpaque(false);
        setPreferredSize(new Dimension(120, 200));


        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (character.getCurrentHP() > 0) {
                    isHovered = true;
                    repaint();
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                isHovered = false;
                repaint();
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                if (clickListener != null && character.getCurrentHP() > 0) {
                    clickListener.onClick(character);
                }
            }
        });
    }

    public void setClickListener(OnClickListener listener) {
        this.clickListener = listener;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (character == null || image == null) return;

        Graphics2D g2 = (Graphics2D) g.create();
        int panelW = getWidth();
        int panelH = getHeight();


        int topMargin = 10;
        int bottomMargin = 35;
        int availableHeight = panelH - topMargin - bottomMargin;


        int imgW = image.getWidth();
        int imgH = image.getHeight();
        double scale = (double) availableHeight / imgH;
        int drawW = (int) (imgW * scale);
        int drawH = (int) (imgH * scale);
        int x = (panelW - drawW) / 2;
        int y = topMargin;


        g2.drawImage(image, x, y, drawW, drawH, this);


        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 14f));
        g2.setColor(Color.WHITE);
        FontMetrics fm = g2.getFontMetrics();
        int nameWidth = fm.stringWidth(character.getName());
        g2.drawString(character.getName(), (panelW - nameWidth) / 2, y + drawH + 15);


        int barWidth = 80;
        int barHeight = 8;
        int barX = (panelW - barWidth) / 2;
        int hpY = y + drawH + 20;

        double hpPercent = (double) character.getCurrentHP() / character.getMaxHP();
        g2.setColor(Color.DARK_GRAY);
        g2.fillRect(barX, hpY, barWidth, barHeight);
        g2.setColor(Color.GREEN);
        g2.fillRect(barX, hpY, (int) (barWidth * hpPercent), barHeight);
        g2.setColor(Color.WHITE);
        g2.drawRect(barX, hpY, barWidth, barHeight);


        int manaY = hpY + 10;
        double manaPercent = (double) character.getCurrentMana() / character.getMaxMana();
        g2.setColor(Color.DARK_GRAY);
        g2.fillRect(barX, manaY, barWidth, barHeight / 2);
        g2.setColor(Color.CYAN);
        g2.fillRect(barX, manaY, (int) (barWidth * manaPercent), barHeight / 2);
        g2.setColor(Color.WHITE);
        g2.drawRect(barX, manaY, barWidth, barHeight / 2);


        if (isHovered) {
            g2.setColor(new Color(0, 255, 0, 60));
            g2.fillRect(0, 0, getWidth(), getHeight());
            g2.setColor(Color.GREEN);
            g2.setStroke(new BasicStroke(2f));
            g2.drawRect(1, 1, getWidth() - 3, getHeight() - 3);
        }

        g2.dispose();
    }
}
