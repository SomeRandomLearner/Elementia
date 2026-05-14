package scenes;

import scenes.arcade_scenes.ArcadeBattleScene;
import scenes.arcade_scenes.ArcadeCharacterSelectScene;
import scenes.arcade_scenes.GameOverScene;
import scenes.arcade_scenes.LevelSelectScene;
import scenes.pvp_scenes.PVPBattleScene;
import scenes.pvp_scenes.PVPCharacterSelectScene;
import scenes.pvp_scenes.PVPStageSelectScene;
import scenes.vs_ai_scenes.VSAIBattleScene;
import scenes.vs_ai_scenes.VSAICharacterSelectScene;
import scenes.vs_ai_scenes.VSAIStageSelectScene;

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
    private GameOverScene gameOver;

    // VS AI Mode
    private VSAIBattleScene vsAIBattle;
    private VSAICharacterSelectScene vsAICharacterSelectScene;
    private VSAIStageSelectScene vsAIStageSelectScene;

    // PVP Mode
    private PVPCharacterSelectScene pvpCharacterSelect;
    private PVPStageSelectScene pvpStageSelect;
    private PVPBattleScene pvpBattle;

    // Leaderboard
    private LeaderboardScene leaderboard;
    private LeaderboardNameInputScene leaderboardNameInput;

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
        gameOver = new GameOverScene(this);
        vsAIBattle = new VSAIBattleScene(this);
        vsAICharacterSelectScene = new VSAICharacterSelectScene(this);
        vsAIStageSelectScene = new VSAIStageSelectScene(this);
        pvpCharacterSelect = new PVPCharacterSelectScene(this);
        pvpStageSelect = new PVPStageSelectScene(this);
        pvpBattle = new PVPBattleScene(this);
        leaderboard = new LeaderboardScene(this);
        leaderboardNameInput = new LeaderboardNameInputScene(this);

        container.add(mainMenu, "MainMenu");
        container.add(modeSelect, "ModeSelect");
        container.add(arcadeCharacterSelect, "ArcadeCharacterSelect");
        container.add(levelSelect, "LevelSelect");
        container.add(arcadeBattle, "Battle");
        container.add(gameOver, "GameOver");

        container.add(vsAICharacterSelectScene, "VSAICharacterSelect");
        container.add(vsAIStageSelectScene, "VSAIStageSelect");
        container.add(vsAIBattle, "VSAIBattle");

        container.add(pvpCharacterSelect, "PVPCharacterSelect");
        container.add(pvpStageSelect, "PVPStageSelect");
        container.add(pvpBattle, "PVPBattle");


        container.add(leaderboard, "Leaderboard");
        container.add(leaderboardNameInput, "LeaderboardNameInput");
        add(container);
        layout.show(container, "MainMenu");

        setVisible(true);
    }

    public void showScreen(Scenes scene) {
        String name = switch (scene){
            case ARCADE_BATTLE -> "Battle";
            case ARCADE_CHARACTER_SELECT -> "ArcadeCharacterSelect";
            case ARCADE_GAME_OVER -> "GameOver";
            case ARCADE_LEVEL_SELECT -> "LevelSelect";
            case ARCADE_NAME_INPUT -> "LeaderboardNameInput";
            case ARCADE_LEADERBOARD -> "Leaderboard";
            case PVP_BATTLE -> "PVPBattle";
            case PVP_CHARACTER_SELECT -> "PVPCharacterSelect";
            case PVP_STAGE_SELECT -> "PVPStageSelect";
            case VS_AI_BATTLE -> "VSAIBattle";
            case VS_AI_CHARACTER_SELECT -> "VSAICharacterSelect";
            case VS_AI_STAGE_SELECT -> "VSAIStageSelect";
            case MAIN_MENU -> "MainMenu";
            case MODE_SELECT -> "ModeSelect";
        };
        layout.show(container, name);
    }

    public ModeSelectScene getModeSelect() { return modeSelect; }

    public LevelSelectScene getLevelSelect() { return levelSelect; }

    public ArcadeBattleScene getArcadeBattle() { return arcadeBattle; }

    public PVPCharacterSelectScene getPVPCharacterSelect() { return pvpCharacterSelect; }

    public PVPStageSelectScene getPVPStageSelect() { return pvpStageSelect; }

    public PVPBattleScene getPVPBattle() { return pvpBattle; }

    public VSAIBattleScene getVSAIBattle() { return vsAIBattle; }

    public LeaderboardScene getLeaderboard() { return leaderboard; }

    public LeaderboardNameInputScene getLeaderboardNameInput() {
        return leaderboardNameInput; }
}
