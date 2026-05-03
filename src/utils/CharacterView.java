package utils;

import characters.GameCharacter;
import logic.Skill;
import logic.SoundPlayer;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class CharacterView extends JPanel {
    private final GameCharacter character;
    private Skill selectedSkill;

    private Timer animTimer;
    private int currentFrameIndex = 0;
    

    public CharacterView(GameCharacter character) {
        this.character = character;

        setOpaque(false);
        setPreferredSize(new Dimension(120, 200));
    }

    public GameCharacter getCharacter() {
        return character;
    }


    public void playSkillAnimation() {
        if (animTimer != null && animTimer.isRunning()) return;

        if(selectedSkill == null) return;

        SoundPlayer.playSound("/resources/sounds/basic_attack_sound.wav");

        ImageIcon[] animationFrames = selectedSkill.getAnimationFrames();
        if (animationFrames == null || animationFrames.length == 0) return;

        currentFrameIndex = 0;
        animTimer = new Timer(200, e -> {
            currentFrameIndex++;

            if (currentFrameIndex >= animationFrames.length) {
                currentFrameIndex = 0;
                animTimer.stop();
            }

            repaint();
        });
        animTimer.start();
    }

    public void setSelectedSkill(Skill selectedSkill){
        this.selectedSkill = selectedSkill;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (character == null) return;

        BufferedImage currentImage = null;
        currentImage = character.getImage();

        if (currentImage == null) return;

        Graphics2D g2 = (Graphics2D) g.create();
        int panelW = getWidth();
        int panelH = getHeight();

        int topMargin = 10;
        int bottomMargin = 35;
        int availableHeight = panelH - topMargin - bottomMargin;

        int imgW = currentImage.getWidth();
        int imgH = currentImage.getHeight();
        double scale = Math.min((double)availableHeight / imgH, (double)panelW / imgW);
        int drawW = (int) (imgW * scale);
        int drawH = (int) (imgH * scale);
        int x = (panelW - drawW) / 2;
        int y = topMargin;

        g2.drawImage(currentImage, x, y, drawW, drawH, this);

        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 14f));
        g2.setColor(Color.WHITE);
        FontMetrics fm = g2.getFontMetrics();
        int nameWidth = fm.stringWidth(character.getName());
        g2.drawString(character.getName(), (panelW - nameWidth) / 2, y + drawH + 15);

        int barWidth = 80;
        int barHeight = 8;
        int barX = (panelW - barWidth) / 2;

        // health bar
        int hpY = y + drawH + 20;
        double hpPercent = (double) character.getCurrentHP() / character.getMaxHP();
        g2.setColor(Color.DARK_GRAY);
        g2.fillRect(barX, hpY, barWidth, barHeight);
        g2.setColor(Color.GREEN);
        g2.fillRect(barX, hpY, (int) (barWidth * hpPercent), barHeight);
        g2.setColor(Color.WHITE);
        g2.drawRect(barX, hpY, barWidth, barHeight);

        // mana bar
        int manaY = hpY + 10;
        double manaPercent = (double) character.getCurrentMana() / character.getMaxMana();
        g2.setColor(Color.DARK_GRAY);
        g2.fillRect(barX, manaY, barWidth, barHeight / 2);
        g2.setColor(Color.CYAN);
        g2.fillRect(barX, manaY, (int) (barWidth * manaPercent), barHeight / 2);
        g2.setColor(Color.WHITE);
        g2.drawRect(barX, manaY, barWidth, barHeight / 2);


        if(selectedSkill == null) return;

        if (animTimer == null || !animTimer.isRunning()) {
            return;
        }
        ImageIcon[] animationFrames = selectedSkill.getAnimationFrames();
        if (animationFrames == null || animationFrames.length == 0) return;

        ImageIcon currentFrameIcon = animationFrames[currentFrameIndex];

        g2.drawImage(currentFrameIcon.getImage(), x, y, drawW, drawH, this);

        g2.dispose();
    }


}