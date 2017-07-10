package br.matheusmessora.cards.domain;

import br.matheusmessora.cards.commands.AttackMinion;
import br.matheusmessora.cards.commands.AttackPlayer;
import br.matheusmessora.cards.events.DeathEvent;
import br.matheusmessora.cards.traits.Live;
import com.google.common.eventbus.EventBus;

/**
 * Created by cin_mmessora call 7/10/17.
 */
public class Game {

    private final EventBus event;
    private Player blue;
    private Player red;

    private Player current;

    public Game() {
        this.event = new EventBus();
        blue = new Player();
        red = new Player();

        this.event.register(blue);
        this.event.register(red);

    }

    public void start() {
        current = blue;
        blue.start();
        red.start();
    }

    public Player currentPlayer() {
        return current;
    }

    public void endTurn() {
        if(currentPlayer().equals(blue)){
            current = red;
        }else {
            current = blue;
        }
        currentPlayer().beginTurn();
    }

    public Player enemy() {
        if(currentPlayer().equals(blue)){
            return red;
        }
        return blue;
    }

    public void call(AttackMinion attackMinion){
        final Card attacker = currentPlayer().field(attackMinion.fieldIndex());
        final Card target = attackMinion.target();
        target.receiveDamage(attacker.power());
        if(target.isDestroyed()){
            event.post(new DeathEvent(target));
        }
    }

    public void call(AttackPlayer attackMinion){
        final Card attacker = currentPlayer().field(attackMinion.fieldIndex());
        final Live target = attackMinion.target();

        attacker.attack();
        target.receiveDamage(attacker.power());
    }

}
