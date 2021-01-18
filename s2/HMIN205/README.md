# HMIN205 - Développement logiciel pour mobiles

## Sommaire

- [HMIN205 - Développement logiciel pour mobiles](#hmin205---développement-logiciel-pour-mobiles)
	- [Sommaire](#sommaire)
	- [Notes et liens utiles](#notes-et-liens-utiles)
	- [Architecture Android](#architecture-android)
		- [Couches](#couches)
		- [Fichier de configuration *(Manifest)*](#fichier-de-configuration-manifest)
		- [Composants](#composants)
		- [Activité](#activité)

## Notes et liens utiles

Note finale:

| Type   | Coeff |
| ------ | ----- |
| Projet | 75%   |
| TP     | 25%   |

:link: [*Accès au moodle*](https://moodle.umontpellier.fr/course/view.php?id=5886 "Accèder au moodle")

:link: Lirmm [Mr. Seriai](https://www.lirmm.fr/~seriai/index.php?n=Enseignement.Activit%e9sDenseignement)

## Architecture Android

### Couches

Une architectures Android est divisée en plusieurs couches:

:bulb: `Noyaux Linux` &rarr; permet le multitâches

:bulb: `Bibliothèques` &rarr; graphisme et multimédias

:bulb: `Machine virtuelles` &rarr; Davik Virtual Machine

:bulb: `Framework applicatif` &rarr; gestion des fenêtres et contenus, téléphonies...

:bulb: `Applications` &rarr; assemblage de composants liées grâce à un fichier de configuration *(manifest)* (Navigateur web, contacts, calendrier...)

Pour plus d'info [voir schéma diapo 16](https://github.com/DocAmaroo/M1Aigle/blob/master/s2/HMIN205/cours/Cours1_2021.pdf)

### Fichier de configuration *(Manifest)*

:bulb: `Manifest` &rarr; Fichier xml contenant:

- Point d'entrée (code exécuté au démarrage de l'application)
- Composants du programme (activités, services...)
- Permissions

Exemple [voir diapo 21](https://github.com/DocAmaroo/M1Aigle/blob/master/s2/HMIN205/cours/Cours1_2021.pdf)

### Composants
Les composants peuvent être classé en deux type d'éléments, applicatifs et intéractif.

| Applicatif             | Intéractif          |
| ---------------------- | ------------------- |
| Activité               | Objet Intent        |
| Service                | Récepteur d'Intents |
| Fournisseur de contenu | Notification        |
| Widget                 |                     |

### Activité

:bulb: `Activité` &rarr; C'est ce que l'on voit sur l'écran. Composé d'une hiérarchie de *vues* (comme sous Angular/React/VueJs...) qui affichent des interfaces graphiques et répondent aux actions utilisateurs.

Une activité est composée en deux parties :

:one: Sa logique métier et gestion de son cycle de vie
  - Implémenté en Java dans une classe héritant de *Activity*

:two: Son interface utilisateur, pouvant être définis de deux façons
  - Programmative: dans le code de l'activité
  - Déclarative: dans un fichier XML
  