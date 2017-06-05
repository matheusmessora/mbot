package br.matheusmessora.mbot.games;

import br.matheusmessora.mbot.domain.message.MessageReceived;

/**
 * Created by cin_mmessora on 5/30/17.
 */
public interface MessageReceivedListener {

    void handle(MessageReceived event);

    Events eventType();

    boolean isON();

}
