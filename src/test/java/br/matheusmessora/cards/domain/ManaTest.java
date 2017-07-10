package br.matheusmessora.cards.domain;

import br.matheusmessora.cards.exception.InsufficientMana;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by cin_mmessora call 7/10/17.
 */
public class ManaTest {

    private Game game;

    @Before
    public void init() {
        game = new Game();
        game.start();
    }

    @Test
    public void shouldConsumeManaWhenSummon() {
        game.currentPlayer().summonCard(0);
        Assert.assertEquals(0, game.currentPlayer().manaPool().available());
    }

    @Test(expected = InsufficientMana.class)
    public void shouldThrowErrorWhenManaCostHigherThanAvailable() {
        game.currentPlayer().summonCard(0);
        game.currentPlayer().summonCard(0);
    }


}