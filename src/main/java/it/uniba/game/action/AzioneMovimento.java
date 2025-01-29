package it.uniba.game.action;

import it.uniba.game.entity.Giocatore;
import it.uniba.game.entity.Stanza;
import it.uniba.game.database.dao.MovimentoDAO;
import it.uniba.game.database.dao.AzioneDAO;
import it.uniba.game.database.dao.StanzaDAO;

import java.util.List;

public class AzioneMovimento {
    public MovimentoDAO movimentoDAO = new MovimentoDAO(); // DAO per i movimenti
    public AzioneDAO azioneDAO = new AzioneDAO(); // DAO per le azioni
    public StanzaDAO stanzaDAO = new StanzaDAO(); // DAO per le stanze

    public AzioneMovimento() {}

    public String movimento(Giocatore giocatore, List<String> parametri) {
        // Controlla se la direzione è valida
        if (parametri == null || parametri.isEmpty()) {
            return "Devi specificare una direzione valida.\n\n";
        }

        String direzione = azioneDAO.getNomeAzioneById(parametri.get(0));
        Stanza stanzaAttuale = giocatore.getPosizioneAttuale();

        // Ottiene la stanza di arrivo per la direzione specificata
        Stanza stanzaDiArrivo = movimentoDAO.getStanzaDiArrivo(stanzaAttuale.getStanzaId(), direzione);

        if (stanzaDiArrivo != null) {
            // Aggiorna la posizione del giocatore
            giocatore.setPosizioneAttuale(stanzaDiArrivo);
            return "Ti sei spostato verso " + direzione + " nella stanza: " + stanzaDiArrivo.getNome() + ".\n" + stanzaDAO.getDescrizioneBreve(stanzaDiArrivo.getStanzaId()) + "\n\n";
        } else {
            return "Non puoi andare in quella direzione.\n\n";
        }
    }
}