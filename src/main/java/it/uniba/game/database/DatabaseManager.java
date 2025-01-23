package it.uniba.game.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseManager {
    private static final String URL = "jdbc:h2:./gamedb"; // Percorso del database
    private static final String USER = "user"; // Nome utente
    private static final String PASSWORD = "1234"; // Password

    /**
     * Ottiene una connessione al database usando la classe Properties.
     *
     * @return Connection oggetto per la connessione al database
     * @throws SQLException in caso di errori durante la connessione
     */
    public static Connection getConnection() throws SQLException {
        Properties dbprops = new Properties();
        dbprops.setProperty("user", USER);
        dbprops.setProperty("password", PASSWORD);

        // Connessione al database
        return DriverManager.getConnection(URL, dbprops);
    }
}

