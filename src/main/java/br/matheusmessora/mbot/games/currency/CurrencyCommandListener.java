package br.matheusmessora.mbot.games.currency;

import br.matheusmessora.mbot.domain.Administrator;
import br.matheusmessora.mbot.domain.MessageSender;
import br.matheusmessora.mbot.domain.command.Command;
import br.matheusmessora.mbot.domain.message.MessageReceived;
import br.matheusmessora.mbot.events.AuthorParticipationEvent;
import br.matheusmessora.mbot.games.Events;
import br.matheusmessora.mbot.games.MessageReceivedListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by cin_mmessora on 6/2/17.
 */
@Service
public class CurrencyCommandListener implements MessageReceivedListener {

    @Autowired
    private CurrencyService service;

    @Autowired
    private MessageSender sender;

    @Autowired
    private Administrator admin;

    private Command CHECK_COMMAND = new Command("chocolate");
    private Command RANKING_COMMAND = new Command("chocolate ranking");
    private Command CLEANUP_COMMAND = new Command("chocolate cleanup");

    @EventListener
    public void handle(MessageReceived event){
        if(isON()){
            task(event);
        }
    }

    @EventListener
    public void eventParticipation(AuthorParticipationEvent event) {
        if(isON()){
            service.increase(event.getAuthor(), event.getAmount());
        }
    }

    private void task(MessageReceived event) {
        if(CHECK_COMMAND.match(event)){
            final Integer balance = service.getBalance(event.getAuthor());
            sender.sendPM(event.getAuthor(), "Você possui **" + balance + "** sapos de chocolate. Participe dos eventos para ganhar mais e troque por prêmios!");
        }else if (RANKING_COMMAND.match(event) && admin.sentbyAdmin(event)){
            final List<Currency> currencies = service.findAll();
            final Currency first = currencies.get(0);
            final Currency second = currencies.get(1);
            final Currency third = currencies.get(2);
            StringBuilder sb = new StringBuilder();
            sb.append("1º lugar: " + first.getAuthor() + " - " + first.getBalance() + " sapos de chocolate\n");
            sb.append("2º lugar: " + second.getAuthor() + " - " + second.getBalance() + " sapos de chocolate\n");
            sb.append("3º lugar: " + third.getAuthor() + " - " + third.getBalance() + " sapos de chocolate");
            sender.send(sb.toString());
        }else if(CLEANUP_COMMAND.match(event) && admin.sentbyAdmin(event)){
            service.cleanUp();
        }
    }

    @Override
    public Events eventType() {
        return Events.CURRENCY;
    }

    @Override
    public boolean isON() {
        return true;
    }

}
