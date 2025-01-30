package it.uniba.game.entity;

import java.util.ArrayList;
import java.util.List;


public class Giocatore{

    private Stanza posizioneAttuale;
    private List<Oggetto> inventario;

    public Giocatore(){
        this.inventario = new ArrayList<>();
    }
    public void aggiungiOggetto(Oggetto oggetto){
        this.inventario.add(oggetto);
    }

    public void rimuoviOggetto(Oggetto oggetto){
        this.inventario.remove(oggetto);
    }

    public Stanza getPosizioneAttuale() {
        return posizioneAttuale;
    }

    public String getPosizioneAttualeId(){ return this.posizioneAttuale.getStanzaId();};
    public void setPosizioneAttuale(Stanza posizioneAttuale) {
        this.posizioneAttuale = posizioneAttuale;
    }

    public List<Oggetto> getInventario() {
        return inventario;
    }
    public String getPlayerParams() {
        StringBuilder inventarioString = new StringBuilder();
        for (int i = 0; i < inventario.size(); i++) {
            inventarioString.append(inventario.get(i).getOggettoId());
            if (i < inventario.size() - 1) {
                inventarioString.append(",");
            }
        }
        if (posizioneAttuale != null) {
            return posizioneAttuale.getStanzaId() + ";" + inventarioString.toString();
        }else{
            return "";
        }
    }


}