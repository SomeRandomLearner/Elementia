package elementia.scenes;
import characters.heroes.*;
import elementia.*;
import javax.swing.*;
import java.awt.*;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.net.URL;
import java.util.List;

public class CharacterSelectScene extends JPanel{
    private final CardLayout cardLayout;
    private final JPanel characterScreens;
    private final List<String> heroes = List.of("Aero", "Kayden", "Psalm", "Ripper", "ZnStream");
    private int currentIndex = 0;
    private String currentCard = heroes.getFirst(); //gets the name of the character in focus

    public CharacterSelectScene(Elementia frame) throws RuntimeException{
        setLayout(new BorderLayout());
        setBackground(Color.BLACK);

        JLabel title = new JLabel("Character Select", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 28));
        title.setForeground(Color.WHITE);
        add(title, BorderLayout.NORTH);

        cardLayout = new CardLayout();
        characterScreens = new JPanel(cardLayout);

        // For individual character scenes
        JPanel aeroScreen = Utility.createScene("Aero", Color.LIGHT_GRAY);
        JPanel kaydenScreen = Utility.createScene("Kayden", Color.CYAN);
        JPanel psalmScreen = Utility.createScene("Psalm", Color.RED);
        JPanel ripperScreen = Utility.createScene("Ripper", Color.DARK_GRAY);
        JPanel znStreamScreen = Utility.createScene("ZnStream", Color.BLUE);

        // Places an image for each character on their individual scenes
        URL aeroPath = getClass().getResource("/images/Aero.png");
        assert aeroPath != null : "Aero image not found";
        var aeroImgHolder = new JLabel(new ImageIcon(aeroPath));
        aeroScreen.add(aeroImgHolder);

        URL psalmPath = getClass().getResource("/images/PsalmFire.png");
        assert psalmPath != null : "PsalmFire image not found";
        var psalmImgHolder = new JLabel(new ImageIcon(psalmPath));
        psalmScreen.add(psalmImgHolder);

        URL kaydenPath = getClass().getResource("/images/Kayden Break Temp.png");
        assert kaydenPath != null : "Kayden Break Temp image not found";
        var kaydenImgHolder = new JLabel(new ImageIcon(kaydenPath));
        kaydenScreen.add(kaydenImgHolder);

        URL znStreamPath = getClass().getResource("/images/ZnStream.png");
        assert znStreamPath != null : "ZnStream image not found";
        var znStreamImgHolder = new JLabel(new ImageIcon(znStreamPath));
        znStreamScreen.add(znStreamImgHolder);

        characterScreens.add(aeroScreen, "Aero");
        characterScreens.add(kaydenScreen, "Kayden");
        characterScreens.add(psalmScreen, "Psalm");
        characterScreens.add(ripperScreen, "Ripper");
        characterScreens.add(znStreamScreen, "ZnStream");

        add(characterScreens, BorderLayout.CENTER);

        JButton nextBtn = Utility.createButton("Next");
        JButton backBtn = Utility.createButton("Back");
        JButton selectBtn = Utility.createButton("Select");


        nextBtn.addActionListener(e -> {
            cardLayout.next(characterScreens);
            currentIndex = (currentIndex + 1) % heroes.size();
            currentCard = heroes.get(currentIndex);

        });
        backBtn.addActionListener(e -> {
            cardLayout.previous(characterScreens);
            currentIndex = (currentIndex - 1 + heroes.size()) % heroes.size(); // without the extra + heroes.size(), an ArrayOutOfBoundsException appears
            currentCard = heroes.get(currentIndex);

        });
        selectBtn.addActionListener(e -> {
            try {
                switch (currentCard) {
                    case "Aero" -> UserData.alliedTeam.add(new Aero());
                    case "Kayden" -> UserData.alliedTeam.add(new Kayden());
                    case "Psalm" -> UserData.alliedTeam.add(new Psalm());
                    case "Ripper" -> UserData.alliedTeam.add(new Ripper());
                    case "ZnStream" -> UserData.alliedTeam.add(new ZnStream());
                    default -> throw new RuntimeException();
                }
            } catch (RuntimeException ex) {
                throw new RuntimeException(ex);
            }

            frame.showScreen("LevelSelect");
        });

        add(nextBtn, BorderLayout.EAST);
        add(backBtn, BorderLayout.WEST);
        add(selectBtn, BorderLayout.SOUTH);
    }


}

