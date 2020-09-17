# HMIN102 - Ingénierie logicielle
*"Développement de logiciel réutilisable"*

## Sommaire
* [Liens utiles](#liens-utiles)
* [1. Programme](#1-programme)
* [2. Réutilisation](#2-réutilisation)
    * [2.1 Définitions](#21-définitions)
    * [2.2 Généralisation](#22-généralisation)
* [3. Schémas avancés de paramétrage](#3-schémas-avancés-de-paramétrage)
    * [3.1 Définitions](#31-définitions)
    * [3.2 Entités d'ordre supérieur](#32-entités-dordre-supérieur)
* [4. Les schémas de réutilisation en PPO](#4-les-schémas-de-réutilisation-en-ppo)
    * [4.1 Rappels](#41-rappels)
    * [4.2 Schéma de réutilisations](#42-schéma-de-réutilisations)
    * [4.3 Paramétrage par spécialisation](#43-paramétrage-par-spécialisation)
    * [4.4 Paramétrage par composition](#44-paramétrage-par-composition)
* [5. Spécificités du typage statique en présence d’affectation polymorphique](#5-spécificités-du-typage-statique-en-présence-daffectation-polymorphique)
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

2. **Composé** les el. configurés

    ![Software Reuse image](https://www.clker.com/cliparts/b/f/d/c/11954226151896466519anywhere_info_Software_Reuse.svg.med.png)
    
    <small>*source : [clker](https://www.clker.com/)*</small>

## 3. Schémas avancés de paramétrage
### 3.1 Définitions

:bulb: lang. monomorphe &rarr; `lang. où f°/procé., toutes var. et val. ont un type unique.`

:bulb: polymorphe &rarr; `qui peut se présenter sous diff. formes.`

:bulb: lang. polymorphe &rarr; `lang. où f°/procé., toutes var. et val. peuvent avoir plusieurs types.`

:bulb: type polymorphe &rarr; `type dt les opé. st applicables à des val. de type diff. où à des val. ayant plusieurs type.`

### 3.2 Entités d'ordre supérieur

:bulb: entité paramétrée d’ordre sup. &rarr; `entité ayant un param. pouvant évoluer par une entité du même type qu'elle même.`

:bulb: f° d’ordre sup. &rarr; `f° qui accepte une f° en arg. et/ou rend une f° en valeur` <small> *(callback)* </small>

#### 3.2.1  Paramétrage d’une fonction par une autre
Les itérateurs par exemple :
```scheme
;exemple d'itérateur
(map carre '(1 2 3))
= (1 4 9)
```

#### 3.2.2 Paramétrage d’une fonction par une fontion
```scheme
;exemple
(tri '(4 3 1 2) <)
= (1 2 3 4)
```

#### 3.2.3 Paramétrage d’un ensemble de fonctions par une fonction
```scheme
;exemple
SC := SortedCollection sortBlock:[:a :b|a year<b year];
SC add:(Date newDay:22year:2000);
SC add:(Date newDay:22year:2000);
```

#### 3.2.4 Paramétrage d'un ensemble de fonctions par un autre ensemble de fonctions
```c
class A{
    public int f1(C c) { return1 + c.g() + c.h(); }
    public int f2(C c){ return2 ∗ c.g() ∗ c.h(); }
```

*<small>NB : Passer un obj. en arg. revient à passer =mt toutes les f° def. sur la classe</small>*

## 4. Les schémas de réutilisation en PPO

### 4.1 Rappels
:bulb: `envoi de message` &rarr; autre nom donné à l'appel de méth.
:bulb: `receveur courant` &rarr; au sein d'une méth. *M*, le receveur *<small>(accessible via `this`)</small>* est l'obj. auquel a été envoyé le msg. ayant conduit à l'exec. de *M*. 
:bulb: `liaison dynamique (ou tardive)` &rarr; mécanisme distinct *<small>(bien qu'étroitement lié)</small>* de l'héritage. C'est le fait qu'un nom de fonction membre d'une classe de base peut être associé à une fonction membre d'une classe dérivée.
*<small>NB : un même nom de fonction pourra correspondre à des réalisations différentes suivant les classes dérivées</small>*

### 4.2 Schéma de réutilisations
:bulb: `description différentielle` &rarr; permet l'ajout sur une nouvelle sous-classe, de nouvelles propriétés et la **spécialisation** de propriétés existantes, notamment des méth.
:bulb: `spécialisation` &rarr; Définition d’une méth. M sur une sous-classe SC d’une classe C où une méth M est déjà définie. (*<small>`@Override`</small>*)
:bulb: `masquage` &rarr; redéf. sur une classe C, masque, pour les instances de C, la méth. redéfinie.
:bulb: `sémantique` &rarr; un appel à *`super`*, revient à envoyer un message au receveur courant mais en cherchant la méth. dans la surclasse dans laquelle la méth. a été trouvée.

### 4.3 Paramétrage par spécialisation
:triangular_flag_on_post: **But :** Adapter une méth. à de nouveaux contexte sans modif./duplicat° de son code. (*<small>`méth./classe abstraite`</small>*)

### 4.4 Paramétrage par composition
:bulb: `affectation polymorphique` &rarr; un type (T), peut être déf. comme un sous-type (ST) d'un autre.
```java
// exemple;
Collection L = new ArrayList();
// Collection : T
// ArrayList() : ST
```

## 5. Spécificités du typage statique en présence d’affectation polymorphique