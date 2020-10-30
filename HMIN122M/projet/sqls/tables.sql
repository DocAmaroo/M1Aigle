DROP TABLE Inscriptions;
DROP TABLE Paiements;
DROP TABLE Joueurs;
DROP TABLE Formats;
DROP TABLE Promotions;
DROP TABLE Dates;

CREATE TABLE Joueurs (
    id_joueur NUMBER(7),
    nom VARCHAR(40),
    prenom VARCHAR(40),
    pseudo VARCHAR(40),
    solde NUMBER(8),
    type NUMBER(1),
    email VARCHAR(255),
    date_naissance Date,
    adresse VARCHAR(255),
    ville VARCHAR(255),
    pays VARCHAR(255),
    haut_fait VARCHAR(255),
    coord_bancaire VARCHAR(255),
    CONSTRAINT PK_ID_JOUEUR PRIMARY KEY (id_joueur)
);

CREATE TABLE Formats (
    id_format NUMBER(7),
    type VARCHAR(127),
    taxe DECIMAL(3),
    montant_min NUMBER(7),
    montant_max NUMBER(7),
    forme_monetaire VARCHAR(127),
    CONSTRAINT PK_ID_FORMAT PRIMARY KEY (id_format)
);

CREATE TABLE Dates (
    id_date NUMBER(7),
    timestamp Date,
    mois NUMBER(2),
    annee NUMBER(4),
    fuseau NUMBER(2),
    CONSTRAINT PK_ID_DATE PRIMARY KEY (id_date)
);

CREATE TABLE Promotions (
    id_promotion NUMBER(7),
    nom VARCHAR(255),
    reduction DECIMAL(3),
    type VARCHAR(127),
    date_debut NUMBER(7),
    date_fin NUMBER(7),
    CONSTRAINT PK_ID_PROMOTION PRIMARY KEY (id_promotion),
    CONSTRAINT FK_PROMOTION_DATE_DEBUT FOREIGN KEY (date_debut) REFERENCES Dates (id_date),
    CONSTRAINT FK_PROMOTION_DATE_FIN FOREIGN KEY (date_fin) REFERENCES Dates (id_date)
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
    id_format NUMBER(7),
    id_promotion NUMBER(7),
    id_date NUMBER(7),
    prix_inscription NUMBER(7),
    CONSTRAINT FK_INSCRIPTION_JOUEUR FOREIGN KEY (id_joueur) REFERENCES Joueurs (id_joueur),
    CONSTRAINT FK_INSCRIPTION_FORMAT FOREIGN KEY (id_format) REFERENCES Formats (id_format),
    CONSTRAINT FK_INSCRIPTION_PROMOTION FOREIGN KEY (id_promotion) REFERENCES Promotions (id_promotion),
    CONSTRAINT FK_INSCRIPTION_DATE FOREIGN KEY (id_date) REFERENCES Dates (id_date)
);

