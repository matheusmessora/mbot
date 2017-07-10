package br.matheusmessora.mbot.events;

import br.matheusmessora.mbot.games.Events;

/**
 * Created by cin_mmessora on 6/6/17.
 */
public class NextPhaseEvent {

    private Events event;

    public NextPhaseEvent(Events event) {
        super();
        this.event = event;
    }

    public Events getEvent() {
        return event;
    }
}
