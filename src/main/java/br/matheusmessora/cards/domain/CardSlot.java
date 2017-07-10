package br.matheusmessora.cards.domain;

/**
 * Created by cin_mmessora call 7/10/17.
 */
public enum CardSlot {

    ONE(0),
    TWO(1),
    THREE(2),
    FOUR(3);

    private final int index;

    CardSlot(int index) {
        this.index = index;
    }

    public int index() {
        return index;
    }
}
