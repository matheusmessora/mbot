package br.matheusmessora.cards.domain;

import br.matheusmessora.cards.commands.AttackMinion;
import br.matheusmessora.cards.commands.AttackPlayer;
import br.matheusmessora.cards.exception.TiredMonster;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Created by cin_mmessora call 7/10/17.
 */
public class AttackTest {

    private Game game;

    @Before
    public void init() {
        game = new Game();
        game.start();
    }

    @Test
    public void firstPlayerShouldAttackEnemyHero() {
        game.currentPlayer().summonCard(0);
        game.endTurn(); // blue player passes
        game.endTurn(); // red player passes
        final Player target = game.enemy();

        game.call(new AttackPlayer(0, target));
        assertEquals(Integer.valueOf(19), target.life());
    }

    @Test
    public void secondPlayerShouldAttackEnemyHero() {
        game.endTurn(); // blue player passes
        game.currentPlayer().summonCard(0);
        final Player target = game.enemy();
        game.call(new AttackPlayer(0, target));
        assertEquals(Integer.valueOf(19), target.life());
    }

    @Test
    public void minionShouldBeAbleToAttackOtherMinon() {
        game.currentPlayer().summonCard(0);
        game.endTurn(); // blue player passes

        game.currentPlayer().summonCard(0);
        game.endTurn(); // red player passes

        final Card target = game.enemy().field(0);
        game.call(new AttackMinion(0, target));
        assertEquals(Integer.valueOf(0), target.life());
        assertEquals(Integer.valueOf(20), game.enemy().life());
    }

    @Test(expected = TiredMonster.class)
    public void minionShouldNotBeAbleToAttackAgain() {
        game.currentPlayer().summonCard(0);
        game.endTurn(); // blue player passes

        game.endTurn(); // red player passes

        game.call(new AttackPlayer(0, game.enemy()));
        game.call(new AttackPlayer(0, game.enemy()));
    }

    @Test
    public void minionShouldBeDestroyed() {
        game.currentPlayer().summonCard(0);
        game.endTurn(); // blue player passes

        game.currentPlayer().summonCard(0);
        game.endTurn(); // red player passes

        final Card target = game.enemy().field(0);
        game.call(new AttackMinion(0, target));
        assertNull(game.enemy().field(0));
        final Card last = game.enemy().graveyard().last();
        assertEquals(target, last);
    }

}