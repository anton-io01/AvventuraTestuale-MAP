package it.uniba.game.action;

import it.uniba.game.database.dao.EdificioDAO;
import it.uniba.game.database.dao.MovimentoDAO;
import it.uniba.game.entity.Edificio;
import it.uniba.game.entity.Giocatore;
import it.uniba.game.database.dao.StanzaDAO;

import java.util.List;


public class AzioneGlobale {
    private final StanzaDAO stanzaDAO = new StanzaDAO();
    private final MovimentoDAO movimentoDAO = new MovimentoDAO();
    private final EdificioDAO edificioDAO = new EdificioDAO();


    public AzioneGlobale() {
    }


    public String inventario(Giocatore giocatore, List<String> parametri) {
        if (giocatore.getInventario().isEmpty()) {
            return "L'inventario è vuoto.\n\n";
        } else {
            StringBuilder inventario = new StringBuilder();
            for (int i = 0; i < giocatore.getInventario().size(); i++) {
                inventario.append(giocatore.getInventario().get(i).getNome()).append("\n");
            }
            return inventario.toString();
        }
    }
    public String esci(Giocatore giocatore, List<String> parametri) {
        System.out.println("Arrivederci!");
        System.exit(0);
        return null;
    }


    public String osserva(Giocatore giocatore, List<String> parametri) {
        return stanzaDAO.getDescrizioneCompleta(giocatore.getPosizioneAttualeId()) + "\n\n";
    }

    /**
     * Mostra l'elenco dei comandi disponibili.
     *
     * @param giocatore
     * @param parametri
     * @return
     */
    public String aiuto(Giocatore giocatore, List<String> parametri) {
        return "Comandi Disponibili:\n" +
                "- mappa (per visualizzare la mappa)\n" +
                "- inventario (per visualizzare l'inventario)\n" +
                "- salva (per salvare la partita)\n" +
                "- carica (per caricare la partita)\n" +
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
        if (stanzaDAO.isStanzaAccessibile(giocatore.getPosizioneAttuale().getStanzaId())) {
            Edificio edificio = stanzaDAO.getEdificioByStanza(giocatore.getPosizioneAttualeId());
            return "Edificio: " + edificio.getNome() + "\nSei nella stanza: " + giocatore.getPosizioneAttuale().getNome() + ".\n" + movimentoDAO.getMovimentiByStanza(giocatore.getPosizioneAttuale()) + "\n\n";
        } else {
            return "Il passaggio è bloccato... Da qui puoi solo tornare indietro.\n\n";
        }
    }

    /**
     * Metodo per salvare lo stato corrente del gioco.
     * @param giocatore il giocatore corrente.
     * @param parametri nessun parametro in input
     * @return feedback per l'utente
     */
    public String salva(Giocatore giocatore, List<String> parametri) {
        return "Partita salvata con successo\n\n";
    }
    /**
     * Metodo per caricare l'ultima partita salvata.
     * @param giocatore il giocatore corrente (non utilizzato qui, si presuppone di crearne uno nuovo)
     * @param parametri  nessun parametro
     * @return feedback per l'utente.
     */
    public String carica(Giocatore giocatore, List<String> parametri) {
        return "Partita caricata con successo\n\n";
    }

}