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

Exemple d'éxécution [voir page 21, figure 2.2]()
