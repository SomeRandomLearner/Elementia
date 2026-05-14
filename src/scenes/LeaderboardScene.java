package scenes;

import utils.Utility;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;

public class LeaderboardScene extends JPanel {
    private final DefaultTableModel tableModel;
    private final JTable leaderboardTable;
    private final File leaderboardDataFile = new File("data/leaderboard_data.txt");
    private final int MAX_SCORE_DISPLAY = 10;
    private final ArrayList<Object[]> sortedLeaderboard = new ArrayList<>();

    public LeaderboardScene(Elementia frame) {
        setLayout(new BorderLayout());

        String[] columns = {"Rank", "Name", "Time"};
        tableModel = new DefaultTableModel(columns, 0);
        leaderboardTable = new JTable(tableModel);
        leaderboardTable.setDefaultEditor(Object.class, null);
        JScrollPane scrollPane = new JScrollPane(leaderboardTable);
        leaderboardTable.getColumnModel().getColumn(0).setPreferredWidth(50);
        leaderboardTable.getColumnModel().getColumn(1).setPreferredWidth(200);
        leaderboardTable.getColumnModel().getColumn(2).setPreferredWidth(150);
        leaderboardTable.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
        leaderboardTable.setRowHeight(40);
        leaderboardTable.setFillsViewportHeight(true);

        BufferedImage loadedImage = null;
        try (InputStream is = getClass().getResourceAsStream("/resources/LEADERB.png")) {
            if (is == null) {
                throw new IOException("Resource not found: /resources/LEADERB.png");
            }
            loadedImage = ImageIO.read(is);
        } catch (IOException e) {
            e.printStackTrace();
        }

        final BufferedImage bg = loadedImage;

        JPanel container = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (bg != null) {
                    Graphics2D g2d = (Graphics2D) g.create();
                    g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                    g2d.drawImage(bg, 0, 0, getWidth(), getHeight(), this);
                    g2d.dispose();
                }
            }
        };
        container.setOpaque(true);
        leaderboardTable.setBackground(new Color(50, 50, 65));
        leaderboardTable.setForeground(Color.WHITE);
        leaderboardTable.getTableHeader().setBackground(new Color(70, 70, 90));
        leaderboardTable.getTableHeader().setForeground(Color.WHITE);
        scrollPane.getViewport().setBackground(new Color(50, 50, 65));

        container.add(scrollPane);
        add(container);

        JButton backButton = Utility.createButton("BACK");
        backButton.addActionListener(e -> frame.showScreen(Scenes.MAIN_MENU));
        add(backButton, BorderLayout.SOUTH);
    }

    void updateLeaderboard() {
        tableModel.setRowCount(0);
        sort();
        try (BufferedReader reader = new BufferedReader(new FileReader(leaderboardDataFile))) {
            int i = 0;
            String dataLine;
            while ((dataLine = reader.readLine()) != null && i < MAX_SCORE_DISPLAY) {
                String[] dataLineSplit = dataLine.split(",");
                if (dataLineSplit.length < 2) continue;

                String name = String.valueOf(sortedLeaderboard.get(i)[0]);
                Long totalTime = Long.parseLong((String) sortedLeaderboard.get(i)[1]);
                String formattedTime = (totalTime / 60) + " minutes and " + (totalTime % 60) + " seconds";
                switch(i){
                    case 0 -> tableModel.addRow(new Object[]{("🥇"), name, formattedTime});
                    case 1 -> tableModel.addRow(new Object[]{("\uD83E\uDD48"), name, formattedTime});
                    case 2 -> tableModel.addRow(new Object[]{("\uD83E\uDD49"), name, formattedTime});
                    default -> tableModel.addRow(new Object[]{(i + 1), name, formattedTime});
                }
                i++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void sort(){
        sortedLeaderboard.clear();

        try (BufferedReader reader = new BufferedReader(new FileReader(leaderboardDataFile))) {
            String dataLine;
            while ((dataLine = reader.readLine()) != null){
                String[] dataLineSplit = dataLine.split(",");
                if (dataLineSplit.length < 2) continue;

                String name = dataLineSplit[0];
                String time = dataLineSplit[1];
                sortedLeaderboard.add(new Object[]{name, time});
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
        for(int i = 0; i < sortedLeaderboard.size(); i++){
            boolean hasSorted = false;
            for(int j = 0; j < sortedLeaderboard.size() - 1 - i; j++){
                Object line1[] = sortedLeaderboard.get(j);
                Object line2[] = sortedLeaderboard.get(j+1);
                Long line1CompletionTime = Long.parseLong((String)line1[1]);
                Long line2CompletionTime = Long.parseLong((String)line2[1]);
                if(line1CompletionTime > line2CompletionTime){
                    Object[] temp = line1;
                    sortedLeaderboard.set(j, line2);
                    sortedLeaderboard.set(j+1, temp);
                    hasSorted = true;
                }
            }
            if(!hasSorted) break;
        }
    }
}