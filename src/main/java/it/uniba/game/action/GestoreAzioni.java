package it.uniba.game.action;

import it.uniba.game.entity.Giocatore;
import java.util.HashMap;
import java.util.List;
import java.util.function.BiFunction;

public class GestoreAzioni {
    private final HashMap<String, BiFunction<Giocatore, List<String>, Void>> azioniMap;
    private final AzioneInterazione azioniInterazione;

    public GestoreAzioni() {
        this.azioniMap = new HashMap<>();
        this.azioniInterazione = new AzioneInterazione();
        inizializzaAzioni();
    }

    private void inizializzaAzioni() {
        // Metodi generici
        azioniMap.put("PR0000", azioniInterazione::prendi);

        // Metodi specifici
        azioniMap.put("US0101", azioniInterazione::usaFlipperZeroStanza1);

    }

    public String generaCodiceAzione(List<String> parametri, Giocatore giocatore) {
        if (parametri == null || parametri.isEmpty()) {
            throw new IllegalArgumentException("I parametri non possono essere vuoti");
        }

        String azioneBase = parametri.get(0).substring(0, 2).toUpperCase();
        String stanzaId = String.format("%02d", giocatore.getPosizioneAttualeId());

        // Se c'è solo un parametro, genera codice per azione generica
        if (parametri.size() == 1) {
            return azioneBase + "0000";
        }

        // Altrimenti genera codice per azione specifica
        String oggettoId = String.format("%02d", getOggettoId(parametri.get(1)));
        return azioneBase + oggettoId + stanzaId;
    }

    public void eseguiAzione(Giocatore giocatore, List<String> parametri) {

        BiFunction<Giocatore, List<String>, Void> azione = azioniMap.get(codiceAzione);

        if (azione == null && codiceAzione.length() == 6) {
            // Se non trova l'azione specifica, prova con l'azione generica
            String codiceGenerico = codiceAzione.substring(0, 2) + "0000";
            azione = azioniMap.get(codiceGenerico);
        }

        if (azione != null) {
            azione.apply(giocatore, parametri);
        } else {
            System.out.println("Azione non riconosciuta");
        }
}
