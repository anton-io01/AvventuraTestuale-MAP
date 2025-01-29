package it.uniba.game.database.dao;

import it.uniba.game.entity.Oggetto;
import it.uniba.game.database.DatabaseManager;

import java.sql.*;

public class OggettoDAO {

    private final Connection connection;

    public OggettoDAO() {
        try {
            this.connection = DatabaseManager.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("Errore durante l'ottenimento della connessione dal DatabaseManager", e);
        }
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
                            rs.getString("nome")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Errore durante l'ottenimento dell'oggetto per ID.");
            e.printStackTrace();
        }
        return null;
    }

    // Metodo per ottenere l'id dell'oggetto tramite il nome
    public String getOggettoIdByNome(String nome) {
        String query = "SELECT oggetto_id FROM OggettiAlias WHERE alias = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, nome);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("oggetto_id");
                }
            }
        } catch (SQLException e) {
            System.err.println("Errore durante la ricerca dell'oggetto per nome.");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Aggiunge un oggetto a una stanza nella tabella OggettiStanze.
     *
     * @param oggettoId ID dell'oggetto.
     * @param stanzaId  ID della stanza.
     */
    public void aggiungiOggettoAStanza(String oggettoId, String stanzaId) {
        String query = "INSERT INTO OggettiStanze (oggetto_id, stanza_id) VALUES (?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, oggettoId);
            preparedStatement.setString(2, stanzaId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Errore durante l'aggiunta dell'oggetto alla stanza: " + e.getMessage());
        }
    }

    /**
     * Elimina un oggetto da una stanza nella tabella OggettiStanze.
     * @param oggettoId
     * @param stanzaId
     */
    public void eliminaOggettoDaStanza(String oggettoId, String stanzaId) {
        String query = """
        DELETE FROM OggettiStanze
        WHERE oggetto_id = ? AND stanza_id = ?
    """;

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            // Imposta i parametri della query
            statement.setString(1, oggettoId);
            statement.setString(2, stanzaId);

            // Esegui l'operazione
            int rowsAffected = statement.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Errore durante l'eliminazione dell'oggetto " + oggettoId +
                    " dalla stanza " + stanzaId + ": " + e.getMessage());
        }
    }

    /**
     * Modifica il parametro visibile di un oggetto.
     *
     * @param oggettoId ID dell'oggetto
     * @param visibile true se l'oggetto deve essere visibile, false altrimenti
     */
    public void setVisibilitaOggetto(String oggettoId, boolean visibile) {
        String query = "UPDATE Oggetti SET visibile = ? WHERE oggetto_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setBoolean(1, visibile);
            statement.setString(2, oggettoId);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Errore durante l'aggiornamento della visibilità dell'oggetto: " + e.getMessage());
        }
    }

    /**
     * Modifica il parametro raccoglibile di un oggetto.
     *
     * @param oggettoId ID dell'oggetto
     * @param raccoglibile true se l'oggetto deve essere raccoglibile, false altrimenti
     */
    public void setRaccoglibilitaOggetto(String oggettoId, boolean raccoglibile) {
        String query = "UPDATE Oggetti SET raccoglibile = ? WHERE oggetto_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setBoolean(1, raccoglibile);
            statement.setString(2, oggettoId);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Errore durante l'aggiornamento della raccoglibilità dell'oggetto: " + e.getMessage());
        }
    }

    /**
     * Verifica se l'oggetto è raccoglibile e presente nella stanza specificata.
     *
     * @param oggettoId l'ID dell'oggetto da verificare
     * @param stanzaId  l'ID della stanza in cui verificare la presenza dell'oggetto
     * @return true se l'oggetto è raccoglibile e presente nella stanza, false altrimenti
     */
    public boolean isOggettoRaccoglibile(String oggettoId, String stanzaId) {
        String query = """
            SELECT COUNT(*) 
            FROM Oggetti o
            JOIN OggettiStanze os ON o.oggetto_id = os.oggetto_id
            WHERE o.oggetto_id = ? 
              AND os.stanza_id = ? 
              AND o.raccoglibile = true
              AND o.visibile = true
        """;

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            // Assegna i parametri alla query
            statement.setString(1, oggettoId);
            statement.setString(2, stanzaId);

            // Esegue la query
            try (ResultSet resultSet = statement.executeQuery()) {
                // Se il risultato è maggiore di 0, allora esiste almeno un oggetto raccoglibile nella stanza
                if (resultSet.next()) {
                    return resultSet.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Gestione dell'errore o rilancio come eccezione personalizzata
        }

        return false; // Default: se qualcosa fallisce, ritorna false
    }

    /**
     * Restituisce la descrizione breve di un oggetto.
     *
     * @param oggettoId ID dell'oggetto
     */
    public String getDescrizioneBreveOggetto(String oggettoId) {
        String query = "SELECT descrizione_breve FROM DescrizioniOggetti WHERE oggetto_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, oggettoId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("descrizione_breve");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Restituisce la descrizione dettagliata di un oggetto.
     *
     * @param oggettoId ID dell'oggetto
     */
    public String getDescrizioneDettagliataOggetto(String oggettoId) {
        String query = "SELECT descrizione_esamina FROM DescrizioniOggetti WHERE oggetto_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, oggettoId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("descrizione_esamina");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}