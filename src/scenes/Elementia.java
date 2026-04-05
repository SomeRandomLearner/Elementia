package scenes;
import javax.swing.*;
import java.awt.*;

public class Elementia extends JFrame{
    private final CardLayout layout = new CardLayout();
    private final JPanel container = new JPanel(layout);

    private JPanel mainMenu;
    private ModeSelectScene modeSelect;

    // Arcade Mode
    private CharacterSelectScene characterSelect;
    private LevelSelectScene levelSelect;
    private BattleScene battle;

    // PVP Mode
    private PVPCharacterSelectScene pvpCharacterSelect;
    private PVPStageSelectScene pvpStageSelect;
    private PVPBattleScene pvpBattle;

    public Elementia() {
        setTitle("Elementia");
        setSize(1000, 800);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        mainMenu = new MainMenuScene(this);
        modeSelect = new ModeSelectScene(this);
        characterSelect = new CharacterSelectScene(this);
        levelSelect = new LevelSelectScene(this);
        battle = new BattleScene(this);
        pvpCharacterSelect = new PVPCharacterSelectScene(this);
        pvpStageSelect = new PVPStageSelectScene(this);
        pvpBattle = new PVPBattleScene(this);

        container.add(mainMenu, "MainMenu");
        container.add(modeSelect, "ModeSelect");
        container.add(characterSelect, "CharacterSelect");
        container.add(levelSelect, "LevelSelect");

        container.add(pvpCharacterSelect, "PVPCharacterSelect");
        container.add(pvpStageSelect, "PVPStageSelect");
        container.add(pvpBattle, "PVPBattle");


        add(container);
        layout.show(container, "MainMenu");

        setVisible(true);
    }

    public void showScreen(String name) {
        layout.show(container, name);
    }

    public void addCharacterDisplayScene(CharacterInfo ChInfo){ container.add(new CharacterDisplayScene(this, ChInfo), "CharacterDisplay"); }

    public void addBattleScene(){
        container.add(new BattleScene(this), "Battle");
    }

    public ModeSelectScene getModeSelect() { return modeSelect; }

    public LevelSelectScene getLevelSelect() { return levelSelect; }

    public BattleScene getBattle() { return battle; }

    public PVPCharacterSelectScene getPVPCharacterSelect() { return pvpCharacterSelect; }

    public PVPStageSelectScene getPVPStageSelect() { return pvpStageSelect; }

    public PVPBattleScene getPVPBattle() { return pvpBattle; }

}
