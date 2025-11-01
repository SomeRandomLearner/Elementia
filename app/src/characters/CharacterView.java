package characters;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CharacterView extends JPanel {
    private GameCharacter character;
    private Image sprite;
    private boolean isSelected = false;

    public CharacterView(GameCharacter character, String spritePath) {
        this.character = character;
        this.sprite = new ImageIcon(spritePath).getImage();
        setPreferredSize(new Dimension(100, 100));
        setOpaque(false);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                isSelected = !isSelected;
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(sprite, 0, 0, getWidth(), getHeight(), this);
        if (isSelected) {
            g.setColor(new Color(0, 255, 0, 100));
            g.fillRect(0, 0, getWidth(), getHeight());
        }
    }

    public boolean getIsSelected(){
        return this.isSelected;
    }
}
