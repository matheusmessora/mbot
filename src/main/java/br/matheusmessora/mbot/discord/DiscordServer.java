package br.matheusmessora.mbot.discord;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;

/**
 * Created by cin_mmessora on 5/30/17.
 */
@Service
public class DiscordServer {

    @Autowired
    private IDiscordClient client;
    private IGuild guild;

    private IChannel channel;

    public static long GUILD_ID = 301534962430246922L;

    public void mainChannel(IChannel channel) {
        this.channel = channel;
    }

    public IDiscordClient client() {
        return client;
    }

    public IChannel channel() {
        return channel;
    }

    public IGuild guild() {
        return guild;
    }

    public void runGuild() {
        this.guild = client.getGuildByID(GUILD_ID);
    }
}
