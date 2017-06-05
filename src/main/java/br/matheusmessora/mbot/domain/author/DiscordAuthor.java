package br.matheusmessora.mbot.domain.author;

import br.matheusmessora.mbot.discord.DiscordServer;
import sx.blah.discord.handle.obj.IPrivateChannel;
import sx.blah.discord.handle.obj.IUser;

/**
 * Created by cin_mmessora on 6/2/17.
 */
public class DiscordAuthor implements Author {

    private final IUser user;
    private final Long uid;
    private String nickname;
    private DiscordServer discordServer;

    private IPrivateChannel privateChannel;

    public DiscordAuthor(IUser user, DiscordServer discordServer){
        this.discordServer = discordServer;
        this.nickname = null;
        this.user = user;
        this.uid = user.getLongID();
        this.privateChannel = user.getOrCreatePMChannel();
    }

    @Override
    public Long uid() {
        return uid;
    }

    @Override
    public String displayName() {
        if(nickname == null){
            this.nickname = user.getDisplayName(discordServer.guild());
        }
        return nickname;
    }

    @Override
    public String mention() {
        return user.mention();
    }

    @Override
    public IPrivateChannel getPrivateChannel() {
        return privateChannel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DiscordAuthor that = (DiscordAuthor) o;

        return uid != null ? uid.equals(that.uid) : that.uid == null;
    }

    @Override
    public int hashCode() {
        return uid != null ? uid.hashCode() : 0;
    }
}
