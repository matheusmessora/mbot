package br.matheusmessora.mbot.games.arena1v1;

import br.matheusmessora.mbot.domain.MessageSender;
import br.matheusmessora.mbot.domain.author.Author;
import br.matheusmessora.mbot.domain.message.MessageReceived;
import br.matheusmessora.mbot.events.AuthorParticipationEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by cin_mmessora on 5/30/17.
 */
public class Battle {

    private static final Logger LOGGER = LoggerFactory.getLogger(Battle.class);

    private Random random;


    private Author playerOne;
    private Author playerTwo;
    private MessageSender messageSender;
    private ApplicationEventPublisher publisher;
    private int playerOnePower;
    private int playerTwoPower;
    private boolean finished;

    private Map<String, Integer> powers;

    public Battle(Author playerOne, Author playerTwo, MessageSender messageSender, ApplicationEventPublisher publisher) {
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
        this.messageSender = messageSender;
        this.publisher = publisher;
        playerOnePower = 0;
        playerTwoPower = 0;
        this.powers = new HashMap<>(4);
        this.random = new Random();

        publisher.publishEvent(new AuthorParticipationEvent(playerOne));
        publisher.publishEvent(new AuthorParticipationEvent(playerTwo));
    }

    public void start() {
        messageSender.send("Duelo entre " + playerOne.displayName() + " x " + playerTwo.displayName() + "\n Jogadores: digitem seu poder (fogo, agua, terra, ar) no chat.");

        powers.put("fogo", random.nextInt(100) + 1);
        powers.put("agua", random.nextInt(100) + 1);
        powers.put("terra", random.nextInt(100) + 1);
        powers.put("ar", random.nextInt(100) + 1);
        LOGGER.info("games=arena1v1,step=start,skills=" + powers);
    }

    private boolean checkSkill(String content) {
        return content.equalsIgnoreCase("fogo") ||
                content.equalsIgnoreCase("ar") ||
                content.equalsIgnoreCase("terra") ||
                content.equalsIgnoreCase("agua");
    }

    public boolean isFinished() {
        return finished;
    }

    public void callWinner() {
        if(playerOnePower > playerTwoPower){
            messageSender.send("O poder de " + playerOne.displayName() + " (" + playerOnePower + ") é muito mais forte do que " + playerTwo.displayName() + " (" + playerTwoPower + "). Vítoria de " + playerOne.mention());
            publisher.publishEvent(new AuthorParticipationEvent(playerOne, 3));
        }else {
            messageSender.send("O poder de " + playerTwo.displayName() + " (" + playerTwoPower + ") é muito mais forte do que " + playerOne.displayName() + " (" + playerOnePower + "). Vítoria de " + playerTwo.mention());
            publisher.publishEvent(new AuthorParticipationEvent(playerTwo, 3));
        }

        LOGGER.info("games=arena1v1,step=finish,skills=" + powers);
    }

    public void power(MessageReceived received) {
        final Author author = received.getAuthor();
        final String skill = received.getMessage();

        Integer power = powers.get(skill.toLowerCase());
        final int bonusPower = random.nextInt(20) + 1;
        final int finalPower = power +(int) Math.floor(bonusPower / 2.34);
        LOGGER.info("games=arena1v1,step=skill,normalPower=" + power + ",bonus=" + bonusPower + ",finalPower=" + finalPower);
        if(author.equals(playerOne)){
            LOGGER.info("games=arena1v1,step=skill,power=" + finalPower + ",skill=" + skill + ",player=one");
            playerOnePower = finalPower;
        }
        if(author.equals(playerTwo)){
            LOGGER.info("games=arena1v1,step=skill,power=" + finalPower + ",skill=" + skill + ",player=two");
            playerTwoPower = finalPower;
        }

        if(playerOnePower != 0 && playerTwoPower != 0){
            this.finished = true;
        }
    }
}
