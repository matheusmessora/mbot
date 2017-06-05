package br.matheusmessora.mbot.events;

import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

/**
 * Created by cin_mmessora on 6/2/17.
 */
public class ReceivedMessage {


    private final String message;

    public ReceivedMessage(MessageReceivedEvent event) {
        this.message = event.getMessage().getContent();
    }

    public String getMessage() {
        return message;
    }
}
