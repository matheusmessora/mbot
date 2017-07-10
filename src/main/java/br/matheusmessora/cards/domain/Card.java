package br.matheusmessora.cards.domain;

import br.matheusmessora.cards.exception.TiredMonster;
import br.matheusmessora.cards.traits.Live;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by cin_mmessora call 7/10/17.
 */
public class Card implements Live {

    private Integer manaCost;
    private Integer power;
    private Integer life;
    private final Player owner;

    private Set<CardAttribute> attrs;

    public Card(Player owner) {
        this.owner = owner;
        attrs = new HashSet<>();
        power = 1;
        life = 1;
        manaCost = 1;
    }

    public Integer power() {
        return power;
    }

    @Override
    public Integer life() {
        return life;
    }

    @Override
    public void receiveDamage(Integer damage) {
        this.life -= damage;
    }

    public boolean isDestroyed() {
        return life == 0;
    }

    public Player owner() {
        return owner;
    }

    public Integer manaCost() {
        return manaCost;
    }

    private boolean canAttack() {
        return !attrs.contains(CardAttribute.TIRED);
    }

    public void attack() {
        if(!canAttack()){
            throw new TiredMonster();
        }
        attrs.add(CardAttribute.TIRED);
    }

    public void clear(CardAttribute attr) {
        attrs.remove(attr);
    }
}
