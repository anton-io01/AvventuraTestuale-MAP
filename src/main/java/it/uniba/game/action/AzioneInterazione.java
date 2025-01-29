package it.uniba.game.action;

import it.uniba.game.database.dao.AzioneDAO;
import it.uniba.game.entity.Giocatore;
import it.uniba.game.database.dao.OggettoDAO;
import it.uniba.game.entity.Oggetto;

import java.util.List;

public class AzioneInterazione {
    private OggettoDAO oggettoDAO = new OggettoDAO();
    private AzioneDAO azioneDAO = new AzioneDAO();

    public AzioneInterazione() {
    }

    /**
     * Esamina un oggetto nella stanza.
     *
     * @param giocatore Il giocatore che esegue l'azione.
     * @param parametri Una lista di parametri per l'azione.
     */
    public String esamina(Giocatore giocatore, List<String> parametri) {
        if (parametri.size() != 2) {
            return "Devi specificare un oggetto da esaminare.\n\n";
        }
        String descrizioneOggetto = oggettoDAO.getDescrizioneDettagliataOggetto(parametri.get(1));
        if (descrizioneOggetto != null) {
            return descrizioneOggetto + "\n\n";
        } else {
            return "Non puoi esaminare questo oggetto.\n\n";
        }

    }

    /**
     * Prende un oggetto dalla stanza, lo aggiunge all'inventario del giocatore e lo elimina dalla stanza attuale.
     *
     * @param giocatore Il giocatore che esegue l'azione.
     * @param parametri Una lista di parametri per l'azione.
     */
    public String prendi(Giocatore giocatore, List<String> parametri) {
        if(parametri.size() < 2){
            return "Devi specificare un oggetto da prendere\n\n";
        }
        if (oggettoDAO.isOggettoRaccoglibile(parametri.get(1), giocatore.getPosizioneAttualeId())) {
            Oggetto oggetto = oggettoDAO.getOggettoById(parametri.get(1));
            giocatore.aggiungiOggetto(oggetto);
            oggettoDAO.eliminaOggettoDaStanza(parametri.get(1), giocatore.getPosizioneAttualeId());
            return "Hai aggiunto " + oggetto.getNome() + " all'inventario.\n\n";
        } else {
            return "Non puoi raccogliere questo oggetto.\n\n";
        }
    }

    /**
     * Lascia un oggetto dall'inventario e lo aggiunge alla stanza attuale.
     *
     * @param giocatore Il giocatore che esegue l'azione.
     * @param parametri Una lista di parametri per l'azione.
     */
    public String lascia(Giocatore giocatore, List<String> parametri) {
        if(parametri.size() < 2){
            return "Devi specificare un oggetto da lasciare\n\n";
        }
        Oggetto oggetto = giocatore.getInventario().stream()
                .filter(o -> o.getOggettoId().equals(parametri.get(1)))
                .findFirst()
                .orElse(null);

        if (oggetto != null) {
            giocatore.rimuoviOggetto(oggetto);
            oggettoDAO.aggiungiOggettoAStanza(parametri.get(1), giocatore.getPosizioneAttualeId());
            return "Hai lasciato " + oggetto.getNome() + ".\n\n";
        } else {
            return "L'oggetto non è nell'inventario.\n\n";
        }
    }

    /**
     * Usa un oggetto dall'inventario.
     *
     * @param giocatore
     * @param parametri
     */
    public String usa(Giocatore giocatore, List<String> parametri) {
        if(parametri.size() < 2){
            return "Devi specificare un oggetto da usare\n\n";
        }
        return null;
    }

    /**
     * Apre un oggetto.
     *
     * @param giocatore
     * @param parametri
     */
    public String apri(Giocatore giocatore, List<String> parametri) {
        if (parametri.size() != 2) {
            return "Devi specificare cosa aprire.\n\n";
        }

        String azioneKey = parametri.get(0) + parametri.get(1) + giocatore.getPosizioneAttualeId();

        switch (azioneKey) {
            case "AP1311":
                return "Hai aperto l'armadio e trovi una strana combinazione.\n\n";
            case "AP1515":
                return "Hai aperto la porta. Un forte sibilo fa capolino.\n\n";
            case "AP1616":
                return "Hai inserito la tua impronta. La porta si apre con un click.\n\n";
            default:
                if (azioneDAO.verificaAzioneInterazione(parametri.get(0), parametri.get(1), giocatore.getPosizioneAttualeId())) {
                    oggettoDAO.setVisibilitaOggetto(parametri.get(1), true);
                    return "Hai aperto l'oggetto.\n\n";
                } else {
                    return "Non puoi aprire questo oggetto.\n\n";
                }
        }
    }
    /**
     * Chiude un oggetto.
     *
     * @param giocatore
     * @param parametri
     */
    public String chiudi(Giocatore giocatore, List<String> parametri) {
        if(parametri.size() < 2){
            return "Devi specificare un oggetto da chiudere\n\n";
        }
        if (azioneDAO.verificaAzioneInterazione(parametri.get(0), parametri.get(1), giocatore.getPosizioneAttualeId())) {
            oggettoDAO.setVisibilitaOggetto(parametri.get(1), false);
            return "Hai chiuso l'oggetto.\n\n";
        } else {
            return "Non puoi chiudere questo oggetto.\n\n";
        }
    }
    /**
     * Parla con un personaggio nella stanza.
     *
     * @param giocatore
     * @param parametri
     */
    public String parla(Giocatore giocatore, List<String> parametri) {
        if (parametri.size() != 2) {
            return "Devi specificare con chi parlare.\n\n";
        }

        String azioneKey = parametri.get(0) + parametri.get(1) + giocatore.getPosizioneAttualeId();

        switch (azioneKey) {
            case "PA1203":
                return "Assistente: Ah, finalmente è arrivato... La stavamo aspettando... tutti qui. Oggi è stata una giornata... un vero incubo. La vittima era nella stanza 203 prima della morte. I medici hanno dovuto asportare... quel coso dal suo corpo. Lo stanno esaminando nel laboratorio medico... preghi che non sia niente di grave... preghi per noi...\n\n";
            case "PA1410":
                return "Paziente: Mmm...ho un gran mal di testa... chi sei?\n\n";
            default:
                if (azioneDAO.verificaAzioneInterazione(parametri.get(0), parametri.get(1), giocatore.getPosizioneAttualeId())) {
                    return oggettoDAO.getDescrizioneDettagliataOggetto(parametri.get(1)) + "\n\n";
                } else {
                    return "Non puoi parlare con questo personaggio.\n\n";
                }
        }
    }

    /**
     * Leggi un oggetto nella stanza.
     *
     * @param giocatore
     * @param parametri
     */
    public String leggi(Giocatore giocatore, List<String> parametri) {
        if (parametri.size() != 2) {
            return "Devi specificare cosa leggere.\n\n";
        }

        String azioneKey = parametri.get(0) + parametri.get(1) + giocatore.getPosizioneAttualeId();

        switch (azioneKey) {
            case "LG0518":
                return "Registro degli appuntamenti:\n" +
                        "Alcuni orari sono stati cerchiati in rosso: Mezzanotte. I nomi associati a quegli orari sono:\n" +
                        "- L.G.,A.T.,N.O.,E.S.\n\n";
            case "LG0919":
                return "Ritaglio di giornale:\n" +
                        "La sorella del programmatore Sato è morta a causa di un esperimento medico fallito del dottor Tanaka.\n" +
                        "C'è un'annotazione scritta a matita in un angolo: <<Vendetta>>.\n\n";
            case "LG1013":
                return "Note mediche del dott. Chen:\n" +
                        "Confermo i sospetti... Il dottor Tanaka sta eseguendo esperimenti non etici su pazienti con malattie cardiache. Sta modificando i loro pacemaker con impulsi che portano ad un'insufficienza cardiaca.\n\n";
            case "LG2927":
                return "Un libro dal titolo illeggibile. Sembra una raccolta di racconti di paura.\n" +
                        "Una pagina sembra essere stata appuntata. Riporta uno strano codice.\n\n";
            default:
                if (azioneDAO.verificaAzioneInterazione(parametri.get(0), parametri.get(1), giocatore.getPosizioneAttualeId())) {
                    return oggettoDAO.getDescrizioneDettagliataOggetto(parametri.get(1)) + "\n\n";
                } else {
                    return "Non puoi leggere questo oggetto.\n\n";
                }
        }
    }
}