package br.matheusmessora.mbot.schedulers;

import br.matheusmessora.mbot.events.NextPhaseEvent;
import br.matheusmessora.mbot.events.StartingEvent;
import br.matheusmessora.mbot.games.Events;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Created by cin_mmessora on 6/6/17.
 */
@Service
public class EventManagerScheduler {

    private static final Logger LOGGER = LoggerFactory.getLogger(EventManagerScheduler.class);

    private LocalTime lastEventHour;
    private Events actualEvent;
    private List<Events> events = new LinkedList<>();
    private Random random;

    @Autowired
    private ApplicationEventPublisher publisher;

    @PostConstruct
    public void init(){
        this.events = new ArrayList<>();
        final Events[] values = Events.values();
        this.events.addAll(Arrays.asList(values));

        this.random = new Random();
        this.actualEvent = Events.POKEMON;
        this.lastEventHour = LocalTime.now();
    }

    @Scheduled(cron="0 0/15 * * * *")
    public void startingEvent() {
        final LocalTime now = LocalTime.now();
        if(canHaveEvents(now)){
            Events event = Events.POKEMON;

            while(event.equals(actualEvent) || !event.isActive()){
                final int index = random.nextInt(events.size());
                event = this.events.get(index);
            }
            this.actualEvent = event;
            LOGGER.info("scheduled=running,event=" + actualEvent);

            this.lastEventHour = now;
            publisher.publishEvent(new StartingEvent(event));
        }
    }

    private boolean canHaveEvents(LocalTime now) {
        final boolean onlyOneEventPerHour = lastEventHour.getHour() != now.getHour();
        final boolean onlyAfternoon = now.getHour() > 12 && now.getHour() <= 23;
        LOGGER.info("scheduled=running,now=" + now + ",lastEvent=" + lastEventHour);
        return onlyOneEventPerHour && onlyAfternoon;
    }

    @Scheduled(cron="0 * * * * *")
    public void nextMove() {
        publisher.publishEvent(new NextPhaseEvent(actualEvent));
    }
}
