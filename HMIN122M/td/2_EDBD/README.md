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

4.
4.1 : Un fait (j,p,c,m,x) existe lorsqu’un produit p est acheté par un client c le jour j au magasin m. La mesure x correspond au prix total.
a. Il s'agit d'un fait transactionnel car si c'était un snapshot il y aurait énormément de ligne qui valent zéro (les jours où il n'achete pas).
b. La mesure est additive car l'on peut ajouter les différentes dépenses.

4.2 : Un  fait (j,p,m,x) existe lorsqu’un produit p est acheté le jour j au magasin m. La mesure correspond au chiffre d’affaires.
a. Il s'agit d'un fait snapshot 
b. La mesure est additive

4.3 : Un fait (j,p,m,x) existe pour chaque combinaison de produit p, magasin m et jour j. La mesure x correspond au stock de p en m le jour j. 
a. Il s'agit d'un fait snapshot
b. La mesure est semi-additive, une mesure additive ici ne serait pas pertinante car on veut le nombre d'éléments par jour, il est pas nécessaire de les additioner

4.4 : Un fait (j,p,m,x) existe pour chaque combinaison de produit p, magasin m et jour j. La mesure x correspond au nombre de ventes de p en m cumulées depuis le début de l’année jusqu’au jour j.
a. Il s'agit d'un fait snapshot
b. La mesure est non additive

4.5 : Un fait (c,e,j) existe lorsqu’un appel du client c le jour j est traité par l’employée. Aucune mesure n’existe.
a. Il s'agit d'un fait transactionnel
b. Il n'y a pas de mesure

4.6 : Un fait (c,j,x) existe lorsqu’un client c le jour j laisse une note sur un produit acheté. La mesure x est la note donnée par le client.
a. Il s'agit d'un fait transactionnel
b. La mesure est semi-additive

4.7 : Un fait (c,e,j,x) existe lorsqu’un appel du client c le jour j est traité par l’employée. La mesure x est la durée de l’appel en secondes.
a. Il s'agit d'un fait transactionnel
b. La mesure est semi-additive

4.8 : Un fait (m,b,j,x) existe lorsque la monnaie m est changée à la banque b le jour j. La mesure x est le montant total de la monnaie changée en euros.
a. Il s'agit d'un fait snapshot
b. La mesure est additive

4.9 : Un fait (m,b,j,x) existe lorsque la monnaie m est changée à la banque b le jour j. La mesure x est le cours de change moyen de m en euros pour toutes les transactions du jour j.
a. Il s'agit d'un fait snapshot
b. La mesure est non-additive