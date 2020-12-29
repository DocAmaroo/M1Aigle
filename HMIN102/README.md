# HMIN102 - Ingénierie logicielle
*"Développement de logiciel réutilisable"*

## Sommaire
- [HMIN102 - Ingénierie logicielle](#hmin102---ingénierie-logicielle)
  - [Sommaire](#sommaire)
  - [Liens utiles :](#liens-utiles-)
  - [Programme](#programme)
  - [Réutilisation](#réutilisation)
    - [Définitions](#définitions)
  - [Schémas avancés de paramétrage](#schémas-avancés-de-paramétrage)
    - [Définitions](#définitions-1)
    - [Entités d'ordre supérieur](#entités-dordre-supérieur)
  - [Les schémas de réutilisation en PPO](#les-schémas-de-réutilisation-en-ppo)
    - [Rappels](#rappels)
    - [Schéma de réutilisations](#schéma-de-réutilisations)
    - [Paramétrage par spécialisation](#paramétrage-par-spécialisation)
    - [Paramétrage par composition](#paramétrage-par-composition)
  - [Spécificités du typage statique en présence d’affectation polymorphique](#spécificités-du-typage-statique-en-présence-daffectation-polymorphique)

## Liens utiles :
[*Accès au cours*](http://www.lirmm.fr/~dony/ "Accèder au cours") <small> ["UEs en cours"] </small>

[*Accès au moodle*](https://moodle.umontpellier.fr/course/view.php?id=5908 "Accèder au moodle")

## Programme
* Shéma de réutilisation utilisant composition & spécialisation.
* Application aux hiérarchies des classes, "API", "Framework" et "lignes de produits".
* Schéma de conception <small> *(design patterns)* </small>.

## Réutilisation
Ensemble de *théories, méthodes, techniques, outils* permettant de *récupérer, étendre*, **adapter à de nouveaux contextes**. <small> (si possible, sans modification du code déjà existant) </small>

### Définitions

:bulb: extensibilité &rarr; `capacité d'ajouter de nouvelles f°nalités.`

:bulb: adaptabilité &rarr; `capacité de modifier ses f°nalités.`

:bulb: entité générique &rarr; `entité apte à être utilisé dans plusieurs concept.`

:bulb: variabilité &rarr; `néologisme dénotant la façon dont un système est susceptible de founir des f°nalités pouvant varier.`

:bulb: paramètre (param) &rarr; `nom dénotant un el. var. d'un système/calcul.`

:bulb: fonction (f°) &rarr; `composition d'op. permettant sa réa. sans recopie.`

:bulb: procédure (proc) &rarr; `suite d'instruct° permettant sa réa. sans recopie.`

:bulb: f°/proc. avec paramètre(s) &rarr; `composition d'opé./suites d'instruct° des val.s de ses params. .`
```scheme
;exemple d'une fonction paramétré
(define (carre x) (* x x))
```

:bulb: application &rarr; `liason des params. formels aux params. actuels puis execute la f° dans l'env. résultant.`
```scheme
;exemple d'application
(carre 2)
= 4
```

:bulb: composition de f° &rarr; `les fonctions peuvent être composables par enchainement d'appel.`
```scheme
;exemple de composition
(sqrt(square(9)))
= 9
```
*<small>NB : les procédures ne sont pas composables</small>*

## Schémas avancés de paramétrage
### Définitions

:bulb: lang. monomorphe &rarr; `lang. où f°/procé., toutes var. et val. ont un type unique.`

:bulb: polymorphe &rarr; `qui peut se présenter sous diff. formes.`

:bulb: lang. polymorphe &rarr; `lang. où f°/procé., toutes var. et val. peuvent avoir plusieurs types.`

:bulb: type polymorphe &rarr; `type dt les opé. st applicables à des val. de type diff. où à des val. ayant plusieurs type.`

### Entités d'ordre supérieur

:bulb: entité paramétrée d’ordre sup. &rarr; `entité ayant un param. pouvant évoluer par une entité du même type qu'elle même.`

:bulb: f° d’ordre sup. &rarr; `f° qui accepte une f° en arg. et/ou rend une f° en valeur` <small> *(callback)* </small>

## Les schémas de réutilisation en PPO

### Rappels
:bulb: `envoi de message` &rarr; autre nom donné à l'appel de méth.

:bulb: `receveur courant` &rarr; au sein d'une méth. *M*, le receveur *<small>(accessible via `this`)</small>* est l'obj. auquel a été envoyé le msg. ayant conduit à l'exec. de *M*. 

:bulb: `liaison dynamique (ou tardive)` &rarr; mécanisme distinct *<small>(bien qu'étroitement lié)</small>* de l'héritage. C'est le fait qu'un nom de fonction membre d'une classe de base peut être associé à une fonction membre d'une classe dérivée.
*<small>NB : un même nom de fonction pourra correspondre à des réalisations différentes suivant les classes dérivées</small>*

### Schéma de réutilisations
:bulb: `description différentielle` &rarr; permet l'ajout sur une nouvelle sous-classe, de nouvelles propriétés et la **spécialisation** de propriétés existantes, notamment des méth.

:bulb: `spécialisation` &rarr; Définition d’une méth. M sur une sous-classe SC d’une classe C où une méth M est déjà définie. (*<small>`@Override`</small>*)

:bulb: `masquage` &rarr; redéf. sur une classe C, masque, pour les instances de C, la méth. redéfinie.

:bulb: `sémantique` &rarr; un appel à *`super`*, revient à envoyer un message au receveur courant mais en cherchant la méth. dans la surclasse dans laquelle la méth. a été trouvée.

### Paramétrage par spécialisation
:triangular_flag_on_post: **But :** Adapter une méth. à de nouveaux contexte sans modif./duplicat° de son code. (*<small>`méth./classe abstraite`</small>*)

### Paramétrage par composition
:bulb: `affectation polymorphique` &rarr; un type (T), peut être déf. comme un sous-type (ST) d'un autre.
```java
// exemple;
Collection L = new ArrayList();
// Collection : T
// ArrayList() : ST
```

## Spécificités du typage statique en présence d’affectation polymorphique
