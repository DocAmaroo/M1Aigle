DROP TABLE Inscriptions;
DROP TABLE Paiements;
DROP TABLE Evenements;
DROP TABLE Joueurs;
DROP TABLE Formats;
DROP TABLE Promotions;
DROP TABLE Dates;

CREATE TABLE Joueurs (
    id_joueur NUMBER(7),
    nom VARCHAR(40),
    prenom VARCHAR(40),
    pseudo VARCHAR(40),
    solde FLOAT(8),
    type NUMBER(1),
    email VARCHAR(255),
    date_naissance DATE,
    age NUMBER(3),
    adresse VARCHAR(255),
    ville VARCHAR(255),
    pays VARCHAR(255),
    forme_monetaire VARCHAR(127),
    haut_fait VARCHAR(255),
    coord_bancaire VARCHAR(255),
    CONSTRAINT PK_ID_JOUEUR PRIMARY KEY (id_joueur)
);

CREATE TABLE Formats (
    id_format NUMBER(2),
    type VARCHAR(127),
    taxe DECIMAL(3),
    montant_min NUMBER(7),
    montant_max NUMBER(7),
    CONSTRAINT PK_ID_FORMAT PRIMARY KEY (id_format)
);

CREATE TABLE Dates (
    id_date NUMBER(7),
    timestamp TIMESTAMP,
    CONSTRAINT PK_ID_DATE PRIMARY KEY (id_date)
);

CREATE TABLE Evenements (
    id_evenement NUMBER(7),
    organisateur VARCHAR(255),
    inscrit NUMBER(5),
    inscrit_max NUMBER(5),
    cashprize NUMBER(7),
    date_debut TIMESTAMP,
    date_fin TIMESTAMP,
    CONSTRAINT PK_ID_EVENEMENT PRIMARY KEY (id_evenement)
);

CREATE TABLE Promotions (
    id_promotion NUMBER(7),
    nom VARCHAR(255),
    reduction DECIMAL(3),
    type VARCHAR(127),
    date_debut TIMESTAMP,
    date_fin TIMESTAMP,
    CONSTRAINT PK_ID_PROMOTION PRIMARY KEY (id_promotion)
);

CREATE TABLE Paiements (
    id_joueur NUMBER(7),
    id_format NUMBER(7),
    id_promotion NUMBER(7),
    id_date NUMBER(7),
    quantite NUMBER(7),
    type NUMBER(1),
    CONSTRAINT FK_PAIEMENT_JOUEUR FOREIGN KEY (id_joueur) REFERENCES Joueurs (id_joueur),
    CONSTRAINT FK_PAIEMENT_FORMAT FOREIGN KEY (id_format) REFERENCES Formats (id_format),
    CONSTRAINT FK_PAIEMENT_PROMOTION FOREIGN KEY (id_promotion) REFERENCES Promotions (id_promotion),
    CONSTRAINT FK_PAIEMENT_DATE FOREIGN KEY (id_date) REFERENCES Dates (id_date)
);

CREATE TABLE Inscriptions (
    id_joueur NUMBER(7),
    id_evenement NUMBER(7),
    id_promotion NUMBER(7),
    id_date NUMBER(7),
    prix_inscription NUMBER(7),
    gain NUMBER(7),
    CONSTRAINT FK_INSCRIPTION_JOUEUR FOREIGN KEY (id_joueur) REFERENCES Joueurs (id_joueur),
    CONSTRAINT FK_INSCRIPTION_EVENEMENT FOREIGN KEY (id_evenement) REFERENCES Evenement (id_evenement),
    CONSTRAINT FK_INSCRIPTION_PROMOTION FOREIGN KEY (id_promotion) REFERENCES Promotions (id_promotion),
    CONSTRAINT FK_INSCRIPTION_DATE FOREIGN KEY (id_date) REFERENCES Dates (id_date)
);
