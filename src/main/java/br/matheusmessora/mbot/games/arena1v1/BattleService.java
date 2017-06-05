package br.matheusmessora.mbot.games.arena1v1;

import br.matheusmessora.mbot.discord.DiscordServer;
import br.matheusmessora.mbot.domain.MessageSender;
import br.matheusmessora.mbot.domain.author.Author;
import br.matheusmessora.mbot.domain.message.MessageReceived;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by cin_mmessora on 5/31/17.
 */
@Service
public class BattleService {
    private static final Logger LOGGER = LoggerFactory.getLogger(BattleService.class);

    @Autowired
    private MessageSender messageSender;

    @Autowired
    private DiscordServer server;

    @Autowired
    private ApplicationEventPublisher publisher;

    private List<Author> users;

    List<Battle> battles;
    private boolean started;

    @PostConstruct
    public void init() {
        this.users = new ArrayList<>();
        this.battles = new ArrayList<>();
    }

    public void add(Author user) {
        this.users.add(user);

        if(this.users.size() == 2){
            battles.add(new Battle(users.get(0), users.get(1), messageSender, publisher));
            this.users.clear();
        }
    }


    public void start() {
        this.started = true;

        battle();
    }

    private void battle() {
        if(battles.size() > 0){
            battles.get(0).start();
        }
    }

    public boolean skill(MessageReceived received){
        final String content = received.getMessage();
        if(checkSkill(content) && hasMoreBattles()){
            final Battle battle = battles.get(0);
            battle.power(received);

            if(battle.isFinished()){
                battle.callWinner();
                battles.remove(battle);
                battle();
            }
            return true;
        }
        return false;
    }

    public boolean isStarted() {
        return started;
    }

    public boolean hasMoreBattles() {
        return started && battles.size() > 0;
    }

    private boolean checkSkill(String content) {
        return content.equalsIgnoreCase("fogo") ||
                content.equalsIgnoreCase("ar") ||
                content.equalsIgnoreCase("terra") ||
                content.equalsIgnoreCase("agua");
    }

    public void reset() {
        this.started = false;
        this.users = new ArrayList<>();
        this.battles = new ArrayList<>();
    }
}

