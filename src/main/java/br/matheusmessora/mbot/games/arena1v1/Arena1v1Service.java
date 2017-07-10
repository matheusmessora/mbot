package br.matheusmessora.mbot.games.arena1v1;

import br.matheusmessora.mbot.discord.DiscordServer;
import br.matheusmessora.mbot.domain.Administrator;
import br.matheusmessora.mbot.domain.MessageSender;
import br.matheusmessora.mbot.domain.author.Author;
import br.matheusmessora.mbot.domain.command.Command;
import br.matheusmessora.mbot.domain.message.MessageReceived;
import br.matheusmessora.mbot.games.BaseGameEvent;
import br.matheusmessora.mbot.games.Events;
import br.matheusmessora.mbot.games.GameEvent;
import br.matheusmessora.mbot.games.MessageReceivedListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

/**
 * Created by cin_mmessora on 5/30/17.
 */
@Service
public class Arena1v1Service extends BaseGameEvent implements GameEvent, MessageReceivedListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(Arena1v1Service.class);
    public static final int SECONDS_TO_JOIN = 25;

    @Autowired
    private MessageSender messageSender;

    @Autowired
    private DiscordServer discordServer;

    @Autowired
    private Administrator admin;

    @Autowired
    private BattleService battleService;

    private String JOIN_MESSAGE = "*Hora do duelo!!! Entre na arena e participe do x1 do Marmita. Digite **!participar** para participar. \n Você tem 3 minutos para participar.*";
    private Command START_COMMAND = new Command("arena");
    private Command RESET_COMMAND = new Command("arena reset");
    private Command FIGHT_COMMAND = new Command("arena on");
    private Command JOIN_COMMAND = new Command("participar");

    private Set<Author> users;
    private List<Function<MessageReceived, Boolean>> steps;

    private boolean joinable;
    private int counts = 0;

    @PostConstruct
    public void init() {
        closeEvent();

        steps = new ArrayList<>();
        steps.add(startEventFunction());
        steps.add(joinFunction());
        steps.add(battleFunction());
        steps.add(skillFunction());
        steps.add(resetFunction());
    }

    public void doTask(MessageReceived event) {
        for (Function<MessageReceived,Boolean> step : steps) {
            if(step.apply(event)){
                break;
            }
        }

        if(started && !joinable && !battleService.hasMoreBattles()){
            closeEvent();
        }
    }

    private Function<MessageReceived, Boolean> startEventFunction() {
        return (MessageReceived received) -> {
            if(admin.sentbyAdmin(received) && START_COMMAND.match(received.getMessage())){
                return startEvent();
            }
            return false;
        };
    }

    public boolean startEvent() {
        messageSender.send(JOIN_MESSAGE);
        LOGGER.info("games=arena1v1,step=start");
        joinable = true;
        started = true;
        counts = 0;
        return true;
    }

    @Override
    public boolean closeEvent() {
        users = new HashSet<>();
        joinable = false;
        battleService.reset();
        LOGGER.info("games=arena1v1,step=close");
        return true;
    }

    @Override
    public boolean nextPhase() {
        counts++;
        if(started && joinable && counts >= 3){
            LOGGER.info("games=arena1v1,step=battle,batltes=" + battleService.battles);
            battleService.start();
            joinable = false;
            return true;
        }
        return false;
    }

    @Override
    public Events eventType() {
        return Events.AREA_1v1;
    }

    private Function<MessageReceived, Boolean> skillFunction() {
        return (MessageReceived received) -> {
            if(started && battleService.isStarted()){
                if(battleService.skill(received)){
                    return true;
                }
            }
            return false;
        };
    }

    private Function<MessageReceived, Boolean> resetFunction() {
        return (MessageReceived received) -> {
            if(admin.sentbyAdmin(received) && RESET_COMMAND.match(received.getMessage())){
                closeEvent();
            }
            return false;
        };
    }

    private boolean isJoinable() {
        return joinable;
    }

    private Function<MessageReceived, Boolean> joinFunction() {
        return (MessageReceived received) -> {
            if(isJoinable()){
                if(JOIN_COMMAND.match(received.getMessage())){
                    final Author author = received.getAuthor();
                    if(users.add(author)) {
                        battleService.add(author);
                        messageSender.send("*" + author.displayName() + " está participando da Arena 1x1.*");
                        LOGGER.info("games=arena1v1,step=join,player=" + author.displayName());
                    }
                    return true;
                }
            }
            return false;
        };
    }

    private Function<MessageReceived, Boolean> battleFunction() {
        return (MessageReceived received) -> {

            if(isJoinable() && !battleService.isStarted()){
                if(admin.sentbyAdmin(received) && FIGHT_COMMAND.match(received.getMessage())) {
                    nextPhase();
                }
            }
            return false;
        };
    }


    private boolean checkSkill(String content) {
        return content.equalsIgnoreCase("fogo") ||
                content.equalsIgnoreCase("ar") ||
                content.equalsIgnoreCase("terra") ||
                content.equalsIgnoreCase("agua");
    }

    @Override
    @EventListener
    public void handle(MessageReceived event) {
        if(true){
            doTask(event);
        }
    }
}
