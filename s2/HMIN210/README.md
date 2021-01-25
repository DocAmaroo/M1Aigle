# HMIN210 - Architecture logicielles distribuées

## Sommaire

- [HMIN210 - Architecture logicielles distribuées](#hmin210---architecture-logicielles-distribuées)
  - [Sommaire](#sommaire)
  - [Liens utiles](#liens-utiles)
  - [Programme](#programme)
  - [Introduction](#introduction)
    - [Définitions](#définitions)
  - [Principes](#principes)
    - [Architecture](#architecture)
    - [Développement et exécution](#développement-et-exécution)
    - [Passage de paramètres](#passage-de-paramètres)

## Liens utiles

:key: `Clé moodle` &rarr; HMIN210-21

:link: [*Accès au moodle*](https://moodle.umontpellier.fr/course/view.php?id=5889 "Accèder au moodle")

## Programme

## Introduction

### Définitions

:bulb: `Middleware` &rarr; Ce qui permet la communication client-serveur à l'aide services.

:bulb: `Proxy` *(stub/bouchon)* &rarr; Intermédiaire client-serveur. Il récupère les requêtes client, encapsule la méthode (sous forme d'objet) dans un squelette (*skeleton*).

:bulb: `Skeleton` &rarr; Invoque la méthode reçu par le proxy sur l'objet et lui renvoie le résultat.


:bulb: `Design Pattern Proxy` &rarr; [voir diapo 8](https://github.com/DocAmaroo/M1Aigle/blob/master/s2/HMIN210/cours/coursRMI.pdf)

## Principes

:triangular_flag_on_post: Invocation de méthodes sur des objets distribués.

:triangular_flag_on_post: Cacher au programmeur les détails de connexion et transport.

:triangular_flag_on_post: Intéragir avec un objet distant comme s'il était en local.

### Architecture

| Client (Proxy)                                   | Serveur (Skeleton)                      |
| ------------------------------------------------ | --------------------------------------- |
| :one: Connexion à la JVM                         |                                         |
| :two: Transmet les paramètres                    |                                         |
| :three: Mise en attente du résultat              | :four: lit les paramètres               |
|                                                  | :five: invoque la méthode               |
|                                                  | :six: transmet le résultat à l'appelant |
| :seven: Lit la (valeur de retour \|\| Exception) |                                         |
| :eight: Renvoi le résultat à l'appelant          |                                         |

voir [Schéma général](https://github.com/DocAmaroo/M1Aigle/blob/master/s2/HMIN210/cours/coursRMI.pdf) | diapo 14

### Développement et exécution

:pushpin: Spécification :
- Doit être `Public`
- Hérite de l'interface `java.rmi.Remote`
- Chaque méthode déclare `throws` une `java.rmi.RemoteException`

:pushpin: Implémentation :
- Implémente une/plusieurs interfaces distantes
- Hérite de `UnicastRemoteObject`
- Implémente toutes les méthodes distantes
- Définit le constructeur d'objets distants

:pencil2: Exemple : [voir tp helloWorld](https://github.com/DocAmaroo/M1Aigle/tree/master/s2/HMIN210/td/helloWorld)

#### Policy

Un fichier *.policy* autorise l'usage du `accept` et du `connect` sur les sockets

| Autorisation | Serveur            | Client             |
| ------------ | ------------------ | ------------------ |
| connect      | :white_check_mark: | :white_check_mark: |
| accept       | :white_check_mark: |                    |


```d
// hello.policy
grant{
  permission java.net.SocketPermission “*:1024-65535”,”connect,accept”;
  permission java.net.SocketPermission “:80”,”connect”;
  // permission java.security.AllPermission;
}
```

On lie le fichier dans le code comme ceci :

```java
System.setProperty("java.security.policy", "path/to/file");
System.setSecurityManager(new SecurityManager());
```

ou via terminal :

```bash
java -Djava.security.policy = ./file.policy MyClass hostreg:1099
```


Illustration d'une exécution [voir diapo 31 à 34](https://github.com/DocAmaroo/M1Aigle/tree/master/s2/HMIN210/td/helloWorld)
#### Couches des références distantes et transport

:triangular_flag_on_post: Références distantes
- Permet l'association proxy/objet distant
- Processus tiers: *rmigestry*

:triangular_flag_on_post: Transport
- Ecoute les appels entrants
- Gère les connexions au sites distants
- Possibilité d'utiliser plusieurs classes


### Passage de paramètres
- valeur primitif
- objet d'une classe Serialisable
- objet d'une classe implémentant l'interface Remote

Serialisable => méthode copier dans le client et qui ne sauras que modifier dans le client.