package br.matheusmessora.mbot.games.pokemon;

import br.matheusmessora.mbot.domain.Administrator;
import br.matheusmessora.mbot.domain.MessageSender;
import br.matheusmessora.mbot.domain.author.Author;
import br.matheusmessora.mbot.domain.command.Command;
import br.matheusmessora.mbot.domain.message.MessageReceived;
import br.matheusmessora.mbot.events.AuthorParticipationEvent;
import br.matheusmessora.mbot.games.BaseGameEvent;
import br.matheusmessora.mbot.games.Events;
import br.matheusmessora.mbot.games.GameEvent;
import br.matheusmessora.mbot.games.MessageReceivedListener;
import br.matheusmessora.mbot.games.currency.CurrencyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import static sx.blah.discord.util.MessageBuilder.Styles.ITALICS;

/**
 * Created by cin_mmessora on 6/2/17.
 */
@Service
public class PokemonCommandListener extends BaseGameEvent implements MessageReceivedListener, GameEvent {

    private static final Logger LOGGER = LoggerFactory.getLogger(PokemonCommandListener.class);

    @Autowired
    private CurrencyService service;

    @Autowired
    private Administrator admin;

    @Autowired
    private MessageSender sender;

    @Autowired
    private ApplicationEventPublisher publisher;

    @Autowired
    private CurrencyService currencyService;

    private Command START_COMMAND = new Command("pokemon");
    private Command NEXT_COMMAND = new Command("pokemon next");
    private Command RESET_COMMAND = new Command("pokemon reset");
    private Command THROW_BALL_COMMAND = new Command("pokebola");
    private Command THROW_ULTRABALL_COMMAND = new Command("ultraball");

    private Random random = new Random();
    private boolean joinable;
    private Optional<Pokemons> selectedPokemon;
    private Map<Author, LocalTime> balls;
    private int counts = 0;

    @PostConstruct
    public void init() {
        joinable = false;
        balls = new HashMap<>();
        random = new Random();
        setSelectedPokemon(null);
    }

    @EventListener
    public void handle(MessageReceived event) {
        if (isON()) {
            if(isJoinable()){
                selectedPokemon.ifPresent(pokemon -> {
                    if(THROW_BALL_COMMAND.match(event)){
                        currencyService.decrease(event.getAuthor(), 1);
                        throwBall(event, pokemon, false);
                    }
                    if(THROW_ULTRABALL_COMMAND.match(event)){
                        currencyService.decrease(event.getAuthor(), 3);
                        throwBall(event, pokemon, true);
                    }
                });
            }
        }
    }

    @EventListener
    public void handleAdmin(MessageReceived event){
        if(isON()){
            if(admin.sentbyAdmin(event)){
                if(START_COMMAND.match(event)){
                    startEvent();
                }
                if(RESET_COMMAND.match(event.getMessage())){
                    closeEvent();
                }
                if(NEXT_COMMAND.match(event) && isJoinable()){
                    nextPhase();
                }
            }
        }
    }

    private synchronized void throwBall(MessageReceived event, Pokemons pokemon, boolean isUltra) {
        final LocalTime lastThrowBall = Optional.ofNullable(balls.get(event.getAuthor())).orElse(LocalTime.MIN);
        final LocalTime now = LocalTime.now();
        if(lastThrowBall.plusSeconds(3).isBefore(now)){
            double luck = Math.random();
            if(isUltra){
                luck += Math.random() + 0.10;
            }
            if(luck >= pokemon.getChance()){
                sender.send("*" + pokemon.getName() + " foi capturado por " + event.getAuthor().mention() + "*");
                LOGGER.info("event=pokemon,luck=" + luck + ",pokemon=" + pokemon.getName() + ",author=" + event.getAuthor().displayName());
                publisher.publishEvent(new AuthorParticipationEvent(event.getAuthor(), pokemon.getExp()));
                setSelectedPokemon(null);
                if(counts > 3){
                    closeEvent();
                }
            }else {
                LOGGER.info("event=pokemon,luck=" + luck + ",pokemon=" + pokemon.getName() + ",author=" + event.getAuthor().displayName());
                if(pokemon.equals(Pokemons.SNORLAX) || pokemon.equals(Pokemons.Megazord)){
                    sender.send("*" + pokemon.getCode() + " solta sua barrigada!!! " + event.getAuthor().displayName() + " perde 3 sapos de chocolate!*");
                    currencyService.decrease(event.getAuthor(), 3);
                }else {
                    sender.send("*" + event.getAuthor().displayName() + " joga uma pokebola mas erra o alvo :(. Aguarde alguns segundos e tente novamente.*");
                }
            }
            balls.put(event.getAuthor(), now);
        }
    }

    private boolean isJoinable() {
        if(joinable){
            return true;
        }
        return false;
    }

    @Override
    public boolean startEvent() {
        joinable = true;
        started = true;
        balls = new HashMap<>();
        sender.send("*Sinto uma presença de Pokemons por perto. Fique atento no chat! A pokebola custa 1 sapo de chocolate. A ultraball custa 3, mas ela aumenta em 10% suas chances de capturar.*");
        return true;
    }

    @Override
    public boolean closeEvent() {
        counts = 0;
        joinable = false;
        setSelectedPokemon(null);
        sender.send(ITALICS + "Não sinto mais a presença dos Pokemons, acho que foram embora.... por enquanto!" + ITALICS);
        started = false;
        balls = new HashMap<>();
        return true;
    }

    @Override
    public boolean nextPhase() {
        if(!getSelectedPokemon().isPresent() && started && counts <= 3){
            final int i = random.nextInt(Pokemons.values().length);
            final Pokemons newPokemon = Pokemons.values()[i];
            setSelectedPokemon(newPokemon);
            sender.send("*Um **" + newPokemon.getCode() + "** selvagem acabou de aparecer no chat! Envie o comando **!pokebola** para tentar captura-lo.*");
            counts++;
            return true;
        }
        return false;
    }

    private synchronized void setSelectedPokemon(Pokemons pokemon){
        this.selectedPokemon = Optional.ofNullable(pokemon);
    }

    private synchronized Optional<Pokemons> getSelectedPokemon(){
        return this.selectedPokemon;
    }

    @Override
    public Events eventType() {
        return Events.POKEMON;
    }

    @Override
    public boolean isON() {
        return eventType().isActive();
    }

}
