package it.uniba.game.util;

import it.uniba.game.database.DatabaseManager;
import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

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
        String query = "INSERT INTO Oggetti (oggetto_id, nome, raccoglibile, visibile) VALUES (?, ?, ?, ?)";
        loadFromCSV(filePath, query, 4);
    }

    public static void loadOggettiAliasFromCSV(String filePath) {
        String query = "INSERT INTO OggettiAlias (oggetto_id, alias) VALUES (?, ?)";
        loadFromCSV(filePath, query, 2);
    }

    public static void loadOggettiStanzeFromCSV(String filePath) {
        String query = "INSERT INTO OggettiStanze (oggetto_id, stanza_id) VALUES (?, ?)";
        loadFromCSV(filePath, query, 2);
    }

    public static void loadDescrizioniOggettiFromCSV(String filePath) {
        String query = "INSERT INTO DescrizioniOggetti (oggetto_id, descrizione_breve, descrizione_esamina) VALUES (?, ?, ?)";
        loadFromCSV(filePath, query, 3);
    }

    public static void loadMovimentiFromCSV(String filePath) {
        String query = "INSERT INTO Movimenti (edificio_id, stanza_id, nord, sud, est, ovest, alto, basso) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        loadFromCSV(filePath, query, 8);
    }

    public static void loadAzioniFromCSV(String filePath) {
        String query = "INSERT INTO Azioni (azione_id, nome, categoria) VALUES (?, ?, ?)";
        loadFromCSV(filePath, query, 3);
    }

    public static void loadAzioniOggettiFromCSV(String filePath) {
        String query = "INSERT INTO AzioniInterazione (azione_id, oggetto_id, stanza_id) VALUES (?, ?, ?)";
        loadFromCSV(filePath, query, 3);
    }

    private static void loadFromCSV(String filePath, String query, int columnCount) {
        try (Connection connection = DatabaseManager.getConnection();
             BufferedReader br = new BufferedReader(new FileReader(filePath));
             PreparedStatement pstmt = connection.prepareStatement(query)) {

            // Salta la prima riga (intestazioni)
            String line = br.readLine();

            // Leggi il file riga per riga
            while ((line = br.readLine()) != null) {
                List<String> data = parseCSVLine(line);

                if (data.size() < columnCount) {
                    System.err.println("Riga non valida nel file CSV: " + line);
                    continue;
                }

                // Imposta i valori nella query
                for (int i = 0; i < columnCount; i++) {
                    String value = data.get(i).trim();
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

    private static List<String> parseCSVLine(String line) {
        List<String> values = new ArrayList<>();
        boolean inQuotes = false;
        StringBuilder currentField = new StringBuilder();

        for (char c : line.toCharArray()) {
            if (c == '"') {
                inQuotes = !inQuotes;
            } else if (c == ',' && !inQuotes) {
                values.add(currentField.toString());
                currentField = new StringBuilder();
            } else {
                currentField.append(c);
            }
        }
        values.add(currentField.toString());
        return values;
    }
}