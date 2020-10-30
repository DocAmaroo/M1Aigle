-- INSERT JOUEURS
INSERT INTO Joueurs(id_joueur, nom, prenom, pseudo, solde, type, email, date_naissance, adresse, ville, pays, haut_fait, coord_bancaire) VALUES
(0, 'Astley', 'Rick', 'Rickou', 1650.52, 1, 'nerver.gonna@giveyou.up', TO_DATE('1966/02/06', 'yyyy/mm/dd'), '75 avenue du rick rolled', 'Warrington', 'Angleterre', 'Sterling' 'Boss ultime', 'FR068480684384');
INSERT INTO Joueurs(id_joueur, nom, prenom, pseudo, solde, type, email, date_naissance, adresse, ville, pays, haut_fait, coord_bancaire) VALUES
(1, 'Canta', 'Thomas', 'Thominou', 250.21, 0, 'thomas.canta@etu.umontpellier.fr', TO_DATE('1998/09/28', 'yyyy/mm/dd'), '250 avenue des amandiers',  'Montpellier', 'France', 'Euros', 'Débutant', 'FR81385041350');
INSERT INTO Joueurs(id_joueur, nom, prenom, pseudo, solde, type, email, date_naissance, adresse, ville, pays, haut_fait, coord_bancaire) VALUES
(2, 'Gracia-Moulis', 'Kevin', 'Kevinou', 3523.24, 1, 'gracia-moulis.kevin@etu.umontpellier.fr', TO_DATE('1998/09/25', 'yyyy/mm/dd'), '35 rue black desert online',  'Montpellier', 'France', 'Euros', 'Expert', 'FR68486443');

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
(4, 'PaysafeCard', 0.05, 5, 3000);

-- INSERT DATES
INSERT INTO Dates (id_date, timestamp, fuseau) VALUES
(0, TO_DATE('13:31:45', 'HH24:MI:SS'), 28, 10, 2020,  1);
INSERT INTO Dates (id_date, timestamp, mois, annee, fuseau) VALUES
(1, TO_DATE('13:32:47', 'HH24:MI:SS'), 28, 10, 2020, 1);
INSERT INTO Dates (id_date, timestamp, mois, annee, fuseau) VALUES
(2, TO_DATE('00:00:00', 'HH24:MI:SS'), 29, 10, 2020, 0);
INSERT INTO Dates (id_date, timestamp, mois, annee, fuseau) VALUES
(3, TO_DATE('23:59:59', 'HH24:MI:SS'), 29, 11, 2020, 0);
INSERT INTO Dates (id_date, timestamp, mois, annee, fuseau) VALUES
(4, TO_DATE('08:04:57', 'HH24:MI:SS'), 29, 10, 2020, -10);
INSERT INTO Dates (id_date, timestamp, mois, annee, fuseau) VALUES
(5, TO_DATE('08:05:17', 'HH24:MI:SS'), 29, 10, 2020, -5);
INSERT INTO Dates (id_date, timestamp, mois, annee, fuseau) VALUES
(6, TO_DATE('09:17:06', 'HH24:MI:SS'), 29, 10, 2020, 2);
INSERT INTO Dates (id_date, timestamp, mois, annee, fuseau) VALUES
(7, TO_DATE('09:17:06', 'HH24:MI:SS'), 05, 11, 2020, 2);
INSERT INTO Dates (id_date, timestamp, mois, annee, fuseau) VALUES
(8, TO_DATE('00:00:00', 'HH24:MI:SS'), 01, 12, 2020, 0);
INSERT INTO Dates (id_date, timestamp, mois, annee, fuseau) VALUES
(9, TO_DATE('23:59:59', 'HH24:MI:SS'), 31, 12, 2020, 0);
INSERT INTO Dates (id_date, timestamp, mois, annee, fuseau) VALUES
(10, TO_DATE('08:00:00', 'HH24:MI:SS'), 02, 10, 2020, 0);
INSERT INTO Dates (id_date, timestamp, mois, annee, fuseau) VALUES
(11, TO_DATE('17:00:00', 'HH24:MI:SS'), 02, 10, 2020, 0);

-- INSERT EVENEMENTS
INSERT INTO Evenements (id_evenement, organisateur, inscrit, inscrit_max, cashprize, date_debut, date_fin) VALUES
(0, 'FDS Montpellier', 1, 50, 1000, 10, 11);


-- INSERT PROMOTIONS
INSERT INTO Promotions (id_promotion, nom, reduction, type, date_debut, date_fin) VALUES
(0, 'Black Friday', 0.25, 'reduction', 2, 3); 
INSERT INTO Promotions (id_promotion, nom, reduction, type, date_debut, date_fin) VALUES
(1, 'Ticket VIP', 50, 'remise', 6, 7); 
INSERT INTO Promotions (id_promotion, nom, reduction, type, date_debut, date_fin) VALUES
(2, 'Promotion de Noêl', 0.5, 'reduction', 8, 9);
INSERT INTO Promotions (id_promotion, nom, reduction, type) VALUES
(3, 'Inscription gratuite au évènement pour VIP', 0, 'reduction'); 

-- INSERT PAIEMENTS
INSERT INTO Paiements (id_joueur, id_format, id_promotion, id_date, quantite, type) VALUES
(0, 0, 1, 0, 400, 0);
INSERT INTO Paiements (id_joueur, id_format, id_promotion, id_date, quantite, type) VALUES
(1, 1, 0, 1, 200, 1);
INSERT INTO Paiements (id_joueur, id_format, id_date, quantite, type) VALUES
(2, 2, 4, 1000, 0);

-- INSERT INSCRIPTION
INSERT INTO Inscriptions (id_joueur, id_evenement, id_promotion, id_date, prix_inscription) VALUES
(0, 0, 3, 5, 20);