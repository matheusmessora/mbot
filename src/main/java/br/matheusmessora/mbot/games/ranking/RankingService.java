package br.matheusmessora.mbot.games.ranking;

import br.matheusmessora.mbot.domain.MessageSender;
import br.matheusmessora.mbot.domain.author.Author;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by cin_mmessora on 6/2/17.
 */
@Service
public class RankingService  {

    @Autowired
    private MessageSender messageSender;

    @Autowired
    private RankingRepository rankingRepository;

    private int[] levelCaps = {40,100,300};

    public void giveExp(Author author, int exp) {
        final Integer authorExp = rankingRepository.increase(author, exp);
        if(authorExp >= levelCaps[2]){
            messageSender.send(author.mention() + ", você acabou de atingir level 4! Parabens :D");
        }else if(authorExp >= levelCaps[1]){
            messageSender.send(author.mention() + ", você acabou de atingir level 3! Parabens :D");
        }else if(authorExp >= levelCaps[0]){
            messageSender.send(author.mention() + ", você acabou de atingir level 2! Parabens :D");
        }
    }


}
