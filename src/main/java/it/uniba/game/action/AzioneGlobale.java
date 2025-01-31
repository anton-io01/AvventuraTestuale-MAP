package it.uniba.game.action;

import it.uniba.game.database.dao.EdificioDAO;
import it.uniba.game.database.dao.MovimentoDAO;
import it.uniba.game.entity.Edificio;
import it.uniba.game.entity.Giocatore;
import it.uniba.game.database.dao.StanzaDAO;

import java.util.List;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.nio.charset.StandardCharsets;


public class AzioneGlobale {
    private final StanzaDAO stanzaDAO = new StanzaDAO();
    private final MovimentoDAO movimentoDAO = new MovimentoDAO();
    private final EdificioDAO edificioDAO = new EdificioDAO();


    public AzioneGlobale() {
    }


    public String inventario(Giocatore giocatore, List<String> parametri) {
        if (giocatore.getInventario().isEmpty()) {
            return "L'inventario è vuoto.\n\n";
        } else {
            StringBuilder inventario = new StringBuilder();
            for (int i = 0; i < giocatore.getInventario().size(); i++) {
                inventario.append(giocatore.getInventario().get(i).getNome()).append("\n");
            }
            return inventario.toString();
        }
    }
    public String esci(Giocatore giocatore, List<String> parametri) {
        System.out.println("Arrivederci!");
        System.exit(0);
        return null;
    }


    public String osserva(Giocatore giocatore, List<String> parametri) {
        return stanzaDAO.getDescrizioneCompleta(giocatore.getPosizioneAttualeId()) + "\n\n";
    }

    /**
     * Mostra l'elenco dei comandi disponibili.
     *
     * @param giocatore
     * @param parametri
     * @return
     */
    public String aiuto(Giocatore giocatore, List<String> parametri) {
        return "Comandi Disponibili:\n" +
                "- mappa (per visualizzare la mappa)\n" +
                "- inventario (per visualizzare l'inventario)\n" +
                "- nord, sud, est, ovest, alto, basso (per muoverti)\n" +
                "- osserva (per mostrare la descrizione della stanza)\n" +
                "- esamina [oggetto] (per esaminare un oggetto)\n" +
                "- prendi [oggetto] (per prendere un oggetto)\n" +
                "- lascia [oggetto] (per lasciare un oggetto)\n" +
                "- usa [oggetto] (per utilizzare un oggetto)\n" +
                "- apri [oggetto] (per aprire un armadio o una porta)\n" +
                "- chiudi [oggetto] (per chiudere un armadio o una porta)\n" +
                "- parla [oggetto] (per parlare con un personaggio)\n" +
                "- leggi [oggetto] (per leggere un libro o un appunto)\n\n" +
                "- Usa il menu in alto a destra per: salvare, iniziare una nuova partita o uscire dal gioco.\n\n";
    }


    /**
     * Mostra la mappa del gioco.
     *
     * @param giocatore
     * @param parametri
     * @return
     */
    public String mappa(Giocatore giocatore, List<String> parametri) {
        Edificio edificio = stanzaDAO.getEdificioByStanza(giocatore.getPosizioneAttualeId());
        if (stanzaDAO.isStanzaAccessibile(giocatore.getPosizioneAttuale().getStanzaId())) {
            return "Edificio: " + edificio.getNome() + "\nSei nella stanza: " + giocatore.getPosizioneAttuale().getNome() + ".\n" + movimentoDAO.getMovimentiByStanza(giocatore.getPosizioneAttuale()) + "\n\n";
        } else {
            return "Edificio: " + edificio.getNome() + "\nSei nella stanza: " + giocatore.getPosizioneAttuale().getNome() + ".\n" +"Il passaggio è bloccato... Da qui puoi solo tornare indietro.\n\n";
        }
    }

    /**
     * Metodo per salvare lo stato corrente del gioco.
     * @param giocatore il giocatore corrente.
     * @param params tempo rimanente.
     * @return feedback per l'utente
     */
    public String salva(Giocatore giocatore, List<String> params) {
        String filePath = "./src/main/resources/saves/save.txt";

        try {
            String playerParams = giocatore.getPlayerParams(); // Metodo modificato
            if (playerParams.equals("")) {
                return "Errore: Non sei ancora in una posizione nel mondo di gioco!";
            }
            // crea la cartella se non esiste
            Path dirPath = Paths.get("./src/main/resources/saves");
            if(!Files.exists(dirPath)) {
                Files.createDirectories(dirPath);
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                if (params != null && params.size() > 0) {
                    try {

                        int timeRemaining = Integer.parseInt(params.get(0));

                        writer.write(timeRemaining + "\n");

                    } catch (NumberFormatException e) {
                        writer.write(7200 + "\n");

                    }
                } else {
                    writer.write(7200 + "\n");
                }

                writer.write(playerParams + "\n");
                if (params != null && params.size() > 1) {
                    String encodedTextContent = params.get(1);
                    if(encodedTextContent != null){
                        writer.write(new String(Base64.getDecoder().decode(encodedTextContent), StandardCharsets.UTF_8));
                    }
                }

                return "Partita salvata correttamente! Percorso file: " + filePath + "\n\n";
            }
        } catch (IOException e) {
            System.err.println("Errore nel salvataggio della partita");
            e.printStackTrace();
            return "Errore nel salvataggio della partita" + e;
        }
    }
}