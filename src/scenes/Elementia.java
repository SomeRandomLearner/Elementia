package scenes;

import javax.swing.*;
import java.awt.*;

public class Elementia extends JFrame{
    private final CardLayout layout = new CardLayout();
    private final JPanel container = new JPanel(layout);

    private JPanel mainMenu;
    private ModeSelectScene modeSelect;

    // Arcade Mode
    private ArcadeCharacterSelectScene arcadeCharacterSelect;
    private LevelSelectScene levelSelect;
    private ArcadeBattleScene arcadeBattle;

    // PVP Mode
    private PVPCharacterSelectScene pvpCharacterSelect;
    private PVPStageSelectScene pvpStageSelect;
    private PVPBattleScene pvpBattle;

    public Elementia() {
        int width =  1200, height = 800;
        setTitle("ELEMENTIA");
        setMinimumSize(new Dimension(width, height));
        setExtendedState(Frame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        mainMenu = new MainMenuScene(this);
        modeSelect = new ModeSelectScene(this);
        arcadeCharacterSelect = new ArcadeCharacterSelectScene(this);
        levelSelect = new LevelSelectScene(this);
        arcadeBattle = new ArcadeBattleScene(this);
        pvpCharacterSelect = new PVPCharacterSelectScene(this);
        pvpStageSelect = new PVPStageSelectScene(this);
        pvpBattle = new PVPBattleScene(this);

        container.add(mainMenu, "MainMenu");
        container.add(modeSelect, "ModeSelect");
        container.add(arcadeCharacterSelect, "ArcadeCharacterSelect");
        container.add(levelSelect, "LevelSelect");
        container.add(arcadeBattle, "Battle");


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

    public ModeSelectScene getModeSelect() { return modeSelect; }

    public LevelSelectScene getLevelSelect() { return levelSelect; }

    public AbstractBattleScene getArcadeBattle() { return arcadeBattle; }

    public PVPCharacterSelectScene getPVPCharacterSelect() { return pvpCharacterSelect; }

    public PVPStageSelectScene getPVPStageSelect() { return pvpStageSelect; }

    public PVPBattleScene getPVPBattle() { return pvpBattle; }

}
