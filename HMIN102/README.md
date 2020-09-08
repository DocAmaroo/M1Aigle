# HMIN102 - Ingénierie logicielle
*"Développement de logiciel réutilisable"*

## Sommaire
* [Liens utiles](#liens-utiles)
* [1. Programme](#programme)
* [2. Réutilisation](#réutilisation)
    * [Définitions](#définitions)
    * [Généralisation](#généralisation)
* [3. Schémas avancés de paramétrage](#schémas-avancés-de-paramétrage)
## Liens utiles :
[*Accès au cours*](http://www.lirmm.fr/~dony/ "Accèder au cours") <small> ["UEs en cours"] </small>
[*Accès au moodle*](https://moodle.umontpellier.fr/course/view.php?id=5908 "Accèder au moodle")

## Programme
* Shéma de réutilisation utilisant composition & spécialisation.
* Application aux hiérarchies des classes, "API", "Framework" et "lignes de produits".
* Schéma de conception <small> *(design patterns)* </small>.

## Réutilisation
Ensemble de *théories, méthodes, techniques, outils* permettant de *récupérer, étendre, **adapter à de nouveaux contextes**. <small> (si possible, sans modification du code déjà existant) </small>

### Définitions

- [ ] <em style="color:rgba(46, 170, 57, 0.8);">extensibilité</em> &rarr; capacité d'ajouter de nouvelles fonctionnalités.
- [ ] <em style="color:rgba(46, 170, 57, 0.8);">adaptabilité</em> &rarr; capacité de modifier ses fonctionnalités.
- [ ] <em style="color:rgba(46, 170, 57, 0.8);">entité générique</em> &rarr; entité apte à être utilisé dans plusieurs concept.
- [ ] <em style="color:rgba(46, 170, 57, 0.8);">variabilité</em> &rarr; néologisme dénotant la façon dont un système est susceptible de founir des fonctionnalités pouvant varier.
- [ ] <em style="color:rgba(46, 170, 57, 0.8);">paramètre</em> &rarr; nom dénotant un élément variable d'un système/calcul.
- [ ] <em style="color:rgba(46, 170, 57, 0.8);">fonction</em> &rarr; composition d'opération permettant sa réalisation sans recopie.
- [ ] <em style="color:rgba(46, 170, 57, 0.8);">procédure</em> &rarr; suite d'instructions permettant sa réalisation sans recopie
- [ ] <em style="color:rgba(46, 170, 57, 0.8);">fonction/procédures avec paramètre(s)</em> &rarr; composition d'opérations/suites d'instructions des valeurs de ses paramètres.
    ```scheme
    ;exemple d'une fonction paramétré
    (define (carre x) (* x x))
    ```
- [ ] <em style="color:rgba(46, 170, 57, 0.8);">application</em> &rarr; liason des paramètres formels aux paramètres actuel puis éxécute la fontion dans l'environnement résultant
    ```scheme
    ;exemple d'application
    (carre 2)
    = 4
    ```
- [ ] <em style="color:rgba(46, 170, 57, 0.8);">composition de fonctions</em> &rarr; les fonctions peuvent être composables par enchainement d'appel.
    ```scheme
    ;exemple de composition
    (sqrt(square(9)))
    = 9
    ```
    <small>*NB : les procédures ne sont pas composables*</small>
## Généralisation
### Décomposer & paramétrer
1. **Décomposer en éléments**
<small><em>élements</em> &rarr; procédure, fonction, objet, classe, API, Framework, module, etc...</small>

2. **Paramétrer**: identifier, nommer et matérialiser ce qui peut varier.

### Configurer & composer
1. **Configurer** les éléments paramatrés <small>*(en les instanciant)*</small> et valuant les paramètres.
```scheme
;exemple de valuation d'un paramètre lors d'une application
(carre 5)
```

2. **Composé** les éléments configurés 
    ![Software Reuse image](https://www.clker.com/cliparts/b/f/d/c/11954226151896466519anywhere_info_Software_Reuse.svg.med.png)
    <small>*source : [clker](https://www.clker.com/)*</small>

## Schémas avancés de paramétrage
### Définitions