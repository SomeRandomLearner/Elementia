package scenes;

public class CharacterInfo {
    private String name;
    private String storyline;
    private String[] skills;
    private String[] skillDescriptions;
    private String imagePath;
    private String[] skillImages;
    private String folderName;

    public CharacterInfo(
            String name,
            String storyline,
            String[] skills,
            String[] skillDescriptions,
            String imagePath,
            String[] skillImages,
            String folderName
    ) {
        this.name = name;
        this.storyline = storyline;
        this.skills = skills;
        this.skillDescriptions = skillDescriptions;
        this.imagePath = imagePath;
        this.skillImages = skillImages;
        this.folderName = folderName;
    }

    public String getName() {
        return name;
    }

    public String getStoryline() {
        return storyline;
    }

    public String[] getSkills() {
        return skills;
    }

    public String[] getSkillDescriptions() {
        return skillDescriptions;
    }

    public String getImagePath() {
        return imagePath;
    }

    public String[] getSkillImages() {
        return skillImages;
    }

    public String getFolderName() {
        return folderName;
    }
}

