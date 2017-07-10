package br.matheusmessora.cards.domain;

/**
 * Created by cin_mmessora call 7/10/17.
 */
public class Deck {

    CardList cards;

    public Deck(Player player) {
        this.cards = new CardList();
        for (int i = 1; i <= 30; i++) {
            cards.push(new Card(player));
        }
    }



    public Card draw() {
        return cards.pop();
    }

    public int size() {
        return cards.size();
    }
}
