package it.uniba.game.entity;

import java.util.ArrayList;
import java.util.List;

public class Giocatore {
    private Stanza posizioneAttuale;
    private List<Oggetto> inventario;

    public Giocatore() {
        this.posizioneAttuale = new Stanza("01","02","ingresso");
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
