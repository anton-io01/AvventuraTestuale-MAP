package it.uniba.game.database;

import it.uniba.game.util.CSVLoader;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class DatabaseInitializer {

    private static final String EDIFICI_CSV = "src/main/resources/csv/edifici.csv";
    private static final String STANZE_CSV = "src/main/resources/csv/stanze.csv";
    private static final String DESCRIZIONI_STANZE_CSV = "src/main/resources/csv/descrizioni_stanze.csv";
    private static final String OGGETTI_CSV = "src/main/resources/csv/oggetti.csv";
    private static final String OGGETTI_STANZE_CSV = "src/main/resources/csv/oggetti_stanze.csv";
    private static final String MOVIMENTI_CSV = "src/main/resources/csv/movimenti.csv";

    /**
     * Metodo principale per inizializzare il database.
     * - Crea il database e le tabelle se non esistono.
     * - Carica i dati iniziali dai file CSV.
     */
    public static void initializeDatabase() {
        if (isDatabaseCreated()) {
            System.out.println("Il database esiste già. Nessuna operazione necessaria.");
            return;
        }

        try (Connection connection = DatabaseManager.getConnection();
             Statement stmt = connection.createStatement()) {

            // Creazione delle tabelle
            createTables(stmt);

            // Caricamento dei dati iniziali
            loadInitialData();

            System.out.println("Database creato e inizializzato con successo!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Controlla se il database è già stato creato.
     *
     * @return true se il database esiste, false altrimenti.
     */
    private static boolean isDatabaseCreated() {
        try (Connection connection = DatabaseManager.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME = 'EDIFICI'")) {

            return rs.next(); // Ritorna true se la tabella "Edifici" esiste

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Crea le tabelle nel database.
     *
     * @param stmt Statement da usare per le query SQL.
     * @throws Exception se si verifica un errore nella creazione delle tabelle.
     */
    private static void createTables(Statement stmt) throws Exception {
        // Crea tabelle nel database (copiando la logica dal file init_database.sql)
        String edificiTable = """
                CREATE TABLE IF NOT EXISTS Edifici (
                    edificio_id VARCHAR(2) PRIMARY KEY,
                    nome VARCHAR(100) NOT NULL UNIQUE,
                    accessibile BOOLEAN DEFAULT true,
                    descrizione TEXT
                );
                """;

        String stanzeTable = """
                CREATE TABLE IF NOT EXISTS Stanze (
                    edificio_id VARCHAR(2) NOT NULL,
                    stanza_id VARCHAR(2) PRIMARY KEY,
                    nome VARCHAR(100) NOT NULL,
                    accessibile BOOLEAN DEFAULT true,
                    FOREIGN KEY (edificio_id) REFERENCES Edifici(edificio_id)
                );
                """;

        String descrizioniStanzeTable = """
                CREATE TABLE IF NOT EXISTS DescrizioniStanze (
                    edificio_id VARCHAR(2) NOT NULL,
                    stanza_id VARCHAR(2) NOT NULL,
                    descrizione_breve TEXT,
                    descrizione_completa TEXT,
                    PRIMARY KEY (edificio_id, stanza_id),
                    FOREIGN KEY (edificio_id) REFERENCES Edifici(edificio_id),
                    FOREIGN KEY (stanza_id) REFERENCES Stanze(stanza_id)
                );
                """;

        String oggettiTable = """
                CREATE TABLE IF NOT EXISTS Oggetti (
                    oggetto_id VARCHAR(3) PRIMARY KEY,
                    nome VARCHAR(100) NOT NULL UNIQUE,
                    descrizione TEXT,
                    raccoglibile BOOLEAN DEFAULT false
                );
                """;

        String oggettiStanzeTable = """
                CREATE TABLE IF NOT EXISTS OggettiStanze (
                    oggetto_id VARCHAR(3) NOT NULL,
                    stanza_id VARCHAR(2) NOT NULL,
                    edificio_id VARCHAR(2) NOT NULL,
                    PRIMARY KEY (oggetto_id, stanza_id, edificio_id),
                    FOREIGN KEY (oggetto_id) REFERENCES Oggetti(oggetto_id),
                    FOREIGN KEY (stanza_id) REFERENCES Stanze(stanza_id),
                    FOREIGN KEY (edificio_id) REFERENCES Edifici(edificio_id)
                );
                """;

        String movimentiTable = """
                CREATE TABLE IF NOT EXISTS Movimenti (
                    edificio_id VARCHAR(2) NOT NULL,
                    stanza_id VARCHAR(2) NOT NULL,
                    nord VARCHAR(2),
                    sud VARCHAR(2),
                    est VARCHAR(2),
                    ovest VARCHAR(2),
                    alto VARCHAR(2),
                    basso VARCHAR(2),
                    PRIMARY KEY (edificio_id, stanza_id),
                    FOREIGN KEY (edificio_id) REFERENCES Edifici(edificio_id),
                    FOREIGN KEY (stanza_id) REFERENCES Stanze(stanza_id),
                    FOREIGN KEY (nord) REFERENCES Stanze(stanza_id),
                    FOREIGN KEY (sud) REFERENCES Stanze(stanza_id),
                    FOREIGN KEY (est) REFERENCES Stanze(stanza_id),
                    FOREIGN KEY (ovest) REFERENCES Stanze(stanza_id),
                    FOREIGN KEY (alto) REFERENCES Stanze(stanza_id),
                    FOREIGN KEY (basso) REFERENCES Stanze(stanza_id)
                );
                """;

        stmt.execute(edificiTable);
        stmt.execute(stanzeTable);
        stmt.execute(descrizioniStanzeTable);
        stmt.execute(oggettiTable);
        stmt.execute(oggettiStanzeTable);
        stmt.execute(movimentiTable);

        System.out.println("Tabelle create con successo.");
    }

    /**
     * Carica i dati iniziali dai file CSV.
     */
    private static void loadInitialData() {
        CSVLoader.loadEdificiFromCSV(EDIFICI_CSV);
        CSVLoader.loadStanzeFromCSV(STANZE_CSV);
        CSVLoader.loadDescrizioniStanzeFromCSV(DESCRIZIONI_STANZE_CSV);
        CSVLoader.loadOggettiFromCSV(OGGETTI_CSV);
        CSVLoader.loadOggettiStanzeFromCSV(OGGETTI_STANZE_CSV);
        CSVLoader.loadMovimentiFromCSV(MOVIMENTI_CSV);
    }
}