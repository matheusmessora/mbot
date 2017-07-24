package br.matheusmessora.cards.domain;

import br.matheusmessora.cards.commands.AttackPlayer;
import br.matheusmessora.cards.commands.AttackScala;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by cin_mmessora call 7/10/17.
 */
public class GameTest {

    private Game game;

    @Before
    public void init() {
        game = new Game();
        game.start();
    }

    @Test
    public void startGame() {
        assertEquals(5, game.currentPlayer().hand().size());
        assertEquals(25, game.currentPlayer().deck().size());
        assertEquals(5, game.enemy().hand().size());
        assertEquals(25, game.enemy().deck().size());
    }

    @Test
    public void firstPLayerSummonCard() {
        final Player firstPlayer = game.currentPlayer();
        firstPlayer.summonCard(0);
        Card card = game.currentPlayer().field(0);
        assertNotNull(card);

        game.endTurn();
        assertNotEquals(game.currentPlayer(), firstPlayer);
    }

    @Test
    public void tiredStatusShouldResetAfterRound() {
        final Player firstPlayer = game.currentPlayer();
        firstPlayer.summonCard(0);
        game.call(new AttackPlayer(0, game.enemy()));
        game.endTurn(); // blue passes

        game.endTurn(); // red passes
        game.call(new AttackPlayer(0, game.enemy()));
    }

    @Test
    public void redPlayerSummonCard() {
        game.endTurn();
        game.currentPlayer().summonCard(0);
        Card card = game.currentPlayer().field(0);
        assertNotNull(card);
    }

}