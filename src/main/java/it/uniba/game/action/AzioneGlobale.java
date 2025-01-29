package it.uniba.game.action;

import it.uniba.game.database.dao.MovimentoDAO;
import it.uniba.game.entity.Giocatore;
import it.uniba.game.database.dao.StanzaDAO;

import java.util.List;
import java.util.ArrayList;

public class AzioneGlobale {
    public StanzaDAO stanzaDAO = new StanzaDAO();
    public MovimentoDAO movimentoDAO = new MovimentoDAO();

    public AzioneGlobale() {}

    public List<String> inventario(Giocatore giocatore, List<String> parametri) {
        if (giocatore.getInventario().isEmpty()) {
            List<String> listaInventario = new ArrayList<>();
            listaInventario.add("L'inventario è vuoto.");
            return listaInventario;
        } else {
            List<String> listaInventario = new ArrayList<>();
            for (int i = 0; i < giocatore.getInventario().size(); i++) {
                listaInventario.add(giocatore.getInventario().get(i).getNome());
            }
            return listaInventario;
        }
    }

    public String esci(Giocatore giocatore, List<String> parametri) {
        System.out.println("Arrivederci!");
        System.exit(0);
        return null;
    }

    public String salva(Giocatore giocatore, List<String> parametri) {
        System.out.println("Salvataggio...");
        System.out.println("Salvataggio completato.");
        return null;
    }

    public String osserva(Giocatore giocatore, List<String> parametri) {
        return stanzaDAO.getDescrizioneCompleta(giocatore.getPosizioneAttualeId()) + "\n\n";
    }

    public String entra(Giocatore giocatore, List<String> parametri) {
        return null;
    }

    /**
     * Mostra l'elenco dei comandi disponibili.
     * @param giocatore
     * @param parametri
     * @return
     */
    public String aiuto(Giocatore giocatore, List<String> parametri) {
        return "Comandi Disponibili:\n" +
                "- mappa (per visualizzare la mappa)\n" +
                "- inventario (per visualizzare l'inventario)\n" +
                "- salva (per salvare la partita)\n" +
                "- esci (per uscire dal gioco)\n" +
                "- nord, sud, est, ovest, alto, basso (per muoverti)\n" +
                "- osserva (per mostrare gli oggetti all'interno della stanza)\n" +
                "- esamina [oggetto] (per esaminare un oggetto)\n" +
                "- prendi [oggetto] (per prendere un oggetto)\n" +
                "- lascia [oggetto] (per lasciare un oggetto)\n" +
                "- usa [oggetto] (per utilizzare un oggetto)\n" +
                "- apri [oggetto] (per aprire un armadio o una porta)\n" +
                "- chiudi [oggetto] (per chiudere un armadio o una porta)\n" +
                "- parla [oggetto] (per parlare con un personaggio)\n" +
                "- leggi [oggetto] (per leggere un libro o un appunto)\n\n";
    }

    /**
     * Mostra la mappa del gioco.
     *
     * @param giocatore
     * @param parametri
     * @return
     */
    public String mappa(Giocatore giocatore, List<String> parametri) {
        return movimentoDAO.getMovimentiByStanza(giocatore.getPosizioneAttuale()) + "\n\n";
    }
}