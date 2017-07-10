package br.matheusmessora.mbot.games.feedme;

import br.matheusmessora.mbot.domain.Administrator;
import br.matheusmessora.mbot.domain.MessageSender;
import br.matheusmessora.mbot.domain.command.Command;
import br.matheusmessora.mbot.domain.message.MessageReceived;
import br.matheusmessora.mbot.events.AuthorParticipationEvent;
import br.matheusmessora.mbot.events.PunishimentEvent;
import br.matheusmessora.mbot.games.BaseGameEvent;
import br.matheusmessora.mbot.games.Events;
import br.matheusmessora.mbot.games.GameEvent;
import br.matheusmessora.mbot.games.MessageReceivedListener;
import br.matheusmessora.mbot.games.currency.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Random;

/**
 * Created by cin_mmessora on 6/2/17.
 */
@Service
public class FeedmeCommandListener extends BaseGameEvent implements MessageReceivedListener, GameEvent {

    @Autowired
    private CurrencyService service;

    @Autowired
    private Administrator admin;

    @Autowired
    private MessageSender sender;

    @Autowired
    private ApplicationEventPublisher publisher;

    private Command START_COMMAND = new Command("feedme");
    private Command NEXT_COMMAND = new Command("feedme next");
    private Command RESET_COMMAND = new Command("feedme reset");
    private int counts = 0;
    private Command[] orders = {
            new Command("agua"),
            new Command("suco"),
            new Command("refrigerante"),

    };
    private Random random = new Random();


    @PostConstruct
    public void init() {
        random = new Random();
    }

    @EventListener
    public void handle(MessageReceived event){
        if(isON()){
            if(START_COMMAND.match(event) && admin.sentbyAdmin(event)){
                startEvent();
            }else if(isJoinable()){
                if(NEXT_COMMAND.match(event) && admin.sentbyAdmin(event)){
                    nextPhase();
                }else {
                    commandReceived(event);
                }
            }
        }
    }

    private synchronized void commandReceived(MessageReceived event) {
        for (Command order : orders) {
            if(order.match(event)){
                sender.send(event.getAuthor().mention() + " ufa, obrigada. Estava morrendo de sede :kiss:!");
                publisher.publishEvent(new AuthorParticipationEvent(event.getAuthor(), 3));
                publisher.publishEvent(new PunishimentEvent(2));
                if(counts > 0){
                    closeEvent();
                }
            }
        }
    }

    @EventListener
    public void handleReset(MessageReceived event){
        if(isON()){
            if(admin.sentbyAdmin(event) && RESET_COMMAND.match(event.getMessage())){
                closeEvent();
            }
        }
    }

    private boolean isJoinable() {
        if(started){
            return true;
        }
        return false;
    }

    @Override
    public boolean startEvent() {
        started = true;
        counts = 0;
        sender.send("*Marmitinhas, estou com sede... Envie !suco, !agua ou !refrigerante para mim. Rapido!*");
        nextPhase();
        return true;
    }

    @Override
    public boolean closeEvent() {
        started = false;
        counts = 0;
        sender.send("*Estou satisfeita, por enquanto... :yum:*");
        return true;
    }

    @Override
    public boolean nextPhase() {
        if(started){
            counts++;
            return true;
        }
        return false;
    }

    @Override
    public Events eventType() {
        return Events.FEEDME;
    }

    @Override
    public boolean isON() {
        return eventType().isActive();
    }

}
