package it.uniba.game.database.dao;

import it.uniba.game.entity.Edificio;
import it.uniba.game.database.DatabaseManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EdificioDAO {

    private final Connection connection;

    public EdificioDAO() {
        try {
            this.connection = DatabaseManager.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("Errore durante l'ottenimento della connessione dal DatabaseManager", e);
        }
    }

    // Metodo per inserire un nuovo edificio nel database
    public void insertEdificio(String edificioId, String nome, boolean accessibile, String descrizione) {
        String query = "INSERT INTO Edifici (edificio_id, nome, accessibile, descrizione) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, edificioId);
            pstmt.setString(2, nome);
            pstmt.setBoolean(3, accessibile);
            pstmt.setString(4, descrizione);
            pstmt.executeUpdate();
            System.out.println("Edificio inserito con successo.");
        } catch (SQLException e) {
            System.err.println("Errore durante l'inserimento dell'edificio.");
            e.printStackTrace();
        }
    }

    // Metodo per ottenere tutti gli edifici dal database
    public List<Edificio> getAllEdifici() {
        List<Edificio> edifici = new ArrayList<>();
        String query = "SELECT * FROM Edifici";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Edificio edificio = new Edificio(
                        rs.getString("edificio_id"),
                        rs.getString("nome"),
                        rs.getBoolean("accessibile"),
                        rs.getString("descrizione")
                );
                edifici.add(edificio);
            }
        } catch (SQLException e) {
            System.err.println("Errore durante l'ottenimento degli edifici.");
            e.printStackTrace();
        }
        return edifici;
    }

    // Metodo per ottenere un edificio per ID
    public Edificio getEdificioById(String edificioId) {
        String query = "SELECT * FROM Edifici WHERE edificio_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, edificioId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Edificio(
                            rs.getString("edificio_id"),
                            rs.getString("nome"),
                            rs.getBoolean("accessibile"),
                            rs.getString("descrizione")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Errore durante l'ottenimento dell'edificio per ID.");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Controlla se un edificio è accessibile.
     * @param edificioId
     * @return
     */
    public Boolean isEdificioAccessibile(String edificioId) {
        String query = "SELECT accessibile FROM Edifici WHERE edificio_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, edificioId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getBoolean("accessibile");
                }
            }
        } catch (SQLException e) {
            System.err.println("Errore durante il controllo dell'accessibilità dell'edificio.");
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Imposta l'accessibilità di un edificio.
     * @param edificioId
     * @param accessibile
     */
    public void setEdificioAccessibilita(String edificioId, boolean accessibile) {
        String query = "UPDATE Edifici SET accessibile = ? WHERE edificio_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setBoolean(1, accessibile);
            pstmt.setString(2, edificioId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Errore durante l'aggiornamento dell'accessibilità dell'edificio.");
            e.printStackTrace();
        }
    }

}
