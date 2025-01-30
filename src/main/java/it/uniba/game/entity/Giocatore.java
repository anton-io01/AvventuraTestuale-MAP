package it.uniba.game.entity;

import it.uniba.game.database.dao.StanzaDAO;

import java.util.ArrayList;
import java.util.List;

public class Giocatore {
    private Stanza posizioneAttuale;
    private StanzaDAO stanzaDAO = new StanzaDAO();
    private List<Oggetto> inventario;

    public Giocatore() {
        this.posizioneAttuale = stanzaDAO.getStanzaById("02");
        this.inventario = new ArrayList<>();
        this.inventario.add(new Oggetto("01","Flipper Zero"));
    }

    public Stanza getPosizioneAttuale() {
        return posizioneAttuale;
    }

    public void setPosizioneAttuale(Stanza posizioneAttuale) {
        this.posizioneAttuale = posizioneAttuale;
    }

    public String getPosizioneAttualeId() {
        return posizioneAttuale.getStanzaId();
    }

    public List<Oggetto> getInventario() {
        return inventario;
    }

    public void aggiungiOggetto(Oggetto oggetto) {
        inventario.add(oggetto);
    }

    public void rimuoviOggetto(Oggetto oggetto) {
        inventario.remove(oggetto);
    }
}
