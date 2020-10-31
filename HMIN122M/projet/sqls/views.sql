DROP VIEW SumDepotNov2020View;
DROP VIEW AvgDepotAnnuel22yoView;
DROP VIEW SumDepotPaypalView;
DROP VIEW CountDepotPromo0View;
DROP VIEW CountInscritEvent0View;
DROP VIEW AvgInscritView;
DROP VIEW CountInscritThisMonthView;
DROP VIEW JoueurInscritAtEventADayDView;

-- Somme des dépôts ayant été effectué par un joueur précis sur un mois précis (ici octobre 2020)
CREATE VIEW 
    SumDepotNov2020View (id_joueur, sumDepotOct2020)
AS
SELECT 
    id_joueur, SUM(quantite) 
FROM 
    Paiements p, Dates d 
WHERE 
    p.id_date = d.id_date 
    AND p.type = 0 
    AND EXTRACT(MONTH FROM d.timestamp) = '10' 
    AND EXTRACT(YEAR FROM d.timestamp) = '2020' 
GROUP BY 
    id_joueur;

-- Moyenne des dépôts annuels effectué par les joueurs de 22 ans
CREATE VIEW 
    AvgDepotAnnuel22yoView (id_joueur, avgDepot22yo)
AS
SELECT 
    p.id_joueur, AVG(quantite) 
FROM 
    Paiements p, Dates d, Joueurs j 
WHERE 
    p.id_joueur = j.id_joueur 
    AND p.id_date = d.id_date 
    AND p.type = 0 
    AND EXTRACT(YEAR FROM d.timestamp) = '2020'
    AND j.age = 22 
GROUP BY 
    p.id_joueur;

-- Somme de la quantite des dépôt effectué via Paypal le jour j, avec une promotion p par joueur
CREATE VIEW 
    SumDepotPaypalView (id_joueur, sumDepot28Oct2020)
AS
SELECT
    p.id_joueur, SUM(quantite)
FROM
    Paiements p, Joueurs j, Dates d, Formats f, Promotions pr
WHERE
    p.id_joueur = j.id_joueur
    AND p.id_date = d.id_date
    AND EXTRACT(DAY FROM d.timestamp) = '28'
    AND EXTRACT(MONTH FROM d.timestamp) = '10'
    AND EXTRACT(YEAR FROM d.timestamp) = '2020'
    AND p.id_format = f.id_format
    AND p.id_promotion = pr.id_promotion
    AND p.type = 0
GROUP BY
    p.id_joueur;

-- Fréquence des dépôts lors d’une promotion
CREATE VIEW 
    CountDepotPromo0View (nbDepotAcPromo0)
AS
SELECT
    COUNT(p.id_promotion)
FROM
    Paiements p, Dates d, Promotions pr
WHERE
    p.id_promotion = 0
    AND p.id_date = d.id_date
    AND p.id_promotion = pr.id_promotion
    AND d.timestamp >= pr.date_debut
    AND d.timestamp <= pr.date_fin;

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

-- Liste des joueurs qui se sont inscrit à la date d à un evenement à l'aide d'une promotion p
CREATE VIEW
    JoueurInscritAtEventADayDView (joueur_nom, joueur_prenom, event_description, promo_nom)
AS
SELECT
    j.nom, j.prenom, e.description, p.nom
FROM
    Inscriptions i, Dates d, Joueurs j, Promotions p, Evenements e
WHERE
    i.id_date = d.id_date
    AND i.id_joueur = j.id_joueur
    AND i.id_evenement = e.id_evenement
    AND i.id_promotion = p.id_promotion
    AND EXTRACT(DAY FROM d.timestamp) = '30'
    AND EXTRACT(MONTH FROM d.timestamp) = '10'
    AND EXTRACT(YEAR FROM d.timestamp) = '2020';

-- EXEX DES VIEWS
SELECT * FROM SumDepotNov2020View;
SELECT * FROM AvgDepotAnnuel22yoView;
SELECT * FROM SumDepotPaypalView;
SELECT * FROM CountDepotPromo0View;
SELECT * FROM AvgInscritView;
SELECT * FROM CountInscritEvent0View;
SELECT * FROM CountInscritThisMonthView;
SELECT * FROM JoueurInscritAtEventADayDView;