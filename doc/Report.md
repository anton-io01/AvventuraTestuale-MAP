# Heartcode: il Virus

## Indice
- [Componenti del gruppo](#Componenti-del-gruppo)
- [Descrizione del caso di studio](#Descrizione-del-caso-di-studio)
- [Diagramma delle classi](#Diagramma-delle-classi)
- [Specifica algebrica](#Specifica-algebrica)
- [Applicazione degli argomenti del corso](#Applicazione-degli-argomenti-del-corso)
- [Struttura del Database](#Struttura-del-Database)
  - [Tabella `Edifici`](#Tabella-Edifici)
  - [Tabella `Stanze`](#Tabella-Stanze)
  - [Tabella `DescrizioniStanze`](#Tabella-DescrizioniStanze)
  - [Tabella `Oggetti`](#Tabella-Oggetti)
  - [Tabella `OggettiStanze`](#Tabella-OggettiStanze)
  - [Tabella `Movimenti`](#Tabella-Movimenti)
- [Azioni](#Azioni)
- [Trama del gioco](#Trama-del-gioco)
- [Mappa del gioco](#Mappa-del-gioco)
  - [Ospedale di Neo Tokyo - 01](#Ospedale-di-Neo-Tokyo---01)
  - [Clinica del Dott. Tanaka - 02](#Clinica-del-Dott.-Tanaka---02)
  - [Nascondiglio di Sato - 03](#Nascondiglio-di-Sato---03)



## Componenti del gruppo
* Antonio Modugno



## Descrizione del caso di studio

L'avventura testuale sviluppata, denominata **Heartcode: il Virus**, immerge il giocatore nei panni di un agente di polizia incaricato di indagare su una serie di morti sospette a Neo Tokyo, legate a pacemaker difettosi. L'obiettivo del gioco è quello di svelare il mistero dietro questi decessi, scoprire il colpevole e fermare un attacco informatico prima che sia troppo tardi.

L'ambientazione principale è una Neo Tokyo futuristica, con diverse location chiave che si susseguono nel corso dell'indagine: l'ospedale cittadino, la clinica privata del dott. Tanaka e il nascondiglio di un programmatore esperto. Il giocatore dovrà muoversi tra queste location, raccogliere indizi, risolvere enigmi e interagire con vari personaggi per progredire nella storia.

La difficoltà del gioco è medio-alta, in quanto la risoluzione degli enigmi richiede un'attenta analisi delle descrizioni e una buona capacità di collegamento tra i vari indizi raccolti nel corso dell'indagine. Il Flipper Zero può essere un untile compagno durante l'avventura.

È stata prevista un'introduzione che presenta al giocatore il contesto di Neo Tokyo e il suo ruolo come agente di polizia, per chi non volesse approfondire la trama prima di iniziare la partita.

La partita può essere salvata, permettendo al giocatore di riprendere l'indagine dal punto in cui si era interrotto, semplicemente caricando la partita dal menu principale.

L'avventura si svolge in un arco temporale di circa 60 minuti, questo per rendere il gameplay dinamico e incalzante, con il culmine nella disattivazione dell'attacco.

(NB. Non è possibile avviare il gioco dal terminale del proprio IDE. Il gioco deve essere avviato eseguendo la classe `Engine`.)

**Punti chiave della descrizione:**

*   **Titolo:** *Heartcode: il Virus*.
*   **Obiettivo:** Svelare il mistero e fermare un attacco informatico.
*   **Ambientazione:** Neo Tokyo futuristica con location chiave (ospedale, clinica, nascondiglio).
*   **Difficoltà:** Medio-alta, basata sull'analisi e il collegamento degli indizi.
*   **Aiuti:** Il Flipper Zero può essere un utile compagno durante l'avventura.
*   **Intro:** Introduzione iniziale al contesto di gioco e ruolo del giocatore.
*   **Salvataggio:** Possibilità di salvare la partita e riprenderla dal menu principale.
*   **Tempo:** Durata stimata di 60 minuti per un'esperienza dinamica.
*   **Avvio:** Il gioco deve essere avviato eseguendo la classe `Engine`.
*   **Nota:** Impedimento di avviare il gioco dal terminale dell'IDE.



## Diagramma delle classi

## Specifica algebrica
### Specifica Algebrica della Classe `Giocatore`

Questa sezione presenta la specifica algebrica della classe `Giocatore`, che gestisce l'inventario, la posizione e le interazioni con gli oggetti nel gioco.

#### 1. Sintassi

*   **Tipo:** `Giocatore`
*   **Operazioni:**
    *   `creaGiocatore` : `--> Giocatore` (Crea un giocatore con un inventario vuoto e senza posizione iniziale)
    *   `aggiungiOggetto` : `Giocatore, Oggetto --> Giocatore` (Aggiunge un oggetto all'inventario del giocatore)
    *   `rimuoviOggetto` : `Giocatore, Oggetto --> Giocatore` (Rimuove un oggetto dall'inventario del giocatore)
    *   `getPosizioneAttuale` : `Giocatore --> Stanza` (Restituisce la stanza attuale del giocatore)
    *   `setPosizioneAttuale` : `Giocatore, Stanza --> Giocatore` (Imposta la stanza attuale del giocatore)
    *   `getInventario` : `Giocatore --> List<Oggetto>` (Restituisce l'inventario del giocatore come una lista di oggetti)
    *   `isOggettoInInventario` : `Giocatore, String --> Boolean` (Verifica se l'inventario del giocatore contiene un oggetto con l'id specificato)
    *   `getOggettiInventarioIds` : `Giocatore --> String` (Restituisce una stringa formattata con gli id degli oggetti nell'inventario)
    *  `getPlayerParams` : `Giocatore --> String` (Restituisce una stringa formattata con l'id della stanza corrente e gli id degli oggetti dell'inventario)
*   **Tipi di supporto:**
    *   `Oggetto`: Un tipo astratto che rappresenta un oggetto di gioco.
    *   `Stanza`: Un tipo astratto che rappresenta una stanza del gioco.
    *   `List<Oggetto>`: Lista di oggetti.
    *   `String`: Stringhe per rappresentare ID di oggetti e stanza e parametri del giocatore.
    *   `Boolean`: Tipo booleano (vero o falso).

#### 2. Semantica (Equazioni Algebriche)

1.  `rimuoviOggetto(creaGiocatore(), o) = creaGiocatore()`
    *   Rimuovere un oggetto da un giocatore appena creato non lo cambia (l'inventario rimane vuoto).
2.  `aggiungiOggetto(rimuoviOggetto(g, o), o) = g`
    *   Se aggiungo un oggetto che avevo precedentemente rimosso, l'inventario del giocatore torna allo stato originale
3. `aggiungiOggetto(aggiungiOggetto(g,o1), o2) = aggiungiOggetto(aggiungiOggetto(g,o2),o1)`
    * L'ordine in cui aggiungo gli oggetti non cambia lo stato dell'inventario del giocatore
4.  `getPosizioneAttuale(creaGiocatore()) = null`
    *   Un giocatore appena creato non ha una posizione di partenza
5.  `getPosizioneAttuale(setPosizioneAttuale(g, s)) = s`
    *   La posizione corrente di un giocatore è quella impostata con `setPosizioneAttuale`.
6. `setPosizioneAttuale(setPosizioneAttuale(g, s1), s2) = setPosizioneAttuale(g, s2)`
    *   La posizione attuale del giocatore viene sempre aggiornata con l'ultimo `setPosizioneAttuale` eseguito.
7.  `getInventario(creaGiocatore()) = {}`
    *   L'inventario di un giocatore appena creato è vuoto.
8.  `getInventario(aggiungiOggetto(g, o)) = getInventario(g) U {o}`
    *   L'inventario di un giocatore risulta dall'unione degli oggetti precedenti e quello appena aggiunto.
9.  `isOggettoInInventario(creaGiocatore(), id) = false`
    *   Un giocatore appena creato non ha oggetti.
10. `isOggettoInInventario(aggiungiOggetto(g,o), id) = if (o.getOggettoId() == id) then true else isOggettoInInventario(g,id)`
    *   Un oggetto è nell'inventario del giocatore se è quello aggiunto o se era già presente.
11. `getOggettiInventarioIds(creaGiocatore()) = "" `
    * La stringa formattata con gli id degli oggetti nell'inventario del giocatore è vuota se l'inventario è vuoto.
12.  `getOggettiInventarioIds(aggiungiOggetto(g,o)) = if getOggettiInventarioIds(g) == "" then o.getOggettoId() else getOggettiInventarioIds(g) + "," + o.getOggettoId()`
     *  La stringa formattata con gli id degli oggetti nell'inventario del giocatore risulta dalla concatenazione dei precedenti e quello appena aggiunto.
13.  `getPlayerParams(creaGiocatore()) = "" ` 
    * La stringa formattata con la posizione e gli oggetti del giocatore è vuota se il giocatore è appena creato e non ha quindi una posizione.
14. `getPlayerParams(setPosizioneAttuale(g,s)) = if getOggettiInventarioIds(g) == "" then s.getStanzaId() + ";" else s.getStanzaId() + ";" + getOggettiInventarioIds(g)`
    * La stringa formattata con la posizione e gli oggetti del giocatore risulta dalla concatenazione della posizione e degli oggetti.

*Dove:*

*   `g` rappresenta un generico `Giocatore`.
*   `o`, `o1`, `o2` rappresentano generici `Oggetti`.
*   `s`, `s1`, `s2` rappresentano generiche `Stanze`.
*   `id` rappresenta un generico id di un oggetto (`String`).
*   `{}` indica una lista vuota.
*   `U` indica l'operazione di unione tra insiemi.

#### 3. Restrizioni

1.  **`aggiungiOggetto(g, o)`:**
    *   Precondizione: Nessuna.
    *   Postcondizione: L'oggetto `o` sarà presente nell'inventario del giocatore `g`. Se l'oggetto era presente non viene duplicato se il tipo `List` non lo consente.

2.  **`rimuoviOggetto(g, o)`:**
    *   Precondizione: Nessuna.
    *   Postcondizione: Se l'oggetto `o` era presente nell'inventario di `g` prima dell'operazione, non sarà presente nell'inventario del giocatore risultante. Se l'oggetto non era presente l'inventario non cambia.

3.  **`getPosizioneAttuale(g)`:**
    *   Precondizione: Nessuna
    *   Postcondizione: Restituisce la stanza impostata con `setPosizioneAttuale`. Se non è stata impostata restituisce `null`.

4.  **`setPosizioneAttuale(g, s)`:**
    *   Precondizione: Nessuna
    *  Postcondizione: La stanza corrente del giocatore `g` sarà `s`.

5.  **`getInventario(g)`:**
    *   Precondizione: Nessuna.
    *   Postcondizione: Restituisce una lista di oggetti, che sono tutti e soli gli oggetti presenti nell'inventario del giocatore al momento della chiamata.

6.  **`isOggettoInInventario(g, id)`:**
    *  Precondizione: Nessuna.
    * Postcondizione: Restituisce `true` se l'inventario del giocatore contiene almeno un oggetto con `id` specificato, `false` altrimenti.

7. **`getOggettiInventarioIds(g)`:**
    *   Precondizione: Nessuna
    *   Postcondizione: Restituisce una stringa formattata con gli id degli oggetti nell'inventario separati da virgola.

8. **`getPlayerParams(g)`:**
    *   Precondizione: Nessuna
    *   Postcondizione: Restituisce una stringa formattata contenente l'id della posizione corrente e gli id degli oggetti nell'inventario del giocatore.

#### Spiegazione e Note:

*   **Modellazione del Giocatore:** Questa specifica descrive come la classe `Giocatore` gestisce i parametri fondamentali di gioco: la posizione corrente e l'inventario degli oggetti.
*   **Gestione di Posizione e Inventario:** La specifica astrae dal dettaglio dell'implementazione e si concentra sul comportamento della classe nella gestione della posizione del giocatore e del suo inventario.
*   **Indipendenza dall'Implementazione:** La specifica non vincola il tipo di lista utilizzata per l'inventario, lasciando aperta la possibilità di implementarla con una `ArrayList`, una `LinkedList`, o altre strutture dati.

Questo dovrebbe essere più preciso e in linea con quello che volevi. Fammi sapere se è tutto ok!
## Applicazione degli argomenti del corso

- **File**:
- **JDBC**:
- **Lambda Expression**:
- **

## Struttura del Database

### Tabella `Edifici`
Contiene informazioni sugli edifici del gioco.

| Attributo      | Tipo          | Vincoli                       | Descrizione                                           |
|----------------|---------------|-------------------------------|-------------------------------------------------------|
| `edificio_id`  | `VARCHAR(2)`  | `PRIMARY KEY`                 | Identificativo univoco dell'edificio (es: "01").      |
| `nome`         | `VARCHAR(100)`| `NOT NULL`, `UNIQUE`          | Nome dell'edificio (es: "Ospedale").                 |
| `accessibile`  | `BOOLEAN`     | `DEFAULT true`                | Se l'edificio è accessibile (default: `true`).       |
| `descrizione`  | `TEXT`        |                               | Descrizione breve dell'edificio.                     |

---

### Tabella `Stanze`
Contiene informazioni sulle stanze presenti negli edifici.

| Attributo      | Tipo          | Vincoli                       | Descrizione                                           |
|----------------|---------------|-------------------------------|-------------------------------------------------------|
| `edificio_id`  | `VARCHAR(2)`  | `NOT NULL`, `FOREIGN KEY`     | Collegamento all'edificio a cui appartiene la stanza. |
| `stanza_id`    | `VARCHAR(2)`  | `PRIMARY KEY`                 | Identificativo univoco della stanza (es: "01").       |
| `nome`         | `VARCHAR(100)`| `NOT NULL`                    | Nome della stanza (es: "Reception").                 |
| `accessibile`  | `BOOLEAN`     | `DEFAULT true`                | Se la stanza è accessibile (default: `true`).        |

**Relazioni:**  
La tabella `Stanze` ha un vincolo di chiave esterna su `edificio_id` che fa riferimento a `Edifici(edificio_id)`.

---

### Tabella `DescrizioniStanze`
Contiene le descrizioni dettagliate delle stanze.

| Attributo            | Tipo          | Vincoli                       | Descrizione                                           |
|----------------------|---------------|-------------------------------|-------------------------------------------------------|
| `edificio_id`        | `VARCHAR(2)`  | `PRIMARY KEY`, `FOREIGN KEY`  | Identificativo dell'edificio.                        |
| `stanza_id`          | `VARCHAR(2)`  | `PRIMARY KEY`, `FOREIGN KEY`  | Identificativo univoco della stanza.                 |
| `descrizione_breve`  | `TEXT`        |                               | Breve descrizione visibile da stanze vicine.         |
| `descrizione_completa`| `TEXT`        |                               | Descrizione completa della stanza.                   |

**Relazioni:**  
La tabella `DescrizioniStanze` ha vincoli di chiave esterna su `stanza_id` e `edificio_id`, che fanno riferimento rispettivamente a `Stanze(stanza_id)` e `Stanze(edificio_id)`.

---

### Tabella `Oggetti`
Contiene informazioni sugli oggetti presenti nel gioco.

| Attributo      | Tipo          | Vincoli                       | Descrizione                                           |
|----------------|---------------|-------------------------------|-------------------------------------------------------|
| `oggetto_id`   | `VARCHAR(3)`  | `PRIMARY KEY`                 | Identificativo univoco dell'oggetto (es: "001").      |
| `nome`         | `VARCHAR(100)`| `NOT NULL`, `UNIQUE`          | Nome dell'oggetto.                                   |
| `descrizione`  | `TEXT`        |                               | Descrizione dell'oggetto.                            |
| `raccoglibile` | `BOOLEAN`     | `DEFAULT false`               | Indica se l'oggetto è raccoglibile.                  |

---

### Tabella `OggettiStanze`
Associa oggetti a stanze specifiche.

| Attributo      | Tipo          | Vincoli                       | Descrizione                                           |
|----------------|---------------|-------------------------------|-------------------------------------------------------|
| `oggetto_id`   | `VARCHAR(3)`  | `PRIMARY KEY`, `FOREIGN KEY`  | Collegamento all'oggetto.                            |
| `stanza_id`    | `VARCHAR(2)`  | `PRIMARY KEY`, `FOREIGN KEY`  | Collegamento alla stanza in cui si trova l'oggetto.  |
| `edificio_id`  | `VARCHAR(2)`  | `PRIMARY KEY`, `FOREIGN KEY`  | Collegamento all'edificio in cui si trova la stanza. |

**Relazioni:**  
La tabella `OggettiStanze` ha vincoli di chiave esterna su `oggetto_id`, `stanza_id` e `edificio_id`, che fanno riferimento rispettivamente a `Oggetti(oggetto_id)`, `Stanze(stanza_id)` e `Stanze(edificio_id)`.

---

### Tabella `Movimenti`
Definisce i collegamenti tra le stanze nelle varie direzioni.

| Attributo     | Tipo         | Vincoli                        | Descrizione                                            |
|---------------|--------------|--------------------------------|--------------------------------------------------------|
| `edificio_id` | `VARCHAR(2)` | `PRIMARY KEY`, `FOREIGN KEY`   | Identificativo dell'edificio.                         |
| `stanza_id`   | `VARCHAR(2)` | `PRIMARY KEY`, `FOREIGN KEY`   | Identificativo della stanza.                          |
| `nord`        | `VARCHAR(2)` | `FOREIGN KEY`                  | Stanza raggiungibile muovendosi a nord.               |
| `sud`         | `VARCHAR(2)` | `FOREIGN KEY`                  | Stanza raggiungibile muovendosi a sud.                |
| `est`         | `VARCHAR(2)` | `FOREIGN KEY`                  | Stanza raggiungibile muovendosi a est.                |
| `ovest`       | `VARCHAR(2)` | `FOREIGN KEY`                  | Stanza raggiungibile muovendosi a ovest.              |
| `alto`        | `VARCHAR(2)` | `FOREIGN KEY`                  | Stanza raggiungibile muovendosi verso l'alto.         |
| `basso`       | `VARCHAR(2)` | `FOREIGN KEY`                  | Stanza raggiungibile muovendosi verso il basso.       |

**Relazioni:**  
La tabella `Movimenti` ha vincoli di chiave esterna su:
- `edificio_id`, che fa riferimento a `Edifici(edificio_id)`.
- `stanza_id`, che fa riferimento a `Stanze(stanza_id)`.
- `nord`, `sud`, `est`, `ovest`, `alto`, `basso`, che fanno riferimento a `Stanze(stanza_id)`.



## Trama del gioco

### **Prologo**: Nuovo Distretto
- Il protagonista, un agente di polizia, viene assegnato a un nuovo distretto a Neo Tokyo.
- Durante il primo turno, una chiamata informa di una morte sospetta presso l'ospedale della città.

### **Capitolo 1**: Indagini in Ospedale
1. **Arrivo in Ospedale**:
   - L'agente si reca in auto presso l'ospedale per raccogliere maggiori informazioni.
   - Alla reception, parla con l'assistente del dottore, che lo indirizza verso la stanza della vittima.
2. **Indizi nella Stanza della Vittima**:
   - Trova un biglietto da visita del dott. Tanaka. Questo svela la posizione della clinica privata del dottore.
3. **Analisi del Pacemaker**:
   - L'agente si reca al laboratorio tecnico dell'ospedale.
   - Scopre che il pacemaker contiene un virus che altera il battito cardiaco inviando impulsi letali al cuore.

### **Capitolo 2**: La Clinica del Dott. Tanaka
1. **Scoperta della Clinica**:
   - L'agente si reca alla clinica del dott. Tanaka, che appare vuota.
   - All'interno trova un registro degli appuntamenti con una lista di persone che avevano visitato la clinica. Tra questi c'è il nome di Sato e il suo indirizzo.
2. **Indizi nella Clinica**:
   - Accede al computer del dottore e scopre un software per controllare da remoto i pacemaker.
   - Trova il corpo del dott. Tanaka, morto, con un mazzo di chiavi al collo.

### **Capitolo 3**: La Connessione con Sato
1. **Ricerca su Sato**:
   - L'agente scopre che Sato è un programmatore esperto.
   - Trova un ritaglio di giornale che rivela la storia di Sato: sua sorella era una paziente del dott. Tanaka e morì a causa di esperimenti medici falliti.
2. **Scoperta del Nascondiglio**:
   - Analizzando il registro degli appuntamenti della clinica, l'agente nota orari sospetti (ad esempio, mezzanotte).
   - I pazienti a quegli orari usavano nomi falsi, e le prime lettere dei nomi formano l'indirizzo del nascondiglio di Sato.

### **Capitolo 4**: Il Nascondiglio di Sato
1. **Il Piano di Sato**:
   - Sato sta pianificando un attacco informatico per uccidere persone con pacemaker, in segno di vendetta contro il dott. Tanaka.



## Mappa del gioco

### Ospedale di Neo Tokyo - 01

![Ospdeale di Neo Tokyo](./img/OspedaleNeoTOkyo.png)

| Codice | Luogo              |Nord|Sud|Est|Ovest|Alto|Basso|
|--|--------------------|--|-|--|--|--|--|
|01|Uscita|Ingresso|-|Porta di Sicurezza-16|-|-|-|
|02|Ingresso|Reception|Uscita|-|-|-|-|
|03|Reception|Cappella|Ingresso|Sala d'attesa|-|Corridoio1.1|-|
|04|Sala d'attesa|-|-|-|Reception|-|-|
|05|Cappella|-|Reception|-|-|-|-|
|06|Corridoio1.1|Corridoio1.2|-|Camera201|Camera200|-|Reception|
|07|Corridoio1.2|-|Corridoio1.1|Camera203|Camera202|Corridoio2|-|
|08|Stanza200|-|-|Corridoio1.1|-|-|-|
|09|Sanza201|-|-|-|Corridoio1.1|-|-|
|10|Sanza202|-|-|Corridoio1.2|-|-|-|
|11|Sanza203|-|-|-|Corridoio1.2|-|-|
|12|Corridoio2|-|Ufficio Dr. Chen|-|-|-|Corridoio1.2|
|13|Ufficio Dr. Chen|Corridoio2|-|-|-|-|-|
|14|Laboratorio medico|-|-|-|Corridoio2|-|-|

### Clinica del Dott. Tanaka - 02

![Clinica del Dott. Tanaka](./img/ClinicaDottTanaka.png)

|Codice|Luogo|Nord|Sud|Est|Ovest|Alto|Basso|
|--|--|--|--|--|--|--|--|
|15|Uscita|Porta di Sicurezza|-|Hall Principale-23|Ingresso-02|-|-|
|16|Porta di Sicurezza|Atrio|Uscita|-|-|-|-|
|17|Atrio|Laboratorio Principale|Porta di Sicurezza|-|-|-|-|
|18|Laboratorio Principale|Testing Area|Atrio|Ufficio|Server Room|-|-|
|19|Ufficio|-|-|-|Laboratorio Principale|-|-|
|20|Server Room|-|-|Laboratorio Principale|-|-|-|
|21|Testing Area|-|Laboratorio Principale|-|-|-|-|

### Magazzino abbandonato (Nascondiglio di Sato) - 03

![Nascondiglio di Sato](./img/NascondiglioSato.png)

|Codice|Luogo|Nord|Sud|Est|Ovest|Alto|Basso|
|--|--|--|--|--|--|--|--|
|22|Uscita|Halla Principale|-|-|Porta di sicurezza-16|-|-|
|23|Hall Principale|Corridoio|Uscita|-|-|-|-|
|24|Corridoio|-|Hall Principale|-|-|-|Server Room|
|25|Server Room|-|-|Sala di Controllo|-|Corridoio|Magazzino|
|26|Sala di Controllo|-|-|-|Server Room|-|-|
|27|Magazzino|-|-|-|-|Server Room|Nascodiglio di Sato|
|28|Nascodiglio di Sato|-|-|-|-|-|Magazzino|