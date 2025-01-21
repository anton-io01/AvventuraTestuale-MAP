# Report - Heartcode: il Virus

## Trama

### **Prologo**: Nuovo Distretto
- Il protagonista, un agente di polizia, viene assegnato a un nuovo distretto a Neo Tokyo.
- Durante il primo turno, una chiamata informa di una morte sospetta presso l'ospedale della città.

### **Capitolo 1**: Indagini in Ospedale
1. **Arrivo in Ospedale**:
    - L'agente si reca in auto presso l'ospedale per raccogliere maggiori informazioni.
    - Alla reception, parla con l'assistente del dottore, che lo indirizza verso la stanza della vittima.
2. **Indizi nella Stanza della Vittima**:
    - Trova un biglietto da visita del dott. Tanaka.
    - Riceve una nuova chiamata dalla centrale: un altro caso di morte sospetta viene segnalato. Anche in questo caso, la vittima portava un pacemaker difettoso.
3. **Analisi del Pacemaker**:
    - L'agente si reca al laboratorio tecnico dell'ospedale.
    - Scopre che il pacemaker contiene un virus che altera il battito cardiaco inviando impulsi letali al cuore.
4. **Nuovo Indizio**:
    - Gli agenti scoprono che la vittima era stata dal dott. Tanaka pochi giorni prima.
    - L'agente collega questo messaggio al biglietto da visita trovato nella stanza della vittima.

### **Capitolo 2**: La Clinica del Dott. Tanaka
1. **Scoperta della Clinica**:
    - L'agente si reca alla clinica del dott. Tanaka, che appare vuota.
    - All'interno trova un registro degli appuntamenti con una lista di persone che avevano visitato la clinica.
2. **Indizi nella Clinica**:
    - Accede al computer del dottore e scopre un software per controllare da remoto i pacemaker.
    - Trova il corpo del dott. Tanaka, morto, con un mazzo di chiavi al collo.
3. **Hard Disk e Video**:
    - Trova un hard disk contenente file criptati.
    - Decifra i file e scopre filmati delle telecamere che mostrano una persona, identificata come Sato, entrare nella clinica.

### **Capitolo 3**: La Connessione con Sato
1. **Ricerca su Sato**:
    - L'agente scopre che Sato è un programmatore esperto.
    - Trova un ritaglio di giornale che rivela la storia di Sato: sua sorella era una paziente del dott. Tanaka e morì a causa di esperimenti medici falliti.
2. **Scoperta del Nascondiglio**:
    - Analizzando il registro degli appuntamenti della clinica, l'agente nota orari sospetti (ad esempio, mezzanotte).
    - I pazienti a quegli orari usavano nomi falsi, e le prime lettere dei nomi formano l'indirizzo del nascondiglio di Sato.

### **Capitolo 4**: Il Nascondiglio di Sato
1. **Ingresso**:
    - L'agente si reca al nascondiglio e trova una porta di sicurezza, che riesce ad aprire risolvendo un puzzle.
2. **Il Piano di Sato**:
    - Sato sta pianificando un attacco informatico per uccidere persone con pacemaker, in segno di vendetta contro il dott. Tanaka.
    - Parte un timer: l'agente deve fermare l'attacco risolvendo una serie di minigame al computer.

### **Epilogo**: Finale Ramificato
1. **Finale Positivo**:
    - L'agente disattiva l'attacco in tempo, salvando le vittime.
    - Riesce ad aprire la porta dove si trova Sato e lo arresta, portando prove schiaccianti.
2. **Finale Negativo**:
    - Se fallisce nel disattivare l'attacco, molte persone muoiono.
    - Se non riesce ad aprire la porta in tempo, Sato cancella tutte le prove e, nonostante venga arrestato, viene rilasciato.

## Direzioni
Nord = 1  
Sud = 2  
Est = 3  
Ovest = 4  
Alto = 5  
Basso = 6

## Edifici
### Distretto di polizia

### Ospedale di Neo Tokyo - 02
|Codice|Luogo|Nord|Sud|Est|Ovest|Alto|Basso|
|--|--|--|--|--|--|--|--|
|01|Ingresso|Reception|Uscita|-|-|-|-|
|02|Reception|Cappella|Ingresso|Sala d'attesa|-|Corridoio1.1|-|
|03|Sala d'attesa|-|-|-|Reception|-|-|
|04|Cappella|-|Reception|-|-|-|-|
|05|Corridoio1.1|Corridoio1.2|-|Camera201|Camera200|-|Reception|
|06|Corridoio1.2|-|Corridoio1.1|Camera203|Camera202|Corridoio2|-|
|07|Stanza200|-|-|Corridoio1.1|-|-|-|
|08|Sanza201|-|-|-|Corridoio1.1|-|-|
|09|Sanza202|-|-|Corridoio1.2|-|-|-|
|10|Sanza203|-|-|-|Corridoio1.2|-|-|
|11|Corridoio2|-|Ufficio Dr. Chen|-|-|-|Corridoio1.2|
|12|Ufficio Dr. Chen|Corridoio2|-|-|-|-|-|
|13|Laboratorio medico|-|-|-|Corridoio2|-|-|

### Clinica del Dott. Tanaka - 03
|Codice|Luogo|Nord|Sud|Est|Ovest|Alto|Basso|
|--|--|--|--|--|--|--|--|
|01|Porta di Sicurezza|Atrio|Uscita|-|-|-|-|
|02|Atrio|Laboratorio Principale|Porta di Sicurezza|-|-|-|-|
|03|Laboratorio Principale|Testing Area|Atrio|Ufficio|Server Room|-|-|
|04|Ufficio|-|-|-|Laboratorio Principale|-|-|
|05|Server Room|-|-|Laboratorio Principale|-|-|-|
|06|Testing Area|-|Laboratorio Principale|-|-|-|-|

### Nascondiglio di Sato
