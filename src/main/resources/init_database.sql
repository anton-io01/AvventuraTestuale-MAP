-- Creazione della tabella Edifici
CREATE TABLE IF NOT EXISTS Edifici (
                                       edificio_id VARCHAR(2) PRIMARY KEY,
    nome VARCHAR(100) NOT NULL UNIQUE,
    accessibile BOOLEAN DEFAULT true,
    descrizione TEXT
    );

-- Creazione della tabella Stanze
CREATE TABLE IF NOT EXISTS Stanze (
                                      edificio_id VARCHAR(2) NOT NULL,
    stanza_id VARCHAR(2) PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    accessibile BOOLEAN DEFAULT true,
    FOREIGN KEY (edificio_id) REFERENCES Edifici(edificio_id)
    );

-- Creazione della tabella DescrizioniStanze
CREATE TABLE IF NOT EXISTS DescrizioniStanze (
                                                 edificio_id VARCHAR(2) NOT NULL,
    stanza_id VARCHAR(2) NOT NULL,
    descrizione_breve TEXT,
    descrizione_completa TEXT,
    PRIMARY KEY (edificio_id, stanza_id),
    FOREIGN KEY (edificio_id) REFERENCES Edifici(edificio_id),
    FOREIGN KEY (stanza_id) REFERENCES Stanze(stanza_id)
    );

-- Creazione della tabella Oggetti
CREATE TABLE IF NOT EXISTS Oggetti (
                                       oggetto_id VARCHAR(2) PRIMARY KEY,
    nome VARCHAR(100) NOT NULL UNIQUE,
    raccoglibile BOOLEAN DEFAULT false,
    visibile BOOLEAN DEFAULT true
    );

-- Creazione della tabella OggettiStanze
CREATE TABLE IF NOT EXISTS OggettiStanze (
                                             oggetto_id VARCHAR(2) NOT NULL,
    stanza_id VARCHAR(2) NOT NULL,
    PRIMARY KEY (oggetto_id, stanza_id),
    FOREIGN KEY (oggetto_id) REFERENCES Oggetti(oggetto_id),
    FOREIGN KEY (stanza_id) REFERENCES Stanze(stanza_id)
    );

-- Creazione della tabella DescrizioniOggetti
CREATE TABLE IF NOT EXISTS DescrizioniOggetti (
                                                  oggetto_id VARCHAR(2) NOT NULL,
    descrizione_breve TEXT,
    descrizione_esamina TEXT,
    PRIMARY KEY (oggetto_id),
    FOREIGN KEY (oggetto_id) REFERENCES Oggetti(oggetto_id)
    );

-- Creazione della tabella Movimenti
CREATE TABLE IF NOT EXISTS Movimenti (
                                         edificio_id VARCHAR(2) NOT NULL,
    stanza_id VARCHAR(2) NOT NULL,
    nord VARCHAR(2),
    sud VARCHAR(2),
    est VARCHAR(2),
    ovest VARCHAR(2),
    alto VARCHAR(2),
    basso VARCHAR(2),
    PRIMARY KEY (edificio_id, stanza_id),
    FOREIGN KEY (edificio_id) REFERENCES Edifici(edificio_id),
    FOREIGN KEY (stanza_id) REFERENCES Stanze(stanza_id),
    FOREIGN KEY (nord) REFERENCES Stanze(stanza_id),
    FOREIGN KEY (sud) REFERENCES Stanze(stanza_id),
    FOREIGN KEY (est) REFERENCES Stanze(stanza_id),
    FOREIGN KEY (ovest) REFERENCES Stanze(stanza_id),
    FOREIGN KEY (alto) REFERENCES Stanze(stanza_id),
    FOREIGN KEY (basso) REFERENCES Stanze(stanza_id)
    );

-- Creazione della tabella Azioni
CREATE TABLE IF NOT EXISTS Azioni (
                                      azione_id VARCHAR(2) PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    categoria VARCHAR(20) NOT NULL
    );

-- Creazione della tabella AzioniInterazione
CREATE TABLE IF NOT EXISTS AzioniInterazione (
                                                 azione_id VARCHAR(2) PRIMARY KEY,
    oggetto_id VARCHAR(2) NOT NULL,
    stanza_id VARCHAR(2),
    FOREIGN KEY (azione_id) REFERENCES Azioni(azione_id),
    FOREIGN KEY (oggetto_id) REFERENCES Oggetti(oggetto_id),
    FOREIGN KEY (stanza_id) REFERENCES Stanze(stanza_id)
    );