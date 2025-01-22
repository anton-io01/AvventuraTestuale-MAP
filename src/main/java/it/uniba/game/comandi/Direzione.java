package it.uniba.game.comandi;

public enum Direzione {
    NORD(1),
    SUD(2),
    EST(3),
    OVEST(4),
    ALTO(5),
    BASSO(6);

    private final int codice;

    Direzione(int codice) {
        this.codice = codice;
    }

    public int getCodice() {
        return codice;
    }

    public static Direzione getDaDirezione(int codice) {
        for (Direzione d : values()) {
            if (d.getCodice() == codice) {
                return d;
            }
        }
        return null;
    }
}