package br.matheusmessora.mbot.games.badWords;

import br.matheusmessora.mbot.domain.Administrator;
import br.matheusmessora.mbot.domain.MessageSender;
import br.matheusmessora.mbot.domain.message.MessageReceived;
import br.matheusmessora.mbot.games.Events;
import br.matheusmessora.mbot.games.MessageReceivedListener;
import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Set;

/**
 * Created by cin_mmessora on 5/30/17.
 */
@Service
public class BadWordsListener implements MessageReceivedListener {

    @Autowired
    private MessageSender sender;

    @Autowired
    private Administrator admin;

    @EventListener
    public void handle(MessageReceived event){
        task(event);
    }

    private static Set<String> words;

    @PostConstruct
    public void init(){
        words = Sets.newHashSet("chupa", "caralho", "caraio", "porra");
    }

    private void task(MessageReceived event) {
        final String message = event.getMessage().toLowerCase();
        for (String word : words) {
            if(message.contains(word)){
                sender.send("*Marmitinhas :hamburger: , vamos maneirar no uso de palavrões!*");
            }
        }
    }

    public Events eventType() {
        return Events.BAD_WORDS;
    }
}
