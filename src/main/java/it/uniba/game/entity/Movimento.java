package it.uniba.game.entity;

public class Movimento {
    private String edificioId;
    private String stanzaPartenza;
    private String direzione;
    private String stanzaArrivo;

    public Movimento(String edificioId, String stanzaPartenza, String direzione, String stanzaArrivo) {
        this.edificioId = edificioId;
        this.stanzaPartenza = stanzaPartenza;
        this.direzione = direzione;
        this.stanzaArrivo = stanzaArrivo;
    }

    public String getEdificioId() {
        return edificioId;
    }

    public void setEdificioId(String edificioId) {
        this.edificioId = edificioId;
    }

    public String getStanzaPartenza() {
        return stanzaPartenza;
    }

    public void setStanzaPartenza(String stanzaPartenza) {
        this.stanzaPartenza = stanzaPartenza;
    }

    public String getDirezione() {
        return direzione;
    }

    public void setDirezione(String direzione) {
        this.direzione = direzione;
    }

    public String getStanzaArrivo() {
        return stanzaArrivo;
    }

    public void setStanzaArrivo(String stanzaArrivo) {
        this.stanzaArrivo = stanzaArrivo;
    }

    @Override
    public String toString() {
        return "Movimento{" +
                "edificioId='" + edificioId + '\'' +
                ", stanzaPartenza='" + stanzaPartenza + '\'' +
                ", direzione='" + direzione + '\'' +
                ", stanzaArrivo='" + stanzaArrivo + '\'' +
                '}';
    }
}
