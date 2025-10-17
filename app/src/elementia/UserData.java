package elementia;
import characters.Person;
import java.util.ArrayList;

//Ideally file handling is used here, but this is easier for me for now.
public class UserData {
    final private byte NO_OF_LEVELS = 5; //Adjust this accordingly
    public static ArrayList<Person> alliedTeam = new ArrayList<>(3);
    protected boolean[]  levelClearStatus = new boolean[NO_OF_LEVELS];
}
