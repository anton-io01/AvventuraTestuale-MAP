package it.uniba.game.database.dao;

import it.uniba.game.entity.Stanza;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StanzaDAO {

    private final Connection connection;

    public StanzaDAO(Connection connection) {
        this.connection = connection;
    }

    // Metodo per inserire una nuova stanza nel database
    public void inserisciStanza(String edificioId, String stanzaId, String nome, boolean accessibile) {
        String query = "INSERT INTO Stanze (edificio_id, stanza_id, nome, accessibile) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, edificioId);
            pstmt.setString(2, stanzaId);
            pstmt.setString(3, nome);
            pstmt.setBoolean(4, accessibile);
            pstmt.executeUpdate();
            System.out.println("Stanza inserita con successo.");
        } catch (SQLException e) {
            System.err.println("Errore durante l'inserimento della stanza.");
            e.printStackTrace();
        }
    }

    // Metodo per ottenere tutte le stanze dal database
    public List<Stanza> getAllStanze() {
        List<Stanza> stanze = new ArrayList<>();
        String query = "SELECT * FROM Stanze";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Stanza stanza = new Stanza(
                        rs.getString("edificio_id"),
                        rs.getString("stanza_id"),
                        rs.getString("nome"),
                        rs.getBoolean("accessibile")
                );
                stanze.add(stanza);
            }
        } catch (SQLException e) {
            System.err.println("Errore durante l'ottenimento delle stanze.");
            e.printStackTrace();
        }
        return stanze;
    }

    // Metodo per ottenere una stanza specifica tramite ID
    public Stanza getStanzaById(String stanzaId) {
        String query = "SELECT * FROM Stanze WHERE stanza_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, stanzaId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Stanza(
                            rs.getString("edificio_id"),
                            rs.getString("stanza_id"),
                            rs.getString("nome"),
                            rs.getBoolean("accessibile")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Errore durante l'ottenimento della stanza per ID.");
            e.printStackTrace();
        }
        return null;
    }

    // Metodo per ottenere l'edificio di una stanza tramite stanza_id
    public String getEdificioByStanza(String stanzaId) {
        String query = "SELECT edificio_id FROM Stanze WHERE stanza_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, stanzaId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("edificio_id");
                }
            }
        } catch (SQLException e) {
            System.err.println("Errore durante l'ottenimento dell'edificio per stanza.");
            e.printStackTrace();
        }
        return null;
    }

    // Metodo per ottenere le stanze di un edificio
    public List<Stanza> getStanzeByEdificio(String edificioId) {
        List<Stanza> stanze = new ArrayList<>();
        String query = "SELECT * FROM Stanze WHERE edificio_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, edificioId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Stanza stanza = new Stanza(
                            rs.getString("edificio_id"),
                            rs.getString("stanza_id"),
                            rs.getString("nome"),
                            rs.getBoolean("accessibile")
                    );
                    stanze.add(stanza);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return stanze;
    }

    // Metodo per aggiornare una stanza
    public void updateStanza(String edificioId, String stanzaId, String nome, boolean accessibile) {
        String query = "UPDATE Stanze SET nome = ?, accessibile = ? WHERE edificio_id = ? AND stanza_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, nome);
            pstmt.setBoolean(2, accessibile);
            pstmt.setString(3, edificioId);
            pstmt.setString(4, stanzaId);
            pstmt.executeUpdate();
            System.out.println("Stanza aggiornata con successo.");
        } catch (SQLException e) {
            System.err.println("Errore durante l'aggiornamento della stanza.");
            e.printStackTrace();
        }
    }

    // Metodo per eliminare una stanza
    public void deleteStanza(String stanzaId) {
        String query = "DELETE FROM Stanze WHERE stanza_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, stanzaId);
            pstmt.executeUpdate();
            System.out.println("Stanza eliminata con successo.");
        } catch (SQLException e) {
            System.err.println("Errore durante l'eliminazione della stanza.");
            e.printStackTrace();
        }
    }

    /**
     * Metodo per ottenere la descrizione completa di una stanza dato il suo ID.
     *
     * @param stanzaId L'ID della stanza da cercare.
     */
    public void getDescrizioneCompleta(String stanzaId) {
        String query = "SELECT descrizione_completa FROM DescrizioniStanze WHERE stanza_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, stanzaId); // Imposta il parametro stanza_id
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    String descrizioneCompleta = resultSet.getString("descrizione_completa");
                    System.out.println("Descrizione completa della stanza con ID " + stanzaId + ": " + descrizioneCompleta);
                } else {
                    System.out.println("Nessuna descrizione trovata per la stanza con ID " + stanzaId);
                }
            }
        } catch (SQLException e) {
            System.err.println("Errore durante il recupero della descrizione completa: " + e.getMessage());
        }
    }

    /**
     * Metodo per ottenere la descrizione breve di una stanza dato il suo ID.
     *
     * @param stanzaId L'ID della stanza da cercare.
     */
    public void getDescrizioneBreve(String stanzaId) {
        String query = "SELECT descrizione_breve FROM DescrizioniStanze WHERE stanza_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, stanzaId); // Imposta il parametro stanza_id
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    String descrizioneCompleta = resultSet.getString("descrizione_completa");
                    System.out.println("Descrizione completa della stanza con ID " + stanzaId + ": " + descrizioneCompleta);
                } else {
                    System.out.println("Nessuna descrizione trovata per la stanza con ID " + stanzaId);
                }
            }
        } catch (SQLException e) {
            System.err.println("Errore durante il recupero della descrizione completa: " + e.getMessage());
        }
    }

}
