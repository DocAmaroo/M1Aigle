# TD 2

## 1. Coût de plans d’exécution logiques
### Question 1 
Cette requête permet d'obtenir le nom des étudiants inscrits au module intitulé "EDBD".

### Question 2
**Plan 1** &rarr; 4200 lignes intermédiaires crées.
**Plan 2** &rarr; 4200 lignes intermédiaires crées.
**Plan 3** &rarr; 4200 lignes intermédiaires crées.
**Plan 4** &rarr; 4200 lignes intermédiaires crées.

### Question 3
Le plan 3 est le plus optimal car il permet une sélection plus spécifique avant l'appel des jointures, réduisant ainsi le nombres maximum de colonnes.

## 2. Définition de plans d’exécution logiques
*voir fichier annexe*

## 3. Réécriture de plans d’exécution logiques

### Question 1
Elles retournent le même résultat car la sélection sur le titre ne s'applique qu'au journal. Si on "descend" la sélection dans l'arbre ceci ne change pas le résultat final.

*voir fichier annexe*

### Question 2
Oui, la deuxième me semble être plus efficace car la double sélection au départ permet de réduire le nombre de lignes.

## 4. Tous les plans d’exécution logiques
### Question 1
*voir fichier annexe*
*NB : Il existe beaucoup de variante possible pour représenter ce plan d'éxécution, j'ai en est donc représentés 3, dont l'une représente la plus optimale parmis toutes celles représentables*

### Question 2
Le premier plan est le plus optimale car il permet une sélection plus spécifique avant l'appel des jointures, réduisant ainsi le nombres maximum de lignes.

