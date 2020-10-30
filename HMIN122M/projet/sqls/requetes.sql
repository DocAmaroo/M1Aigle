-- Somme des dépôts ayant été effectué par un joueur précis sur un mois précis (ici novembre 2020)
SELECT 
    id_joueur, SUM(quantite) 
FROM 
    Paiements p, Dates d 
WHERE 
    p.id_date = d.id_date 
    AND p.type = 0 
    AND EXTRACT(MONTH FROM d.timestamp) = 10 
    AND EXTRACT(YEAR FROM d.timestamp) = 2020 
GROUP BY 
    id_joueur;

-- Moyenne des dépôts annuels effectué par les joueurs de 22 ans
SELECT 
    p.id_joueur, AVG(quantite) 
FROM 
    Paiements p, Dates d , Joueurs j 
WHERE 
    p.id_joueur = j.id_joueur 
    AND p.id_date = d.id_date 
    AND p.type = 0 
    AND EXTRACT(YEAR FROM d.timestamp) = 2020 
    AND j.age = 22 
GROUP BY 
    p.id_joueur;

-- Fréquence des dépôts lors d’une promotion
SELECT
    COUNT(p.id_promotion)
FROM
    Paiements p, Dates d, Promotions pr
WHERE
    p.id_promotion = 0
    AND p.id_date = d.id_date
    AND p.id_promotion = pr.id_promotion
    AND d.timestamp >= (SELECT timestamp FROM Dates d WHERE d.id_date = pr.date_debut)
    AND d.timestamp <= (SELECT timestamp FROM Dates d WHERE d.id_date = pr.date_fin);