package it.uniba.game.util;

import it.uniba.game.database.DatabaseManager;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CSVLoader {
    private static final Logger LOGGER = Logger.getLogger(CSVLoader.class.getName());


    public static void loadEdificiFromCSV(String filePath) {
        String query = "INSERT INTO Edifici (edificio_id, nome, accessibile, descrizione) VALUES (?, ?, ?, ?)";
        loadFromCSV(filePath, query, new int[]{java.sql.Types.INTEGER, java.sql.Types.VARCHAR, java.sql.Types.BOOLEAN, java.sql.Types.VARCHAR});
    }

    public static void loadStanzeFromCSV(String filePath) {
        String query = "INSERT INTO Stanze (edificio_id, stanza_id, nome, accessibile) VALUES (?, ?, ?, ?)";
        loadFromCSV(filePath, query, new int[]{java.sql.Types.INTEGER, java.sql.Types.INTEGER, java.sql.Types.VARCHAR, java.sql.Types.BOOLEAN});
    }

    public static void loadDescrizioniStanzeFromCSV(String filePath) {
        String query = "INSERT INTO DescrizioniStanze (edificio_id, stanza_id, descrizione_breve, descrizione_completa) VALUES (?, ?, ?, ?)";
        loadFromCSV(filePath, query, new int[]{java.sql.Types.INTEGER, java.sql.Types.INTEGER, java.sql.Types.VARCHAR, java.sql.Types.VARCHAR});
    }

    public static void loadOggettiFromCSV(String filePath) {
        String query = "INSERT INTO Oggetti (oggetto_id, nome, descrizione, raccoglibile, tipo, contenitore_id, stato) VALUES (?, ?, ?, ?, ?, ?, ?)";
        loadFromCSV(filePath, query, new int[]{java.sql.Types.INTEGER, java.sql.Types.VARCHAR, java.sql.Types.VARCHAR,java.sql.Types.BOOLEAN, java.sql.Types.VARCHAR, java.sql.Types.INTEGER, java.sql.Types.VARCHAR});

    }

    public static void loadOggettiStanzeFromCSV(String filePath) {
        String query = "INSERT INTO OggettiStanze (oggetto_id, stanza_id, edificio_id) VALUES (?, ?, ?)";
        loadFromCSV(filePath, query, new int[]{java.sql.Types.INTEGER, java.sql.Types.INTEGER, java.sql.Types.INTEGER});
    }

    public static void loadMovimentiFromCSV(String filePath) {
        String query = "INSERT INTO Movimenti (edificio_id, stanza_id, nord, sud, est, ovest, alto, basso) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        loadFromCSV(filePath, query,new int[]{java.sql.Types.INTEGER, java.sql.Types.INTEGER,java.sql.Types.INTEGER, java.sql.Types.INTEGER, java.sql.Types.INTEGER, java.sql.Types.INTEGER,java.sql.Types.INTEGER,java.sql.Types.INTEGER});
    }
    public static void loadAzioniFromCSV(String filePath) {
        String query = "INSERT INTO Azioni (azione_id, nome) VALUES (?, ?)";
        loadFromCSV(filePath, query, new int[]{java.sql.Types.INTEGER, java.sql.Types.VARCHAR});
    }
    public static void loadAzioniOggettiFromCSV(String filePath) {
        String query = "INSERT INTO AzioniOggetti (azione_id, oggetto_id, descrizione, effetto_id) VALUES (?, ?, ?, ?)";
        loadFromCSV(filePath, query, new int[]{java.sql.Types.INTEGER, java.sql.Types.INTEGER, java.sql.Types.VARCHAR, java.sql.Types.INTEGER});
    }
    public static void loadEffettiFromCSV(String filePath) {
        String query = "INSERT INTO Effetti (effetto_id, nome, descrizione, tipo) VALUES (?, ?, ?, ?)";
        loadFromCSV(filePath, query, new int[]{java.sql.Types.INTEGER, java.sql.Types.VARCHAR, java.sql.Types.VARCHAR, java.sql.Types.VARCHAR});
    }
    public static void loadEffettiStatoFromCSV(String filePath) {
        String query = "INSERT INTO EffettiStato (effetto_id, oggetto_id, stato) VALUES (?, ?, ?)";
        loadFromCSV(filePath, query, new int[]{java.sql.Types.INTEGER, java.sql.Types.INTEGER, java.sql.Types.VARCHAR});
    }
    public static void loadEffettiInventarioFromCSV(String filePath) {
        String query = "INSERT INTO EffettiInventario (effetto_id, oggetto_id, tipo) VALUES (?, ?, ?)";
        loadFromCSV(filePath, query, new int[]{java.sql.Types.INTEGER, java.sql.Types.INTEGER, java.sql.Types.VARCHAR});
    }
    public static void loadEffettiCondizionaliFromCSV(String filePath) {
        String query = "INSERT INTO EffettiCondizionali (effetto_id, condition, doorId, targetStanzaId) VALUES (?, ?, ?, ?)";
        loadFromCSV(filePath, query, new int[]{java.sql.Types.INTEGER, java.sql.Types.VARCHAR, java.sql.Types.INTEGER, java.sql.Types.INTEGER});
    }

    private static void loadFromCSV(String filePath, String query, int[] columnTypes) {
        try (Connection connection = DatabaseManager.getConnection();
             BufferedReader br = new BufferedReader(new FileReader(filePath));
             PreparedStatement pstmt = connection.prepareStatement(query)) {

            // Salta la prima riga (intestazioni)
            String line = br.readLine();
            int rowNumber = 0;


            // Leggi il file riga per riga
            while ((line = br.readLine()) != null) {
                rowNumber++;
                String[] data = line.split(",");
                if (data.length < columnTypes.length) {
                    LOGGER.log(Level.WARNING, "Riga non valida nel file CSV (Riga " + rowNumber + "): " + line);
                    continue;
                }

                try {
                    for (int i = 0; i < columnTypes.length; i++) {
                        String value = data[i].trim();
                        if (value.equalsIgnoreCase("NULL")) {
                            pstmt.setNull(i + 1, java.sql.Types.NULL);
                        } else {
                            switch(columnTypes[i]){
                                case java.sql.Types.INTEGER:
                                    pstmt.setInt(i+1,Integer.parseInt(value));
                                    break;
                                case java.sql.Types.BOOLEAN:
                                    pstmt.setBoolean(i + 1, Boolean.parseBoolean(value));
                                    break;
                                default:
                                    pstmt.setString(i + 1, value);
                            }
                        }
                    }
                    pstmt.executeUpdate();
                }catch (SQLException | NumberFormatException ex) {
                    LOGGER.log(Level.WARNING, "Errore durante l'inserimento dati nella riga " + rowNumber +": "+ ex.getMessage());
                    continue;
                }

            }

            // Estrai il nome del file
            String[] pathName = filePath.split("/");
            String fileName = pathName[pathName.length - 1].split("\\.")[0];
            System.out.println("Dati caricati con successo nella tabella " + fileName + "!");

        } catch (IOException | SQLException e) {
            LOGGER.log(Level.SEVERE, "Errore durante il caricamento del file CSV: " + e.getMessage());
        }
    }
}