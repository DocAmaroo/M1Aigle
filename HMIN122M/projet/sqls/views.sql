set linesize 450;

DROP VIEW Paiement2020View;
DROP VIEW PaiementNov2020View;
DROP VIEW Inscription2020View;
DROP VIEW InscriptionNov2020View;

-- _______________________________ --
-- __________ PAIEMENTS __________ --
-- _______________________________ --

-- TOUT LES PAIEMENTS DE CETTE ANNEE
CREATE VIEW 
    Paiement2020View (id_j, nom_j, prenom_j, type_j, age_j, pays_j, timestamp_d, quantite, type, type_f, taxe_f, id_pm, date_deb_pm, date_fin_pm)
AS
SELECT 
    p.id_joueur, j.nom, j.prenom, j.type, j.age, j.pays, d.timestamp, p.quantite, p.type, f.type, f.taxe, pm.id_promotion, pm.date_debut, pm.date_fin
FROM 
    Paiements p, Dates d, Joueurs j, Formats f, Promotions pm
WHERE 
    p.id_date = d.id_date
    AND p.id_joueur = j.id_joueur
    AND p.id_format = f.id_format
    AND p.id_promotion = pm.id_promotion
    AND EXTRACT(YEAR FROM d.timestamp) = EXTRACT(YEAR FROM (SELECT CURRENT_TIMESTAMP FROM DUAL));

-- TOUT LES PAIEMENTS DU MOIS DE NOVEMBRE 2020
CREATE VIEW 
    PaiementNov2020View (id_j, nom_j, prenom_j, type_j, age_j, pays_j, timestamp_d, quantite, type, type_f, taxe_f, id_pm, date_deb_pm, date_fin_pm)
AS
SELECT 
    *
FROM 
    Paiement2020View
WHERE 
    EXTRACT(MONTH FROM timestamp_d) = '11';

-- _______________________________ --
-- ________ INSCRIPTIONS _________ --
-- _______________________________ --

-- TOUTES LES INSCRIPTIONS DE CETTE ANNEE
CREATE VIEW 
    Inscription2020View (id_j, nom_j, prenom_j, type_j, age_j, pays_j, timestamp_d, prix_insc, gain, id_e, type_e, cashprize_e, id_pm, date_deb_pm, date_fin_pm)
AS
SELECT 
    i.id_joueur, j.nom, j.prenom, j.type, j.age, j.pays, d.timestamp, prix_inscription, gain, e.id_evenement, e.type, e.cashprize, pm.id_promotion, pm.date_debut, pm.date_fin
FROM 
    Inscriptions i, Dates d, Joueurs j, Evenements e, Promotions pm
WHERE 
    i.id_date = d.id_date
    AND i.id_joueur = j.id_joueur
    AND i.id_evenement = e.id_evenement
    AND i.id_promotion = pm.id_promotion
    AND EXTRACT(YEAR FROM d.timestamp) = EXTRACT(YEAR FROM (SELECT CURRENT_TIMESTAMP FROM DUAL));

-- TOUTES LES INSCRIPTIONS DU MOIS DE NOVEMBRE 2020
CREATE VIEW 
    InscriptionNov2020View (id_j, nom_j, prenom_j, type_j, age_j, pays_j, timestamp_d, prix_insc, gain, id_e, type_e, cashprize_e, id_pm, date_deb_pm, date_fin_pm)
AS
SELECT 
    *
FROM 
    Inscription2020View
WHERE 
    EXTRACT(MONTH FROM timestamp_d) = '11';

-- REQUESTS
-- Somme des dépôts ayant été effectué par un joueur précis sur un mois précis (ici novembre 2020)
SELECT id_j, nom_j, prenom_j, SUM(quantite) FROM PaiementNov2020View WHERE type = 0 GROUP BY id_j, nom_j, prenom_j;

-- Moyenne des dépôts effectué par les joueurs de 22 ans pendant le mois de novembre
SELECT id_j, nom_j, prenom_j, AVG(quantite) FROM PaiementNov2020View WHERE age_j = 22 AND type = 0 GROUP BY id_j, nom_j, prenom_j;

-- Somme de la quantite des dépôt effectué par chaque type de paiement
SELECT type_f, taxe_f, SUM(quantite) FROM PaiementNov2020View GROUP BY type_f, taxe_f;

-- Nombres de dépôts effectués lors d'une promotion en novembre par promotion
SELECT id_pm, COUNT(*) FROM PaiementNov2020View WHERE id_pm > -1 GROUP BY id_pm;

-- Nombre de joueur total inscrit au évenement de l'année par évènement
SELECT id_e AS ID_E, COUNT(id_j) AS NB_INSCRIT_ON_2020 FROM Inscription2020View GROUP BY id_e;

-- Nombre de joueur total inscrit au évenement du mois de novembre par évènement
SELECT id_e AS ID_E, COUNT(id_j) AS NB_INSCRIT_ON_NOV_2020 FROM InscriptionNov2020View GROUP BY id_e;

-- Somme des gains obtenus par inscriptions par joueurs en 2020
SELECT id_j, nom_j, prenom_j, SUM(gain) AS GAIN_TOTAL_2020 FROM Inscription2020View GROUP BY id_j, nom_j, prenom_j;

-- Somme des gains obtenus par inscriptions par joueurs de novembre en 2020
SELECT id_j, nom_j, prenom_j, SUM(gain) AS GAIN_TOTAL_NOV FROM InscriptionNov2020View GROUP BY id_j, nom_j, prenom_j;

-- Somme des gains par évenements obtenus durant le mois de novembre 2020,
-- et le cashprize de l'évènement est supérieur à 999€
SELECT id_e, SUM(gain) AS GAIN_TOTAL_EVENT_NOV FROM InscriptionNov2020View WHERE cashprize_e > 999 GROUP BY id_e;

-- Nom et prénom des joueurs qui ce sont inscrit à un evenement avec une promotion d'anniversaire
SELECT nom_j, prenom_j FROM Inscription2020View WHERE id_pm = 6;