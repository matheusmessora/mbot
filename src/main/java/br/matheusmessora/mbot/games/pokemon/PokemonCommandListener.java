package br.matheusmessora.mbot.games.pokemon;

import br.matheusmessora.mbot.domain.Administrator;
import br.matheusmessora.mbot.domain.MessageSender;
import br.matheusmessora.mbot.domain.command.Command;
import br.matheusmessora.mbot.domain.message.MessageReceived;
import br.matheusmessora.mbot.events.AuthorParticipationEvent;
import br.matheusmessora.mbot.games.Events;
import br.matheusmessora.mbot.games.MessageReceivedListener;
import br.matheusmessora.mbot.games.currency.CurrencyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

import static sx.blah.discord.util.MessageBuilder.Styles.ITALICS;

/**
 * Created by cin_mmessora on 6/2/17.
 */
@Service
public class PokemonCommandListener implements MessageReceivedListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(PokemonCommandListener.class);

    @Autowired
    private CurrencyService service;

    @Autowired
    private Administrator admin;

    @Autowired
    private MessageSender sender;

    @Autowired
    private ApplicationEventPublisher publisher;

    private Command START_COMMAND = new Command("pokemon");
    private Command NEXT_COMMAND = new Command("pokemon next");
    private Command RESET_COMMAND = new Command("pokemon reset");
    private Command THROW_BALL_COMMAND = new Command("pokebola");
    private Optional<LocalDateTime> startedAt;

    private Optional<Pokemons> selectedPokemon;

    private Random random = new Random();

    @PostConstruct
    public void init() {
        startedAt = Optional.empty();
        random = new Random();
        selectedPokemon = Optional.empty();
    }

    @EventListener
    public void handle(MessageReceived event){
        if(isON()){
            if(START_COMMAND.match(event) && admin.sentbyAdmin(event)){
                final LocalDateTime now = LocalDateTime.now();
                startedAt = Optional.of(now);
                sender.send("Sinto uma presença de Pokemons por perto. Fique atento no chat!");
            }else if(isJoinable()){
                if(NEXT_COMMAND.match(event) && admin.sentbyAdmin(event)){
                    final int i = random.nextInt(Pokemons.values().length);
                    selectedPokemon = Optional.of(Pokemons.values()[i]);
                    sender.send("Um **" + selectedPokemon.get().getName() + "** selvagem acabou de aparecer no chat! Envie o comando **!pokebola** para tentar captura-lo.");
                }else {
                    selectedPokemon.ifPresent(command -> {
                        if(THROW_BALL_COMMAND.match(event)){
                            final double luck = Math.random();
                            if(luck >= selectedPokemon.get().getChance()){
                                sender.send(selectedPokemon.get().getName() + " foi capturado por " + event.getAuthor().mention());
                                LOGGER.info("event=pokemon,luck=" + luck + ",pokemon=" + selectedPokemon.get().getName() + ",author=" + event.getAuthor().displayName());
                                publisher.publishEvent(new AuthorParticipationEvent(event.getAuthor(), selectedPokemon.get().getExp()));
                                selectedPokemon = Optional.empty();
                            }else {
                                LOGGER.info("event=pokemon,luck=" + luck + ",pokemon=" + selectedPokemon.get().getName() + ",author=" + event.getAuthor().displayName());
                                sender.send(event.getAuthor().displayName() + " joga uma pokebola mas erra o alvo :(. Tente novamente.");
                            }
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
        selectedPokemon = Optional.empty();
        startedAt = Optional.empty();
        sender.send(ITALICS + "Não sinto mais a presença dos Pokemons, acho que foram embora.... por enquanto!" + ITALICS);
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
