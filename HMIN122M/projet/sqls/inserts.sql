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
(7, 'Sennet', 'Sacré', 'Larocque', 50.13, 0, 'SennetSacre@rhyta.com', TO_DATE('1944/10/28', 'yyyy/mm/dd'), 76, '57, rue des lieutemants Thomazo',  'Digne-les-bains', 'France', 'Euros', 'Débutant', 'FR644368468');


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
(0, TO_DATE('2020/10/28:13:31:45', 'YYYY/MM/DD:HH24:MI:SS'));
INSERT INTO Dates (id_date, timestamp) VALUES
(1, TO_DATE('2020/10/30:13:32:47', 'YYYY/MM/DD:HH24:MI:SS'));
INSERT INTO Dates (id_date, timestamp) VALUES
(2, TO_DATE('2020/10/29:08:05:17', 'YYYY/MM/DD:HH24:MI:SS'));
INSERT INTO Dates (id_date, timestamp) VALUES
(3, TO_DATE('2020/10/01:09:00:00', 'YYYY/MM/DD:HH24:MI:SS'));
INSERT INTO Dates (id_date, timestamp) VALUES
(4, TO_DATE('2020/09/28:12:56:17', 'YYYY/MM/DD:HH24:MI:SS'));
INSERT INTO Dates (id_date, timestamp) VALUES
(5, TO_DATE('2020/10/01:10:16:13', 'YYYY/MM/DD:HH24:MI:SS'));
INSERT INTO Dates (id_date, timestamp) VALUES
(6, TO_DATE('2020/10/30:01:15:54', 'YYYY/MM/DD:HH24:MI:SS'));
INSERT INTO Dates (id_date, timestamp) VALUES
(7, TO_DATE('2020/10/12:02:15:54', 'YYYY/MM/DD:HH24:MI:SS'));
INSERT INTO Dates (id_date, timestamp) VALUES
(8, TO_DATE('2020/10/15:20:43:15', 'YYYY/MM/DD:HH24:MI:SS'));
INSERT INTO Dates (id_date, timestamp) VALUES
(9, TO_DATE('2020/10/13:16:11:15', 'YYYY/MM/DD:HH24:MI:SS'));
INSERT INTO Dates (id_date, timestamp) VALUES
(10, TO_DATE('2020/10/13:14:41:26', 'YYYY/MM/DD:HH24:MI:SS'));
INSERT INTO Dates (id_date, timestamp) VALUES
(11, TO_DATE('2020/10/16:16:02:34', 'YYYY/MM/DD:HH24:MI:SS'));
INSERT INTO Dates (id_date, timestamp) VALUES
(12, TO_DATE('2020/10/14:11:06:45', 'YYYY/MM/DD:HH24:MI:SS'));
INSERT INTO Dates (id_date, timestamp) VALUES
(13, TO_DATE('2020/10/16:16:34:26', 'YYYY/MM/DD:HH24:MI:SS'));
INSERT INTO Dates (id_date, timestamp) VALUES
(14, TO_DATE('2020/10/17:18:55:53', 'YYYY/MM/DD:HH24:MI:SS'));
INSERT INTO Dates (id_date, timestamp) VALUES
(15, TO_DATE('2020/10/16:16:55:53', 'YYYY/MM/DD:HH24:MI:SS'));
INSERT INTO Dates (id_date, timestamp) VALUES
(16, TO_DATE('2020/10/16:17:02:42', 'YYYY/MM/DD:HH24:MI:SS'));
INSERT INTO Dates (id_date, timestamp) VALUES
(17, TO_DATE('2020/10/30:20:53:02', 'YYYY/MM/DD:HH24:MI:SS'));
INSERT INTO Dates (id_date, timestamp) VALUES
(18, TO_DATE('2020/08/10:21:02:42', 'YYYY/MM/DD:HH24:MI:SS'));
INSERT INTO Dates (id_date, timestamp) VALUES
(19, TO_DATE('2020/10/30:16:23:25', 'YYYY/MM/DD:HH24:MI:SS'));

-- INSERT EVENEMENTS
INSERT INTO Evenements (id_evenement, organisateur, description, inscrit_max, cashprize, date_debut, date_fin) VALUES
(0, 'FDS Montpellier', 'Tournois de la faculte des sciences de montpellier !', 50, 1000, TO_DATE('2020/10/02:08:00:00', 'YYYY/MM/DD:HH24:MI:SS'), TO_DATE('2020/10/02:17:00:00', 'YYYY/MM/DD:HH24:MI:SS'));
INSERT INTO Evenements (id_evenement, organisateur, description, inscrit_max, cashprize, date_debut, date_fin) VALUES
(1, 'Halloween Party', 'Tournois Halloween annuel !', 5, 500, TO_DATE('2020/10/31:00:00:00', 'YYYY/MM/DD:HH24:MI:SS'), TO_DATE('2020/11/01:12:00:00', 'YYYY/MM/DD:HH24:MI:SS'));


-- INSERT PROMOTIONS
INSERT INTO Promotions (id_promotion, nom, reduction, type, date_debut, date_fin) VALUES
(0, 'Black Friday - 25 pourcent', 0.25, 'reduction', TO_DATE('2020/10/29:00:00:00', 'YYYY/MM/DD:HH24:MI:SS'), TO_DATE('2020/11/29:23:59:59', 'YYYY/MM/DD:HH24:MI:SS')); 
INSERT INTO Promotions (id_promotion, nom, reduction, type, date_debut, date_fin) VALUES
(1, 'Ticket VIP - 50 euros remises', 50, 'remise', TO_DATE('2020/10/29:09:17:06', 'YYYY/MM/DD:HH24:MI:SS'), TO_DATE('2020/11/05:09:17:06', 'YYYY/MM/DD:HH24:MI:SS')); 
INSERT INTO Promotions (id_promotion, nom, reduction, type, date_debut, date_fin) VALUES
(2, 'Promotion de Noel - 5 pourcent', 0.5, 'reduction', TO_DATE('2020/12/01:00:00:00', 'YYYY/MM/DD:HH24:MI:SS'), TO_DATE('2020/12/31:23:59:59', 'YYYY/MM/DD:HH24:MI:SS'));
INSERT INTO Promotions (id_promotion, nom, reduction, type) VALUES
(3, 'Inscription gratuite au evenement pour VIP', 0, 'reduction'); 
INSERT INTO Promotions (id_promotion, nom, reduction, type) VALUES
(4, 'Bonus 10 euros dépôt VIP', 0.1, 'bonus'); 
INSERT INTO Promotions (id_promotion, nom, reduction, type) VALUES
(5, 'Bonus 15 euros sur dépôt VIP Premium', 0.15, 'bonus');
INSERT INTO Promotions (id_promotion, nom, reduction, type) VALUES
(6, 'Code Promo anniversaire - 10 euros remises', 10, 'remise');

-- INSERT PAIEMENTS
INSERT INTO Paiements (id_joueur, id_format, id_promotion, id_date, quantite, type) VALUES
(0, 0, 1, 0, 400, 0);
INSERT INTO Paiements (id_joueur, id_format, id_promotion, id_date, quantite, type) VALUES
(1, 1, 0, 1, 200, 1);
INSERT INTO Paiements (id_joueur, id_format, id_date, quantite, type) VALUES
(2, 2, 2, 1000, 0);
INSERT INTO Paiements (id_joueur, id_format, id_date, quantite, type) VALUES
(3, 5, 7, 1000, 0);
INSERT INTO Paiements (id_joueur, id_format, id_date, quantite, type) VALUES
(3, 5, 8, 1000, 0);
INSERT INTO Paiements (id_joueur, id_format, id_date, quantite, type) VALUES
(4, 4, 9, 2000, 1);
INSERT INTO Paiements (id_joueur, id_format, id_date, quantite, type) VALUES
(5, 1, 10, 1000, 0);
INSERT INTO Paiements (id_joueur, id_format, id_date, quantite, type) VALUES
(5, 1, 11, 250, 1);
INSERT INTO Paiements (id_joueur, id_format, id_date, quantite, type) VALUES
(6, 1, 12, 200, 0);
INSERT INTO Paiements (id_joueur, id_format, id_date, quantite, type) VALUES
(6, 2, 13, 50, 1);
INSERT INTO Paiements (id_joueur, id_format, id_date, quantite, type) VALUES
(6, 2, 14, 25, 1);
INSERT INTO Paiements (id_joueur, id_format, id_date, quantite, type) VALUES
(7, 2, 15, 100, 0);
INSERT INTO Paiements (id_joueur, id_format, id_date, quantite, type) VALUES
(7, 2, 16, 100, 0);

-- INSERT INSCRIPTION
INSERT INTO Inscriptions (id_joueur, id_evenement, id_promotion, id_date, prix_inscription, gain) VALUES
(0, 0, 3, 3, 20, 60);
INSERT INTO Inscriptions (id_joueur, id_evenement, id_promotion, id_date, prix_inscription, gain) VALUES
(0, 1, 1, 4, 70, 0);
INSERT INTO Inscriptions (id_joueur, id_evenement, id_promotion, id_date, prix_inscription, gain) VALUES
(1, 1, 2, 5, 70, 30);
INSERT INTO Inscriptions (id_joueur, id_evenement, id_promotion, id_date, prix_inscription, gain) VALUES
(2, 1, 3, 6, 70, 40);
INSERT INTO Inscriptions (id_joueur, id_evenement, id_promotion, id_date, prix_inscription, gain) VALUES
(3, 1, 3, 17, 70, 200);
INSERT INTO Inscriptions (id_joueur, id_evenement, id_promotion, id_date, prix_inscription, gain) VALUES
(4, 1, 3, 18, 20, 0);
INSERT INTO Inscriptions (id_joueur, id_evenement, id_promotion, id_date, prix_inscription, gain) VALUES
(5, 0, 6, 19, 20, 20);