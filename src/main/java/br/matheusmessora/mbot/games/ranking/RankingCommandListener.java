package br.matheusmessora.mbot.games.ranking;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import br.matheusmessora.mbot.domain.message.MessageReceived;
import br.matheusmessora.mbot.events.AuthorParticipationEvent;
import br.matheusmessora.mbot.games.Events;
import br.matheusmessora.mbot.games.MessageReceivedListener;

/**
 * Created by cin_mmessora on 6/2/17.
 */
@Service
public class RankingCommandListener implements MessageReceivedListener {

    @Autowired
    private RankingService service;

    @EventListener
    public void handle(MessageReceived event){
        if(isON()){
            task(event);
        }
    }

    @EventListener
    public void eventParticipation(AuthorParticipationEvent event) {
        if(isON()){
            service.giveExp(event.getAuthor(), 10);
        }
    }

    private void task(MessageReceived event) {

    }

    @Override
    public Events eventType() {
        return Events.RANKING;
    }

    @Override
    public boolean isON() {
        return false;
    }

}
