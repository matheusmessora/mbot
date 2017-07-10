package br.matheusmessora.mbot.games.reply;

import br.matheusmessora.mbot.domain.Administrator;
import br.matheusmessora.mbot.domain.MessageSender;
import br.matheusmessora.mbot.domain.command.Command;
import br.matheusmessora.mbot.domain.message.MessageReceived;
import br.matheusmessora.mbot.games.Events;
import br.matheusmessora.mbot.games.MessageReceivedListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

/**
 * Created by cin_mmessora on 5/30/17.
 */
@Service
public class ReplyListener  implements MessageReceivedListener {

    @Autowired
    private MessageSender sender;

    @Autowired
    private Administrator admin;

    private Command SAY_COMMAND = new Command("say");

    @EventListener
    public void handle(MessageReceived event){
        task(event);
    }

    private void task(MessageReceived event) {
        if(SAY_COMMAND.matchWithValue(event) && admin.sentbyAdmin(event)){
            final String[] split = event.getMessage().split(" ", 2);
            sender.send(split[1]);
        }
    }

    public Events eventType() {
        return Events.REPLY;
    }
}
