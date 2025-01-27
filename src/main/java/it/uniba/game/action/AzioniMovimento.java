package it.uniba.game.action;

import it.uniba.game.entity.Giocatore;
import it.uniba.game.entity.Stanza;
import it.uniba.game.database.dao.MovimentoDAO;
import it.uniba.game.database.DatabaseManager;
import java.util.List;
import java.util.Locale;

public class AzioniMovimento extends AzioneBase {
    private DatabaseManager databaseManager; // Gestore del database per recuperare i movimenti disponibili

    public AzioniMovimento(Giocatore giocatore) {
        super(giocatore);
    }

    @Override
    public String esegui(Giocatore giocatore, List<String> parametri) {
        // Controlla se la direzione è valida
        if (parametri == null || parametri.isEmpty()) {
            return "Devi specificare una direzione valida.";
        }

        String direzione = parametri.get(0).toLowerCase();
        Stanza stanzaAttuale = giocatore.getPosizioneAttuale();
        String stanzaId = stanzaAttuale.getStanzaId();

        // Recupera l'istanza del DAO per i movimenti
        MovimentoDAO movimentoDAO = DatabaseManager.getMovimentoDAO();

        // Ottiene la stanza di arrivo per la direzione specificata
        Stanza stanzaDiArrivo = movimentoDAO.getStanzaDiArrivo(stanzaId, direzione);

        if (stanzaDiArrivo != null) {
            // Aggiorna la posizione del giocatore
            giocatore.setPosizioneAttuale(stanzaDiArrivo);
            return "Ti sei spostato verso " + direzione + " nella stanza: " + stanzaDiArrivo.getNome() + ".";
        } else {
            return "Non puoi andare in quella direzione.";
        }
    }
}
