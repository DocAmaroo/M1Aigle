# HMIN224 - Algorithmique distribuée

## Sommaire

- [HMIN224 - Algorithmique distribuée](#hmin224---algorithmique-distribuée)
  - [Sommaire](#sommaire)
  - [Liens utiles](#liens-utiles)
  - [Introduction](#introduction)
    - [Le temps](#le-temps)
    - [Les modèles](#les-modèles)
    - [Les services](#les-services)
  - [Communications](#communications)
    - [Diffusion asynchrone en cas de pannes de sites](#diffusion-asynchrone-en-cas-de-pannes-de-sites)
    - [Diffusion respectant l'ordre FIFO des messages](#diffusion-respectant-lordre-fifo-des-messages)

## Liens utiles

## Introduction

### Le temps

### Les modèles

:bulb: `sites` &rarr; machine qui constitue le système

:bulb: `message` &rarr; \<name, data1, data2\> \|\| \<data>

Méthodes associées aux messages

```bash
# P_i envoie un message à P_j
Envoyer(<name, 13, Tab_i>) à P_j

# La réception se fait par des évènements
```

Mise en attente du système :
```bash
# Attendre tant que cond est faux
Attendre(cond)
```

:pencil2: Exemple
```d
// Envoie un message à tout le monde
procedure diffuser(message)
  Pour (x_p dans V-{p})
    Envoyer(<message>) à x_p
```

```d
// Diffuse la nouvelle valeur d'une valeur globale partagée entre les sites
procedure diffuser_valeur(value)
  Pour (x_p dans V-{p})
    Envoyer(<ECRITURE, value>) à x_p

// A la réception
Lors de la réception de <ECRITURE, v> depuis q
  C_p := v;

// Compléxité 	: (n-1) | pour chaque modif. de la valeur globale
// Inconvéniant	: très nombreux
```

### Les services

!!! todo page 8
  Pas mal de définition par ici à check 


## Communications

Temps de calcul local est **négligeable**, on ne s'occupe que du temps de communication.

### Diffusion asynchrone en cas de pannes de sites

- p &rarr; un site
- V &rarr; ensemble de tous les sites
- S0 = {q1, q2,...,qt} &rarr; sous ensembles de sites formant un relais

```d
// Envoie
procedure diffuser(message)
  Envoyer(<message>) à q1, q2,...,qt dans cet ordre;
  Pour tout (q € V-S0-{p}) faire
    Envoyer(<message>) à q;

// Réception
Lors de la réception de <message>
Si (q € S0) alors	// Suppossons q = qk
  Si (k < t) alors
    Envoyer(<message>) à qk+1,...,qt dans cet ordre;
    Pour tout (r € V-S0-{p}) faire
      Envoyer(<message>) à r;
    Accepter(message);
  Sinon
    Accepter(message);
```

Exemple d'éxécution [voir page 21, figure 2.2](https://github.com/DocAmaroo/M1Aigle/blob/master/s2/HMIN224/cours/main_cours.pdf)

Calcul lorsqu'il n'y a pas de panne [voir page 22, Preuve du Lemme 2.2.1](https://github.com/DocAmaroo/M1Aigle/blob/master/s2/HMIN224/cours/main_cours.pdf)

### Diffusion respectant l'ordre FIFO des messages

:children_crossing: En supposant que la machine ne plante pas!

Code du site *p*:
```d
numEnvoi := 0;
procedure diffuser(message)
  numEnvoip := numEnvoiP + 1;
  Pour tout (xp € V-{p}) faire
    Envoyer(<message, numEnvoip>) à xp
```

code du site i:
```d
seqi := 1;
Lors de la réception de <message, numEnvoiM> de p
  // Stocke le message
  Stocker(message);
  // Reste en attente tant qu'on reçoit pas tout les paquets
  Attendre(numEnvoiM = seqi);
  // On donne le message au système
  Delivrer(message);
  seqi := seqi + 1;
  // On le détruit
  Detruire(message);
```

Inconvénient:
- Un site i peut avoir à stocker beaucoup de message (avant de délivrer/détruire)
- Le numéro de séquence des messages croît au delà de toute limite raisonnable si p diffuse beaucoup de message.
- Si perte de message, tout est bloqué