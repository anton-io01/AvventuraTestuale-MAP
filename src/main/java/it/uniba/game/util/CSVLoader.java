package it.uniba.game.util;

import it.uniba.game.database.DatabaseManager;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class CSVLoader {

    public static void loadEdificiFromCSV(String filePath) {
        String query = "INSERT INTO Edifici (edificio_id, nome, accessibile, descrizione) VALUES (?, ?, ?, ?)";
        loadFromCSV(filePath, query, 4);
    }

    public static void loadStanzeFromCSV(String filePath) {
        String query = "INSERT INTO Stanze (edificio_id, stanza_id, nome, accessibile) VALUES (?, ?, ?, ?)";
        loadFromCSV(filePath, query, 4);
    }

    public static void loadDescrizioniStanzeFromCSV(String filePath) {
        String query = "INSERT INTO DescrizioniStanze (edificio_id, stanza_id, descrizione_breve, descrizione_completa) VALUES (?, ?, ?, ?)";
        loadFromCSV(filePath, query, 4);
    }

    public static void loadOggettiFromCSV(String filePath) {
        String query = "INSERT INTO Oggetti (oggetto_id, nome, descrizione, raccoglibile) VALUES (?, ?, ?, ?)";
        loadFromCSV(filePath, query, 4);
    }

    public static void loadOggettiStanzeFromCSV(String filePath) {
        String query = "INSERT INTO OggettiStanze (oggetto_id, stanza_id, edificio_id) VALUES (?, ?, ?)";
        loadFromCSV(filePath, query, 3);
    }

    public static void loadMovimentiFromCSV(String filePath) {
        String query = "INSERT INTO Movimenti (edificio_id, stanza_id, nord, sud, est, ovest, alto, basso) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        loadFromCSV(filePath, query, 8);
    }

    public static void loadAzioniFromCSV(String filePath) {
        String query = "INSERT INTO Azioni (alias, azione_id, categoria) VALUES (?, ?, ?)";
        loadFromCSV(filePath, query, 3);
    }

    public static void loadAzioniOggettiFromCSV(String filePath) {
        String query = "INSERT INTO AzioniInterazione (azione_id, oggetto_id) VALUES (?, ?)";
        loadFromCSV(filePath, query, 2);
    }

    private static void loadFromCSV(String filePath, String query, int columnCount) {
        try (Connection connection = DatabaseManager.getConnection();
             BufferedReader br = new BufferedReader(new FileReader(filePath));
             PreparedStatement pstmt = connection.prepareStatement(query)) {

            // Salta la prima riga (intestazioni)
            String line = br.readLine();

            // Leggi il file riga per riga
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length < columnCount) {
                    System.err.println("Riga non valida nel file CSV: " + line);
                    continue;
                }

                // Imposta i valori nella query
                for (int i = 0; i < columnCount; i++) {
                    pstmt.setString(i + 1, data[i].trim());
                    String value = data[i].trim();
                    if (value.equalsIgnoreCase("NULL")) {
                        pstmt.setNull(i + 1, java.sql.Types.VARCHAR);
                    } else {
                        pstmt.setString(i + 1, value);
                    }
                }
                pstmt.executeUpdate();
            }

            // Estrai il nome del file
            String[] pathName = filePath.split("/");
            String fileName = pathName[pathName.length - 1].split("\\.")[0];
            System.out.println("Dati caricati con successo nella tabella " + fileName + "!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}