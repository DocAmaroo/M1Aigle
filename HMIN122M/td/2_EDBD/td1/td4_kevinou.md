1.
1.1
C'est une requete transactionnelle puisqu'on effectue une requete très précise.
1.2
C'est une requete analytique puisque l'on va devoir analyser les différentes salles libres correspondante au créneau.
1.3
C'est une requete analytique car l'on cherche le nombre de place dispo pour chaque cinéma, film, créneau.
1.4
C'est une requete analytique car on calcul le nombre de ventes pour chaque créneau.
1.5
C'est une requete transactionnelle car on insert simplement une vente.

2.
2.1
Amazon ( produit / client / vendeur / date / promotion )
2.2
Vente au centre reliant les 5 bouts de l'étoile avec un attribut commentaire.
2.3
SELECT idClient, count(*) FROM vente GROUP BY idClient; // Nbr d'achat du client
SELECT idVendeur, count(*) FROM vente GROUP BY idVendeur; // Nbr de vente du vendeur
SELECT idDate, count(*) FROM vente GROUP BY idDate; // Nbr de vente chaque jour

3.
3.1
SELECT id_produit, SUM(montant_journalier) FROM ventes_monoprix GROUP BY id_produit;
3.2
SELECT id_produit, id_ville, SUM(montant_journalier) FROM ventes_monoprix GROUP BY id_produit, id_ville;
3.3
SELECT id_produit, id_date, SUM(montant_journalier) FROM ventes_monoprix GROUP BY id_produit, id_date;
3.4
SELECT id_magasin, id_date, AVG(montant_journalier) FROM ventes_monoprix GROUP BY id_magasin, id_date;
3.5
SELECT id_ville, id_date, SUM(montant_journalier) FROM ventes_monoprix GROUP BY id_ville, id_date;
3.6
SELECT id_produit, id_ville, id_date, SUM(montant_journalier) FROM ventes_monoprix GROUP BY id_produit, id_ville, id_date;

Les options ROLLUP et CUBE permettrait d'enlever la plupart des requetes puisqu'elles effectueraient les différentes combinaisons.

4.
4.1
a. Il s'agit d'un 
b. La mesure est 
4.2
a. Il s'agit d'un 
b. La mesure est 
4.3
a. Il s'agit d'un 
b. La mesure est 
4.4
a. Il s'agit d'un 
b. La mesure est 
4.5
a. Il s'agit d'un 
b. Il n'y a pas de mesure.
4.6
a. Il s'agit d'un 
b. La mesure est 
4.7
a. Il s'agit d'un 
b. La mesure est 
4.8
a. Il s'agit d'un 
b. La mesure est 
4.9
a. Il s'agit d'un 
b. La mesure est 