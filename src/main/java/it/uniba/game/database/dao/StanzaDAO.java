package it.uniba.game.database.dao;

import it.uniba.game.entity.Stanza;
import it.uniba.game.entity.Edificio;
import it.uniba.game.database.DatabaseManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StanzaDAO {

    private final Connection connection;

    public StanzaDAO() {
        try {
            this.connection = DatabaseManager.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("Errore durante l'ottenimento della connessione dal DatabaseManager", e);
        }
    }

    /**
     * Metodo per ottenere lo stato di accessibilità di una stanza tramite il suo ID.
     *
     * @param stanzaId L'ID della stanza di cui si vuole conoscere l'accessibilità.
     * @return true se la stanza è accessibile, false altrimenti, o null se la stanza non esiste.
     */
    public Boolean isStanzaAccessibile(String stanzaId) {
        String query = "SELECT accessibile FROM Stanze WHERE stanza_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, stanzaId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getBoolean("accessibile");
                }
            }
        } catch (SQLException e) {
            System.err.println("Errore durante la verifica dell'accessibilità della stanza " + stanzaId + ": " + e.getMessage());
            e.printStackTrace();
            return null;
        }
        return null; // La stanza non è stata trovata
    }

    /**
     * Metodo per impostare lo stato di accessibilità di una stanza tramite il suo ID.
     *
     * @param stanzaId L'ID della stanza di cui si vuole modificare l'accessibilità.
     * @param accessibile Il nuovo stato di accessibilità (true per accessibile, false per non accessibile).
     */
    public void setStanzaAccessibile(String stanzaId, boolean accessibile) {
        String query = "UPDATE Stanze SET accessibile = ? WHERE stanza_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setBoolean(1, accessibile);
            pstmt.setString(2, stanzaId);
            int rowsAffected = pstmt.executeUpdate();
            if(rowsAffected > 0){
                System.out.println("Accessibilità della stanza " + stanzaId + " aggiornata con successo a " + accessibile + ".");
            } else {
                System.out.println("Nessuna stanza trovata con ID " + stanzaId);
            }
        } catch (SQLException e) {
            System.err.println("Errore durante l'aggiornamento dell'accessibilità della stanza " + stanzaId + ": " + e.getMessage());
            e.printStackTrace();
        }
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
                        rs.getString("nome")
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
                            rs.getString("nome")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Errore durante l'ottenimento della stanza per ID.");
            e.printStackTrace();
        }
        return null;
    }

    //metodo per ottenere l'edificioId di una stanza tramite stanza_id
    public String getEdificioIdByStanza(String stanzaId) {
        String query = "SELECT edificio_id FROM Stanze WHERE stanza_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, stanzaId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("edificio_id");
                }
            }
        } catch (SQLException e) {
            System.err.println("Errore durante l'ottenimento dell'edificio per stanza: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    // Metodo per ottenere l'edificio di una stanza tramite stanza_id
    public Edificio getEdificioByStanza(String stanzaId) {
        Edificio edificio = null;
        String query = "SELECT e.edificio_id, e.nome, e.accessibile, e.descrizione FROM Edifici e " +
                "JOIN Stanze s ON e.edificio_id = s.edificio_id " +
                "WHERE s.stanza_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, stanzaId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    String edificioId = rs.getString("edificio_id");
                    String nome = rs.getString("nome");
                    boolean accessibile = rs.getBoolean("accessibile");
                    String descrizione = rs.getString("descrizione");
                    edificio = new Edificio(edificioId, nome, accessibile, descrizione);
                }
            }
        } catch (SQLException e) {
            System.err.println("Errore durante l'ottenimento dell'edificio per stanza: " + e.getMessage());
            e.printStackTrace();
        }
        return edificio;
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
                            rs.getString("nome")
                    );
                    stanze.add(stanza);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return stanze;
    }

    /**
     * Metodo per ottenere la descrizione completa di una stanza dato il suo ID.
     *
     * @param stanzaId L'ID della stanza da cercare.
     * @return La descrizione breve della stanza o null se non trovata.
     */
    public String getDescrizioneBreve(String stanzaId) {
        String query = "SELECT descrizione_breve FROM DescrizioniStanze WHERE stanza_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, stanzaId); // Imposta il parametro stanza_id
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString("descrizione_breve");
                }
            }
        } catch (SQLException e) {
            System.err.println("Errore durante il recupero della descrizione completa: " + e.getMessage());
        }

        return null; // Restituisce null se non trova la stanza o in caso di errore
    }

    /**
     * Metodo per ottenere la descrizione completa di una stanza dato il suo ID.
     *
     * @param stanzaId L'ID della stanza da cercare.
     * @return La descrizione completa della stanza o null se non trovata.
     */
    public String getDescrizioneCompleta(String stanzaId) {
        String query = "SELECT descrizione_completa FROM DescrizioniStanze WHERE stanza_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, stanzaId); // Imposta il parametro stanza_id
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString("descrizione_completa");
                }
            }
        } catch (SQLException e) {
            System.err.println("Errore durante il recupero della descrizione completa: " + e.getMessage());
        }

        return null; // Restituisce null se non trova la stanza o in caso di errore
    }

    /**
     * Metodo per ottenere una stringa descrittiva degli oggetti presenti in una stanza.
     *
     * @param stanzaId L'ID della stanza di cui si vogliono ottenere gli oggetti.
     * @return Una stringa che descrive gli oggetti presenti nella stanza,
     *         o un messaggio se non ci sono oggetti o in caso di errore.
     */
    public String getAllOggettiStanza(String stanzaId) {
        String query = "SELECT o.nome FROM Oggetti o " +
                "JOIN OggettiStanze os ON o.oggetto_id = os.oggetto_id " +
                "WHERE os.stanza_id = ?";
        StringBuilder sb = new StringBuilder();
        List<String> oggetti = new ArrayList<>();

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, stanzaId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    oggetti.add(rs.getString("nome"));
                }
            }

            if (oggetti.isEmpty()) {
                return "Non sembra esserci nulla di interessante qui intorno.\n";
            } else {
                sb.append("Osservando attentamente, noti:\n");
                for(int i = 0; i < oggetti.size(); i++){
                    sb.append("- ").append(oggetti.get(i));
                    if(i < oggetti.size() -1) {
                        sb.append(",\n");
                    } else {
                        sb.append(".\n");
                    }
                }

            }

        } catch (SQLException e) {
            System.err.println("Errore durante l'ottenimento degli oggetti della stanza: " + e.getMessage());
            return "C'è qualcosa che non va, ma non sai identificare cosa.\n";
        }
        return sb.toString();
    }
}