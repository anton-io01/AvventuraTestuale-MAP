package it.uniba.game.util;

import it.uniba.game.database.dao.AzioneDAO;
import it.uniba.game.database.dao.OggettoDAO;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

public class Parser {
    private final AzioneDAO azioneDAO;
    private final OggettoDAO oggettoDAO;

    public Parser(AzioneDAO azioneDAO, OggettoDAO oggettoDAO) {
        this.azioneDAO = azioneDAO;
        this.oggettoDAO = oggettoDAO;
    }

    public List<String> parseInput(String input) {
        List<String> parametri = new ArrayList<>();

        // Converti input in minuscolo e dividi in parole
        String[] parole = input.toLowerCase().trim().split("\\s+");

        // Caso 1: Input vuoto
        if (parole.length == 0) {
            return parametri;
        }

        // Cerca prima l'azione
        String azioneId = cercaAzione(parole[0]);
        if (azioneId != null) {
            parametri.add(azioneId);

            // Se c'è più di una parola, cerca l'oggetto
            if (parole.length > 1) {
                // Prendi tutte le parole dopo l'azione come potenziale oggetto
                String oggettoInput = String.join(" ",
                        Arrays.copyOfRange(parole, 1, parole.length));

                String oggettoId = cercaOggetto(oggettoInput);
                if (oggettoId != null) {
                    parametri.add(oggettoId);
                }
            }
        }

        return parametri;
    }

    private String cercaAzione(String input) {
        // Questo metodo dovrebbe usare azioniDAO per cercare l'azione nel database
        // e restituire l'azione_id se trovata, null altrimenti
        return azioneDAO.getAzioneIdByNome(input);
    }

    private String cercaOggetto(String input) {
        // Questo metodo dovrebbe usare oggettiDAO per cercare l'oggetto nel database
        // e restituire l'oggetto_id se trovato, null altrimenti
        return oggettoDAO.getOggettoIdByNome(input);
    }
}
