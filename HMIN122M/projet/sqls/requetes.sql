-- Somme des dépôts ayant été effectué par un joueur précis sur un mois précis
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