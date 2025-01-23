package it.uniba.game.entity;

public class Edificio {
    private String edificioId;
    private String nome;
    private boolean accessibile;
    private String descrizione;

    public Edificio(String edificioId, String nome, boolean accessibile, String descrizione) {
        this.edificioId = edificioId;
        this.nome = nome;
        this.accessibile = accessibile;
        this.descrizione = descrizione;
    }

    public String getEdificioId() {
        return edificioId;
    }

    public void setEdificioId(String edificioId) {
        this.edificioId = edificioId;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public boolean isAccessibile() {
        return accessibile;
    }

    public void setAccessibile(boolean accessibile) {
        this.accessibile = accessibile;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    @Override
    public String toString() {
        return "Edificio{" +
                "edificioId='" + edificioId + '\'' +
                ", nome='" + nome + '\'' +
                ", accessibile=" + accessibile +
                ", descrizione='" + descrizione + '\'' +
                '}';
    }
}