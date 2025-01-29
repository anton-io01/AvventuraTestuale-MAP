package it.uniba.game.entity;

public class Stanza {
    private String edificioId;
    private String stanzaId;
    private String nome;

    public Stanza(String edificioId, String stanzaId, String nome) {
        this.edificioId = edificioId;
        this.stanzaId = stanzaId;
        this.nome = nome;
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
}
