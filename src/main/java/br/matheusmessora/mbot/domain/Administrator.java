package br.matheusmessora.mbot.domain;

import br.matheusmessora.mbot.discord.DiscordServer;
import br.matheusmessora.mbot.domain.author.Author;
import br.matheusmessora.mbot.domain.author.DiscordAuthor;
import br.matheusmessora.mbot.domain.message.MessageReceived;
import org.springframework.stereotype.Service;

/**
 * Created by cin_mmessora on 5/30/17.
 */
@Service
public class Administrator {

    private Author author;
    private String displayName;

    public void admin(DiscordServer discordServer) {
        this.author = new DiscordAuthor(discordServer.client().getUsersByName("*").get(0), discordServer);
//        this.author = new DiscordAuthor(discordServer.client().getUsersByName("_Cond_").get(0), discordServer);
        this.displayName = author.displayName();
    }

    public String displayName() {
        return displayName;
    }

    public boolean sentbyAdmin(MessageReceived received) {
        return received.getAuthor().equals(author);
    }

    public Author getAuthor() {
        return author;
    }
}
