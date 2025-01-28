package it.uniba.game.action;

import it.uniba.game.entity.Giocatore;
import it.uniba.game.database.dao.StanzaDAO;

import java.util.List;

public class AzioneGlobale {
    private StanzaDAO stanzaDAO;

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

    public String osserva(Giocatore giocatore, List<String> parametri) {
        return stanzaDAO.getDescrizioneCompleta(giocatore.getPosizioneAttualeId());
    }
}
