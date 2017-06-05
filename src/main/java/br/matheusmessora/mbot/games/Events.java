package br.matheusmessora.mbot.games;

/**
 * Created by cin_mmessora on 5/30/17.
 */
public enum Events {

    AREA_1v1("arena1v1"), RANKING("ranking"), CURRENCY("chocolates");

    private String command;

    Events(String command) {
        this.command = command;
    }

    public String command() {
        return command;
    }
}
