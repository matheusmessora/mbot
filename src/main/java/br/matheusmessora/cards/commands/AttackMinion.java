package br.matheusmessora.cards.commands;

import br.matheusmessora.cards.domain.Card;

/**
 * Created by cin_mmessora call 7/10/17.
 */
public class AttackMinion implements Command {

    private int fieldIndex;
    private Card target;

    public AttackMinion(int fieldIndex, Card target) {
        this.fieldIndex = fieldIndex;
        this.target = target;
    }

    public int fieldIndex() {
        return fieldIndex;
    }

    public Card target() {
        return target;
    }
}
