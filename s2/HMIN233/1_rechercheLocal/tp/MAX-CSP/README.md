# HMIN233 - MAX-CSP

## Etudiants

| :mortar_board: **NOM** Prénom    | :email: Email                                  |
| -------------------------------- | ---------------------------------------------- |
| **CANTA** Thomas                 | thomas.canta@etu.umontpellier.fr               |
| **DESGENETEZ** Charles           | charles.desgenetez@etu.umontpellier.fr         |
| **FONTAINE** Quentin             | quentin.fontaine02@etu.umontpellier.fr         |
| **PRUD'HOMME GATEAU**  Sébastien | sebastien.prudhomme-gateau@etu.umontpellier.fr |
| **REITER** Maxime                | maxime.reiter@etu.umontpellier.fr              |

## Compilation et exécution
Méthode de compilation:
```bash
javac src/*.java
cd src
java Main COL_FILE_PATH
```

:pencil2: Exemple avec le fichier *test.col* fournis
```bash
java Main ../files/test.col
```

:pushpin: D'autre fichier *.col* sont utilisable dans le dossier *files*


:children_crossing: NB: Nous avons voulus utiliser la méthode recuit-simulé, qui nous est totalement inconnue (comme les autres méthodes). Le programme n'est supposement pas optimisée car il arrive à réduire le nombre de couleurs mais le processus est bien trop long. Malgrée tout il s'arrête au bout d'un certains (sans trouver le minimum).
