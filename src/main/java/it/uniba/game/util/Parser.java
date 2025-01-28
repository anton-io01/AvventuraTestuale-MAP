package it.uniba.game.util;

import it.uniba.game.database.dao.AzioneDAO;
import it.uniba.game.database.dao.OggettoDAO;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.Arrays;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Parser {
    private final String STOPWORDS_FILE = "/stopwords.txt";
    private final AzioneDAO azioneDAO;
    private final OggettoDAO oggettoDAO;
    private final Set<String> stopwords;

    public Parser(AzioneDAO azioneDAO, OggettoDAO oggettoDAO) {
        this.azioneDAO = azioneDAO;
        this.oggettoDAO = oggettoDAO;
        this.stopwords = caricaStopwords();
    }

    /**
     * Carica le stopwords da un file nella directory delle risorse.
     *
     * @return Un insieme di stopwords.
     */
    private Set<String> caricaStopwords() {
        Set<String> stopwordsSet = new HashSet<>();
        try (InputStream inputStream = getClass().getResourceAsStream(STOPWORDS_FILE);
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                stopwordsSet.add(line.trim().toLowerCase());
            }
        } catch (Exception e) {
            System.err.println("Errore durante il caricamento delle stopwords" + e.getMessage());
        }
        return stopwordsSet;
    }

    /**
     * Filtra le stopwords da una lista di parole.
     *
     * @param parole La lista di parole da filtrare.
     * @return Una lista di parole senza stopwords.
     */
    private List<String> filtraStopwords(List<String> parole) {
        List<String> filtrate = new ArrayList<>();
        for (String parola : parole) {
            if (!stopwords.contains(parola)) {
                filtrate.add(parola);
            }
        }
        return filtrate;
    }

    /**
     * Analizza l'input dell'utente e restituisce una lista di parametri.
     *
     * @param input L'input dell'utente.
     * @return Una lista di parametri (azione e oggetto, se presenti).
     */
    public List<String> parseInput(String input) {
        List<String> parametri = new ArrayList<>();

        if (input == null || input.trim().isEmpty()) {
            System.err.println("Input vuoto o nullo");
            return parametri;
        }

        // Normalizza l'input e divide in parole
        List<String> parole = Arrays.asList(input.toLowerCase().trim().split("\\s+"));
        parole = filtraStopwords(parole); // Filtra le stopwords

        if (parole.isEmpty()) {
            System.err.println("Nessuna parola valida dopo la rimozione delle stopwords");
            return parametri;
        }

        // Cerca l'azione
        String azioneId = cercaAzione(parole.get(0));
        if (azioneId == null) {
            System.err.println("Azione non riconosciuta: " + parole.get(0));
            return parametri;
        }
        parametri.add(azioneId);

        // Se ci sono più parole, cerca l'oggetto
        if (parole.size() > 1) {
            String oggettoInput = String.join(" ", parole.subList(1, parole.size()));
            String oggettoId = cercaOggetto(oggettoInput);
            if (oggettoId != null) {
                parametri.add(oggettoId);
            } else {
                System.err.println("Oggetto non riconosciuto: " + oggettoInput);
            }
        }

        return parametri;
    }

    /**
     * Cerca l'ID di un'azione nel database.
     *
     * @param input Il nome dell'azione.
     * @return L'ID dell'azione, o null se non trovata.
     */
    private String cercaAzione(String input) {
        try {
            return azioneDAO.getAzioneIdByNome(input);
        } catch (Exception e) {
            System.err.println("Errore durante la ricerca dell'azione" + e.getMessage());
            return null;
        }
    }

    /**
     * Cerca l'ID di un oggetto nel database.
     *
     * @param input Il nome dell'oggetto.
     * @return L'ID dell'oggetto, o null se non trovato.
     */
    private String cercaOggetto(String input) {
        try {
            return oggettoDAO.getOggettoIdByNome(input);
        } catch (Exception e) {
            System.err.println("Errore durante la ricerca dell'oggetto" + e.getMessage());
            return null;
        }
    }
}
