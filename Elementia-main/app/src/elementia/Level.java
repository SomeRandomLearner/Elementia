package elementia;

import characters.Person;
import elementia.scenes.VSAIBattleScene;

import javax.swing.*;
import java.util.ArrayList;

public class Level {
    private boolean isCleared = false;
    public boolean getClearStatus() { return this.isCleared;}
    public void setClearStatus(boolean status) { isCleared = status;}

    public Level(JPanel battleScene, ArrayList<Person> allies, ArrayList<Person> enemies){

    }
}
