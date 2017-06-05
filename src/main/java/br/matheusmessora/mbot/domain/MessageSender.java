package br.matheusmessora.mbot.domain;

import br.matheusmessora.mbot.discord.DiscordServer;
import br.matheusmessora.mbot.domain.author.Author;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sx.blah.discord.util.MessageBuilder;

/**
 * Created by cin_mmessora on 5/30/17.
 */
@Service
public class MessageSender {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageSender.class);

    @Autowired
    private DiscordServer discordServer;

    public void send(String message){
        LOGGER.info("Sending message. msg='" + message + "'");
        new MessageBuilder(discordServer.client()).withChannel(discordServer.channel()).withContent(message).send();
    }

    public void quote(String message){
        LOGGER.info("Sending quote. msg='" + message + "'");
        new MessageBuilder(discordServer.client()).withChannel(discordServer.channel()).withQuote(message).send();
    }

    public void sendPM(Author author, String message){
        LOGGER.info("Sending PM. author= " + author.displayName() + ",msg='" + message + "'");
        new MessageBuilder(discordServer.client()).withChannel(author.getPrivateChannel()).withContent(message).send();
    }

    public void sendPM(Author author, String message, String quote){
        LOGGER.info("Sending PM QUOTE. author= " + author.displayName() + ",msg='" + message + "'");
        new MessageBuilder(discordServer.client()).withChannel(author.getPrivateChannel()).withContent(message).withQuote(quote).send();
    }

}
