package it.uniba.game;

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

    public Engine() {
        try {
            // Inizializzazione del database
            DatabaseInitializer.initializeDatabase();

            gestoreAzioni = new GestoreAzioni();
            giocatore = new Giocatore();
            parser = new Parser();
            // Inizializza e avvia la GUI nel costruttore
            SwingUtilities.invokeLater(() -> {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                }
                catch(Exception e){
                    e.printStackTrace();
                }
                gui = new GUI(this); // Passa l'istanza di Engine alla GUI
                gui.setVisible(true);

            });
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
        new Engine(); // Crea un'istanza di Engine per avviare il gioco

    }
}