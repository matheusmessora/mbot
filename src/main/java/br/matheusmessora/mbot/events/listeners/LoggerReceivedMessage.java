package br.matheusmessora.mbot.events.listeners;

import br.matheusmessora.mbot.domain.message.MessageReceived;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

/**
 * Created by cin_mmessora on 6/2/17.
 */
@Service
public class LoggerReceivedMessage {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoggerReceivedMessage.class);

    @EventListener
    public void handle(MessageReceived event){
        LOGGER.info("event=receivedMessage,author=" + event.getAuthor().displayName() + ",data=" + event.getMessage());
    }



}
