// it/uniba/game/database/DatabaseInitializer.java
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
    private static final String OGGETTI_ALIAS_CSV = "src/main/resources/csv/oggetti_alias.csv";
    private static final String OGGETTI_STANZE_CSV = "src/main/resources/csv/oggetti_stanze.csv";
    private static final String DESCRIZIONI_OGGETTI_CSV = "src/main/resources/csv/descrizioni_oggetti.csv";
    private static final String MOVIMENTI_CSV = "src/main/resources/csv/movimenti.csv";
    private static final String AZIONI_CSV = "src/main/resources/csv/azioni.csv";
    private static final String AZIONI_INTERAZIONE_CSV = "src/main/resources/csv/azioni_interazione.csv";

    /**
     * Metodo principale per inizializzare il database.
     * - Crea il database e le tabelle se non esistono, o se force è true
     * - Carica i dati iniziali dai file CSV.
     * @param force boolean, se true il database viene reinizializzato, se false solo se non esiste
     */
    public static void initializeDatabase(boolean force) {

        Connection connection = null; //inizializzo una connection per lo scope della funzione
        Statement stmt = null;


        if (!force && isDatabaseCreated()) {
            System.out.println("Il database esiste già. Nessuna operazione necessaria.");
            return;
        }
        try {
            connection = DatabaseManager.getConnection();
            stmt = connection.createStatement();

            //Se il database è gia stato creato viene prima distrutto, successivamente si creano nuove tabelle
            if(force){
                dropTables(stmt);
            }
            // Creazione delle tabelle
            createTables(stmt,force); //passaggio variabile force
            // Caricamento dei dati iniziali
            loadInitialData();

            if (force) {
                System.out.println("Database reinizializzato con successo!");
            } else {
                System.out.println("Database creato e inizializzato con successo!");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Chiude le risorse nel blocco finally
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (Exception e){
                e.printStackTrace();
            }


        }

    }


    /**
     * Controlla se il database è già stato creato.
     *
     * @return true se il database esiste, false altrimenti.
     */
    private static boolean isDatabaseCreated() {

        Connection connection = null;
        Statement stmt = null;
        ResultSet rs = null;


        try {
            connection = DatabaseManager.getConnection();
            stmt = connection.createStatement();
            rs = stmt.executeQuery("SELECT * FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME = 'EDIFICI'");
            return rs.next(); // Ritorna true se la tabella "Edifici" esiste

        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }  finally {
            try {
                if(rs!= null) rs.close();
                if(stmt!= null) stmt.close();
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch(Exception e) {
                e.printStackTrace();
            }


        }
    }


    private static void dropTables(Statement stmt) {

        try{
            String[] tables = {"AzioniInterazione","Azioni","Movimenti",
                    "DescrizioniOggetti",
                    "OggettiStanze","OggettiAlias","Oggetti",
                    "DescrizioniStanze","Stanze", "Edifici"};


            for(String table: tables){
                String dropTable = "DROP TABLE IF EXISTS " + table;
                stmt.execute(dropTable);

            }
            System.out.println("Tabelle eliminate con successo");
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }
    /**
     * Crea le tabelle nel database.
     *
     * @param stmt Statement da usare per le query SQL.
     * @param force variabile che indica se siamo in una nuova partita o no
     * @throws Exception se si verifica un errore nella creazione delle tabelle.
     */
    private static void createTables(Statement stmt, boolean force) throws Exception {
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
                    oggetto_id VARCHAR(2) PRIMARY KEY,
                    nome VARCHAR(100) NOT NULL,
                    raccoglibile BOOLEAN DEFAULT false,
                    visibile BOOLEAN DEFAULT true
                );
                """;

        String oggettiAliasTable = """
                CREATE TABLE IF NOT EXISTS OggettiAlias (
                    oggetto_id VARCHAR(2) NOT NULL,
                    alias VARCHAR(100) NOT NULL,
                    PRIMARY KEY (oggetto_id, alias),
                    FOREIGN KEY (oggetto_id) REFERENCES Oggetti(oggetto_id)
                );
                """;

        String oggettiStanzeTable = """
                CREATE TABLE IF NOT EXISTS OggettiStanze (
                    oggetto_id VARCHAR(2) NOT NULL,
                    stanza_id VARCHAR(2) NOT NULL,
                    PRIMARY KEY (oggetto_id, stanza_id),
                    FOREIGN KEY (oggetto_id) REFERENCES Oggetti(oggetto_id),
                    FOREIGN KEY (stanza_id) REFERENCES Stanze(stanza_id)
                );
                """;

        String DescrizioneOggetti = """
                CREATE TABLE IF NOT EXISTS DescrizioniOggetti (
                    oggetto_id VARCHAR(2) PRIMARY KEY,
                    descrizione_breve TEXT,
                    descrizione_esamina TEXT,
                    PRIMARY KEY (oggetto_id),
                    FOREIGN KEY (oggetto_id) REFERENCES Oggetti(oggetto_id)
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

        String azioniTable = """
                CREATE TABLE IF NOT EXISTS Azioni (
                    azione_id VARCHAR(2) PRIMARY KEY,
                    nome VARCHAR(100) NOT NULL,
                    categoria VARCHAR(20) NOT NULL
                );
                """;

        String azioniInterazioneTable = """
                CREATE TABLE IF NOT EXISTS AzioniInterazione (
                    azione_id VARCHAR(2) NOT NULL,
                    oggetto_id VARCHAR(2) NOT NULL,
                    stanza_id VARCHAR(2),
                    FOREIGN KEY (azione_id) REFERENCES Azioni(azione_id),
                    FOREIGN KEY (oggetto_id) REFERENCES Oggetti(oggetto_id)
                );
                """;

        stmt.execute(edificiTable);
        stmt.execute(stanzeTable);
        stmt.execute(descrizioniStanzeTable);
        stmt.execute(oggettiTable);
        stmt.execute(oggettiAliasTable);
        stmt.execute(oggettiStanzeTable);
        stmt.execute(DescrizioneOggetti);
        stmt.execute(movimentiTable);
        stmt.execute(azioniTable);
        stmt.execute(azioniInterazioneTable);

        if (force) {
            System.out.println("Tabelle ricreate con successo.");

        } else {
            System.out.println("Tabelle create con successo.");
        }


    }

    /**
     * Carica i dati iniziali dai file CSV.
     */
    private static void loadInitialData() {
        CSVLoader.loadEdificiFromCSV(EDIFICI_CSV);
        CSVLoader.loadStanzeFromCSV(STANZE_CSV);
        CSVLoader.loadDescrizioniStanzeFromCSV(DESCRIZIONI_STANZE_CSV);
        CSVLoader.loadOggettiFromCSV(OGGETTI_CSV);
        CSVLoader.loadOggettiAliasFromCSV(OGGETTI_ALIAS_CSV);
        CSVLoader.loadOggettiStanzeFromCSV(OGGETTI_STANZE_CSV);
        CSVLoader.loadDescrizioniOggettiFromCSV(DESCRIZIONI_OGGETTI_CSV);
        CSVLoader.loadMovimentiFromCSV(MOVIMENTI_CSV);
        CSVLoader.loadAzioniFromCSV(AZIONI_CSV);
        CSVLoader.loadAzioniOggettiFromCSV(AZIONI_INTERAZIONE_CSV);
    }
}