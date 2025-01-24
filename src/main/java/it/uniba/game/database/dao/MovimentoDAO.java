package it.uniba.game.database.dao;

import it.uniba.game.entity.Movimento;

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
     * Elimina i movimenti associati a una stanza in un edificio.
     *
     * @param edificioId L'ID dell'edificio.
     * @param stanzaId   L'ID della stanza.
     * @return true se almeno un movimento è stato eliminato, false altrimenti.
     */
    public boolean deleteMovimentiByStanza(String edificioId, String stanzaId) {
        String query = "DELETE FROM Movimenti WHERE edificio_id = ? AND stanza_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, edificioId);
            stmt.setString(2, stanzaId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Errore durante l'eliminazione dei movimenti per la stanza " + stanzaId +
                    " nell'edificio " + edificioId + ": " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}
