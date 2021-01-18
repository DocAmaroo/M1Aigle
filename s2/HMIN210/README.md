# HMIN210 - Architecture logicielles distribuées

## Sommaire

- [HMIN210 - Architecture logicielles distribuées](#hmin210---architecture-logicielles-distribuées)
	- [Sommaire](#sommaire)
	- [Liens utiles](#liens-utiles)
	- [Programme](#programme)
	- [Introduction](#introduction)
		- [Définitions](#définitions)
	- [Principes](#principes)

## Liens utiles

:key: `Clé moodle` &rarr; HMIN210-21

:link: [*Accès au moodle*](https://moodle.umontpellier.fr/course/view.php?id=5889 "Accèder au moodle")

## Programme

## Introduction

### Définitions

:bulb: `Middleware` &rarr; Ce qui permet la communication client-serveur à l'aide service.

:bulb: `Proxy` &rarr; Intermédiaire client-serveur. Il récupère les requêtes client, encapsule la méthode (sous forme d'objet) dans un squellette (*skeleton*).

:bulb: `Skeleton` &rarr; Invoque et renvoie le résultat de la méthode reçu au proxy.

## Principes

:triangular_flag_on_post: Invocation de méthodes sur des objets distribués.

:triangular_flag_on_post: Cacher au programmeur les détails de connexion et transport.

:triangular_flag_on_post: Intéragir avec un objet distant comme s'il était en local.

voir [Schéma général](https://github.com/DocAmaroo/M1Aigle/tree/master/s2/HMIN210/cours/coursRMI.pdf) | diapo 14