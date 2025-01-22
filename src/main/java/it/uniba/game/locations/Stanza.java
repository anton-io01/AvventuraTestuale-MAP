package it.uniba.game.locations;

import java.util.HashMap;
import java.util.Map;

public class Stanza {
    private String stanzaId;      // formato "07"
    private String edificioId;    // formato "01"
    private String nome;
    private String descrizione;
    private boolean accessibile;



    public Stanza(String stanzaId, String edificioId, String nome, String descrizione, boolean accessibile) {
        this.stanzaId = stanzaId;
        this.edificioId = edificioId;
        this.nome = nome;
        this.descrizione = descrizione;
        this.accessibile = accessibile;
    }

    public String getStanzaId() {
        return stanzaId;
    }

    public String getEdificioId() {
        return edificioId;
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

    public void setAccessibile(boolean accessibile) {
        this.accessibile = accessibile;
    }

    public String getIdCompleto() {
        return edificioId + "-" + stanzaId;
    }

}