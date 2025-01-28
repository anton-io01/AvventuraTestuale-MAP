package it.uniba.game.action;

import it.uniba.game.entity.Giocatore;
import it.uniba.game.entity.Stanza;
import it.uniba.game.database.dao.MovimentoDAO;
import it.uniba.game.database.DatabaseManager;
import java.util.List;

public class AzioneMovimento {
    private MovimentoDAO movimentoDAO; // DAO per i movimenti

    public AzioneMovimento() {}

    public void movimento(Giocatore giocatore, List<String> parametri) {
        // Controlla se la direzione è valida
        if (parametri == null || parametri.isEmpty()) {
            System.out.println("Devi specificare una direzione valida.");
        }

        String direzione = parametri.get(0).toLowerCase();
        Stanza stanzaAttuale = giocatore.getPosizioneAttuale();

        // Ottiene la stanza di arrivo per la direzione specificata
        Stanza stanzaDiArrivo = movimentoDAO.getStanzaDiArrivo(stanzaAttuale.getStanzaId(), direzione);

        if (stanzaDiArrivo != null) {
            // Aggiorna la posizione del giocatore
            giocatore.setPosizioneAttuale(stanzaDiArrivo);
            System.out.println("Ti sei spostato verso " + direzione + " nella stanza: " + stanzaDiArrivo.getNome() + ".\n" + movimentoDAO.getMovimentiByStanza(giocatore.getPosizioneAttuale()));
        } else {
            System.out.println("Non puoi andare in quella direzione.");
        }
    }
}
