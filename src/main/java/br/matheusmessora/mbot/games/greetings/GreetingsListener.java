package br.matheusmessora.mbot.games.greetings;

import br.matheusmessora.mbot.discord.DiscordServer;
import br.matheusmessora.mbot.domain.Administrator;
import br.matheusmessora.mbot.domain.MessageSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.ReadyEvent;
import sx.blah.discord.handle.obj.IChannel;

import java.util.List;

/**
 * Created by cin_mmessora on 5/30/17.
 */
@Service
public class GreetingsListener {

    @Autowired
    private DiscordServer discordServer;

    @Autowired
    private MessageSender messageSender;

    @Autowired
    private Administrator administrator;

    @EventSubscriber
    public void onMessage(ReadyEvent event){
        discordServer.runGuild();

        administrator.admin(discordServer);

        final List<IChannel> channels = discordServer.client().getChannels();
        for (IChannel channel : channels) {
//            if(channel.getName().equalsIgnoreCase("escolinha-do-bot")) {
            if(channel.getName().equalsIgnoreCase("bate-papo")) {
                discordServer.mainChannel(channel);
                messageSender.sendPM(administrator.getAuthor(), "", "**Bot** se apresentando para o serviço. " +
                        "\n\n!chocolate: Mostra a quantidade de sapos de chocolate que voce possui" +
                        "\n!pokebola: Lança uma Pokebola (só funciona no evento de Pokemon)" +
                        "\n\nOs sapos de chocolate podem ser usados da seguinte forma: " +
                        "\nEu ainda não sei!");
            }
        }
    }

}
