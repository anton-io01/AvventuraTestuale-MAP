package it.uniba.game.action;

import it.uniba.game.entity.Giocatore;

public abstract class AzioneBase implements Azione {
    protected Giocatore giocatore;

    public AzioneBase(Giocatore giocatore) {
        this.giocatore = giocatore;
    }

    public Giocatore getGiocatore() {
        return giocatore;
    }
}