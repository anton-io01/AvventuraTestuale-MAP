package it.uniba.game;

import it.uniba.game.util.CSVLoader;

public class Engine {
    public static void main(String[] args) {
        String filePath = "src/main/resources/edifici.csv"; // Percorso del file CSV
        CSVLoader.loadEdificiFromCSV(filePath);
    }
}
