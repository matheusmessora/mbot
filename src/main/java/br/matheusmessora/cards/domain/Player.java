package br.matheusmessora.cards.domain;

import br.matheusmessora.cards.events.DeathEvent;
import br.matheusmessora.cards.traits.Live;
import com.google.common.eventbus.Subscribe;

/**
 * Created by cin_mmessora call 7/10/17.
 */
public class Player implements Live {

    private Integer life;
    private Deck deck;
    private CardList hand;
    private PlayerField field;
    private ManaPool manaPool;

    public Player() {
        life = 20;
        manaPool = new ManaPool();
        hand = new CardList();
        deck = new Deck(this);
        field = new PlayerField();
    }

    public CardList hand() {
        return hand;
    }

    public void start() {
        life = 20;
        for (int i = 1; i <= 5; i++) {
            hand.push(deck.draw());
        }
    }

    public Deck deck() {
        return deck;
    }

    public void summonCard(int handIndex) {
        final Card card = hand.pop(handIndex);
        manaPool.consume(card);
        this.field.summon(card);
    }


    public Card field(int slotIndex) {
        try {
            return field.slots().get(slotIndex);
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }

    @Override
    public Integer life() {
        return life;
    }

    @Override
    public void receiveDamage(Integer damage) {
        this.life -= damage;
    }

    @Subscribe
    public void on(DeathEvent deathEvent){
        if(deathEvent.getTarget().owner() == this){
            field.destroyCard(deathEvent.getTarget());
        }
    }

    public CardList graveyard() {
        return field.graveyard();
    }

    public ManaPool manaPool() {
        return manaPool;
    }

    public void beginTurn() {
        field.beginTurn();
    }
}
