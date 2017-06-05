package br.matheusmessora.mbot.events;

import br.matheusmessora.mbot.domain.author.Author;

/**
 * Created by cin_mmessora on 6/2/17.
 */
public class AuthorParticipationEvent {
    private final int amount;
    private Author author;

    public AuthorParticipationEvent(Author playerOne) {
        this(playerOne, 1);
    }

    public AuthorParticipationEvent(Author author, int amount) {
        this.author = author;
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }

    public Author getAuthor() {
        return author;
    }
}
