package it.uniba.game.entity;

public class Stanza {
    private String edificioId;
    private String stanzaId;
    private String nome;
    private boolean accessibile;

    public Stanza(String edificioId, String stanzaId, String nome, boolean accessibile) {
        this.edificioId = edificioId;
        this.stanzaId = stanzaId;
        this.nome = nome;
        this.accessibile = accessibile;
    }

    public String getEdificioId() {
        return edificioId;
    }

    public void setEdificioId(String edificioId) {
        this.edificioId = edificioId;
    }

    public String getStanzaId() {
        return stanzaId;
    }

    public void setStanzaId(String stanzaId) {
        this.stanzaId = stanzaId;
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

    @Override
    public String toString() {
        return "Stanza{" +
                "edificioId='" + edificioId + '\'' +
                ", stanzaId='" + stanzaId + '\'' +
                ", nome='" + nome + '\'' +
                ", accessibile=" + accessibile +
                '}';
    }
}
