package it.uniba.game.locations;

public class Edificio {
    private int id;
    private String nome;
    private boolean accessibile;

    public Edificio(int id, String nome, boolean accessibile) {
        this.id = id;
        this.nome = nome;
        this.accessibile = accessibile;
    }

    public int getId() {
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
}
