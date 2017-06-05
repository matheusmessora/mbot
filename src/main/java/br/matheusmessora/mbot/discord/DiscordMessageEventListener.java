package br.matheusmessora.mbot.discord;

import br.matheusmessora.mbot.domain.message.MessageReceived;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

/**
 * Created by cin_mmessora on 5/30/17.
 */
@Service
public class DiscordMessageEventListener {

    @Autowired
    private ApplicationEventPublisher publisher;

    @Autowired
    private DiscordServer discordServer;

    @EventSubscriber
    public void onMessage(MessageReceivedEvent event){
        publisher.publishEvent(new MessageReceived(event, discordServer));
    }
}
