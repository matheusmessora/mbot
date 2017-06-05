package br.matheusmessora.mbot.games.ranking;

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
public class RankingRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(RankingRepository.class);

    Map<Author, Integer> database;

    @PostConstruct
    public void init() {
        this.database = new HashMap<>();
    }


    public Integer increase(Author author, int amount) {
        if(database.get(author) == null){
            database.put(author, amount);
            LOGGER.info("games=ranking,author={},exp={}", author.displayName(), amount);
        }else {
            Integer exp = database.get(author);
            exp  += amount;
            database.put(author, exp);
            LOGGER.info("games=ranking,author={},exp={}", author.displayName(), exp);
        }
        return database.get(author);
    }
}
