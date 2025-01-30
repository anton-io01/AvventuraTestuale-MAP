package it.uniba.game.util;

import it.uniba.game.database.DatabaseManager;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class SaveManager {

    private static final String SAVE_DIRECTORY = "./src/main/resources/saves/";
    private static final String SAVE_FILE_NAME = "savegame.mv.db";


    /**
     * Salva lo stato attuale del database. Se esiste già un file di salvataggio lo sovrascrive.
     *
     * @return true se il salvataggio ha avuto successo, false altrimenti.
     */
    public static boolean saveGame() {
        String sourcePath = DatabaseManager.getDatabasePath();
        if (sourcePath == null){
            System.out.println("Non è possibile effettuare il salvataggio di un database in memoria.");
            return false;
        }

        // Rimuovi il prefisso "h2:" dal sourcePath
        if (sourcePath.startsWith("h2:")) {
            sourcePath = sourcePath.substring(sourcePath.indexOf(":") + 1);
        }

        String destinationPath = SAVE_DIRECTORY + SAVE_FILE_NAME;
        try {
            // Crea la cartella di salvataggio se non esiste
            File saveDir = new File(SAVE_DIRECTORY);
            if (!saveDir.exists()) {
                if(!saveDir.mkdirs()){
                    System.out.println("Errore durante la creazione della cartella di salvataggio!");
                    return false;
                }
            }

            // Chiudo la connessione al database.
            DatabaseManager.closeConnection();
            // Copia il file del database nella cartella di salvataggio sovrascrivendo se esiste
            Files.copy(Paths.get(sourcePath), Paths.get(destinationPath), StandardCopyOption.REPLACE_EXISTING);
            // Riapro la connessione al database.
            DatabaseManager.getConnection();

            System.out.println("Partita salvata con successo in: " + destinationPath);
            return true;
        } catch (IOException e) {
            // Riapro la connessione in caso di errore
            try {
                DatabaseManager.getConnection();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            System.err.println("Errore durante il salvataggio della partita: " + e.getMessage());
            return false;
        } catch(Exception ex){
            // Riapro la connessione in caso di errore
            try {
                DatabaseManager.getConnection();
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.err.println("Errore durante la chiusura del database: " + ex.getMessage());
            return false;
        }
    }
    /**
     * Carica lo stato del database dall'unico file di salvataggio presente
     *
     * @return true se il caricamento ha avuto successo, false altrimenti.
     */
    public static boolean loadGame() {
        String sourcePath = SAVE_DIRECTORY + SAVE_FILE_NAME;
        String destinationPath = DatabaseManager.getDatabasePath();
        if(destinationPath == null) {
            System.out.println("Non è possibile effettuare il caricamento su un database in memoria.");
            return false;
        }

        // Rimuovi il prefisso "h2:" dal destinationPath
        if (destinationPath.startsWith("h2:")) {
            destinationPath = destinationPath.substring(destinationPath.indexOf(":") + 1);
        }


        File saveFile = new File(sourcePath);

        // Verifica se il file di salvataggio esiste
        if (!saveFile.exists() || !saveFile.isFile()) {
            System.err.println("Nessun salvataggio esistente.");
            return false; // Il file non esiste, ritorna false
        }

        try {
            // Chiudo la connessione al database.
            DatabaseManager.closeConnection();
            // Copia il file di salvataggio nel percorso del database
            Files.copy(Paths.get(sourcePath), Paths.get(destinationPath), StandardCopyOption.REPLACE_EXISTING);
            // Riapro la connessione al database.
            DatabaseManager.getConnection();
            System.out.println("Partita caricata con successo da: " + sourcePath);
            return true;

        } catch (IOException e) {
            // Riapro la connessione in caso di errore
            try{
                DatabaseManager.getConnection();
            }catch(Exception ex){
                ex.printStackTrace();
            }
            System.err.println("Errore durante il caricamento della partita: " + e.getMessage());
            return false;
        }  catch (Exception ex) {
            // Riapro la connessione in caso di errore
            try{
                DatabaseManager.getConnection();
            }catch(Exception e){
                e.printStackTrace();
            }
            System.err.println("Errore durante la chiusura del database: " + ex.getMessage());
            return false;
        }
    }
}