package br.matheusmessora.cards.domain;

import br.matheusmessora.cards.exception.InsufficientMana;

/**
 * Created by cin_mmessora on 7/10/17.
 */
public class ManaPool {

    private int max;
    private int available;

    public ManaPool() {
        this.max = 1;
        this.available = 1;
    }

    public int available() {
        return available;
    }

    public void consume(Card card) {
        if(card.manaCost() > this.available){
            throw new InsufficientMana();
        }
        this.available -= card.manaCost();
    }
}
