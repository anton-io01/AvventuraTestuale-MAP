package it.uniba.game.action;

import it.uniba.game.entity.Giocatore;
import java.util.HashMap;
import java.util.List;
import java.util.function.BiFunction;

public class GestoreAzioni implements Azione{
    private final HashMap<String, BiFunction<Giocatore, List<String>, ?>> azioniMap;
    private final AzioneMovimento azioneMovimento;
    private final AzioneGlobale azioneGlobale;
    private final AzioneInterazione azioneInterazione;

    public GestoreAzioni() {
        this.azioniMap = new HashMap<>();
        this.azioneMovimento = new AzioneMovimento();
        this.azioneGlobale = new AzioneGlobale();
        this.azioneInterazione = new AzioneInterazione();
        inizializzaAzioni();
    }

    private void inizializzaAzioni() {
        // Azioni di movimento
        azioniMap.put("NO", azioneMovimento::movimento);
        azioniMap.put("SU", azioneMovimento::movimento);
        azioniMap.put("ES", azioneMovimento::movimento);
        azioniMap.put("OV", azioneMovimento::movimento);
        azioniMap.put("AL", azioneMovimento::movimento);
        azioniMap.put("BA", azioneMovimento::movimento);


        // Azioni globali
        azioniMap.put("IN", azioneGlobale::inventario);
        azioniMap.put("EX", azioneGlobale::esci);
        azioniMap.put("SA", azioneGlobale::salva);
        azioniMap.put("OS", azioneGlobale::osserva);
        azioniMap.put("AI", azioneGlobale::aiuto);
        azioniMap.put("EN", azioneGlobale::entra);

        // Azioni di interazione
        azioniMap.put("EM", azioneInterazione::esamina);
        azioniMap.put("PR", azioneInterazione::prendi);
        azioniMap.put("LA", azioneInterazione::lascia);
        azioniMap.put("US", azioneInterazione::usa);
        azioniMap.put("AP", azioneInterazione::apri);
        azioniMap.put("CH", azioneInterazione::chiudi);
        azioniMap.put("PA", azioneInterazione::parla);
        azioniMap.put("LG", azioneInterazione::leggi);
    }

    public void esegui(Giocatore giocatore, List<String> parametri) {

        BiFunction<Giocatore, List<String>, ?> azione = azioniMap.get(parametri.get(0));
        if (azione != null) {
            azione.apply(giocatore, parametri);
        } else {
            System.out.println("Azione non riconosciuta");
        }
    }
}
