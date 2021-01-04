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
	- [Application aux 'Frameworks' et 'Lignes de produits'](#application-aux-frameworks-et-lignes-de-produits)
	- [Framework de test](#framework-de-test)
		- [JUnit](#junit)
		- [Mockito](#mockito)
	- [Pattern](#pattern)
	- [Ligne de produits](#ligne-de-produits)
		- [Modélisation](#modélisation)

## Liens utiles :

[*Accès au cours*](http://www.lirmm.fr/~dony/ "Accèder au cours") ["UEs en cours"]

[*Accès au moodle*](https://moodle.umontpellier.fr/course/view.php?id=5908 "Accèder au moodle")

## Programme

- Shéma de réutilisation utilisant composition & spécialisation.
- Application aux hiérarchies des classes, "API", "Framework" et "lignes de produits".
- Schéma de conception *(design patterns)*.

## Réutilisation

Ensemble de *théories, méthodes, techniques, outils* permettant de *récupérer, étendre*, **adapter à de nouveaux contextes**. (si possible, sans modification du code déjà existant)

### Définitions

:bulb: `extensibilité` &rarr; capacité d'ajouter de nouvelles f°nalités.

:bulb: `adaptabilité` &rarr; capacité de modifier ses f°nalités.

:bulb: `entité générique` &rarr; entité apte à être utilisé dans plusieurs concept.

:bulb: `variabilité` &rarr; néologisme dénotant la façon dont un système est susceptible de founir des f°nalités pouvant varier.

:bulb: `paramètre (param)` &rarr; nom dénotant un el. var. d'un système/calcul.

:bulb: `fonction (f°)` &rarr; composition d'op. permettant sa réa. sans recopie.

:bulb: `procédure (proc)` &rarr; suite d'instruct° permettant sa réa. sans recopie.

:bulb: `f°/proc. avec paramètre(s)` &rarr; composition d'opé./suites d'instruct° des val.s de ses params.

```scheme
;exemple d'une fonction paramétré
(define (carre x) (* x x))
```

:bulb: `application` &rarr; liason des params. formels aux params. actuels puis execute la f° dans l'env. résultant.

```scheme
;exemple d'application
(carre 2)
= 4
```

:bulb: `composition de f°` &rarr; les fonctions peuvent être composables par enchainement d'appel.

```scheme
;exemple de composition
(sqrt(square(9)))
= 9
```

NB : les procédures ne sont pas composables*

## Schémas avancés de paramétrage

### Définitions

:bulb: `lang. monomorphe` &rarr; lang. où f°/procé., toutes var. et val. ont un type unique.

:bulb: `polymorphe` &rarr; qui peut se présenter sous diff. formes.

:bulb: `lang. polymorphe` &rarr; lang. où f°/procé., toutes var. et val. peuvent avoir plusieurs types.

:bulb: `type polymorphe` &rarr; type dt les opé. st applicables à des val. de type diff. où à des val. ayant plusieurs type.

### Entités d'ordre supérieur

:bulb: `entité paramétrée d’ordre sup.` &rarr; entité ayant un param. pouvant évoluer par une entité du même type qu'elle même.

:bulb: `f° d’ordre sup.` &rarr; f° qui accepte une f° en arg. et/ou rend une f° en valeur *(callback)*

## Les schémas de réutilisation en PPO

### Rappels

:bulb: `envoi de message` &rarr; autre nom donné à l'appel de méth.

:bulb: `receveur courant` &rarr; au sein d'une méth. *M*, le receveur *(this)* est l'obj. auquel a été envoyé le msg. ayant conduit à l'exec. de *M*. 

:bulb: `liaison dynamique (ou tardive)` &rarr; mécanisme distinct de l'héritage. C'est le fait qu'un nom de fonction membre d'une classe de base peut être associé à une fonction membre d'une classe dérivée.

### Schéma de réutilisations

:bulb: `description différentielle` &rarr; permet l'ajout sur une nouvelle sous-classe, de nouvelles propriétés et la **spécialisation** de propriétés existantes, notamment des méth.

:bulb: `spécialisation/redéfinition` &rarr; Définition d’une méth. M sur une sous-classe SC d’une classe C où une méth M est déjà définie. *(@Override)*

:bulb: `masquage` &rarr; redéf. sur une classe C, masque, pour les instances de C, la méth. redéfinie.

:bulb: `spécialisation/redéfinition partielle` &rarr; Redéfinition faisant appel à la méthode redéfinie (masquée) *(super)*

:bulb: `paramétrage par spécialisation` &rarr; Lors d'un appel d'une méthode d'une sous-classe dans la classe (exemple dans le cours: [voir p10](https://github.com/DocAmaroo/M1Aigle/blob/master/HMIN102/cours/1_reuseAndFrameworks.pdf))

:bulb: `paramétrage par composition` &rarr; Une méthode utilise les méthodes d'une méthode.

```java
class Brandade {
		Brandade(){}
		String toString(){ return "brandade de morue <3"; }
}
class Classname {
		Brandade b;
		Classname(Brandade b) { this.b = b; }
		String insaneMethod(){ return b.toString(); }
}

// insaneMethod est paramétrée par toString() de par le paramètre b
```

(autre exemple dans le cours: [voir p10](https://github.com/DocAmaroo/M1Aigle/blob/master/HMIN102/cours/1_reuseAndFrameworks.pdf))

:bulb: `affectation polymorphique` &rarr; un type (T), peut être déf. comme un sous-type (ST) d'un autre. (*`Collection L = new ArrayList();`*)

## Application aux 'Frameworks' et 'Lignes de produits'

## Framework de test

:triangular_flag_on_post: JUnit est un cadre simple pour écrire des tests répétables.

### JUnit

:triangular_flag_on_post: Permet de rédiger et effectuer des tests unitaires. 

Si il est nécessaire de créer des classes de données pour tester l'API il faudras utiliser mockito.

### Mockito

:triangular_flag_on_post: Utilisez spécifiquement pour rédiger efficacement certains types de tests, notammment pour créer objets factices.

```java
class Test {
	@Test
	// init mock
	MyInterface mock = mock(MyInterface.class);

	// when myMethod is called return ...
	when(mock.myMethod()).thenReturn(0); // int
	when(mock.myMethod()).thenThrow(Exception.class); // throws exception
	doThrow(new Exception()).when(i).myVoidMethod(); // force exception on void method

	// Check if it works
	assertEquals(0, mock.myMethod()); // int
	assertThrows(Exception.class, i::myMethod); // throws exception
	assertThrows(Exception.class, i::myVoidMethod); // force exception on void method

	// mocking with parameters
	when(mock.myParamMethod(anyInt())).thenReturn(0); // anyInt return 0
	when(mock.myParamMethod(3)).thenReturn(3); // 3 return 3
	when(mock.myParamMethod(5)).thenReturn(10); // 5 return 10
	for (int k=0; k < 10; k++) { System.out.print(mock.myParamMethod(k) + ";"); } // Simple display to verify

			
	// matcher on param
	when(mock.myParamMethod(gt(10))).thenReturn(42);
	when(mock.myParamMethod(leq(10))).thenReturn(0);

	// mock on arrayList

	ArrayList<String> l1 = new ArrayList<String>(); // l1 = [1, 0, 42]
	l1.add("1"); l1.add("0"); l1.add("42");

	ArrayList<String> l2 = new ArrayList<String>(); // l2 = [1, 0, 5]
	l2.add("1"); l2.add("0"); l2.add("5");	

	// -- if (array contains 42 || array size = 1) -> return 42
	when(mock.methodParamArrayList(argThat( 
		array -> array.contains("42") || array.size() == 1 )
	)).thenReturn(42);

	assertEquals(42, mock.methodParamArrayList(l1)); // true
	assertEquals(42, mock.methodParamArrayList(l2)); // false


	// mock verify
	verify(mock, times(number)).myMethod(); // check if myMethod have been called 'number' times
}
```

## Pattern

:bulb: `Décorateur` &rarr; Ajout/Modif. d'une fonctionnalité à un objet sans modifier sa classe (ni les instances de celle-ci). [voir p5](https://github.com/DocAmaroo/M1Aigle/blob/master/HMIN102/cours/4_cpatterns.pdf)

:bulb: `Adaptateur` &rarr; Permet de convertir l'interface d'une classe en une autre interface que le client attend. [voir p9](https://github.com/DocAmaroo/M1Aigle/blob/master/HMIN102/cours/4_cpatterns.pdf)

:bulb: `State` &rarr; Gestion par état. [voir p15 & 16](https://github.com/DocAmaroo/M1Aigle/blob/master/HMIN102/cours/4_cpatterns.pdf)

## Ligne de produits

:triangular_flag_on_post: Permet de créer une collection de produits (ou système logiciel).

Cette méthode permet:
- augmenter la productivité/qualité
- diminuer le coût / la main-d'œuvre
- Rapidement mise en place

### Modélisation

[voir diapo 22](https://github.com/DocAmaroo/M1Aigle/blob/master/HMIN102/cours/5_SPLE.pdf)