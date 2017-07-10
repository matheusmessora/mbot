package br.matheusmessora.mbot.domain.command;

import br.matheusmessora.mbot.domain.message.MessageReceived;
import sx.blah.discord.handle.obj.IMessage;

/**
 * Created by cin_mmessora on 5/30/17.
 */
public class Command {

    private final String command;

    public Command(String command) {
        this.command = command;
    }

    public String value() {
        return command;
    }

    public boolean match(String content) {
        return content.equalsIgnoreCase("!" + command);
    }

    public boolean match(IMessage message) {
        return message.getContent().equalsIgnoreCase("!" + command);
    }

    public boolean match(MessageReceived event) {
        return event.getMessage().equalsIgnoreCase("!" + command);
    }

    public boolean matchWithValue(MessageReceived event) {
        final String command = event.getMessage().split(" ", 2)[0];
        return command.equalsIgnoreCase("!" + this.command);
    }
}
