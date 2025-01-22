package it.uniba.game.locations;

public class Edificio {
    private String EdificioId;          // formato "01", "02", ecc.
    private String nome;
    private boolean accessibile;

    public Edificio(String EdificioId, String nome, boolean accessibile) {
        this.EdificioId = EdificioId;
        this.nome = nome;
        this.accessibile = accessibile;
    }

    public String getEdificioId() {
        return EdificioId;
    }

    public String getNome() {
        return nome;
    }

    public boolean isAccessibile() {
        return accessibile;
    }

    public void setAccessibile(boolean accessibile) {
        this.accessibile = accessibile;
    }
}
