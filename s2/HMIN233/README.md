# HMIN233 - Algorithmes d'explorations et de mouvements

## Sommaire

- [HMIN233 - Algorithmes d'explorations et de mouvements](#hmin233---algorithmes-dexplorations-et-de-mouvements)
  - [Sommaire](#sommaire)
  - [Recherche local](#recherche-local)
    - [Définitions](#définitions)
    - [Algorithmes](#algorithmes)

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