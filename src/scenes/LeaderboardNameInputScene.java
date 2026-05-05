package scenes;

import utils.Utility;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.io.*;
import java.time.Duration;
import java.time.Instant;

public class LeaderboardNameInputScene extends JPanel {
    private File playerDataFile = new File("data/player_data.txt");
    private File leaderboardDataFile = new File("data/leaderboard_data.txt");
    private JLabel congratulationsLabel;
    private JLabel timeLabel;
    private JLabel nameLabel;
    private JTextField nameTextField;
    private JPanel namePanel;
    private JButton confirmButton;

    private Instant timeStarted;
    private Instant timeCompleted;
    private Duration timeElapsed;

    private String playerTime;
    public LeaderboardNameInputScene(Elementia frame){
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        congratulationsLabel = new JLabel("CONGRATULATIONS ON CLEARING THE GAME!");
        timeLabel = new JLabel("YOU CLEARED IT IN: ");

        if(!playerDataFile.getParentFile().exists()){
            playerDataFile.getParentFile().mkdirs();
        }
        if(!leaderboardDataFile.getParentFile().exists()){
            leaderboardDataFile.getParentFile().mkdirs();
        }

        namePanel = new JPanel(new FlowLayout());
        confirmButton = Utility.createButton("CONFIRM");
        confirmButton.setEnabled(false);

        nameLabel = new JLabel("Input Name: ");
        nameTextField = new JTextField(20);
        nameTextField.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) { check(); }
            public void removeUpdate(DocumentEvent e) { check(); }
            public void insertUpdate(DocumentEvent e) { check(); }
            public void check() {
                confirmButton.setEnabled(!nameTextField.getText().trim().isEmpty());
            }
        });
        confirmButton.addActionListener(e ->{
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(leaderboardDataFile, true))){
                writer.write(nameTextField.getText().toUpperCase() + "," + timeElapsed.toSeconds());
                writer.newLine();
            }
            catch (IOException ex) {
                ex.printStackTrace();
            }
            frame.getLeaderboard().updateLeaderboard();
            frame.showScreen("Leaderboard");
        });

        add(congratulationsLabel, gbc);
        gbc.gridy = 1;
        JPanel timePanel = new JPanel(new FlowLayout());
        timePanel.add(timeLabel);
        add(timePanel, gbc);
        namePanel.add(nameLabel);
        namePanel.add(nameTextField);
        gbc.gridy = 2;
        add(namePanel, gbc);
        gbc.gridy = 3;
        add(confirmButton, gbc);
    }

    public void calculateTimeElapsed(){
        if (playerDataFile.exists() && playerDataFile.length() > 0) {
            try (BufferedReader reader = new BufferedReader(new FileReader(playerDataFile), 256)){
                String dataLine = reader.readLine();
                if(dataLine == null || dataLine.isBlank()) return;

                String[] playerData = dataLine.split(",");
                if(playerData.length < 2) return;
                timeStarted = Instant.parse(playerData[0]);
                timeCompleted = Instant.parse(playerData[1]);
                timeElapsed = Duration.between(timeStarted, timeCompleted);
                playerTime = (timeElapsed.getSeconds() / 60 + " minutes and " + timeElapsed.getSeconds() % 60 + " seconds");
                displayTimes();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("No player data found at: " + playerDataFile.getAbsolutePath());
        }
    }
    private void displayTimes(){
        if (playerTime != null) {
            timeLabel.setText("YOU CLEARED IT IN: " + playerTime);
            this.revalidate();
            this.repaint();
        }
    }
}
