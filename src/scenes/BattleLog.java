package scenes;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class BattleLog extends JPanel {
    private final List<String> logEntries = new ArrayList<>();
    private final JTextArea textArea;

    public BattleLog() {
        setLayout(new BorderLayout());
        setBackground(new Color(20, 20, 30));
        setBorder(BorderFactory.createLineBorder(new Color(100, 100, 120), 2));

        JLabel titleLabel = new JLabel(" Battle Log");
        titleLabel.setForeground(new Color(150, 150, 200));
        titleLabel.setFont(new Font("Arial", Font.BOLD, 12));
        titleLabel.setBackground(new Color(40, 40, 50));
        titleLabel.setOpaque(true);
        add(titleLabel, BorderLayout.NORTH);

        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setBackground(new Color(30, 30, 40));
        textArea.setForeground(new Color(200, 200, 220));
        textArea.setFont(new Font("Courier New", Font.PLAIN, 11));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setMargin(new Insets(5, 5, 5, 5));

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBackground(new Color(30, 30, 40));
        scrollPane.setBorder(null);
        add(scrollPane, BorderLayout.CENTER);
    }

    public void addEntry(String entry) {
        synchronized (logEntries) {
            logEntries.add(entry);
        }
        scheduleUpdateDisplay();
    }

    public void addSkillUse(String attacker, String skillName, String target, int damage) {
        addEntry(attacker + " uses " + skillName + " on " + target + " (DMG: " + damage + ")");
    }

    public void addDefeated(String characterName) {
        addEntry(">>> " + characterName + " was defeated! <<<");
    }

    public void addTurn(String characterName) {
        addEntry("--- " + characterName + "'s Turn ---");
    }

    public void addResult(String result) {
        addEntry("=== BATTLE END === " + result);
    }

    @SuppressWarnings("unused")
    public void clearLog() {
        synchronized (logEntries) {
            logEntries.clear();
        }
        scheduleUpdateDisplay();
    }

    @SuppressWarnings("unused")
    public int getEntryCount() {
        synchronized (logEntries) {
            return logEntries.size();
        }
    }

    private void scheduleUpdateDisplay() {
        SwingUtilities.invokeLater(() -> {
            StringBuilder sb = new StringBuilder();
            synchronized (logEntries) {
                for (String e : logEntries) {
                    sb.append(e).append("\n");
                }
            }
            textArea.setText(sb.toString());
            textArea.setCaretPosition(textArea.getDocument().getLength());
        });
    }
}