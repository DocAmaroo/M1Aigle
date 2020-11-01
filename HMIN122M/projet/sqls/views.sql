DROP VIEW Paiement2020View;
DROP VIEW PaiementNov2020View;
DROP VIEW CountInscritEvent0View;
DROP VIEW AvgInscritView;
DROP VIEW CountInscritThisMonthView;
DROP VIEW CountInscritThisMonthByEventView;
DROP VIEW JoueurInscritAtEventAcPromoView;

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

-- Sélection du nombre de joueur total inscrit sur un événement(tournoi, table, pari) précis
CREATE VIEW 
    CountInscritEvent0View (nbInscritEvent0)
AS
SELECT
    COUNT(i.id_joueur)
FROM
    Inscriptions i, Evenements e
WHERE
    i.id_evenement = 0
    AND i.id_evenement = e.id_evenement;

-- Moyenne du nombre de joueur inscrit aux événements
CREATE VIEW 
    AvgInscritView (nbAvgInscrit)
AS
SELECT
    AVG(COUNT(id_joueur))
FROM
    Inscriptions
GROUP BY
    id_evenement;

-- Nombre total des inscription ce mois-ci
CREATE VIEW 
    CountInscritThisMonthView (nbInscritThisMonth)
AS
SELECT
    COUNT(i.id_joueur)
FROM
    Inscriptions i, Dates d
WHERE
    i.id_date = d.id_date
    AND EXTRACT(MONTH FROM d.timestamp) = EXTRACT(MONTH FROM (SELECT CURRENT_TIMESTAMP FROM DUAL))
    AND EXTRACT(YEAR FROM d.timestamp) = EXTRACT(YEAR FROM (SELECT CURRENT_TIMESTAMP FROM DUAL));

-- Nombre total des inscription ce mois-ci par type d'evenement
CREATE VIEW 
    CountInscritThisMonthByEventView (idEvent, typeEvent, nbInscritThisMonth)
AS
SELECT
    e.id_evenement, e.type, COUNT(i.id_joueur) AS nbInscrit
FROM
    Inscriptions i, Dates d, Evenements e
WHERE
    i.id_date = d.id_date
    AND i.id_evenement = e.id_evenement
    AND EXTRACT(MONTH FROM d.timestamp) = EXTRACT(MONTH FROM (SELECT CURRENT_TIMESTAMP FROM DUAL))
    AND EXTRACT(YEAR FROM d.timestamp) = EXTRACT(YEAR FROM (SELECT CURRENT_TIMESTAMP FROM DUAL))
GROUP BY
    e.id_evenement, e.type;


-- Liste des joueurs qui se sont inscrit à la date d à un evenement à l'aide d'une promotion p
CREATE VIEW
    JoueurInscritAtEventAcPromoView (nom, prenom, e_description, promo_nom, promo_id)
AS
SELECT
    j.nom, j.prenom, e.description, p.nom, p.id_promotion
FROM
    Inscriptions i, Joueurs j, Promotions p, Evenements e
WHERE
    i.id_joueur = j.id_joueur
    AND i.id_evenement = e.id_evenement
    AND i.id_promotion = p.id_promotion;

-- EXEX DES VIEWS
-- SELECT * FROM AvgInscritView;
-- SELECT * FROM CountInscritEvent0View;
-- SELECT * FROM CountInscritThisMonthView;
-- SELECT * FROM CountInscritThisMonthByEventView;
-- SELECT * FROM JoueurInscritAtEventAcPromoView;

-- REQUESTS
-- Somme des dépôts ayant été effectué par un joueur précis sur un mois précis (ici novembre 2020)
SELECT id_j, nom_j, prenom_j, SUM(quantite) FROM PaiementNov2020View WHERE type = 0 GROUP BY id_j, nom_j, prenom_j;

-- Moyenne des dépôts effectué par les joueurs de 22 ans pendant le mois de novembre
SELECT id_j, nom_j, prenom_j, AVG(quantite) FROM PaiementNov2020View WHERE age_j = 22 AND type = 0 GROUP BY id_j, nom_j, prenom_j;

-- Somme de la quantite des dépôt effectué par chaque type de paiement
SELECT type_f, taxe_f, SUM(quantite) FROM PaiementNov2020View GROUP BY type_f, taxe_f;

-- Fréquence des dépôts lors d’une promotion (ici la promotion pour id 0)
SELECT COUNT(*) FROM PaiementNov2020View WHERE id_pm > 0 AND timestamp_d >= date_deb_pm AND timestamp_d <= date_fin_pm



-- nom et prénom des joueurs inscrit à un evenement avec une promotion d'id = (0 || 1 || 2)
SELECT nom, prenom FROM JoueurInscritAtEventAcPromoView WHERE promo_id = 0 OR promo_id = 1 OR promo_id = 2;