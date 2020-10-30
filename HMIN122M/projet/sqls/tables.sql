DROP TABLE IF EXISTS Inscriptions;
DROP TABLE IF EXISTS Paiements;
DROP TABLE IF EXISTS Joueurs;
DROP TABLE IF EXISTS Formats;
DROP TABLE IF EXISTS Promotions;
DROP TABLE IF EXISTS Dates;

CREATE TABLE Paiements (
    id_joueur INT(7),
    id_format INT(7),
    id_promotion INT(7),
    id_date INT(7),
    quantite INT(7),
    type INT(1),
    CONSTRAINT FK_PAIEMENT_JOUEUR FOREIGN KEY (id_joueur) REFERENCES Joueurs (id_joueur),
    CONSTRAINT FK_PAIEMENT_FORMAT FOREIGN KEY (id_format) REFERENCES Formats (id_format),
    CONSTRAINT FK_PAIEMENT_PROMOTION FOREIGN KEY (id_promotion) REFERENCES Promotions (id_promotion),
    CONSTRAINT FK_PAIEMENT_DATE FOREIGN KEY (id_date) REFERENCES Dates (id_date)
);

CREATE TABLE Inscriptions (
    id_joueur INT(7),
    id_format INT(7),
    id_promotion INT(7),
    id_date INT(7),
    prix_inscription INT(7),
    CONSTRAINT FK_PAIEMENT_JOUEUR FOREIGN KEY (id_joueur) REFERENCES Joueurs (id_joueur),
    CONSTRAINT FK_PAIEMENT_FORMAT FOREIGN KEY (id_format) REFERENCES Formats (id_format),
    CONSTRAINT FK_PAIEMENT_PROMOTION FOREIGN KEY (id_promotion) REFERENCES Promotions (id_promotion),
    CONSTRAINT FK_PAIEMENT_DATE FOREIGN KEY (id_date) REFERENCES Dates (id_date)
);

CREATE TABLE Joueurs (
    id_joueur INT(7),
    nom VARCHAR(40),
    prenom VARCHAR(40),
    pseudo VARCHAR(40),
    solde INT(8),
    type INT(1),
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
    id_format INT(7),
    type VARCHAR(127),
    taxe DECIMAL(3),
    montant_min INT(7),
    montant_max INT(7),
    forme_monetaire VARCHAR(127),
    CONSTRAINT PK_ID_FORMAT PRIMARY KEY (id_format)
);

CREATE TABLE Promotions (
    id_promotion INT(7),
    nom VARCHAR(255),
    reduction DECIMAL(3),
    type VARCHAR(127),
    date_debut INT(7),
    date_fin INT(7),
    CONSTRAINT PK_ID_PROMOTION PRIMARY KEY (id_promotion),
    CONSTRAINT FK_PROMOTION_DATE_DEBUT FOREIGN KEY (date_debut) REFERENCES Dates (id_date),
    CONSTRAINT FK_PROMOTION_DATE_FIN FOREIGN KEY (date_fin) REFERENCES Dates (id_date)
);

CREATE TABLE Dates (
    id_date INT(7),
    timestamp Date,
    mois INT(2),
    annee INT(4),
    fuseau INT(2),
    CONSTRAINT PK_ID_DATE PRIMARY KEY (id_date)
);