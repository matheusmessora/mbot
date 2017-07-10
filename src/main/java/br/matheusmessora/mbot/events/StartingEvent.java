package br.matheusmessora.mbot.events;

import br.matheusmessora.mbot.games.Events;

/**
 * Created by cin_mmessora on 6/6/17.
 */
public class StartingEvent {

    private Events event;

    public StartingEvent(Events event) {
        super();
        this.event = event;
    }

    public Events getEvent() {
        return event;
    }
}
