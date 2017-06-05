package br.matheusmessora.mbot.games.currency;

import br.matheusmessora.mbot.domain.MessageSender;
import br.matheusmessora.mbot.domain.author.Author;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by cin_mmessora on 6/2/17.
 */
@Service
public class CurrencyService {

    @Autowired
    private MessageSender messageSender;

    @Autowired
    private CurrencyRepository repository;

    @Autowired
    private MongoCurrencyRepository mongoCurrencyRepository;

    public List<Currency> findAll() {
        final List<Currency> all = mongoCurrencyRepository.findAll(new Sort(Sort.Direction.DESC, "balance"));

        return all;
    }

    public Integer getBalance(Author author) {
        return getOne(author).getBalance();
    }

    public void increase(Author author, int amount) {
        Currency currency = getOne(author);
        currency.setBalance(currency.getBalance() + amount);
        mongoCurrencyRepository.save(currency);
        messageSender.sendPM(author, "Você acaba de ganhar " + amount + " sapos de chocolate. Digite !chocolate para saber o seu total.");
    }

    private Currency getOne(Author author) {
        final Currency one = mongoCurrencyRepository.findOne(author.uid());
        if (one == null) {
            mongoCurrencyRepository.save(new Currency(author, 0));
        }
        return mongoCurrencyRepository.findOne(author.uid());
    }

    public void cleanUp() {
        mongoCurrencyRepository.deleteAll();
    }
}
