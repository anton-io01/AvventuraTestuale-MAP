package it.uniba.game.database.dao;

import it.uniba.game.entity.Edificio;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EdificioDAO {

    private final Connection connection;

    public EdificioDAO(Connection connection) {
        this.connection = connection;
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

    // Metodo per aggiornare un edificio
    public void updateEdificio(String edificioId, String nome, boolean accessibile, String descrizione) {
        String query = "UPDATE Edifici SET nome = ?, accessibile = ?, descrizione = ? WHERE edificio_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, nome);
            pstmt.setBoolean(2, accessibile);
            pstmt.setString(3, descrizione);
            pstmt.setString(4, edificioId);
            pstmt.executeUpdate();
            System.out.println("Edificio aggiornato con successo.");
        } catch (SQLException e) {
            System.err.println("Errore durante l'aggiornamento dell'edificio.");
            e.printStackTrace();
        }
    }

    // Metodo per eliminare un edificio
    public void deleteEdificio(String edificioId) {
        String query = "DELETE FROM Edifici WHERE edificio_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, edificioId);
            pstmt.executeUpdate();
            System.out.println("Edificio eliminato con successo.");
        } catch (SQLException e) {
            System.err.println("Errore durante l'eliminazione dell'edificio.");
            e.printStackTrace();
        }
    }
}
