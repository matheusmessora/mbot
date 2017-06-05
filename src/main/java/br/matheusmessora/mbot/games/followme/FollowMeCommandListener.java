package br.matheusmessora.mbot.games.followme;

import br.matheusmessora.mbot.domain.Administrator;
import br.matheusmessora.mbot.domain.MessageSender;
import br.matheusmessora.mbot.domain.command.Command;
import br.matheusmessora.mbot.domain.message.MessageReceived;
import br.matheusmessora.mbot.events.AuthorParticipationEvent;
import br.matheusmessora.mbot.games.Events;
import br.matheusmessora.mbot.games.MessageReceivedListener;
import br.matheusmessora.mbot.games.currency.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

/**
 * Created by cin_mmessora on 6/2/17.
 */
@Service
public class FollowMeCommandListener implements MessageReceivedListener {

    @Autowired
    private CurrencyService service;

    @Autowired
    private Administrator admin;

    @Autowired
    private MessageSender sender;

    @Autowired
    private ApplicationEventPublisher publisher;

    private Command START_COMMAND = new Command("mestre");
    private Command NEXT_COMMAND = new Command("mestre next");
    private Command RESET_COMMAND = new Command("mestre reset");
    private Optional<LocalDateTime> startedAt;

    private Optional<Command> selectedCommand;
    private Command[] orders = {
            new Command("estatua"),
            new Command("feijoada"),
            new Command("MarmitaGames"),
            new Command("temaki"),
            new Command("macarronada"),
            new Command("esfiha")

    };
    private Random random = new Random();

    @PostConstruct
    public void init() {
        startedAt = Optional.empty();
        random = new Random();
        selectedCommand = Optional.empty();
    }

    @EventListener
    public void handle(MessageReceived event){
        if(isON()){
            if(START_COMMAND.match(event) && admin.sentbyAdmin(event)){
                final LocalDateTime now = LocalDateTime.now();
                startedAt = Optional.of(now);
                sender.send("Hora de **O Mestre Mandou**! Fique atento no chat e repita o comando. O mais rápido ganhará sapos de chocolate.");
            }else if(isJoinable()){
                if(NEXT_COMMAND.match(event) && admin.sentbyAdmin(event)){
                    final int i = random.nextInt(orders.length);
                    selectedCommand = Optional.of(orders[i]);
                    sender.send("Mestre MANDOU... **!" + selectedCommand.get().value() + "** , seja o mais rápido e repita o comando! VAMOS");
                }else {
                    selectedCommand.ifPresent(command -> {
                        if(command.match(event)){
                            sender.send(event.getAuthor().mention() + " foi o mais rápido!");
                            publisher.publishEvent(new AuthorParticipationEvent(event.getAuthor(), 1));
                            selectedCommand = Optional.empty();
                        }
                    });
                }
            }
        }
    }

    @EventListener
    public void handleReset(MessageReceived event){
        if(isON()){
            if(admin.sentbyAdmin(event) && RESET_COMMAND.match(event.getMessage())){
                reset();
            }
        }
    }

    private void reset() {
        selectedCommand = Optional.empty();
        startedAt = Optional.empty();
    }

    private boolean isJoinable() {
        if(startedAt.isPresent()){
            return true;
        }
        return false;
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
