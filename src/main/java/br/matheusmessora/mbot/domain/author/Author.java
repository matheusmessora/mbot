package br.matheusmessora.mbot.domain.author;

import sx.blah.discord.handle.obj.IPrivateChannel;

/**
 * Created by cin_mmessora on 6/2/17.
 */
public interface Author {

    Long uid();

    String displayName();

    String mention();

    IPrivateChannel getPrivateChannel();
}
