package it.uniba.game.action;

import it.uniba.game.entity.Giocatore;
import it.uniba.game.entity.Stanza;
import it.uniba.game.entity.Edificio;
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
        Edificio edificio;
        // Controlla se la direzione è valida
        if (parametri == null || parametri.isEmpty()) {
            return "Devi specificare una direzione valida.\n\n";
        }

        String direzione = azioneDAO.getNomeAzioneById(parametri.get(0));
        Stanza stanzaAttuale = giocatore.getPosizioneAttuale();

        // Ottiene la stanza di arrivo per la direzione specificata
        Stanza stanzaDiArrivo = movimentoDAO.getStanzaDiArrivo(stanzaAttuale.getStanzaId(), direzione);

        if (stanzaDiArrivo != null) {
            if(!stanzaAttuale.getEdificioId().equals(stanzaDiArrivo.getEdificioId())) {
                edificio = stanzaDAO.getEdificioByStanza(stanzaDiArrivo.getStanzaId());
                giocatore.setPosizioneAttuale(stanzaDiArrivo);
                return edificio.getDescrizione() + "\n\n" +
                        "Ti sei spostato verso " + direzione + " nella stanza: " + stanzaDiArrivo.getNome() + ".\n" + stanzaDAO.getDescrizioneCompleta(stanzaDiArrivo.getStanzaId()) + "\n\n";
            }
            // Aggiorna la posizione del giocatore
            giocatore.setPosizioneAttuale(stanzaDiArrivo);
            if (stanzaDiArrivo.getStanzaId().equals("01") || stanzaDiArrivo.getStanzaId().equals("15") || stanzaDiArrivo.getStanzaId().equals("22")) {
                edificio = stanzaDAO.getEdificioByStanza(stanzaDiArrivo.getStanzaId());
                String descrizione = "Ti sei spostato verso " + direzione + " all'esterno dell'edificio " + edificio.getNome() + ".\n" + movimentoDAO.getMovimentiByStanza(stanzaDiArrivo) + "\n";
                return descrizione;
            } else {
                return "Ti sei spostato verso " + direzione + " nella stanza: " + stanzaDiArrivo.getNome() + ".\n" + stanzaDAO.getDescrizioneCompleta(stanzaDiArrivo.getStanzaId()) + "\n\n";
            }
        } else {
            return "Non puoi andare in quella direzione.\n\n";
        }
    }
}