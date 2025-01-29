package it.uniba.game.action;

import it.uniba.game.database.dao.AzioneDAO;
import it.uniba.game.entity.Giocatore;
import it.uniba.game.database.dao.OggettoDAO;
import it.uniba.game.entity.Oggetto;

import java.sql.Connection;
import java.util.List;

public class AzioneInterazione {
    private OggettoDAO oggettoDAO = new OggettoDAO();
    private AzioneDAO azioneDAO = new AzioneDAO();

    public AzioneInterazione() {
    }

    /**
     * Esamina un oggetto nella stanza.
     *
     * @param giocatore Il giocatore che esegue l'azione.
     * @param parametri Una lista di parametri per l'azione.
     */
    public Void esamina(Giocatore giocatore, List<String> parametri) {
        if (azioneDAO.verificaAzioneInterazione(parametri.get(0), parametri.get(1), giocatore.getPosizioneAttualeId())) {
            System.out.println(oggettoDAO.getDescrizioneDettagliataOggetto(parametri.get(1)));
        } else {
            System.out.println("Non puoi esaminare questo oggetto.");
        }
        return null;
    }

    /**
     * Prende un oggetto dalla stanza, lo aggiunge all'inventario del giocatore e lo elimina dalla stanza attuale.
     *
     * @param giocatore Il giocatore che esegue l'azione.
     * @param parametri Una lista di parametri per l'azione.
     */
    public Void prendi(Giocatore giocatore, List<String> parametri) {
        if (oggettoDAO.isOggettoRaccoglibile(parametri.get(1), giocatore.getPosizioneAttualeId())) {
            Oggetto oggetto = oggettoDAO.getOggettoById(parametri.get(1));
            giocatore.aggiungiOggetto(oggetto);
            oggettoDAO.eliminaOggettoDaStanza(parametri.get(1), giocatore.getPosizioneAttualeId());
            System.out.println("Hai aggiunto " + oggetto.getNome() + " all'inventario.");
        } else {
            System.out.println("Non puoi raccogliere questo oggetto.");
        }
        return null;
    }

    /**
     * Lascia un oggetto dall'inventario e lo aggiunge alla stanza attuale.
     *
     * @param giocatore Il giocatore che esegue l'azione.
     * @param parametri Una lista di parametri per l'azione.
     */
    public Void lascia(Giocatore giocatore, List<String> parametri) {
        Oggetto oggetto = giocatore.getInventario().stream()
                .filter(o -> o.getOggettoId().equals(parametri.get(1)))
                .findFirst()
                .orElse(null);

        if (oggetto != null) {
            giocatore.rimuoviOggetto(oggetto);
            oggettoDAO.aggiungiOggettoAStanza(parametri.get(1), giocatore.getPosizioneAttualeId());
            System.out.println("Hai lasciato " + oggetto.getNome() + ".");
        } else {
            System.out.println("L'oggetto non è nell'inventario.");
        }
        return null;
    }

    /**
     * Usa un oggetto dall'inventario.
     *
     * @param giocatore
     * @param parametri
     */
    public Void usa(Giocatore giocatore, List<String> parametri) {
        return null;
    }

    /**
     * Apre un oggetto.
     *
     * @param giocatore
     * @param parametri
     */
    public Void apri(Giocatore giocatore, List<String> parametri) {
        if (azioneDAO.verificaAzioneInterazione(parametri.get(0), parametri.get(1), giocatore.getPosizioneAttualeId())) {
            oggettoDAO.setVisibilitaOggetto("10", true);
        } else {
            System.out.println("Non puoi aprire questo oggetto.");
        }
        return null;
    }

    /**
     * Chiude un oggetto.
     *
     * @param giocatore
     * @param parametri
     */
    public Void chiudi(Giocatore giocatore, List<String> parametri) {
        if (azioneDAO.verificaAzioneInterazione(parametri.get(0), parametri.get(1), giocatore.getPosizioneAttualeId())) {
            oggettoDAO.setVisibilitaOggetto("10", false);
        } else {
            System.out.println("Non puoi chiudere questo oggetto.");
        }
        return null;
    }

    /**
     * Parla con un personaggio nella stanza.
     *
     * @param giocatore
     * @param parametri
     */
    public Void parla(Giocatore giocatore, List<String> parametri) {
        if (azioneDAO.verificaAzioneInterazione(parametri.get(0), parametri.get(1), giocatore.getPosizioneAttualeId())) {
            System.out.println(oggettoDAO.getDescrizioneDettagliataOggetto(parametri.get(1)));
        } else {
            System.out.println("Non puoi parlare con questo personaggio.");
        }
        return null;
    }

    /**
     * Leggi un oggetto nella stanza.
     *
     * @param giocatore
     * @param parametri
     */
    public Void leggi(Giocatore giocatore, List<String> parametri) {
        if (azioneDAO.verificaAzioneInterazione(parametri.get(0), parametri.get(1), giocatore.getPosizioneAttualeId())) {
            System.out.println(oggettoDAO.getDescrizioneDettagliataOggetto(parametri.get(1)));
        } else {
            System.out.println("Non puoi leggere questo oggetto.");
        }
        return null;
    }
}