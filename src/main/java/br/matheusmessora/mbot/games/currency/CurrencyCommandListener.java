package br.matheusmessora.mbot.games.currency;

import br.matheusmessora.mbot.domain.Administrator;
import br.matheusmessora.mbot.domain.MessageSender;
import br.matheusmessora.mbot.domain.command.Command;
import br.matheusmessora.mbot.domain.message.MessageReceived;
import br.matheusmessora.mbot.events.AuthorParticipationEvent;
import br.matheusmessora.mbot.events.PunishimentEvent;
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
        task(event);
    }

    @EventListener
    public void eventParticipation(AuthorParticipationEvent event) {
        service.increase(event.getAuthor(), event.getAmount());
    }

    @EventListener
    public void eventParticipation(PunishimentEvent event) {
        final List<Currency> all = service.findAll();
        for (Currency currency : all) {
            currency.decrease(event.getAmount());
        }
        service.saveAll(all);
    }

    private void task(MessageReceived event) {
        if(CHECK_COMMAND.match(event)){
            final Integer balance = service.getBalance(event.getAuthor());
            sender.sendPM(event.getAuthor(), "Você possui **" + balance + "** sapos de chocolate :chocolate_bar: . Participe dos eventos para ganhar mais e troque por prêmios!");
        }else if (RANKING_COMMAND.match(event) && admin.sentbyAdmin(event)){
            final List<Currency> currencies = service.findAll();
            final Currency first = currencies.get(0);
            final Currency second = currencies.get(1);
            final Currency third = currencies.get(2);
            StringBuilder sb = new StringBuilder();
            sb.append(":first_place: " + first.getAuthor() + " - " + first.getBalance() + " sapos de chocolate :chocolate_bar: \n");
            sb.append(":second_place: " + second.getAuthor() + " - " + second.getBalance() + " sapos de chocolate :chocolate_bar: \n");
            sb.append(":third_place: " + third.getAuthor() + " - " + third.getBalance() + " sapos de chocolate :chocolate_bar: ");
            sender.send(sb.toString());
        }else if(CLEANUP_COMMAND.match(event) && admin.sentbyAdmin(event)){
            service.cleanUp();
        }
    }

    public Events eventType() {
        return Events.CURRENCY;
    }

}
