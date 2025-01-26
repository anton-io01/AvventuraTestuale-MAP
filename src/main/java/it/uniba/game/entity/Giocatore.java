package it.uniba.game.entity;

import java.util.ArrayList;
import java.util.List;

public class Giocatore {
    private Edificio posizioneAttuale_edificio;
    private Stanza posizioneAttuale_stanza;
    private List<Oggetto> inventario;

    public Giocatore(Edificio posizioneAttuale_edificio, Stanza posizioneAttuale_stanza) {
        this.posizioneAttuale_edificio = posizioneAttuale_edificio;
        this.posizioneAttuale_stanza = posizioneAttuale_stanza;
        this.inventario = new ArrayList<>();
    }

    public Edificio getPosizioneAttuale_edificio() {
        return posizioneAttuale_edificio;
    }

    public void setPosizioneAttuale_edificio(Edificio posizioneAttuale_edificio) {
        this.posizioneAttuale_edificio = posizioneAttuale_edificio;
    }

    public Stanza getPosizioneAttuale_stanza() {
        return posizioneAttuale_stanza;
    }

    public void setPosizioneAttuale(Stanza posizioneAttuale_stanza) {
        this.posizioneAttuale_stanza = posizioneAttuale_stanza;
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
