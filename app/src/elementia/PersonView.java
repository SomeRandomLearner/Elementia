package elementia;

import characters.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PersonView extends JPanel {
    private Person person;
    private Image sprite;
    private boolean isSelected = false;

    public PersonView(Person person, String spritePath) {
        this.person = person;
        this.sprite = new ImageIcon(spritePath).getImage();
        setPreferredSize(new Dimension(100, 100));
        setOpaque(false);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                isSelected = !isSelected;
                System.out.println(person.getName() + " selected!");
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
