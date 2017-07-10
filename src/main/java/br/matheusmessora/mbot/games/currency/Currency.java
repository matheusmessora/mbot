package br.matheusmessora.mbot.games.currency;

import org.springframework.data.annotation.Id;

import br.matheusmessora.mbot.domain.author.Author;

/**
 * Created by cin_mmessora on 6/5/17.
 */
public class Currency {

    public Currency() {
    }

    public Currency(Author author, Integer balance) {
        this.id = author.uid();
        this.author = author.displayName();
        this.balance = balance;
    }

    @Id
    private Long id;

    private String author;

    private Integer balance;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getBalance() {
        return balance;
    }

    public void decrease(int quantity){
        balance -= quantity;
        if(balance < 0){
            balance = 0;
        }
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
