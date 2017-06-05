package br.matheusmessora.mbot.domain.message;

import br.matheusmessora.mbot.discord.DiscordServer;
import br.matheusmessora.mbot.domain.author.Author;
import br.matheusmessora.mbot.domain.author.DiscordAuthor;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

/**
 * Created by cin_mmessora on 6/2/17.
 */
public class MessageReceived {

    private final Author author;
    private final String message;

    public MessageReceived(MessageReceivedEvent event, DiscordServer discordServer){
        this.author = new DiscordAuthor(event.getAuthor(), discordServer);
        this.message = event.getMessage().getContent();
    }

    public String getMessage() {
        return message;
    }

    public Author getAuthor() {
        return author;
    }
}
