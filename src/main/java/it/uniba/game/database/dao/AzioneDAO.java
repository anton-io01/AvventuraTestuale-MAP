package it.uniba.game.database;

import it.uniba.game.entity.Giocatore;
import java.sql.*;

public class AzioneDAO {

    private Connection connection;

    // Costruttore che accetta una connessione al database
    public AzioneDAO(Connection connection) {
        this.connection = connection;
    }

    // Verifica se un alias è presente nella tabella Azioni
    public boolean isAliasPresente(String alias) throws SQLException {
        String query = "SELECT COUNT(*) FROM Azioni WHERE alias = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, alias);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        }
        return false;
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
}
