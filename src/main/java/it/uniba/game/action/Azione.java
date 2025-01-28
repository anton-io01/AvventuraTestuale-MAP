package it.uniba.game.action;

import it.uniba.game.entity.Giocatore;
import java.util.List;

public interface Azione {
    /**
     * Esegue l'azione specificata dal giocatore.
     *
     * @param giocatore Il giocatore che esegue l'azione.
     * @param parametri Una lista di parametri per l'azione. (es. direzione o nome dell'oggetto).
     * @return Una stringa che descrive il risultato dell'azione.
     */
    void esegui(Giocatore giocatore, List<String> parametri);
}
