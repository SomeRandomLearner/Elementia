package scenes;
import javax.swing.*;
import java.awt.*;

public class Elementia extends JFrame{
    private final CardLayout layout = new CardLayout();
    private final JPanel container = new JPanel(layout);

    public Elementia() {
        setTitle("Elementia");
        setSize(1000, 800);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        container.add(new MainMenuScene(this), "MainMenu");
        container.add(new CharacterSelectScene(this), "CharacterSelect");
        container.add(new LevelSelectScene(this), "LevelSelect");
        container.add(new PVPCharacterSelectScene(this), "PVPCharacterSelect");

        add(container);
        layout.show(container, "MainMenu");

        setVisible(true);
    }

    public void showScreen(String name) {
        layout.show(container, name);
    }

    public void addCharacterDisplayScene(CharacterInfo ChInfo){ container.add(new CharacterDisplayScene(this, ChInfo), "CharacterDisplay"); }

    public void addVSAIBattleScene(){
        container.add(new VSAIBattleScene(this), "VSAIBattle");
    }

    public void addPVPBattleScene(){ container.add(new PVPBattleScene(this), "PVPBattle"); }
}
