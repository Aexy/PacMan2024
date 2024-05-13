package ChooseYourMusic;

public enum AudioChoices {
    NO_MUSIC(0,"NO MUSIC = NO PATH"),
    PACMAN_THEME(1, "sources/Next-Steps-half.cool.wav"),
    BEST_THEME(2, "sources/Ricksong.wav"),
    AUDACITY_THEME(3,"sources/audacity.wav"),
    SELF_INPUT(4, "NoPathDefined");


    private final int number;
    private String absolutePath;

    AudioChoices(int n, String s) {
        this.number = n;
        this.absolutePath = s;
    }
    public static AudioChoices getAudioChoiceByNumber(int number) {
        for (AudioChoices choice : AudioChoices.values()) {
            if (choice.getNumber() == number) {
                return choice;
            }
        }
        return NO_MUSIC;
    }

    public int getNumber() {
        return number;
    }

    public String getAbsolutePath() {
        return absolutePath;
    }

    public void setAbsolutePath(String updatedPath){
        this.absolutePath = updatedPath;
    }
}
