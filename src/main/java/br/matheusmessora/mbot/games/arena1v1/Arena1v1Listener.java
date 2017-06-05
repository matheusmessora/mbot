package br.matheusmessora.mbot.games.arena1v1;

import br.matheusmessora.mbot.domain.message.MessageReceived;
import br.matheusmessora.mbot.games.Events;
import br.matheusmessora.mbot.games.MessageReceivedListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

/**
 * Created by cin_mmessora on 5/30/17.
 */
@Service
public class Arena1v1Listener implements MessageReceivedListener {

    @Autowired
    private Arena1v1Service service;

    @EventListener
    public void handle(MessageReceived event){
        if(isON()){
            task(event);
        }
    }


    public void task(MessageReceived event){
        service.doTask(event);
    }

    @Override
    public Events eventType() {
        return Events.AREA_1v1;
    }

    @Override
    public boolean isON() {
        return true;
    }

}
