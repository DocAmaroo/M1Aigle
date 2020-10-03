# Entrepôts de données

## 1. Les interrogations : requêtes transactionnelles vs analytiques.

1. C'est une requête transactionelle car le but ici n'est pas de faire de l'analyse sur le résultat de cette requêtes, au contraire on affiche une information très précise.

2. C'est une requête analytique car ici on veut analyser les résultats de cette requête afin de pouvoir potentiellement tirer des bénéfices en offrant plus de places et plus de séances.

3. C'est une requête analytique car ici on affiche le nombres de places totales par le titre du film, nom du cinéma et le mois ceci pourrait permettre, par la suite, une analyse plus complexe  de ces données.

4. C'est une requête analytique car on calcul le nombre de ventes pour chaque créneau.

5. C'est une requête transactionnelle, cette requête n'a pas vocation à être analysée


## 2. Un entrepôt de données pour Amazon

1. Voir image

2. On ajoute les commentaires en attr de la table ventes

3.
```sql
-- Nbr d'achat du client
SELECT idClient, count(*) FROM vente GROUP BY idClient;

-- Nbr de vente du vendeur
SELECT idVendeur, count(*) FROM vente GROUP BY idVendeur;

-- Nbr de vente chaque jour
SELECT idDate, count(*) FROM vente GROUP BY idDate; 
```


## 3. Requêtes Analytiques
1.
```sql
SELECT id_produit, SUM(montant_journalier) FROM ventes_monoprix GROUP BY id_produit;
SELECT id_produit, SUM(montant_journalier) FROM ventes_monoprix GROUP BY ROLLUP id_produit;
SELECT id_produit, SUM(montant_journalier) FROM ventes_monoprix GROUP BY CUBE id_produit;
```

2.
```sql
SELECT id_produit, id_ville, SUM(montant_journalier) FROM ventes_monoprix GROUP BY id_produit, id_ville;
SELECT id_produit, id_ville, SUM(montant_journalier) FROM ventes_monoprix GROUP BY ROLLUP id_produit, id_ville;
SELECT id_produit, id_ville, SUM(montant_journalier) FROM ventes_monoprix GROUP BY CUBE id_produit, id_ville;
```

3.
```sql
SELECT id_produit, id_date, SUM(montant_journalier) FROM ventes_monoprix GROUP BY id_produit, id_date;
SELECT id_produit, id_date, SUM(montant_journalier) FROM ventes_monoprix GROUP BY ROLLUP id_produit, id_date;
SELECT id_produit, id_date, SUM(montant_journalier) FROM ventes_monoprix GROUP BY CUBE id_produit, id_date;
```
4.
```sql
SELECT id_magasin, id_date, AVG(montant_journalier) FROM ventes_monoprix GROUP BY id_magasin, id_date;
SELECT id_magasin, id_date, AVG(montant_journalier) FROM ventes_monoprix GROUP BY ROLLUP id_magasin, id_date;
SELECT id_magasin, id_date, AVG(montant_journalier) FROM ventes_monoprix GROUP BY CUBE id_magasin, id_date;
```

5.
```sql
SELECT id_ville, id_date, SUM(montant_journalier) FROM ventes_monoprix GROUP BY id_ville, id_date;
SELECT id_ville, id_date, SUM(montant_journalier) FROM ventes_monoprix GROUP BY ROLLUP id_ville, id_date;
SELECT id_ville, id_date, SUM(montant_journalier) FROM ventes_monoprix GROUP BY CUBE id_ville, id_date;
```

6.
```sql
SELECT id_produit, id_ville, id_date, SUM(montant_journalier) FROM ventes_monoprix GROUP BY id_produit, id_ville, id_date;
SELECT id_produit, id_ville, id_date, SUM(montant_journalier) FROM ventes_monoprix GROUP BY ROLLUP id_produit, id_ville, id_date;
SELECT id_produit, id_ville, id_date, SUM(montant_journalier) FROM ventes_monoprix GROUP BY CUBE id_produit, id_ville, id_date;
```

Les options ROLLUP et CUBE permettrait d'enlever la plupart des requetes puisqu'elles effectueraient les différentes combinaisons.
