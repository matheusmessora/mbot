package br.matheusmessora.cards.events;

import br.matheusmessora.cards.domain.Card;

/**
 * Created by cin_mmessora call 7/10/17.
 */
public class DeathEvent {
    private Card target;

    public DeathEvent(Card target) {
        this.target = target;
    }

    public Card getTarget() {
        return target;
    }
}
