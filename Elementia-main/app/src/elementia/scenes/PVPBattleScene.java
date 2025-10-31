package elementia.scenes;

import characters.Person;
import javax.swing.*;
import java.awt.*;

public class PVPBattleScene extends JPanel {
    private Person player1;
    private Person player2;
    private Person selectedTarget;
    private JButton[] player1Skills = new JButton[3];
    private JButton[] player2Skills = new JButton[3];
    private JLabel player1Status;
    private JLabel player2Status;

    public PVPBattleScene(JFrame frame) {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel title = new JLabel("PVP Battle", SwingConstants.CENTER);
        title.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
        add(title, BorderLayout.NORTH);

        JPanel battleArea = new JPanel(new BorderLayout(20, 10));

        JPanel charactersPanel = new JPanel(new GridLayout(1, 2, 20, 0));
        charactersPanel.add(createCharacterPanel(true));
        charactersPanel.add(createCharacterPanel(false));
        battleArea.add(charactersPanel, BorderLayout.CENTER);

        JPanel skillsPanel = new JPanel(new GridLayout(1, 2, 20, 0));
        skillsPanel.add(createSkillsPanel(true));
        skillsPanel.add(createSkillsPanel(false));
        battleArea.add(skillsPanel, BorderLayout.SOUTH);
        
        add(battleArea, BorderLayout.CENTER);

        JPanel controls = new JPanel();
        JButton backBtn = new JButton("Back to Menu");
        backBtn.addActionListener(e -> {
            Window w = SwingUtilities.getWindowAncestor(this);
            if (w instanceof JFrame) {
                ((JFrame) w).getContentPane().removeAll();
                w.revalidate();
                w.repaint();
            }
        });
        controls.add(backBtn);
        add(controls, BorderLayout.SOUTH);
    }

    public void setPlayer1(Person p) {
        this.player1 = p;
        if (p == null) return;
        if (player1Skills[0] != null) {
            player1Skills[0].setText(p.getSkill1Name());
            player1Skills[0].setEnabled(true);
        }
        if (player1Skills[1] != null) {
            player1Skills[1].setText(p.getSkill2Name());
            player1Skills[1].setEnabled(true);
        }
        if (player1Skills[2] != null) {
            player1Skills[2].setText(p.getSkill3Name());
            player1Skills[2].setEnabled(true);
        }
        updateStatusDisplays();
    }

    public void setPlayer2(Person p) {
        this.player2 = p;
        if (p == null) return;
        if (player2Skills[0] != null) {
            player2Skills[0].setText(p.getSkill1Name());
            player2Skills[0].setEnabled(true);
        }
        if (player2Skills[1] != null) {
            player2Skills[1].setText(p.getSkill2Name());
            player2Skills[1].setEnabled(true);
        }
        if (player2Skills[2] != null) {
            player2Skills[2].setText(p.getSkill3Name());
            player2Skills[2].setEnabled(true);
        }
        updateStatusDisplays();
    }

    private JPanel createCharacterPanel(boolean isPlayer1) {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBorder(BorderFactory.createTitledBorder(isPlayer1 ? "Player 1" : "Player 2"));

        JButton charButton = new JButton("Click to Target");
        charButton.setPreferredSize(new Dimension(120, 120));
        charButton.addActionListener(e -> handleTargetSelection(isPlayer1));
        panel.add(charButton, BorderLayout.CENTER);

        JLabel status = new JLabel("HP: --- / --- | MP: --- / ---", SwingConstants.CENTER);
        if (isPlayer1) {
            player1Status = status;
        } else {
            player2Status = status;
        }
        panel.add(status, BorderLayout.SOUTH);
        
        return panel;
    }

    private JPanel createSkillsPanel(boolean isPlayer1) {
        JPanel panel = new JPanel(new GridLayout(1, 3, 5, 0));
        JButton[] skills = isPlayer1 ? player1Skills : player2Skills;
        
        for (int i = 0; i < 3; i++) {
            JButton skillBtn = new JButton("Skill " + (i + 1));
            int skillNum = i + 1;
            skillBtn.addActionListener(e -> handleSkillUse(isPlayer1, skillNum));
            skillBtn.setEnabled(false);
            skills[i] = skillBtn;
            panel.add(skillBtn);
        }
        
        return panel;
    }

    private void handleTargetSelection(boolean isPlayer1) {
        selectedTarget = isPlayer1 ? player1 : player2;
        System.out.println("Selected target: " + (isPlayer1 ? "Player 1" : "Player 2"));
    }

    private void handleSkillUse(boolean isPlayer1, int skillNum) {
        if (selectedTarget == null) {
            JOptionPane.showMessageDialog(this, "Select a target first!");
            return;
        }

        Person attacker = isPlayer1 ? player1 : player2;
        if (attacker == null) return;

        try {
            switch (skillNum) {
                case 1 -> attacker.skill1(selectedTarget);
                case 2 -> attacker.skill2(selectedTarget);
                case 3 -> attacker.skill3(selectedTarget);
            }
            updateStatusDisplays();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Not enough mana/wrong target!");
        }
    }

    private void updateStatusDisplays() {
        if (player1 != null) {
            player1Status.setText(String.format("HP: %d/%d | MP: %d/%d", 
                player1.getCurrentHealth(), player1.getMaxHealth(),
                player1.getCurrentMana(), player1.getMaxMana()));
        }
        if (player2 != null) {
            player2Status.setText(String.format("HP: %d/%d | MP: %d/%d", 
                player2.getCurrentHealth(), player2.getMaxHealth(),
                player2.getCurrentMana(), player2.getMaxMana()));
        }
    }
}