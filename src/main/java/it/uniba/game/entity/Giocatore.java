package it.uniba.game.entity;

import java.util.ArrayList;
import java.util.List;

public class Giocatore {
    private Stanza posizioneAttuale;
    private List<Oggetto> inventario;

    public Giocatore(Stanza posizioneAttuale) {
        this.posizioneAttuale = posizioneAttuale;
        this.inventario = new ArrayList<>();
    }

    public Stanza getPosizioneAttuale() {
        return posizioneAttuale;
    }

    public void setPosizioneAttuale(Stanza posizioneAttuale) {
        this.posizioneAttuale = posizioneAttuale;
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
