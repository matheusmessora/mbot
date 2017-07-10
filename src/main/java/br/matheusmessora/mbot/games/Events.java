package br.matheusmessora.mbot.games;

/**
 * Created by cin_mmessora on 5/30/17.
 */
public enum Events {

    AREA_1v1("arena1v1", false),
    RANKING("ranking", false),
    CURRENCY("chocolates", false),
    POKEMON("pokemon", true),
    FOLLOW_ME("mestre", true),
    REPLY("reply", false),
    BAD_WORDS("words", false),
    FEEDME("feedme", true);

    private String command;
    private boolean active;

    Events(String command, boolean active) {
        this.command = command;
        this.active = active;
    }

    public String command() {
        return command;
    }

    public boolean isActive() {
        return active;
    }
}
