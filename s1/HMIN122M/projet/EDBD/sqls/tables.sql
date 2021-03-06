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
    solde DECIMAL(10,3),
    type NUMBER(1),
    email VARCHAR(64),
    date_naissance DATE,
    age NUMBER(3),
    adresse VARCHAR(127),
    ville VARCHAR(127),
    pays VARCHAR(64),
    forme_monetaire VARCHAR(64),
    haut_fait VARCHAR(64),
    coord_bancaire VARCHAR(20),
    CONSTRAINT PK_ID_JOUEUR PRIMARY KEY (id_joueur)
);

CREATE TABLE Formats (
    id_format NUMBER(2),
    type VARCHAR(32),
    taxe DECIMAL(3,2),
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
    organisateur VARCHAR(20),
    description VARCHAR(127),
    type VARCHAR(25),
    inscrit_max NUMBER(5),
    cashprize NUMBER(7),
    date_debut TIMESTAMP,
    date_fin TIMESTAMP,
    CONSTRAINT PK_ID_EVENEMENT PRIMARY KEY (id_evenement)
);

CREATE TABLE Promotions (
    id_promotion NUMBER(7),
    nom VARCHAR(50),
    reduction DECIMAL(5,2),
    type VARCHAR(20),
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
    CONSTRAINT FK_INSCRIPTION_EVENEMENT FOREIGN KEY (id_evenement) REFERENCES Evenements (id_evenement),
    CONSTRAINT FK_INSCRIPTION_PROMOTION FOREIGN KEY (id_promotion) REFERENCES Promotions (id_promotion),
    CONSTRAINT FK_INSCRIPTION_DATE FOREIGN KEY (id_date) REFERENCES Dates (id_date)
);

-- _______________________________ --
-- ___________ INSERTS ___________ --
-- _______________________________ --

-- INSERT JOUEURS
INSERT INTO Joueurs(id_joueur, nom, prenom, pseudo, solde, type, email, date_naissance, age, adresse, ville, pays, forme_monetaire, haut_fait, coord_bancaire) VALUES
(0, 'Astley', 'Rick', 'Rickou', 1650.52, 1, 'nerver.gonna@giveyou.up', TO_DATE('1966/02/06', 'yyyy/mm/dd'), 54, '75 avenue du rick rolled', 'Warrington', 'Angleterre', 'Sterling', 'Boss ultime', 'FR068480684384');
INSERT INTO Joueurs(id_joueur, nom, prenom, pseudo, solde, type, email, date_naissance, age, adresse, ville, pays, forme_monetaire, haut_fait, coord_bancaire) VALUES
(1, 'Canta', 'Thomas', 'Thominou', 250.21, 0, 'thomas.canta@etu.umontpellier.fr', TO_DATE('1998/09/28', 'yyyy/mm/dd'), 22, '250 avenue des amandiers',  'Montpellier', 'France', 'Euros', 'Debutant', 'FR81385041350');
INSERT INTO Joueurs(id_joueur, nom, prenom, pseudo, solde, type, email, date_naissance, age, adresse, ville, pays, forme_monetaire, haut_fait, coord_bancaire) VALUES
(2, 'Gracia-Moulis', 'Kevin', 'Kevinou', 3523.24, 1, 'gracia-moulis.kevin@etu.umontpellier.fr', TO_DATE('1998/09/25', 'yyyy/mm/dd'), 22, '35 rue black desert online',  'Montpellier', 'France', 'Euros', 'Expert', 'FR68486443');
INSERT INTO Joueurs(id_joueur, nom, prenom, pseudo, solde, type, email, date_naissance, age, adresse, ville, pays, forme_monetaire, haut_fait, coord_bancaire) VALUES
(3, 'Philippine ', 'Laforest', 'Jodion', 102.41, 1, 'PhilippineLaforest@armyspy.com', TO_DATE('1985/09/21', 'yyyy/mm/dd'), 35, '6, rue du Fossé des Tanneurs',  'Torcy', 'France', 'Euros', 'Débutant', 'FR4864436');
INSERT INTO Joueurs(id_joueur, nom, prenom, pseudo, solde, type, email, date_naissance, age, adresse, ville, pays, forme_monetaire, haut_fait, coord_bancaire) VALUES
(4, 'Bayard', 'Patenaude', 'Larocque', 8656.51, 1, 'patatax2000@fritas.com', TO_DATE('1968/12/28', 'yyyy/mm/dd'), 51, '10, rue Ernest Renan',  'Chelles', 'France', 'Euros', 'Profesionnel', 'FR644368468');
INSERT INTO Joueurs(id_joueur, nom, prenom, pseudo, solde, type, email, date_naissance, age, adresse, ville, pays, forme_monetaire, haut_fait, coord_bancaire) VALUES
(5, 'Voleta', 'Paquette', 'Quiron', 2203.05, 0, 'VoletaPaquette@jourrapide.com', TO_DATE('1952/05/12', 'yyyy/mm/dd'), 68, '84, cours Jean Jaures',  'Bordeaux', 'France', 'Euros', 'Amateur', 'FR436846858');
INSERT INTO Joueurs(id_joueur, nom, prenom, pseudo, solde, type, email, date_naissance, age, adresse, ville, pays, forme_monetaire, haut_fait, coord_bancaire) VALUES
(6, 'Senior', 'Parmentier', 'Bonenfant', 800.15, 0, 'SeniorParmentier@teleworm.us', TO_DATE('1988/03/22', 'yyyy/mm/dd'), 32, '72, rue du Général Ailleret',  'Lens', 'France', 'Euros', 'Débutant', 'FR436846877');
INSERT INTO Joueurs(id_joueur, nom, prenom, pseudo, solde, type, email, date_naissance, age, adresse, ville, pays, forme_monetaire, haut_fait, coord_bancaire) VALUES
(7, 'Sennet', 'Sacre', 'Larocque', 50.13, 0, 'SennetSacre@rhyta.com', TO_DATE('1944/10/28', 'yyyy/mm/dd'), 76, '57, rue des lieutemants Thomazo',  'Digne-les-bains', 'France', 'Euros', 'Débutant', 'FR644368468');


-- INSERT FORMATS
INSERT INTO Formats (id_format, type, taxe, montant_min, montant_max) VALUES 
(0, 'Paypal', 0.05, 5, 1500);
INSERT INTO Formats (id_format, type, taxe, montant_min, montant_max) VALUES 
(1, 'Skrill', 0.05, 5, 1500);
INSERT INTO Formats (id_format, type, taxe, montant_min, montant_max) VALUES 
(2, 'Virement Bancaire', 0.05, 5, 3000);
INSERT INTO Formats (id_format, type, taxe, montant_min, montant_max) VALUES 
(3, 'Carte Bancaire', 0.05, 5, 3000);
INSERT INTO Formats (id_format, type, taxe, montant_min, montant_max) VALUES 
(4, 'CashLib', 0.05, 2, 200);
INSERT INTO Formats (id_format, type, taxe, montant_min, montant_max) VALUES 
(5, 'PaysafeCard', 0.05, 5, 3000);

-- INSERT DATES
INSERT INTO Dates (id_date, timestamp) VALUES
(0, TO_DATE('2020/11/28:13:31:45', 'YYYY/MM/DD:HH24:MI:SS'));
INSERT INTO Dates (id_date, timestamp) VALUES
(1, TO_DATE('2020/11/25:13:32:47', 'YYYY/MM/DD:HH24:MI:SS'));
INSERT INTO Dates (id_date, timestamp) VALUES
(2, TO_DATE('2020/11/29:08:05:17', 'YYYY/MM/DD:HH24:MI:SS'));
INSERT INTO Dates (id_date, timestamp) VALUES
(3, TO_DATE('2020/11/01:09:00:00', 'YYYY/MM/DD:HH24:MI:SS'));
INSERT INTO Dates (id_date, timestamp) VALUES
(4, TO_DATE('2020/09/28:12:56:17', 'YYYY/MM/DD:HH24:MI:SS'));
INSERT INTO Dates (id_date, timestamp) VALUES
(5, TO_DATE('2020/10/01:10:16:13', 'YYYY/MM/DD:HH24:MI:SS'));
INSERT INTO Dates (id_date, timestamp) VALUES
(6, TO_DATE('2020/10/30:01:15:54', 'YYYY/MM/DD:HH24:MI:SS'));
INSERT INTO Dates (id_date, timestamp) VALUES
(7, TO_DATE('2020/11/12:02:15:54', 'YYYY/MM/DD:HH24:MI:SS'));
INSERT INTO Dates (id_date, timestamp) VALUES
(8, TO_DATE('2020/11/15:20:43:15', 'YYYY/MM/DD:HH24:MI:SS'));
INSERT INTO Dates (id_date, timestamp) VALUES
(9, TO_DATE('2020/11/13:16:11:15', 'YYYY/MM/DD:HH24:MI:SS'));
INSERT INTO Dates (id_date, timestamp) VALUES
(10, TO_DATE('2020/11/13:14:41:26', 'YYYY/MM/DD:HH24:MI:SS'));
INSERT INTO Dates (id_date, timestamp) VALUES
(11, TO_DATE('2020/11/16:16:02:34', 'YYYY/MM/DD:HH24:MI:SS'));
INSERT INTO Dates (id_date, timestamp) VALUES
(12, TO_DATE('2020/11/14:11:06:45', 'YYYY/MM/DD:HH24:MI:SS'));
INSERT INTO Dates (id_date, timestamp) VALUES
(13, TO_DATE('2020/11/16:16:34:26', 'YYYY/MM/DD:HH24:MI:SS'));
INSERT INTO Dates (id_date, timestamp) VALUES
(14, TO_DATE('2020/11/17:18:55:53', 'YYYY/MM/DD:HH24:MI:SS'));
INSERT INTO Dates (id_date, timestamp) VALUES
(15, TO_DATE('2020/11/16:16:55:53', 'YYYY/MM/DD:HH24:MI:SS'));
INSERT INTO Dates (id_date, timestamp) VALUES
(16, TO_DATE('2020/11/16:17:02:42', 'YYYY/MM/DD:HH24:MI:SS'));
INSERT INTO Dates (id_date, timestamp) VALUES
(17, TO_DATE('2020/10/30:20:53:02', 'YYYY/MM/DD:HH24:MI:SS'));
INSERT INTO Dates (id_date, timestamp) VALUES
(18, TO_DATE('2020/08/10:21:02:42', 'YYYY/MM/DD:HH24:MI:SS'));
INSERT INTO Dates (id_date, timestamp) VALUES
(19, TO_DATE('2020/11/30:16:23:25', 'YYYY/MM/DD:HH24:MI:SS'));
INSERT INTO Dates (id_date, timestamp) VALUES
(20, TO_DATE('2020/11/10:15:35:16', 'YYYY/MM/DD:HH24:MI:SS'));
INSERT INTO Dates (id_date, timestamp) VALUES
(21, TO_DATE('2020/11/10:14:26:13', 'YYYY/MM/DD:HH24:MI:SS'));
INSERT INTO Dates (id_date, timestamp) VALUES
(22, TO_DATE('2020/11/02:16:02:15', 'YYYY/MM/DD:HH24:MI:SS'));
INSERT INTO Dates (id_date, timestamp) VALUES
(23, TO_DATE('2020/11/16:16:56:34', 'YYYY/MM/DD:HH24:MI:SS'));
INSERT INTO Dates (id_date, timestamp) VALUES
(24, TO_DATE('2020/11/17:17:24:35', 'YYYY/MM/DD:HH24:MI:SS'));
INSERT INTO Dates (id_date, timestamp) VALUES
(25, TO_DATE('2020/11/21:18:25:56', 'YYYY/MM/DD:HH24:MI:SS'));

-- INSERT EVENEMENTS
INSERT INTO Evenements (id_evenement, organisateur, description, type, inscrit_max, cashprize, date_debut, date_fin) VALUES
(0, 'FDS Montpellier', 'Tournois de la faculte des sciences de montpellier !', 'Poker', 50, 1000, TO_DATE('2020/11/02:08:00:00', 'YYYY/MM/DD:HH24:MI:SS'), TO_DATE('2020/11/02:17:00:00', 'YYYY/MM/DD:HH24:MI:SS'));
INSERT INTO Evenements (id_evenement, organisateur, description, type, inscrit_max, cashprize, date_debut, date_fin) VALUES
(1, 'Polytech', 'Tournois Halloween annuel !', 'Poker', 5, 500, TO_DATE('2020/10/31:00:00:00', 'YYYY/MM/DD:HH24:MI:SS'), TO_DATE('2020/11/01:12:00:00', 'YYYY/MM/DD:HH24:MI:SS'));
INSERT INTO Evenements (id_evenement, organisateur, description, type, cashprize, date_debut, date_fin) VALUES
(2, 'FDJ', 'Match France - Bresil ce soir a 21h', 'Pari', 5000, TO_DATE('2020/11/10:00:00:00', 'YYYY/MM/DD:HH24:MI:SS'), TO_DATE('2020/11/10:22:30:00', 'YYYY/MM/DD:HH24:MI:SS'));


-- INSERT PROMOTIONS
INSERT INTO Promotions (id_promotion) VALUES
(-1);
INSERT INTO Promotions (id_promotion, nom, reduction, type, date_debut, date_fin) VALUES
(0, 'Black Friday - 25 pourcent', 0.25, 'reduction', TO_DATE('2020/10/29:00:00:00', 'YYYY/MM/DD:HH24:MI:SS'), TO_DATE('2020/11/29:23:59:59', 'YYYY/MM/DD:HH24:MI:SS')); 
INSERT INTO Promotions (id_promotion, nom, reduction, type, date_debut, date_fin) VALUES
(1, 'Ticket VIP - 50 euros remises', 50, 'remise', TO_DATE('2020/11/29:09:17:06', 'YYYY/MM/DD:HH24:MI:SS'), TO_DATE('2020/12/06:09:17:06', 'YYYY/MM/DD:HH24:MI:SS')); 
INSERT INTO Promotions (id_promotion, nom, reduction, type, date_debut, date_fin) VALUES
(2, 'Promotion de Noel - 5 pourcent', 0.50, 'reduction', TO_DATE('2020/12/01:00:00:00', 'YYYY/MM/DD:HH24:MI:SS'), TO_DATE('2020/12/31:23:59:59', 'YYYY/MM/DD:HH24:MI:SS'));
INSERT INTO Promotions (id_promotion, nom, reduction, type) VALUES
(3, 'Inscription gratuite au evenement pour VIP', 0, 'reduction'); 
INSERT INTO Promotions (id_promotion, nom, reduction, type) VALUES
(4, 'Bonus 10 euros dépôt VIP', 0.10, 'bonus'); 
INSERT INTO Promotions (id_promotion, nom, reduction, type) VALUES
(5, 'Bonus 15 euros sur dépôt VIP Premium', 0.15, 'bonus');
INSERT INTO Promotions (id_promotion, nom, reduction, type) VALUES
(6, 'Code Promo anniversaire - 10 euros remises', 10, 'remise');

-- INSERT PAIEMENTS
INSERT INTO Paiements (id_joueur, id_format, id_promotion, id_date, quantite, type) VALUES
(0, 0, 1, 0, 400, 0);
INSERT INTO Paiements (id_joueur, id_format, id_promotion, id_date, quantite, type) VALUES
(1, 1, 0, 1, 200, 1);
INSERT INTO Paiements (id_joueur, id_format, id_promotion, id_date, quantite, type) VALUES
(2, 2, 2, 0, 1000, 0);
INSERT INTO Paiements (id_joueur, id_format, id_promotion, id_date, quantite, type) VALUES
(3, 5, 4, 7, 1000, 0);
INSERT INTO Paiements (id_joueur, id_format, id_promotion, id_date, quantite, type) VALUES
(3, 5, 5, 8, 1000, 0);
INSERT INTO Paiements (id_joueur, id_format, id_promotion, id_date, quantite, type) VALUES
(4, 4, 5, 9, 2000, 1);
INSERT INTO Paiements (id_joueur, id_format, id_promotion, id_date, quantite, type) VALUES
(5, 1, -1, 10, 1000, 0);
INSERT INTO Paiements (id_joueur, id_format, id_promotion, id_date, quantite, type) VALUES
(5, 1, -1, 11, 250, 1);
INSERT INTO Paiements (id_joueur, id_format, id_promotion, id_date, quantite, type) VALUES
(6, 1, 1, 12, 200, 0);
INSERT INTO Paiements (id_joueur, id_format, id_promotion, id_date, quantite, type) VALUES
(6, 2, 1, 13, 50, 1);
INSERT INTO Paiements (id_joueur, id_format, id_promotion, id_date, quantite, type) VALUES
(6, 2, 1, 14, 25, 1);
INSERT INTO Paiements (id_joueur, id_format, id_promotion, id_date, quantite, type) VALUES
(7, 2, -1, 15, 100, 0);
INSERT INTO Paiements (id_joueur, id_format, id_promotion, id_date, quantite, type) VALUES
(7, 2, -1, 16, 100, 0);
INSERT INTO Paiements (id_joueur, id_format, id_promotion, id_date, quantite, type) VALUES
(0, 2, 4, 22, 50, 0);
INSERT INTO Paiements (id_joueur, id_format, id_promotion, id_date, quantite, type) VALUES
(1, 2, 0, 23, 1000, 0);
INSERT INTO Paiements (id_joueur, id_format, id_promotion, id_date, quantite, type) VALUES
(2, 2, 4, 24, 100, 0);
INSERT INTO Paiements (id_joueur, id_format, id_promotion, id_date, quantite, type) VALUES
(3, 2, 4, 25, 20, 0);

-- INSERT INSCRIPTION
INSERT INTO Inscriptions (id_joueur, id_evenement, id_promotion, id_date, prix_inscription, gain) VALUES
(0, 0, 3, 3, 20, 1000);
INSERT INTO Inscriptions (id_joueur, id_evenement, id_promotion, id_date, prix_inscription, gain) VALUES
(0, 1, 1, 4, 70, 0);
INSERT INTO Inscriptions (id_joueur, id_evenement, id_promotion, id_date, prix_inscription, gain) VALUES
(1, 1, 2, 5, 70, 0);
INSERT INTO Inscriptions (id_joueur, id_evenement, id_promotion, id_date, prix_inscription, gain) VALUES
(2, 1, 3, 6, 70, 500);
INSERT INTO Inscriptions (id_joueur, id_evenement, id_promotion, id_date, prix_inscription, gain) VALUES
(3, 1, 3, 17, 70, 0);
INSERT INTO Inscriptions (id_joueur, id_evenement, id_promotion, id_date, prix_inscription, gain) VALUES
(4, 1, 3, 18, 20, 0);
INSERT INTO Inscriptions (id_joueur, id_evenement, id_promotion, id_date, prix_inscription, gain) VALUES
(5, 0, 6, 19, 20, 0);
INSERT INTO Inscriptions (id_joueur, id_evenement, id_promotion, id_date, prix_inscription, gain) VALUES
(6, 2, -1, 20, 5, 20);
INSERT INTO Inscriptions (id_joueur, id_evenement, id_promotion, id_date, prix_inscription, gain) VALUES
(7, 2, -1, 21, 10, 300);