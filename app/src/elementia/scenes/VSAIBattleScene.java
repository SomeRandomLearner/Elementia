package elementia.scenes;
import characters.Person;
import elementia.Elementia;
import elementia.Utility;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class VSAIBattleScene extends JPanel {
    private boolean hasWon = false; //For clear status and 1-star clears
    private boolean hasNoDeaths = true; //For 3-star clears
    private boolean hasOnlyOneDeath = false; //For 2-star clears

    public VSAIBattleScene(Elementia frame){
        setLayout(new GridBagLayout());
        setBackground(Color.BLACK);

        GridBagConstraints gridBagConstraints = new GridBagConstraints();

        JButton skill1btn = Utility.createButton("Skill 1");
        JButton skill2btn = Utility.createButton("Skill 2");
        JButton skill3btn = Utility.createButton("Skill 3");

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        add(skill1btn, gridBagConstraints);

        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        add(skill2btn, gridBagConstraints);

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = gridBagConstraints.HORIZONTAL;
        add(skill3btn, gridBagConstraints);
        //Not really sure how this works
        //Work in progress

        JPanel battlePanel = new JPanel();
        battlePanel.setBackground(Color.RED);

        add(battlePanel);
    }
}
