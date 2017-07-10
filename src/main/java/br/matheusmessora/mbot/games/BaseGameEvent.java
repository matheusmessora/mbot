package br.matheusmessora.mbot.games;

import br.matheusmessora.mbot.events.NextPhaseEvent;
import org.springframework.context.event.EventListener;

import br.matheusmessora.mbot.events.StartingEvent;
import org.springframework.stereotype.Service;

/**
 * Created by cin_mmessora on 6/6/17.
 */
@Service
public abstract class BaseGameEvent implements GameEvent {

    private int globalCount = 0;
    protected boolean started;

    public boolean isON() {
        return eventType().isActive();
    }

    @EventListener
    public void handle(StartingEvent event){
        if(event.getEvent().equals(eventType())){
            startEvent();
            globalCount = 1;
            started = true;
        }
    }

    @EventListener
    public void handle(NextPhaseEvent event){
        if(started && event.getEvent().equals(eventType())){
            nextPhase();
            globalCount++;

            if(globalCount > 10){
                closeEvent();
            }
        }
    }
}
