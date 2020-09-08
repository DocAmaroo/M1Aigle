# HMIN102 - Ingénierie logicielle
*"Développement de logiciel réutilisable"*

## Sommaire
* [Liens utiles](#liens-utiles)
* [1. Programme](#1-programme)
* [2. Réutilisation](#2-réutilisation)
    * [2.1 - Définitions](#21-définitions)
    * [2.2 - Généralisation](#22-généralisation)
* [3. Schémas avancés de paramétrage](#3-schémas-avancés-de-paramétrage)
    * [3.1 - Définitions](#31-définitions)
    * [3.2 - Entités d'ordre supérieur](#32-entités-dordre-supérieur)
        * [3.2.1 Paramétrage d’une fonction par une autre](#321-paramétrage-dune-fonction-par-une-autre)

## Liens utiles :
[*Accès au cours*](http://www.lirmm.fr/~dony/ "Accèder au cours") <small> ["UEs en cours"] </small>

[*Accès au moodle*](https://moodle.umontpellier.fr/course/view.php?id=5908 "Accèder au moodle")

## 1. Programme
* Shéma de réutilisation utilisant composition & spécialisation.
* Application aux hiérarchies des classes, "API", "Framework" et "lignes de produits".
* Schéma de conception <small> *(design patterns)* </small>.

## 2. Réutilisation
Ensemble de *théories, méthodes, techniques, outils* permettant de *récupérer, étendre*, **adapter à de nouveaux contextes**. <small> (si possible, sans modification du code déjà existant) </small>

### 2.1 Définitions
- [ ] <em style="color:rgba(46, 170, 57, 0.8);">extensibilité</em> &rarr; capacité d'ajouter de nouvelles f°nalités.
- [ ] <em style="color:rgba(46, 170, 57, 0.8);">adaptabilité</em> &rarr; capacité de modifier ses f°nalités.
- [ ] <em style="color:rgba(46, 170, 57, 0.8);">entité générique</em> &rarr; entité apte à être utilisé dans plusieurs concept.
- [ ] <em style="color:rgba(46, 170, 57, 0.8);">variabilité</em> &rarr; néologisme dénotant la façon dont un système est susceptible de founir des f°nalités pouvant varier.
- [ ] <em style="color:rgba(46, 170, 57, 0.8);">paramètre (param)</em> &rarr; nom dénotant un el. var. d'un système/calcul.
- [ ] <em style="color:rgba(46, 170, 57, 0.8);">fonction (f°)</em> &rarr; composition d'op. permettant sa réa. sans recopie.
- [ ] <em style="color:rgba(46, 170, 57, 0.8);">procédure (proc)</em> &rarr; suite d'instruct° permettant sa réa. sans recopie.
- [ ] <em style="color:rgba(46, 170, 57, 0.8);">f°/proc. avec paramètre(s)</em> &rarr; composition d'opé./suites d'instruct° des val.s de ses params. .
    ```scheme
    ;exemple d'une fonction paramétré
    (define (carre x) (* x x))
    ```
- [ ] <em style="color:rgba(46, 170, 57, 0.8);">application</em> &rarr; *liason des params. formels* aux *params actuels* puis execute la f° dans l'env. résultant.
    ```scheme
    ;exemple d'application
    (carre 2)
    = 4
    ```
- [ ] <em style="color:rgba(46, 170, 57, 0.8);">composition de f°</em> &rarr; les fonctions peuvent être composables par enchainement d'appel.
    ```scheme
    ;exemple de composition
    (sqrt(square(9)))
    = 9
    ```
    <small>*NB : les procédures ne sont pas composables*</small>
### 2.2 Généralisation
#### Décomposer & paramétrer
1. **Décomposer en éléments.**
<small><em>éléments</em> &rarr; proc., f°, obj, classe, API, framework, module, etc...</small>

2. **Paramétrer**: identifier, nommer et matérialiser ce qui peut varier.

#### Configurer & composer
1. **Configurer** les el. paramatrés <small>*(en les instanciant)*</small> et valuant les param.
```scheme
;exemple de valuation d'un paramètre lors d'une application
(carre 5)
```

2. **Composé** les el.s configurés

    ![Software Reuse image](https://www.clker.com/cliparts/b/f/d/c/11954226151896466519anywhere_info_Software_Reuse.svg.med.png)
    
    <small>*source : [clker](https://www.clker.com/)*</small>

## 3. Schémas avancés de paramétrage
### 3.1 Définitions
- [ ] <em style="color:rgba(46, 170, 57, 0.8);">polymorphe</em> &rarr; qui peut se présenter sous &#8800; formes.
- [ ] <em style="color:rgba(46, 170, 57, 0.8);">lang. monomorphe</em> &rarr; lang. où f°/procé., **toutes var. et val.** ont un **type unique**.
- [ ] <em style="color:rgba(46, 170, 57, 0.8);">lang. polymorphe</em> &rarr; lang. où f°/procé., **toutes var. et val.** peuvent avoir **plusieurs types**.
- [ ] <em style="color:rgba(46, 170, 57, 0.8);">type polymorphe</em> &rarr; type dt les opé. st applicables à des val. de type &#8800; où à des val. ayant plusieurs type.
### 3.2 Entités d'ordre supérieur
- [ ] <em style="color:rgba(46, 170, 57, 0.8);">entité paramétrée d’ordre sup.</em> &rarr; entité ayant un param. pouvant évoluer par une entité du même type qu'elle même.
- [ ] <em style="color:rgba(46, 170, 57, 0.8);">f° d’ordre sup.</em> &rarr;  f° qui accepte une f° en arg. et/ou rend une f° en valeur *(callback)*.

#### 3.2.1  Paramétrage d’une fonction par une autre
Les itérateurs par exemple :
```scheme
;exemple d'itérateur
(map carre '(1 2 3))
= (1 4 9)
```
