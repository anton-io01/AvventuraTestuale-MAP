package it.uniba.game.entity;

public class Oggetto {
    private String oggettoId;
    private String nome;
    private boolean raccoglibile;
    private boolean visibile;

    public Oggetto(String oggettoId, String nome, boolean raccoglibile, boolean visibile) {
        this.oggettoId = oggettoId;
        this.nome = nome;
        this.raccoglibile = raccoglibile;
        this.visibile = visibile;
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

    public boolean isRaccoglibile() {
        return raccoglibile;
    }

    public void setRaccoglibile(boolean raccoglibile) {
        this.raccoglibile = raccoglibile;
    }

    public boolean isVisibile() {
        return visibile;
    }

    public void setVisibile(boolean visibile) {
        this.visibile = visibile;
    }
}
