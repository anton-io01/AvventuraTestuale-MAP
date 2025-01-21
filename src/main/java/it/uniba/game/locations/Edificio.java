package it.uniba.game.locations;

public class Edificio {
    private String id;          // formato "01", "02", ecc.
    private String nome;
    private boolean accessibile;

    public Edificio(String id, String nome, boolean accessibile) {
        this.id = id;
        this.nome = nome;
        this.accessibile = accessibile;
    }

    public String getId() {
        return id;
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

    @Override
    public String toString() {
        return "Edificio " + id + ": " + nome + (accessibile ? " (Accessibile)" : " (Non accessibile)");
    }
}
