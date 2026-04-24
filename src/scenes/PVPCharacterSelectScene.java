package scenes;

import characters.*;
import logic.BattleLogic;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Objects;

public class PVPCharacterSelectScene extends JPanel {

    private Font normalFont = new Font("Times New Roman", Font.PLAIN, 30);

    private GameCharacter player1ChosenCharacter;
    private GameCharacter player2ChosenCharacter;

    private boolean player1HasChosen = false;
    private boolean player2HasChosen = false;

    private JLabel characterSelectLabel;
    private JButton confirmButton;

    private JPanel player1Preview;
    private JPanel player2Preview;

    private Image bgImage;

    public PVPCharacterSelectScene(Elementia frame) {

        setLayout(new BorderLayout());

        bgImage = new ImageIcon(
                Objects.requireNonNull(getClass().getResource("/resources/CharacterSelectBG.png"))
        ).getImage();

        characterSelectLabel = new JLabel("Player 1 Choose a Character");
        characterSelectLabel.setFont(new Font("Times New Roman", Font.BOLD, 28));
        characterSelectLabel.setForeground(Color.WHITE);
        characterSelectLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(characterSelectLabel, BorderLayout.NORTH);

        GameCharacter[] characters = {
                new Aero(), new Kaelis(), new Kangel(),
                new Kayden(), new Maelor(), new Psalm(),
                new Ripper(), new Veyrion(), new ZenStream()
        };

        JPanel grid = new JPanel(new GridLayout(3, 3, 10, 10));
        grid.setOpaque(false);

        for (GameCharacter c : characters) {
            grid.add(createCharacterPanel(c));
        }

        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setOpaque(false);
        leftPanel.setPreferredSize(new Dimension(800, 800));
        leftPanel.add(grid, BorderLayout.CENTER);

        add(leftPanel, BorderLayout.WEST);

        player1Preview = createPreviewPanel("PLAYER 1");
        player2Preview = createPreviewPanel("PLAYER 2");

        JPanel rightPanel = new JPanel(new GridLayout(2, 1));
        rightPanel.setOpaque(false);
        rightPanel.setPreferredSize(new Dimension(500, 800));

        rightPanel.add(player1Preview);
        rightPanel.add(player2Preview);

        add(rightPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setOpaque(false);

        confirmButton = createStyledButton("Confirm");
        confirmButton.setEnabled(false);

        confirmButton.addActionListener(e -> {

            BattleLogic battleLogic = new BattleLogic();
            battleLogic.resetCharacterChoices();

            battleLogic.addToTeam(1, player1ChosenCharacter);
            battleLogic.addToTeam(2, player2ChosenCharacter);

            frame.getPVPBattle().setBattleLogic(battleLogic);
            frame.showScreen("PVPStageSelect");
        });

        JButton backBtn = createStyledButton("Return to Main Menu");
        backBtn.addActionListener(e -> frame.showScreen("MainMenu"));

        bottomPanel.add(confirmButton);
        bottomPanel.add(backBtn);

        add(bottomPanel, BorderLayout.SOUTH);
    }

    private JPanel createCharacterPanel(GameCharacter character) {

        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);
        panel.setPreferredSize(new Dimension(120, 120));
        panel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));

        JLabel image = new JLabel(getCharacterImageIcon(character.getImagePath()));
        image.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel name = new JLabel(character.getName(), SwingConstants.CENTER);
        name.setForeground(Color.WHITE);

        panel.add(image, BorderLayout.CENTER);
        panel.add(name, BorderLayout.SOUTH);

        panel.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {

                panel.setBorder(BorderFactory.createLineBorder(Color.CYAN, 2));


                if (isDuplicate(character)) {
                    showPlayer2Warning("CHARACTER HAS ALREADY BEEN SELECTED!");
                    clearPreview(player2Preview);
                    return;
                }

                if (!player1HasChosen) {
                    showPreview(character, player1Preview);
                } else if (!player2HasChosen) {
                    showPreview(character, player2Preview);
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                panel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));

                if (!player1HasChosen) {
                    clearPreview(player1Preview);
                } else if (!player2HasChosen) {
                    clearPreview(player2Preview);
                }
            }

            @Override
            public void mouseClicked(MouseEvent e) {

                if (!player1HasChosen) {

                    player1ChosenCharacter = character;
                    player1HasChosen = true;

                    showPreview(character, player1Preview);
                    characterSelectLabel.setText("Player 2 Choose a Character");

                } else if (!player2HasChosen) {

                    if (isDuplicate(character)) {
                        showPlayer2Warning("CHARACTER ALREADY SELECTED!");
                        return;
                    }

                    player2ChosenCharacter = character;
                    player2HasChosen = true;

                    showPreview(character, player2Preview);

                    characterSelectLabel.setText("All Ready!");
                    confirmButton.setEnabled(true);
                }
            }
        });

        return panel;
    }


    private JPanel createPreviewPanel(String title) {

        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 24));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel warning = new JLabel("");
        warning.setName("warning");
        warning.setForeground(Color.RED);
        warning.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel image = new JLabel();
        image.setName("image");
        image.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel name = new JLabel();
        name.setName("name");

        JLabel element = new JLabel();
        element.setName("element");

        JLabel skill1 = new JLabel();
        skill1.setName("skill1");

        JLabel skill2 = new JLabel();
        skill2.setName("skill2");

        JLabel skill3 = new JLabel();
        skill3.setName("skill3");

        for (JLabel l : new JLabel[]{name, element, skill1, skill2, skill3}) {
            l.setForeground(Color.WHITE);
            l.setAlignmentX(Component.CENTER_ALIGNMENT);
        }

        panel.add(titleLabel);
        panel.add(warning);
        panel.add(Box.createVerticalStrut(10));
        panel.add(name);
        panel.add(element);
        panel.add(Box.createVerticalStrut(10));
        panel.add(image);
        panel.add(Box.createVerticalStrut(10));
        panel.add(skill1);
        panel.add(skill2);
        panel.add(skill3);

        return panel;
    }


    private void showPreview(GameCharacter character, JPanel panel) {

        clearWarning(panel);

        JLabel image = (JLabel) findComponent(panel, "image");
        JLabel name = (JLabel) findComponent(panel, "name");
        JLabel element = (JLabel) findComponent(panel, "element");
        JLabel skill1 = (JLabel) findComponent(panel, "skill1");
        JLabel skill2 = (JLabel) findComponent(panel, "skill2");
        JLabel skill3 = (JLabel) findComponent(panel, "skill3");

        image.setIcon(new ImageIcon(
                new ImageIcon(getClass().getResource(character.getImagePath()))
                        .getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH)
        ));

        name.setText("Name: " + character.getName());
        element.setText("Element: " + character.getElement());

        String[] skills = character.getSkillNames();

        skill1.setText("Skill 1: " + (skills.length > 0 ? skills[0] : "-"));
        skill2.setText("Skill 2: " + (skills.length > 1 ? skills[1] : "-"));
        skill3.setText("Skill 3: " + (skills.length > 2 ? skills[2] : "-"));
    }


    private void clearPreview(JPanel panel) {

        JLabel image = (JLabel) findComponent(panel, "image");
        JLabel name = (JLabel) findComponent(panel, "name");
        JLabel element = (JLabel) findComponent(panel, "element");
        JLabel skill1 = (JLabel) findComponent(panel, "skill1");
        JLabel skill2 = (JLabel) findComponent(panel, "skill2");
        JLabel skill3 = (JLabel) findComponent(panel, "skill3");

        if (image != null) image.setIcon(null);
        if (name != null) name.setText("");
        if (element != null) element.setText("");
        if (skill1 != null) skill1.setText("");
        if (skill2 != null) skill2.setText("");
        if (skill3 != null) skill3.setText("");
    }


    private void showPlayer2Warning(String msg) {
        JLabel warning = (JLabel) findComponent(player2Preview, "warning");
        if (warning != null) warning.setText(msg);
    }

    private void clearWarning() {
        JLabel warning = (JLabel) findComponent(player2Preview, "warning");
        if (warning != null) warning.setText("");
    }

    private void clearWarning(JPanel panel) {
        JLabel warning = (JLabel) findComponent(panel, "warning");
        if (warning != null) warning.setText("");
    }


    private boolean isDuplicate(GameCharacter character) {
        return (player1ChosenCharacter != null &&
                player1ChosenCharacter.getClass() == character.getClass())
                || (player2ChosenCharacter != null &&
                player2ChosenCharacter.getClass() == character.getClass());
    }


    private Component findComponent(Container container, String name) {
        for (Component c : container.getComponents()) {
            if (name.equals(c.getName())) return c;
            if (c instanceof Container) {
                Component found = findComponent((Container) c, name);
                if (found != null) return found;
            }
        }
        return null;
    }


    private ImageIcon getCharacterImageIcon(String path) {
        return new ImageIcon(
                new ImageIcon(
                        Objects.requireNonNull(getClass().getResource(path))
                ).getImage().getScaledInstance(250, 250, Image.SCALE_SMOOTH)
        );
    }


    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(Color.BLACK);
        button.setForeground(Color.WHITE);
        button.setFont(normalFont);
        button.setFocusPainted(false);
        return button;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (bgImage != null) {
            g.drawImage(bgImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}