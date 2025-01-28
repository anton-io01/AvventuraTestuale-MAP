-- Creazione della tabella Edifici
CREATE TABLE Edifici (
                         edificio_id VARCHAR(2) PRIMARY KEY,
                         nome VARCHAR(100) NOT NULL UNIQUE,
                         accessibile BOOLEAN DEFAULT true,
                         descrizione TEXT
);

-- Creazione della tabella Stanze
CREATE TABLE Stanze (
                        edificio_id VARCHAR(2) NOT NULL,
                        stanza_id VARCHAR(2) PRIMARY KEY,
                        nome VARCHAR(100) NOT NULL,
                        accessibile BOOLEAN DEFAULT true,
                        CONSTRAINT fk_edificio_stanze FOREIGN KEY (edificio_id) REFERENCES Edifici(edificio_id) ON DELETE CASCADE
);

-- Creazione della tabella DescrizioniStanze
CREATE TABLE DescrizioniStanze (
                                   edificio_id VARCHAR(2),
                                   stanza_id VARCHAR(2),
                                   descrizione_breve TEXT,
                                   descrizione_completa TEXT,
                                   PRIMARY KEY (edificio_id, stanza_id),
                                   CONSTRAINT fk_edificio_descrizioni FOREIGN KEY (edificio_id) REFERENCES Edifici(edificio_id) ON DELETE CASCADE,
                                   CONSTRAINT fk_stanza_descrizioni FOREIGN KEY (stanza_id) REFERENCES Stanze(stanza_id) ON DELETE CASCADE
);

-- Creazione della tabella Oggetti
CREATE TABLE Oggetti (
                         oggetto_id VARCHAR(2) PRIMARY KEY,
                         nome VARCHAR(100) NOT NULL UNIQUE,
                         descrizione TEXT,
                         raccoglibile BOOLEAN DEFAULT false
);

-- Creazione della tabella OggettiStanze
CREATE TABLE OggettiStanze (
                               oggetto_id VARCHAR(3),
                               stanza_id VARCHAR(2),
                               PRIMARY KEY (oggetto_id, stanza_id),
                               CONSTRAINT fk_oggetto_stanza FOREIGN KEY (oggetto_id) REFERENCES Oggetti(oggetto_id) ON DELETE CASCADE,
                               CONSTRAINT fk_stanza_oggetti FOREIGN KEY (stanza_id) REFERENCES Stanze(stanza_id) ON DELETE CASCADE,
);

-- Creazione della tabella Movimenti
CREATE TABLE Movimenti (
                           edificio_id VARCHAR(2),
                           stanza_id VARCHAR(2),
                           nord VARCHAR(2),
                           sud VARCHAR(2),
                           est VARCHAR(2),
                           ovest VARCHAR(2),
                           alto VARCHAR(2),
                           basso VARCHAR(2),
                           PRIMARY KEY (edificio_id, stanza_id),
                           CONSTRAINT fk_edificio_movimenti FOREIGN KEY (edificio_id) REFERENCES Edifici(edificio_id) ON DELETE CASCADE,
                           CONSTRAINT fk_stanza_movimenti FOREIGN KEY (stanza_id) REFERENCES Stanze(stanza_id) ON DELETE CASCADE,
                           CONSTRAINT fk_nord_movimenti FOREIGN KEY (nord) REFERENCES Stanze(stanza_id) ON DELETE SET NULL,
                           CONSTRAINT fk_sud_movimenti FOREIGN KEY (sud) REFERENCES Stanze(stanza_id) ON DELETE SET NULL,
                           CONSTRAINT fk_est_movimenti FOREIGN KEY (est) REFERENCES Stanze(stanza_id) ON DELETE SET NULL,
                           CONSTRAINT fk_ovest_movimenti FOREIGN KEY (ovest) REFERENCES Stanze(stanza_id) ON DELETE SET NULL,
                           CONSTRAINT fk_alto_movimenti FOREIGN KEY (alto) REFERENCES Stanze(stanza_id) ON DELETE SET NULL,
                           CONSTRAINT fk_basso_movimenti FOREIGN KEY (basso) REFERENCES Stanze(stanza_id) ON DELETE SET NULL
);

-- Creazione della tabella Azioni
CREATE TABLE Azioni (
                        alias VARCHAR(50) PRIMARY KEY,
                        azione_id VARCHAR(2) NOT NULL,
                        categoria VARCHAR(20) NOT NULL CHECK (categoria IN ('globali', 'interazione', 'navigazione'))
);

-- Creazione della tabella AzioniInterazione
CREATE TABLE AzioniInterazione (
                                   azione_id VARCHAR(2) PRIMARY KEY,
                                   oggetto_id VARCHAR(2) NOT NULL,
                                   descrizione TEXT,
                                   CONSTRAINT fk_oggetto FOREIGN KEY (oggetto_id) REFERENCES Oggetti(oggetto_id) ON DELETE CASCADE
);
