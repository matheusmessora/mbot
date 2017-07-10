package br.matheusmessora.cards.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cin_mmessora call 7/10/17.
 */
public class CardList {

    List<Card> cards;

    public CardList() {
        cards = new ArrayList<>();
    }

    public int size() {
        return cards.size();
    }

    public Card pop() {
        return pop(0);
    }

    public Card pop(int index) {
        return cards.remove(index);
    }

    public Card pop(Card card) {
        cards.remove(card);
        return card;
    }

    public void push(Card card) {
        cards.add(card);
    }

    public Card last() {
        try {
            return cards.get(cards.size() - 1);
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }

    public Card get(int slotIndex) {
        try {
            return cards.get(slotIndex);
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }
}
