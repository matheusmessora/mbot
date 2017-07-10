package br.matheusmessora.mbot.games;

/**
 * Created by cin_mmessora on 6/6/17.
 */
public interface GameEvent {

    boolean startEvent();

    boolean closeEvent();

    boolean nextPhase();

    Events eventType();
}
