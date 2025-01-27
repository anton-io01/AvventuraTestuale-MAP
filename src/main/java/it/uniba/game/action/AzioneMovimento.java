package it.uniba.game.action;

import it.uniba.game.entity.Giocatore;
import it.uniba.game.entity.Stanza;
import it.uniba.game.database.dao.MovimentoDAO;
import it.uniba.game.database.DatabaseManager;
import java.util.List;

public class AzioneMovimento extends AzioneBase {
    private final MovimentoDAO movimentoDAO; // DAO per i movimenti

    public AzioneMovimento(Giocatore giocatore, MovimentoDAO movimentoDAO) {
        super(giocatore);
        this.movimentoDAO = movimentoDAO;
    }

    @Override
    public String esegui(Giocatore giocatore, List<String> parametri) {
        // Controlla se la direzione è valida
        if (parametri == null || parametri.isEmpty()) {
            return "Devi specificare una direzione valida.";
        }

        String direzione = parametri.get(0).toLowerCase();
        Stanza stanzaAttuale = giocatore.getPosizioneAttuale();

        // Ottiene la stanza di arrivo per la direzione specificata
        Stanza stanzaDiArrivo = movimentoDAO.getStanzaDiArrivo(stanzaAttuale.getStanzaId(), direzione);

        if (stanzaDiArrivo != null) {
            // Aggiorna la posizione del giocatore
            giocatore.setPosizioneAttuale(stanzaDiArrivo);
            return "Ti sei spostato verso " + direzione + " nella stanza: " + stanzaDiArrivo.getNome() + ".\n" + movimentoDAO.getMovimentiByStanza(giocatore.getPosizioneAttuale());
        } else {
            return "Non puoi andare in quella direzione.";
        }
    }
}
