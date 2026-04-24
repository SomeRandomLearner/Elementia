package scenes;

import characters.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Objects;

public class ArcadeCharacterSelectScene extends JPanel {

    private Font normalFont = new Font("Times New Roman", Font.PLAIN, 30);

    private final ImageIcon aeroImgIcon;
    private final ImageIcon kaelisImgIcon;
    private final ImageIcon kangelImgIcon;
    private final ImageIcon kaydenImgIcon;
    private final ImageIcon psalmImgIcon;
    private final ImageIcon maelorImgIcon;
    private final ImageIcon ripperImgIcon;
    private final ImageIcon veyrionImgIcon;
    private final ImageIcon zenStreamImgIcon;

    private GameCharacter chosenCharacter;
    private GameCharacter lockedCharacter = null;
    private JButton confirmButton;

    private Image bgImage;


    private JLabel previewImage;
    private JLabel nameLabel;
    private JLabel elementLabel;
    private JLabel skill1;
    private JLabel skill2;
    private JLabel skill3;

    public ArcadeCharacterSelectScene(Elementia frame) {

        setLayout(new BorderLayout());

        bgImage = new ImageIcon(
                Objects.requireNonNull(getClass().getResource("/resources/CharacterSelectBG.png"))
        ).getImage();

        JLabel characterSelectLabel = new JLabel("Select a Character");
        characterSelectLabel.setFont(new Font("Times New Roman", Font.BOLD, 28));
        characterSelectLabel.setForeground(Color.WHITE);
        characterSelectLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);
        topPanel.add(characterSelectLabel, BorderLayout.CENTER);

        aeroImgIcon = getCharacterImageIcon("/resources/Aero.png");
        kaelisImgIcon = getCharacterImageIcon("/resources/Kaelis.png");
        kangelImgIcon = getCharacterImageIcon("/resources/Kangel.png");
        kaydenImgIcon = getCharacterImageIcon("/resources/Kayden.png");
        maelorImgIcon = getCharacterImageIcon("/resources/Maelor.png");
        psalmImgIcon = getCharacterImageIcon("/resources/Psalm.png");
        ripperImgIcon = getCharacterImageIcon("/resources/Ripper.png");
        veyrionImgIcon = getCharacterImageIcon("/resources/Veyrion.png");
        zenStreamImgIcon = getCharacterImageIcon("/resources/ZenStream.png");

        JPanel grid = new JPanel(new GridLayout(3, 3, 8, 8));
        grid.setOpaque(false);

        grid.add(createCharacterCard(GameCharacter.Character.AERO, aeroImgIcon));
        grid.add(createCharacterCard(GameCharacter.Character.KAELIS, kaelisImgIcon));
        grid.add(createCharacterCard(GameCharacter.Character.KANGEL, kangelImgIcon));
        grid.add(createCharacterCard(GameCharacter.Character.KAYDEN, kaydenImgIcon));
        grid.add(createCharacterCard(GameCharacter.Character.MAELOR, maelorImgIcon));
        grid.add(createCharacterCard(GameCharacter.Character.PSALM, psalmImgIcon));
        grid.add(createCharacterCard(GameCharacter.Character.RIPPER, ripperImgIcon));
        grid.add(createCharacterCard(GameCharacter.Character.VEYRION, veyrionImgIcon));
        grid.add(createCharacterCard(GameCharacter.Character.ZENSTREAM, zenStreamImgIcon));

        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setOpaque(false);
        leftPanel.setPreferredSize(new Dimension(650, 800));
        leftPanel.add(grid, BorderLayout.CENTER);

        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setOpaque(false);
        rightPanel.setPreferredSize(new Dimension(600, 800));

        JPanel topInfo = new JPanel();
        topInfo.setOpaque(false);
        topInfo.setLayout(new BoxLayout(topInfo, BoxLayout.Y_AXIS));

        nameLabel = createPreviewLabel("Name: -", 26);
        elementLabel = createPreviewLabel("Element: -", 22);

        topInfo.add(nameLabel);
        topInfo.add(elementLabel);

        previewImage = new JLabel();
        previewImage.setHorizontalAlignment(SwingConstants.CENTER);


        previewImage.setPreferredSize(new Dimension(450, 450));

        JPanel imagePanel = new JPanel(new BorderLayout());
        imagePanel.setOpaque(false);
        imagePanel.add(previewImage, BorderLayout.CENTER);

        JPanel skillPanel = new JPanel();
        skillPanel.setOpaque(false);
        skillPanel.setLayout(new BoxLayout(skillPanel, BoxLayout.Y_AXIS));

        skill1 = createPreviewLabel("Skill 1: -", 20);
        skill2 = createPreviewLabel("Skill 2: -", 20);
        skill3 = createPreviewLabel("Skill 3: -", 20);

        skillPanel.add(skill1);
        skillPanel.add(skill2);
        skillPanel.add(skill3);

        rightPanel.add(topInfo, BorderLayout.NORTH);
        rightPanel.add(imagePanel, BorderLayout.CENTER);
        rightPanel.add(skillPanel, BorderLayout.SOUTH);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setOpaque(false);

        confirmButton = createStyledButton("Confirm");
        confirmButton.setEnabled(false);

        confirmButton.addActionListener(e -> {
            Teams.clearAlliedTeam();
            Teams.addToAlliedTeam(chosenCharacter);
            frame.showScreen("LevelSelect");
        });

        JButton backBtn = createStyledButton("Return to Main Menu");
        backBtn.addActionListener(e -> frame.showScreen("MainMenu"));

        bottomPanel.add(confirmButton);
        bottomPanel.add(backBtn);

        add(topPanel, BorderLayout.NORTH);
        add(leftPanel, BorderLayout.WEST);
        add(rightPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private JPanel createCharacterCard(GameCharacter.Character character, ImageIcon icon) {

        JPanel card = new JPanel(new BorderLayout());
        card.setPreferredSize(new Dimension(110, 110));
        card.setOpaque(false);
        card.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));

        JLabel img = new JLabel(icon);
        img.setHorizontalAlignment(SwingConstants.CENTER);

        card.add(img, BorderLayout.CENTER);

        card.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {

                if (lockedCharacter == null) {
                    updatePreview(character);
                }

                card.setBorder(BorderFactory.createLineBorder(Color.CYAN, 2));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                card.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
            }

            @Override
            public void mouseClicked(MouseEvent e) {

                chosenCharacter = switch (character) {
                    case AERO -> new Aero();
                    case KAELIS -> new Kaelis();
                    case KANGEL -> new Kangel();
                    case KAYDEN -> new Kayden();
                    case MAELOR -> new Maelor();
                    case PSALM -> new Psalm();
                    case RIPPER -> new Ripper();
                    case VEYRION -> new Veyrion();
                    case ZENSTREAM -> new ZenStream();
                };

                lockedCharacter = chosenCharacter;
                confirmButton.setEnabled(true);

                updatePreview(character);
            }
        });

        return card;
    }

    private void updatePreview(GameCharacter.Character character) {

        GameCharacter temp = switch (character) {
            case AERO -> new Aero();
            case KAELIS -> new Kaelis();
            case KANGEL -> new Kangel();
            case KAYDEN -> new Kayden();
            case MAELOR -> new Maelor();
            case PSALM -> new Psalm();
            case RIPPER -> new Ripper();
            case VEYRION -> new Veyrion();
            case ZENSTREAM -> new ZenStream();
        };

        previewImage.setIcon(new ImageIcon(
                new ImageIcon(getClass().getResource(temp.getImagePath()))
                        .getImage().getScaledInstance(450, 450, Image.SCALE_SMOOTH)
        ));

        nameLabel.setText(temp.getName());
        elementLabel.setText("The " + temp.getElement() + " Element");

        String[] skills = temp.getSkillNames();

        skill1.setText((skills.length > 0 ? skills[0] : "-"));
        skill2.setText((skills.length > 1 ? skills[1] : "-"));
        skill3.setText((skills.length > 2 ? skills[2] : "-"));
    }


    private JLabel createPreviewLabel(String text, int size) {
        JLabel label = new JLabel(text);
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Times New Roman", Font.BOLD, size));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        return label;
    }

    private ImageIcon getCharacterImageIcon(String path) {
        return new ImageIcon(
                new ImageIcon(
                        Objects.requireNonNull(getClass().getResource(path))
                ).getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH)
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

