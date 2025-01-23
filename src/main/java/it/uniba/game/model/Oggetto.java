package it.uniba.game.model;

public class Oggetto {
    private String oggettoId;
    private String nome;
    private String descrizione;
    private boolean raccoglibile;

    public Oggetto(String oggettoId, String nome, String descrizione, boolean raccoglibile) {
        this.oggettoId = oggettoId;
        this.nome = nome;
        this.descrizione = descrizione;
        this.raccoglibile = raccoglibile;
    }

    public String getOggettoId() {
        return oggettoId;
    }

    public String getNome() {
        return nome;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public boolean isRaccoglibile() {
        return raccoglibile;
    }
}
