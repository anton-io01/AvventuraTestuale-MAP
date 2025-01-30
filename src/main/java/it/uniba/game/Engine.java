package it.uniba.game;

import it.uniba.game.database.dao.StanzaDAO;
import it.uniba.game.database.dao.OggettoDAO;
import it.uniba.game.action.GestoreAzioni;
import it.uniba.game.database.DatabaseInitializer;
import it.uniba.game.entity.Giocatore;
import it.uniba.game.util.GUI;
import it.uniba.game.util.Parser;

import javax.swing.*;
import java.util.List;

public class Engine {

    private GestoreAzioni gestoreAzioni;
    private Giocatore giocatore;
    private Parser parser;
    private GUI gui;
    private StanzaDAO stanzaDAO;
    private OggettoDAO oggettoDAO;

    public Engine() {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            startGame();
        });
    }

    private void startGame() {
        try {
            // Inizializzazione del database
            DatabaseInitializer.initializeDatabase();


            //istanzio i DAO
            stanzaDAO = new StanzaDAO();
            oggettoDAO = new OggettoDAO();
            gestoreAzioni = new GestoreAzioni();


            //Creazione del giocatore di default all'avvio del game.
            giocatore = new Giocatore();
            //Imposto posizione iniziale
            if(giocatore.getPosizioneAttuale()==null) { // set posizione default / lista oggetti
                giocatore.setPosizioneAttuale(stanzaDAO.getStanzaById("02"));
                giocatore.aggiungiOggetto(oggettoDAO.getOggettoById("01"));
            }
            parser = new Parser();
            // Inizializza e avvia la GUI passando il giocatore come argomento.
            gui = new GUI(this);  // Rimozione giocatore dal costruttore

            gui.setVisible(true);


        } catch (Exception e) {
            System.err.println("Errore durante l'inizializzazione del gioco:");
            e.printStackTrace();
        }

    }

    public Object processCommand(String input) {
        List<String> parametri = parser.parseInput(input);
        if (parametri.isEmpty()) {
            return "Comando non riconosciuto!";
        }
        return gestoreAzioni.esegui(giocatore, parametri);
    }


    public static void main(String[] args) {
        new Engine();
    }
}