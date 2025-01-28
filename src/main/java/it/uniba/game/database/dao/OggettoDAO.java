package it.uniba.game.database.dao;

import it.uniba.game.entity.Oggetto;
import it.uniba.game.entity.Stanza;

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

    // Metodo per ottenere l'id dell'oggetto tramite il nome
    public String getOggettoIdByNome(String nome) {
        String query = "SELECT oggetto_id FROM Oggetti WHERE nome = ?";
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
     * Verifica se l'oggetto si può leggere nella stanza specificata.
     *
     * @param oggettoId l'ID dell'oggetto da verificare
     * @param stanzaId  l'ID della stanza in cui verificare la presenza dell'oggetto
     */
    public boolean isOggettoLeggibile(String oggettoId, String stanzaId) {
        String query = """
            SELECT COUNT(*) 
            FROM Oggetti o
            JOIN OggettiStanze os ON o.oggetto_id = os.oggetto_id
            WHERE o.oggetto_id = ? 
              AND os.stanza_id = ? 
              AND o.descrizione IS NOT NULL
        """;

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            // Assegna i parametri alla query
            statement.setString(1, oggettoId);
            statement.setString(2, stanzaId);

            // Esegue la query
            try (ResultSet resultSet = statement.executeQuery()) {
                // Se il risultato è maggiore di 0, allora esiste almeno un oggetto leggibile nella stanza
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

}