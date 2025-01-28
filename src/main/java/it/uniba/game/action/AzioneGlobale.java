package it.uniba.game.action;

import it.uniba.game.entity.Giocatore;
import it.uniba.game.database.dao.StanzaDAO;

import java.util.List;

public class AzioneGlobale {
    private StanzaDAO stanzaDAO;

    public AzioneGlobale() {}

    public void inventario(Giocatore giocatore, List<String> parametri) {
        System.out.println("Inventario:");
        if (giocatore.getInventario().isEmpty()) {
            System.out.println("L'inventario è vuoto.");
        } else {
            for (int i = 0; i < giocatore.getInventario().size(); i++) {
                System.out.println(giocatore.getInventario().get(i).getNome());
            }
        }
    }

    public void osserva(Giocatore giocatore, List<String> parametri) {
        System.out.println(stanzaDAO.getDescrizioneCompleta(giocatore.getPosizioneAttualeId()));
    }
}
