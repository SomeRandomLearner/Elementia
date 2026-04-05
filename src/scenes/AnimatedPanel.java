package scenes;

import characters.CharacterView;
import logic.Skill;
import logic.SoundPlayer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AnimatedPanel extends JPanel {
    private Skill selectedSkill;

    private Timer animTimer;
    private int currentFrameIndex = 0;

    public AnimatedPanel(){
        setOpaque(false);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                playSkillAnimation();
            }
        });
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
        if(selectedSkill == null) return;

        if (animTimer == null || !animTimer.isRunning()) {
            return;
        }
        ImageIcon[] animationFrames = selectedSkill.getAnimationFrames();
        if (animationFrames == null || animationFrames.length == 0) return;

        Graphics2D g2 = (Graphics2D) g.create();
        int panelW = getWidth();
        int panelH = getHeight();

        int topMargin = 20;
        int bottomMargin = 40;
        int availableHeight = panelH - topMargin - bottomMargin;

        ImageIcon currentFrameIcon = animationFrames[currentFrameIndex];
        Image currentImage = currentFrameIcon.getImage();

        int imgW = currentFrameIcon.getIconWidth();
        int imgH = currentFrameIcon.getIconHeight();

        double scale = Math.min((double)availableHeight / imgH, (double)panelW / imgW);
        int drawW = (int) (imgW * scale);
        int drawH = (int) (imgH * scale);
        int x = (panelW - drawW) / 2;
        int y = topMargin;

        g2.drawImage(currentImage, x, y, drawW, drawH, this);

        g2.dispose();
    }
}