package br.matheusmessora.mbot.events;

/**
 * Created by cin_mmessora on 6/2/17.
 */
public class PunishimentEvent {
    private final int amount;

    public PunishimentEvent(int amount) {
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }
}
