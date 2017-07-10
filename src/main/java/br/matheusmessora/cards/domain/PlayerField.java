package br.matheusmessora.cards.domain;

/**
 * Created by cin_mmessora call 7/10/17.
 */
public class PlayerField {

    private CardList slots;
    private CardList graveyard;

    public PlayerField() {
        slots = new CardList();
        graveyard = new CardList();
    }

    public CardList slots() {
        return slots;
    }

    public void destroyCard(Card card){
        slots.pop(card);
        graveyard.push(card);
    }

    public void summon(Card card) {
        this.slots.push(card);
    }

    public CardList graveyard() {
        return graveyard;
    }

    public void beginTurn() {
        for (Card card : slots.cards) {
            card.clear(CardAttribute.TIRED);
        }
    }
}
