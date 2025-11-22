package scenes;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Simple battle log system that tracks and displays battle events
 */
public class BattleLog extends JPanel {
    private ArrayList<String> logEntries;
    private JTextArea textArea;
    private JScrollPane scrollPane;

    public BattleLog() {
        this.logEntries = new ArrayList<>();
        setLayout(new BorderLayout());
        setBackground(new Color(20, 20, 30));
        setBorder(BorderFactory.createLineBorder(new Color(100, 100, 120), 2));

        // Create text area for displaying log
        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setBackground(new Color(30, 30, 40));
        textArea.setForeground(new Color(200, 200, 220));
        textArea.setFont(new Font("Courier New", Font.PLAIN, 11));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setMargin(new Insets(5, 5, 5, 5));

        // Add scroll pane
        scrollPane = new JScrollPane(textArea);
        scrollPane.setBackground(new Color(30, 30, 40));
        scrollPane.setBorder(null);
        add(scrollPane, BorderLayout.CENTER);

        // Title label
        JLabel titleLabel = new JLabel(" Battle Log");
        titleLabel.setForeground(new Color(150, 150, 200));
        titleLabel.setFont(new Font("Arial", Font.BOLD, 12));
        titleLabel.setBackground(new Color(40, 40, 50));
        titleLabel.setOpaque(true);
        add(titleLabel, BorderLayout.NORTH);
    }

    /**
     * Add an entry to the battle log
     */
    public void addEntry(String entry) {
        logEntries.add(entry);
        updateDisplay();
    }

    /**
     * Add a skill usage entry
     */
    public void addSkillUse(String attacker, String skillName, String target, int damage) {
        String entry = attacker + " uses " + skillName + " on " + target + " (DMG: " + damage + ")";
        addEntry(entry);
    }

    /**
     * Add a character defeated entry
     */
    public void addDefeated(String characterName) {
        String entry = ">>> " + characterName + " was defeated! <<<";
        addEntry(entry);
    }

    /**
     * Add a turn indicator entry
     */
    public void addTurn(String characterName) {
        String entry = "\n--- " + characterName + "'s Turn ---";
        addEntry(entry);
    }

    /**
     * Add a battle result entry
     */
    public void addResult(String result) {
        String entry = "\n=== BATTLE END ===\n" + result;
        addEntry(entry);
    }

    /**
     * Clear the entire log
     */
    public void clearLog() {
        logEntries.clear();
        updateDisplay();
    }

    /**
     * Update the text area display
     */
    private void updateDisplay() {
        StringBuilder sb = new StringBuilder();
        for (String entry : logEntries) {
            sb.append(entry).append("\n");
        }
        textArea.setText(sb.toString());
        
        // Auto-scroll to bottom
        SwingUtilities.invokeLater(() -> {
            textArea.setCaretPosition(textArea.getDocument().getLength());
        });
    }

    /**
     * Get the number of entries in the log
     */
    public int getEntryCount() {
        return logEntries.size();
    }
}
