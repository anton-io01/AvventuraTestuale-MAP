package it.uniba.game.util;

import it.uniba.game.database.DatabaseManager;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SaveManager {

    private static final String SAVE_DIRECTORY = "./src/main/resources/saves/";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");

    /**
     * Salva lo stato attuale del database in un file di salvataggio.
     *
     * @return true se il salvataggio ha avuto successo, false altrimenti.
     */
    public static boolean saveGame() {
        String sourcePath = DatabaseManager.getDatabasePath();
        if (sourcePath == null){
            System.out.println("Non è possibile effettuare il salvataggio di un database in memoria.");
            return false;
        }

        // Crea la cartella di salvataggio se non esiste
        File saveDir = new File(SAVE_DIRECTORY);
        if (!saveDir.exists()) {
            if(!saveDir.mkdirs()){
                System.out.println("Errore durante la creazione della cartella di salvataggio!");
                return false;
            }
        }


        String timestamp = LocalDateTime.now().format(FORMATTER);
        String saveFileName = "save_" + timestamp + ".mv.db";
        String destinationPath = SAVE_DIRECTORY + saveFileName;


        try {
            //Chiudo la connessione al database.
            DatabaseManager.closeConnection();
            // Copia il file del database nella cartella di salvataggio
            Files.copy(Paths.get(sourcePath), Paths.get(destinationPath), StandardCopyOption.REPLACE_EXISTING);
            //riapro la connessione.
            DatabaseManager.getConnection();
            System.out.println("Partita salvata con successo in: " + destinationPath);
            return true;
        } catch (IOException e) {
            //riapro la connessione.
            try{
                DatabaseManager.getConnection();
            }catch(Exception ex){
                ex.printStackTrace();
            }

            System.err.println("Errore durante il salvataggio della partita: " + e.getMessage());
            return false;
        } catch (Exception ex) {
            //riapro la connessione.
            try{
                DatabaseManager.getConnection();
            }catch(Exception e){
                e.printStackTrace();
            }
            System.err.println("Errore durante la chiusura del database: " + ex.getMessage());
            return false;
        }
    }


    /**
     * Carica lo stato del database da un file di salvataggio.
     *
     * @param saveFileName il nome del file di salvataggio da caricare.
     * @return true se il caricamento ha avuto successo, false altrimenti.
     */
    public static boolean loadGame(String saveFileName) {
        String sourcePath = SAVE_DIRECTORY + saveFileName;
        String destinationPath = DatabaseManager.getDatabasePath();
        if(destinationPath == null) {
            System.out.println("Non è possibile effettuare il caricamento su un database in memoria.");
            return false;
        }


        try {
            // Chiudo la connessione al database.
            DatabaseManager.closeConnection();
            // Copia il file di salvataggio nel percorso del database
            Files.copy(Paths.get(sourcePath), Paths.get(destinationPath), StandardCopyOption.REPLACE_EXISTING);
            // Riapro la connessione.
            DatabaseManager.getConnection();
            System.out.println("Partita caricata con successo da: " + sourcePath);
            return true;
        } catch (IOException e) {
            // Riapro la connessione se il caricamento fallisce
            try{
                DatabaseManager.getConnection();
            }catch(Exception ex){
                ex.printStackTrace();
            }
            System.err.println("Errore durante il caricamento della partita: " + e.getMessage());
            return false;
        }  catch (Exception ex) {
            // Riapro la connessione se il caricamento fallisce
            try{
                DatabaseManager.getConnection();
            }catch(Exception e){
                e.printStackTrace();
            }
            System.err.println("Errore durante la chiusura del database: " + ex.getMessage());
            return false;
        }
    }

    /**
     *  Restituisce la lista dei salvataggi.
     * @return array con la lista dei nomi dei file
     */
    public static String[] listSaves(){
        File saveDir = new File(SAVE_DIRECTORY);

        //verifica se la directory esiste.
        if(!saveDir.exists() || !saveDir.isDirectory()) {
            return new String[0];
        }

        return saveDir.list((dir, name) -> name.toLowerCase().startsWith("save_") && name.toLowerCase().endsWith(".mv.db"));
    }
}