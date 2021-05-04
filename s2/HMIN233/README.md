# HMIN233 - Algorithmes d'explorations et de mouvements

## Sommaire

- [HMIN233 - Algorithmes d'explorations et de mouvements](#hmin233---algorithmes-dexplorations-et-de-mouvements)
  - [Sommaire](#sommaire)
  - [Recherche local](#recherche-local)
    - [Définitions](#définitions)
    - [Algorithmes](#algorithmes)
  - [Fouille de donnée](#fouille-de-donnée)
  - [Mouvement](#mouvement)
    - [Définitions](#définitions-1)
    - [Calcul des vecteurs](#calcul-des-vecteurs)
    - [Choix de chemin](#choix-de-chemin)

## Recherche local

### Définitions

💡 `Objective Function` &rarr; la fonction des coûts à optimiser. minimisation possible:
* date d'échéance de la dernière tâche du problème.
* nombre de ressources.
* prix de la production.
* nombre de contraintes violées.

💡 `Voisinage` &rarr; Ensemble des config.s qui peuvent être obtenues par une transformation locale de la config. actuelle. 
* Coloration de graphes: changer une couleur
* SAT: "flip" d'une var. booléenne
* CSP: modification de la valeur d'une variable

💡 `Modèles SAT et CSP` &rarr; Résoudre le problème en minimisant les conflits dans MAX-SAT et MAX-CSP

💡 `MAX-SAT` &rarr; minimise le nombre de clauses violées

💡 `MAX-CSP` &rarr; minimise le nombre de contraintes violées

💡 `Recherche locale` &rarr; améliorer une config. courante par des transformations locales itératives.

### Algorithmes

#### Inconvéniant d'un métaheuristiques

🔴 `Incomplétude` &rarr; la recherche n'est pas systématique (toutes les possibilités ne sont pas essayées) => aucune preuve garantie que la meilleure solution a été trouvée.

🔴 `Optimum local` &rarr; une recherche locale peut être bloquée à l'intérieur d'un optimum local ou sur un plateau et visiter plusieurs fois la même config.

🔴 `Sensibilité` à la config. initiale

#### Optimisation possibles

🟢 `GSAT` &rarr; Interrompre la recherche en cours et réessayer avec une nouvelle config.

🟢 `Algo génétique` &rarr; Gérer plusieurs config. en parallèle.

🟢 `Recherche Tabu` &rarr; Enregistrez les derniers mouvements pour éviter de revenir en boucle sur les mêmes config.

🟢 `Recuit simulé` &rarr; Accepter parfois une config. qui donne une moins bonne config.

🟢 `IDWalk` &rarr; N'utilisez que la gestion des voisins pour intensifier ou diversifier la recherche.

## Fouille de donnée

/ feuille déjà faite

## Mouvement

### Définitions

💡 `Flocking` &rarr; Mouvement d'agent (homme, oiseau, ...) qui se déplacent en formation.

### Calcul des vecteurs

Voir pdf

### Choix de chemin

#### Difficultées

🔴 Trouver le but le plus rapidement

🔴 Ne pas rester bloqué (obstacle || min. local)

🔴 Optimiser le chemin (détour inutile)

#### Choix possibles

🟢 `Dijkstra` &rarr; Explorer l'espace et essayer de trouver le "meilleur" chemin.

* On ne sait pas où se trouve le but.
* On parcours tout l'espace.
* On trouve le meilleur chemin de cette espace.

🏁 Recherche coûteuse mais résultat parfait.

🟢 `Best-first` &rarr; Essayer d'atteindre le but en utilisant une heuristique.

* On choisis toujours le chemin qui diminue au mieux la distance avec le but. Si blocage on explore autour.

🏁 Recherche rapide mais pas optimal.

🟢 `A*` &rarr; Combinaison des deux précédents.

* On applique l'heuristique de `Best-first`
* On optimise le chemin parcourus en tenant compte de la distance à l'origine comme avec `Dijkstra`.

🏁 Bon compromis entre temps de recherche et qualité. (#IA)