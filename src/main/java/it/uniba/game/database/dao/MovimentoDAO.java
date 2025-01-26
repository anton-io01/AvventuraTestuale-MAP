package it.uniba.game.database.dao;

import it.uniba.game.entity.Movimento;
import it.uniba.game.entity.Stanza;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MovimentoDAO {
    private final Connection connection;

    /**
     * Costruttore che accetta una connessione al database.
     *
     * @param connection La connessione al database da utilizzare.
     */
    public MovimentoDAO(Connection connection) {
        this.connection = connection;
    }

    /**
     * Ottiene i movimenti per una determinata stanza di un edificio.
     *
     * @param edificioId L'ID dell'edificio.
     * @param stanzaId   L'ID della stanza.
     * @return Una lista di oggetti Movimento contenenti i movimenti della stanza.
     */
    public List<Movimento> getMovimentiByStanza(String edificioId, String stanzaId) {
        List<Movimento> movimenti = new ArrayList<>();
        String query = "SELECT * FROM Movimenti WHERE edificio_id = ? AND stanza_id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, edificioId);
            pstmt.setString(2, stanzaId);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    String nord = rs.getString("nord");
                    if (nord != null) {
                        movimenti.add(new Movimento(edificioId, stanzaId, "nord", nord));
                    }
                    String sud = rs.getString("sud");
                    if (sud != null) {
                        movimenti.add(new Movimento(edificioId, stanzaId, "sud", sud));
                    }
                    String est = rs.getString("est");
                    if (est != null) {
                        movimenti.add(new Movimento(edificioId, stanzaId, "est", est));
                    }
                    String ovest = rs.getString("ovest");
                    if (ovest != null) {
                        movimenti.add(new Movimento(edificioId, stanzaId, "ovest", ovest));
                    }
                    String alto = rs.getString("alto");
                    if (alto != null) {
                        movimenti.add(new Movimento(edificioId, stanzaId, "alto", alto));
                    }
                    String basso = rs.getString("basso");
                    if (basso != null) {
                        movimenti.add(new Movimento(edificioId, stanzaId, "basso", basso));
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Errore durante il recupero dei movimenti per la stanza " + stanzaId +
                    " nell'edificio " + edificioId + ": " + e.getMessage());
            e.printStackTrace();
        }

        return movimenti;
    }

    /**
     * Restituisce la stanza di arrivo per una determinata direzione.
     *
     * @param edificioId L'ID dell'edificio.
     * @param stanzaId   L'ID della stanza di partenza.
     * @param direzione  La direzione del movimento.
     * @return La stanza di arrivo o null se il movimento non è possibile.
     */
    public Stanza getStanzaDiArrivo(String edificioId, String stanzaId, String direzione) {
        String query = "SELECT " + direzione + " FROM Movimenti WHERE edificio_id = ? AND stanza_id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, edificioId);
            pstmt.setString(2, stanzaId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    String stanzaArrivoId = rs.getString(1);
                    if (stanzaArrivoId != null) {
                        // Recupera la stanza dal suo DAO
                        StanzaDAO stanzaDAO = new StanzaDAO(connection);
                        return stanzaDAO.getStanzaById(edificioId, stanzaArrivoId);
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
