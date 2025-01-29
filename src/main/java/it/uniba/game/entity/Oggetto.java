package it.uniba.game.entity;

public class Oggetto {
    private String oggettoId;
    private String nome;

    public Oggetto(String oggettoId, String nome) {
        this.oggettoId = oggettoId;
        this.nome = nome;
    }

    public String getOggettoId() {
        return oggettoId;
    }

    public void setOggettoId(String oggettoId) {
        this.oggettoId = oggettoId;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
