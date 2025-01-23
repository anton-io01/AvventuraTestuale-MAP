package it.uniba.game.database.dao;

import it.uniba.game.entity.Movimento;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MovimentoDAO {

    private final Connection connection;

    public MovimentoDAO(Connection connection) {
        this.connection = connection;
    }

    // Metodo per inserire un nuovo movimento nel database
    public void insertMovimento(String edificioId, String stanzaPartenza, String direzione, String stanzaArrivo) {
        String query = "INSERT INTO Movimenti (edificio_id, stanza_partenza, direzione, stanza_arrivo) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, edificioId);
            pstmt.setString(2, stanzaPartenza);
            pstmt.setString(3, direzione);
            pstmt.setString(4, stanzaArrivo);
            pstmt.executeUpdate();
            System.out.println("Movimento inserito con successo.");
        } catch (SQLException e) {
            System.err.println("Errore durante l'inserimento del movimento.");
            e.printStackTrace();
        }
    }

    // Metodo per ottenere tutti i movimenti dal database
    public List<Movimento> getAllMovimenti() {
        List<Movimento> movimenti = new ArrayList<>();
        String query = "SELECT * FROM Movimenti";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Movimento movimento = new Movimento(
                        rs.getString("edificio_id"),
                        rs.getString("stanza_partenza"),
                        rs.getString("direzione"),
                        rs.getString("stanza_arrivo")
                );
                movimenti.add(movimento);
            }
        } catch (SQLException e) {
            System.err.println("Errore durante l'ottenimento dei movimenti.");
            e.printStackTrace();
        }
        return movimenti;
    }

    // Metodo per ottenere i movimenti da una stanza specifica
    public List<Movimento> getMovimentiByStanza(String edificioId, String stanzaPartenza) {
        List<Movimento> movimenti = new ArrayList<>();
        String query = "SELECT * FROM Movimenti WHERE edificio_id = ? AND stanza_partenza = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, edificioId);
            pstmt.setString(2, stanzaPartenza);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Movimento movimento = new Movimento(
                            rs.getString("edificio_id"),
                            rs.getString("stanza_partenza"),
                            rs.getString("direzione"),
                            rs.getString("stanza_arrivo")
                    );
                    movimenti.add(movimento);
                }
            }
        } catch (SQLException e) {
            System.err.println("Errore durante l'ottenimento dei movimenti dalla stanza specificata.");
            e.printStackTrace();
        }
        return movimenti;
    }

    // Metodo per eliminare un movimento
    public void deleteMovimento(String edificioId, String stanzaPartenza, String direzione) {
        String query = "DELETE FROM Movimenti WHERE edificio_id = ? AND stanza_partenza = ? AND direzione = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, edificioId);
            pstmt.setString(2, stanzaPartenza);
            pstmt.setString(3, direzione);
            pstmt.executeUpdate();
            System.out.println("Movimento eliminato con successo.");
        } catch (SQLException e) {
            System.err.println("Errore durante l'eliminazione del movimento.");
            e.printStackTrace();
        }
    }
}
