package it.uniba.game.database;

import it.uniba.game.database.dao.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {
    private static final String DB_URL = "jdbc:h2:./src/main/resources/db/game_database"; // Percorso embedded
    private static final String DB_USERNAME = "admin"; // Username predefinito di H2
    private static final String DB_PASSWORD = "password";   // Password vuota per impostazione predefinita
    private static Connection connection;

    /**
     * Metodo per ottenere la connessione al database.
     *
     * @return un'istanza valida di Connection
     * @throws SQLException se si verifica un errore nella connessione
     */
    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                // Registra il driver H2 (necessario per vecchie versioni di Java o H2)
                Class.forName("org.h2.Driver");
            } catch (ClassNotFoundException e) {
                throw new SQLException("Driver H2 non trovato.", e);
            }
            // Crea la connessione
            connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
        }
        return connection;
    }

    /**
     * Metodo per chiudere la connessione al database.
     */
    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
