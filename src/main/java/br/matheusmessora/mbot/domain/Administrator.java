package br.matheusmessora.mbot.domain;

import br.matheusmessora.mbot.discord.DiscordServer;
import br.matheusmessora.mbot.domain.author.Author;
import br.matheusmessora.mbot.domain.author.DiscordAuthor;
import br.matheusmessora.mbot.domain.message.MessageReceived;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cin_mmessora on 5/30/17.
 */
@Service
public class Administrator {

    private List<Author> admins;
    private String displayName;

    public void admin(DiscordServer discordServer) {
        final DiscordAuthor marmita = new DiscordAuthor(discordServer.client().getUsersByName("MarmitaGames").get(0), discordServer);
        final DiscordAuthor rafa = new DiscordAuthor(discordServer.client().getUsersByName("RafaSantos").get(0), discordServer);
        this.admins = new ArrayList<>();
        this.admins.add(marmita);
        this.admins.add(rafa);

        this.displayName = marmita.displayName();
    }

    public String displayName() {
        return displayName;
    }

    public boolean sentbyAdmin(MessageReceived received) {
        for (Author admin : admins) {
            if(received.getAuthor().equals(admin)){
                return true;
            }
        }
        return false;
    }

    public Author getAuthor() {
        return admins.get(0);
    }
}
