package br.matheusmessora.mbot.games.currency;

import br.matheusmessora.mbot.domain.author.Author;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by cin_mmessora on 6/2/17.
 */
@Service
public class CurrencyRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(CurrencyRepository.class);

    Map<Author, Integer> database;

    @PostConstruct
    public void init() {
        this.database = new HashMap<>();
    }


    public Integer findByAuthor(Author author) {
        final Integer integer = database.get(author);
        if(integer != null){
            return integer;
        }
        return 0;
    }

    public void save(Author author, int balance) {
        database.put(author, balance);
        LOGGER.info("games=currency,author={},balance={}", author.displayName(), balance);
    }
}
