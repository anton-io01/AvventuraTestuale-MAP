package it.uniba.game.database.dao;

import java.sql.*;

public class AzioneDAO {

    private Connection connection;

    // Costruttore che accetta una connessione al database
    public AzioneDAO(Connection connection) {
        this.connection = connection;
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

    // Restituisce l'azione_id se la combinazione esiste
    public String getAzioneIdSeCombinazionePresente(String azioneId, String oggettoId) throws SQLException {
        // Controlliamo prima se la combinazione esiste
        String queryCheckCombinazione = "SELECT COUNT(*) FROM AzioniInterazione WHERE azione_id = ? AND oggetto_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(queryCheckCombinazione)) {
            stmt.setString(1, azioneId);
            stmt.setString(2, oggettoId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                // Se la combinazione è presente, otteniamo l'azione_id
                String queryGetAzioneId = "SELECT azione_id FROM Azioni WHERE azione_id = ?";
                try (PreparedStatement stmt2 = connection.prepareStatement(queryGetAzioneId)) {
                    stmt2.setString(1, azioneId);
                    ResultSet rs2 = stmt2.executeQuery();
                    if (rs2.next()) {
                        return rs2.getString("azione_id");
                    }
                }
            }
        }
        return null;  // Restituisce null se non trova il valore
    }

    // Restituisce l'azione_id dato un alias
    public String getAzioneIdPerAlias(String alias) throws SQLException {
        String query = "SELECT azione_id FROM Azioni WHERE alias = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, alias);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("azione_id");
            }
        }
        return null;  // Restituisce null se non trova il valore
    }

    /**
     * Restituisce la descrizione dell'AzioneInterazione
     * @param azioneId
     * @param oggettoId
     * @param stanzaId
     * @return la descrizione dell'azione
     */
    public String getDescrizioneAzioneInterazione(String azioneId, String oggettoId, String stanzaId) {
        String query = "SELECT descrizione FROM AzioniInterazione WHERE azione_id = ? AND oggetto_id = ? AND stanza_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, azioneId);
            stmt.setString(2, oggettoId);
            stmt.setString(3, stanzaId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("descrizione");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
