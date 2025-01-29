package it.uniba.game.database.dao;

import it.uniba.game.database.DatabaseManager;

import java.sql.*;

public class AzioneDAO {

    private Connection connection;

    // Costruttore che accetta una connessione al database
    public AzioneDAO() {
        try {
            this.connection = DatabaseManager.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("Errore durante l'ottenimento della connessione dal DatabaseManager", e);
        }
    }

    // Metodo per ottenere l'id dell'azione dato il nome
    public String getAzioneIdByNome(String nome) {
        String query = "SELECT azione_id FROM Azioni WHERE nome = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, nome);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("azione_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Metodo per ottenere il nome dell'azione dato l'id
    public String getNomeAzioneById(String azioneId) {
        String query = "SELECT nome FROM Azioni WHERE azione_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, azioneId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("nome");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Verifica se esiste una riga nella tabella AzioniInterazione con i valori specificati.
     *
     * @param azioneId  ID dell'azione
     * @param oggettoId ID dell'oggetto
     * @param stanzaId  ID della stanza
     * @return true se la combinazione esiste, false altrimenti
     */
    public boolean verificaAzioneInterazione(String azioneId, String oggettoId, String stanzaId) {
        String query = """
            SELECT COUNT(*) 
            FROM AzioniInterazione 
            WHERE azione_id = ? AND oggetto_id = ? AND (stanza_id = ? OR stanza_id IS NULL)
        """;

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, azioneId);
            ps.setString(2, oggettoId);
            ps.setString(3, stanzaId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Gestione dell'errore
        }

        return false;
    }
}
