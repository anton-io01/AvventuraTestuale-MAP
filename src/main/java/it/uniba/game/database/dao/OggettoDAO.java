package it.uniba.game.database.dao;

import it.uniba.game.entity.Oggetto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OggettoDAO {

    private final Connection connection;

    public OggettoDAO(Connection connection) {
        this.connection = connection;
    }

    // Metodo per inserire un nuovo oggetto nel database
    public void insertOggetto(String oggettoId, String nome, String descrizione, boolean raccoglibile) {
        String query = "INSERT INTO Oggetti (oggetto_id, nome, descrizione, raccoglibile) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, oggettoId);
            pstmt.setString(2, nome);
            pstmt.setString(3, descrizione);
            pstmt.setBoolean(4, raccoglibile);
            pstmt.executeUpdate();
            System.out.println("Oggetto inserito con successo.");
        } catch (SQLException e) {
            System.err.println("Errore durante l'inserimento dell'oggetto.");
            e.printStackTrace();
        }
    }

    // Metodo per ottenere tutti gli oggetti dal database
    public List<Oggetto> getAllOggetti() {
        List<Oggetto> oggetti = new ArrayList<>();
        String query = "SELECT * FROM Oggetti";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Oggetto oggetto = new Oggetto(
                        rs.getString("oggetto_id"),
                        rs.getString("nome"),
                        rs.getString("descrizione"),
                        rs.getBoolean("raccoglibile")
                );
                oggetti.add(oggetto);
            }
        } catch (SQLException e) {
            System.err.println("Errore durante l'ottenimento degli oggetti.");
            e.printStackTrace();
        }
        return oggetti;
    }

    // Metodo per ottenere un oggetto specifico tramite ID
    public Oggetto getOggettoById(String oggettoId) {
        String query = "SELECT * FROM Oggetti WHERE oggetto_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, oggettoId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Oggetto(
                            rs.getString("oggetto_id"),
                            rs.getString("nome"),
                            rs.getString("descrizione"),
                            rs.getBoolean("raccoglibile")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Errore durante l'ottenimento dell'oggetto per ID.");
            e.printStackTrace();
        }
        return null;
    }

    // Metodo per aggiornare un oggetto
    public void updateOggetto(String oggettoId, String nome, String descrizione, boolean raccoglibile) {
        String query = "UPDATE Oggetti SET nome = ?, descrizione = ?, raccoglibile = ? WHERE oggetto_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, nome);
            pstmt.setString(2, descrizione);
            pstmt.setBoolean(3, raccoglibile);
            pstmt.setString(4, oggettoId);
            pstmt.executeUpdate();
            System.out.println("Oggetto aggiornato con successo.");
        } catch (SQLException e) {
            System.err.println("Errore durante l'aggiornamento dell'oggetto.");
            e.printStackTrace();
        }
    }

    // Metodo per eliminare un oggetto
    public void deleteOggetto(String oggettoId) {
        String query = "DELETE FROM Oggetti WHERE oggetto_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, oggettoId);
            pstmt.executeUpdate();
            System.out.println("Oggetto eliminato con successo.");
        } catch (SQLException e) {
            System.err.println("Errore durante l'eliminazione dell'oggetto.");
            e.printStackTrace();
        }
    }
}
