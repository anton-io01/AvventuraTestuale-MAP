package it.uniba.game.entity;

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

    public void setOggettoId(String oggettoId) {
        this.oggettoId = oggettoId;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public boolean isRaccoglibile() {
        return raccoglibile;
    }

    public void setRaccoglibile(boolean raccoglibile) {
        this.raccoglibile = raccoglibile;
    }

    @Override
    public String toString() {
        return "Oggetto{" +
                "oggettoId='" + oggettoId + '\'' +
                ", nome='" + nome + '\'' +
                ", descrizione='" + descrizione + '\'' +
                ", raccoglibile=" + raccoglibile +
                '}';
    }
}
