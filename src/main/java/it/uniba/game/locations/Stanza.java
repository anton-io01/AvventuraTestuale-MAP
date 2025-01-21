package it.uniba.game.locations;

import java.util.HashMap;
import java.util.Map;

public class Stanza {
    private String idCompleto;    // formato "01-07"
    private String idStanza;      // formato "07"
    private String nome;
    private String descrizione;
    private boolean accessibile;
    private Edificio edificio;



    public Stanza(String idStanza, String nome, String descrizione, boolean accessibile, Edificio edificio) {
        this.idStanza = idStanza;
        this.nome = nome;
        this.descrizione = descrizione;
        this.accessibile = accessibile;
        this.edificio = edificio;
        this.idCompleto = edificio.getId() + "-" + idStanza;
    }

    public String getIdCompleto() {
        return idCompleto;
    }

    public String getIdStanza() {
        return idStanza;
    }

    public String getNome() {
        return nome;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public boolean isAccessibile() {
        return accessibile;
    }

    public Edificio getEdificio() {
        return edificio;
    }

    public void setAccessibile(boolean accessibile) {
        this.accessibile = accessibile;
    }

    @Override
    public String toString() {
        return "Stanza " + idCompleto + ": " + nome +
                (accessibile ? " (Accessibile)" : " (Non accessibile)");
    }
}