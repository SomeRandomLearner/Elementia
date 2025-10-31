package scenes;
import characters.GameCharacter;
import scenes.Utility;

import javax.swing.*;
import java.awt.*;

public class VSAIBattleScene extends JPanel {
    private boolean hasWon = false; //For clear status and 1-star clears
    private boolean hasNoDeaths = true; //For 3-star clears
    private boolean hasOnlyOneDeath = false; //For 2-star clears

    public VSAIBattleScene(Elementia frame){
        setLayout(new BorderLayout());
        setBackground(Color.BLACK);

        GridBagConstraints gridBagConstraints = new GridBagConstraints();

        JButton skill1btn = Utility.createButton("Skill 1");
        JButton skill2btn = Utility.createButton("Skill 2");
        JButton skill3btn = Utility.createButton("Skill 3");

        JPanel buttonPanel = new JPanel(new GridBagLayout());

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        buttonPanel.add(skill1btn, gridBagConstraints);

        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        buttonPanel.add(skill2btn, gridBagConstraints);

        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        buttonPanel.add(skill3btn, gridBagConstraints);


        JPanel battlePanel = new JPanel();
        battlePanel.setBackground(Color.RED);



        add(buttonPanel, BorderLayout.SOUTH);
    }
}
