import javax.swing.*;
import characters.*;
import characters.heroes.*;
import elementia.Elementia;

import java.awt.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class test extends JPanel {

    public test(JFrame frame, boolean isPvp){
        setLayout(null);
        setBackground(Color.CYAN);
        ArrayList<Person> left = new ArrayList<>(3);
        ArrayList<Person> right = new ArrayList<>(3);
        ArrayList<JLabel> JLabelLeftArray = new ArrayList<>(3);
        ArrayList<JLabel> JLabelRightArray = new ArrayList<>(3);


        left.add(new Aero());
        left.add(new Aero());
        left.add(new Aero());

        right.add(new Assassin());
        right.add(new Assassin());
        right.add(new Assassin());

        for(Person hero: left){
            URL aeroPath = getClass().getResource("/images/Aero.png");
            assert aeroPath != null;
            ImageIcon aeroImg = new ImageIcon(aeroPath);
            JLabelLeftArray.add(new JLabel(aeroImg));
        }
        for(Person hero: left){
            URL aeroPath = getClass().getResource("/images/Aero.png");
            assert aeroPath != null;
            ImageIcon aeroImg = new ImageIcon(aeroPath);
            JLabelLeftArray.add(new JLabel(aeroImg));
        }
        frame.add(this);
    }
    public static void main(String[] args){
        JFrame frame = new JFrame();
        frame.setTitle("Elementia");
        frame.setSize(1000, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        new test(frame, false);
        frame.setVisible(true);
    }
}
