package it.uniba.game.comandi;

import java.util.HashMap;
import java.util.Map;

public class GestoreMovimenti {
    private Map<String, Map<Direzione, String>> mappaCollegamenti;  // Mappa: IdStanzaAttuale -> (Direzione -> IdStanzaDestinazione)

    public GestoreMovimenti() {
        this.mappaCollegamenti = new HashMap<>();
    }

    // Aggiunge un collegamento tra due stanze
    public void aggiungiCollegamento(String idStanzaPartenza, Direzione direzione, String idStanzaDestinazione) {
        mappaCollegamenti.putIfAbsent(idStanzaPartenza, new HashMap<>());
        mappaCollegamenti.get(idStanzaPartenza).put(direzione, idStanzaDestinazione);
    }

    // Restituisce l'ID della stanza di destinazione dato l'ID della stanza attuale e una direzione
    public String getDestinazione(String idStanzaAttuale, Direzione direzione) {
        Map<Direzione, String> collegamenti = mappaCollegamenti.get(idStanzaAttuale);
        if (collegamenti != null) {
            return collegamenti.get(direzione);
        }
        return null;
    }

    // Verifica se è possibile muoversi in una certa direzione da una stanza
    public boolean isMovimentoPossibile(String idStanzaAttuale, Direzione direzione) {
        Map<Direzione, String> collegamenti = mappaCollegamenti.get(idStanzaAttuale);
        return collegamenti != null && collegamenti.containsKey(direzione);
    }

    // Restituisce tutte le direzioni possibili da una stanza
    public Map<Direzione, String> getDirezioniPossibili(String idStanzaAttuale) {
        return mappaCollegamenti.getOrDefault(idStanzaAttuale, new HashMap<>());
    }
}
