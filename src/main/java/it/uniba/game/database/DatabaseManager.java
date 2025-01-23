package it.uniba.game.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {

    // URL del database
    private static final String DB_URL = "jdbc:sqlite:game_database.db";

    // Connessione singleton
    private static Connection connection = null;

    // Metodo per ottenere la connessione al database
    public static Connection getConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(DB_URL);
                System.out.println("Connessione al database stabilita.");
            } catch (SQLException e) {
                System.err.println("Errore durante la connessione al database.");
                e.printStackTrace();
            }
        }
        return connection;
    }

    // Metodo per chiudere la connessione al database
    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
                System.out.println("Connessione al database chiusa.");
            } catch (SQLException e) {
                System.err.println("Errore durante la chiusura della connessione al database.");
                e.printStackTrace();
            }
        }
    }

    // Metodo per ottenere l'istanza di un DAO
    /*public static EdificioDAO getEdificioDAO() {
        return new EdificioDAO(getConnection());
    }

    public static StanzaDAO getStanzaDAO() {
        return new StanzaDAO(getConnection());
    }

    public static OggettoDAO getOggettoDAO() {
        return new OggettoDAO(getConnection());
    }

    public static MovimentoDAO getMovimentoDAO() {
        return new MovimentoDAO(getConnection());
    }*/
}
