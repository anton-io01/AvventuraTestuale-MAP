package it.uniba.game.action;

import it.uniba.game.entity.Giocatore;
import it.uniba.game.database.dao.OggettoDAO;
import it.uniba.game.entity.Oggetto;

import java.sql.Connection;
import java.util.List;

public class AzioneInterazione {
    private Connection connection;
    private OggettoDAO oggettoDAO;

    public AzioneInterazione(Connection connection) {
        this.connection = connection;
    }

    /**
     * Prende un oggetto dalla stanza, lo aggiunge all'inventario del giocatore e lo elimina dalla stanza attuale.
     *
     * @param giocatore Il giocatore che esegue l'azione.
     * @param parametri Una lista di parametri per l'azione.
     */
    public void prendi(Giocatore giocatore, List<String> parametri) {
        if(oggettoDAO.isOggettoRaccoglibile(parametri.get(1), giocatore.getPosizioneAttualeId())) {
            Oggetto oggetto = oggettoDAO.getOggettoById(parametri.get(1));
            giocatore.aggiungiOggetto(oggetto);
            System.out.println("Hai aggiunto " + oggetto.getNome() + " all'inventario.");
        } else {
            System.out.println("Non puoi raccogliere questo oggetto.");
        }
    }

    /**
     * Lascia un oggetto dall'inventario e lo aggiunge alla stanza attuale.
     *
     * @param giocatore Il giocatore che esegue l'azione.
     * @param parametri Una lista di parametri per l'azione.
     */
    public void lascia(Giocatore giocatore, List<String> parametri) {
        Oggetto oggetto = giocatore.getInventario().stream()
                .filter(o -> o.getOggettoId().equals(parametri.get(1)))
                .findFirst()
                .orElse(null);

        if(oggetto != null) {
            giocatore.rimuoviOggetto(oggetto);
            oggettoDAO.aggiungiOggettoAStanza(parametri.get(1), giocatore.getPosizioneAttualeId());
            System.out.println("Hai lasciato " + oggetto.getNome() + ".");
        } else {
            System.out.println("Non hai questo oggetto nell'inventario.");
        }
    }


}
