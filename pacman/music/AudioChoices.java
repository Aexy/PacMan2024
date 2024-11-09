package pacman.music;

public enum AudioChoices {
    NO_MUSIC(0,""),
    PACMAN_THEME(1, "sources/8bit.wav"),
    SURPRISE_THEME(2, "sources/Next-Steps-half.cool.wav"),
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
