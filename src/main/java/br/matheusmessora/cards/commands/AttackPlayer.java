package br.matheusmessora.cards.commands;

import br.matheusmessora.cards.domain.Player;

/**
 * Created by cin_mmessora call 7/10/17.
 */
public class AttackPlayer implements ICommand {

    private int fieldIndex;
    private Player target;

    public AttackPlayer(int fieldIndex, Player target) {
        this.fieldIndex = fieldIndex;
        this.target = target;
    }

    public int fieldIndex() {
        return fieldIndex;
    }

    public Player target() {
        return target;
    }
}
