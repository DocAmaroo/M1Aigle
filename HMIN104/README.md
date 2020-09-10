# HMIN102 - Compilation et interprétation
*"D. Delahaye :heart:"*

## Sommaire
* [Liens utiles](#liens-utiles)
* [Introduction](#introduction)
    * [Définitions](#définitions)
    * [Difficultées](#difficultées)
    * [Attentes d'un compilateur](#attentes-dun-compilateur)
* [Programme](#programme)

## Liens utiles :
[*Accès au moodle*](https://moodle.umontpellier.fr/course/view.php?id=5906 "Accèder au moodle") | `clé: compil;2020`

## Introduction
### Définitions
:bulb: compilateur &rarr; `traduit un lang. de haut niv. vers du bas niv.`

:bulb: analyse syntax. &rarr; `passer du txt. à une structure arborescente sur laquelle on peut travailler.`

:bulb: compilat° à la volée <small>*('Just-In-Time' JIT)*</small> &rarr; `Combinaison de la compilation native et bytecode pour offrir portabilité et performance.`

### Difficultées
:children_crossing: Choix de la structure de data (AST)

:children_crossing: Décomposit° en étape intermédiaire

:children_crossing: Bonne connaissance du lang. cible

:children_crossing: Gestion des erreurs

### Attentes d'un compilateur
:triangular_flag_on_post: **Correction** &rarr; le prog. traduit fait ce qu'on att. de lui)

:triangular_flag_on_post: **Efficacité**

## Programme
* Compilat° native (+opti. | lang. c)
* Compilat° bytecode (+portable | lang. Java)

## Le langage
*Voir diapos 14 &rarr; [intro.pdf](https://github.com/DocAmaroo/M1Aigle/blob/master/HMIN104/cours/intro.pdf "cours d'introduction HMIN104")*

:fire: Le langage utilisé est Turing-complet.

