package it.uniba.game.database.dao;

import it.uniba.game.entity.Stanza;
import it.uniba.game.database.DatabaseManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MovimentoDAO {
    private final Connection connection;

    /**
     * Costruttore che accetta una connessione al database.
     */
    public MovimentoDAO() {
        try {
            this.connection = DatabaseManager.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("Errore durante l'ottenimento della connessione dal DatabaseManager", e);
        }
    }

    /**
     * Ottiene i movimenti disponibili per una determinata stanza e li restituisce come testo formattato.
     *
     * @param stanza La stanza di partenza.
     * @return Una stringa che descrive i movimenti disponibili con i nomi delle stanze di arrivo.
     */
    public String getMovimentiByStanza(Stanza stanza) {
        StringBuilder descrizioneMovimenti = new StringBuilder();
        String edificioId = stanza.getEdificioId();
        String stanzaId = stanza.getStanzaId();
        String query = "SELECT * FROM Movimenti WHERE edificio_id = ? AND stanza_id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, edificioId);
            pstmt.setString(2, stanzaId);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    if (rs.getString("nord") != null) {
                        Stanza stanzaArrivo = new StanzaDAO().getStanzaById(rs.getString("nord"));
                        descrizioneMovimenti.append("- A nord la stanza ").append(stanzaArrivo.getNome()).append(".\n");
                    }
                    if (rs.getString("sud") != null) {
                        Stanza stanzaArrivo = new StanzaDAO().getStanzaById(rs.getString("sud"));
                        descrizioneMovimenti.append("- A sud la stanza ").append(stanzaArrivo.getNome()).append(".\n");
                    }
                    if (rs.getString("est") != null) {
                        Stanza stanzaArrivo = new StanzaDAO().getStanzaById(rs.getString("est"));
                        descrizioneMovimenti.append("- A est la stanza ").append(stanzaArrivo.getNome()).append(".\n");
                    }
                    if (rs.getString("ovest") != null) {
                        Stanza stanzaArrivo = new StanzaDAO().getStanzaById(rs.getString("ovest"));
                        descrizioneMovimenti.append("- A ovest la stanza ").append(stanzaArrivo.getNome()).append(".\n");
                    }
                    if (rs.getString("alto") != null) {
                        Stanza stanzaArrivo = new StanzaDAO().getStanzaById(rs.getString("alto"));
                        descrizioneMovimenti.append("- In alto la stanza ").append(stanzaArrivo.getNome()).append(".\n");
                    }
                    if (rs.getString("basso") != null) {
                        Stanza stanzaArrivo = new StanzaDAO().getStanzaById(rs.getString("basso"));
                        descrizioneMovimenti.append("- In basso la stanza ").append(stanzaArrivo.getNome()).append(".\n");
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Errore durante il recupero dei movimenti per la stanza " + stanzaId +
                    " nell'edificio " + edificioId + ": " + e.getMessage());
            e.printStackTrace();
        }

        // Restituisce il risultato, oppure un messaggio se non ci sono movimenti
        if (descrizioneMovimenti.length() == 0) {
            return "Non ci sono movimenti disponibili per questa stanza.";
        }

        return descrizioneMovimenti.toString();
    }

    /**
     * Restituisce la stanza di arrivo per una determinata direzione.
     *
     * @param stanzaId   L'ID della stanza di partenza.
     * @param direzione  La direzione del movimento.
     * @return La stanza di arrivo o null se il movimento non è possibile.
     */
    public Stanza getStanzaDiArrivo(String stanzaId, String direzione) {
        String query = "SELECT " + direzione + " FROM Movimenti WHERE stanza_id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, stanzaId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    String stanzaArrivoId = rs.getString(1);
                    if (stanzaArrivoId != null) {
                        // Recupera la stanza dal suo DAO
                        StanzaDAO stanzaDAO = new StanzaDAO();
                        return stanzaDAO.getStanzaById(stanzaArrivoId);
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Errore durante il recupero della stanza di arrivo: " + e.getMessage());
            e.printStackTrace();
        }

        return null; // Movimento non disponibile
    }

}
