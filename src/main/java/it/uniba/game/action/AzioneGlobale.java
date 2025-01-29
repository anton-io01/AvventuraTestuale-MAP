package it.uniba.game.action;

import it.uniba.game.entity.Giocatore;
import it.uniba.game.database.dao.StanzaDAO;

import java.util.List;

public class AzioneGlobale {
    public StanzaDAO stanzaDAO = new StanzaDAO();

    public AzioneGlobale() {}

    public String inventario(Giocatore giocatore, List<String> parametri) {
        System.out.println("Inventario:");
        if (giocatore.getInventario().isEmpty()) {
            return "L'inventario è vuoto.";
        } else {
            String inventario = null;
            for (int i = 0; i < giocatore.getInventario().size(); i++) {
                inventario = giocatore.getInventario().get(i).getNome() + "/n";
            }
            return inventario;
        }
    }
    
    public Void esci(Giocatore giocatore, List<String> parametri) {
        System.out.println("Arrivederci!");
        System.exit(0);
        return null;
    }

    public Void salva(Giocatore giocatore, List<String> parametri) {
        System.out.println("Salvataggio...");
        System.out.println("Salvataggio completato.");
        return null;
    }

    public Void osserva(Giocatore giocatore, List<String> parametri) {
        System.out.println(stanzaDAO.getDescrizioneCompleta(giocatore.getPosizioneAttualeId()));
        return null;
    }

    public Void entra(Giocatore giocatore, List<String> parametri) {
        return null;
    }

    public Void aiuto(Giocatore giocatore, List<String> parametri) {
        System.out.println("Comandi Disponibili:");
        System.out.println("- nord, sud, est, ovest, alto, basso (per muoverti)");
        System.out.println("- osserva (per mostrare gli oggetti all'interno della stanza)");
        System.out.println("- esamina [oggetto] (per esaminare un oggetto)");
        System.out.println("- prendi [oggetto] (per prendere un oggetto)");
        System.out.println("- lascia [oggetto] (per lasciare un oggetto)");
        System.out.println("- apri [oggetto] (per aprire un armadio o una porta)");
        System.out.println("- chiudi [oggetto] (per chiudere un armadio o una porta)");
        System.out.println("- usa [oggetto] (per utilizzare un oggetto)");
        System.out.println("- inventario (per visualizzare l'inventario)");
        System.out.println("- salva (per salvare la partita)");
        System.out.println("- esci (per uscire dal gioco)");
        return null;
    }
}
