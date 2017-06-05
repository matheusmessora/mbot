package br.matheusmessora.mbot.games.currency;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by cin_mmessora on 6/2/17.
 */
public interface MongoCurrencyRepository extends MongoRepository<Currency, Long> {

}
