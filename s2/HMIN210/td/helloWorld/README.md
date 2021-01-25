# Question 1

```bash
# On compile
javac helloWorld/*.java

# On lance le registre 
# Il tourne sur une JVM tierce en localhost:1099
rmigestry &

# On lance le serveur
# Il récupère le registre via RmiRegestry.locateRegistry();
java helloWorld.Server

# On lance le client en lui passant en param le port sur lequel le registre écoute
java helloWorld.Client 1099
```

# Question 2

Si on commente la ligne 15 et décommente la ligne 14, alors le registre est lancé depuis le serveur et tourne donc dans la même machine virtuelle que le serveur.

Pour lancer le serveur et le client :
```bash
javac helloWorld/*.java
java helloWorld.Server
java helloWorld.Client 1099
```

# Question 3

| step | side    | result                           |
| ---- | ------- | -------------------------------- |
| 1    | serveur | Server ready                     |
| 2    | client  | response: Hello, World!          |
| 3    | serveur | The server prints: Hello, World! |

# Question 4

Dans la JVM du serveur

# Question 5

Elle sert à :
-  typer côté client le proxy reçu à la suite du lookup &rarr; mise en œuvre du patron Proxy
- spécifier les méthodes accessibles à distance pour les classes implémentant cette interface (ici: HelloImpl).

# Question 6

A cause du bind:

- `AlreadyBoundException` (nom déjà présent dans le registre)
- `RemoteException`
- `AccessException`
- `NullPointerException`

# Question 7

Naming.bind jette une exeception `AlreadyBoundException` si le nom est déjà utilisé dans le registre, 

Naming.rebind remplace dans ce cas l'ancien obj. associé par le nouveau passé en param.

# Question 8

Ça fonctionne correctement

# Question 9

![Question 9](https://github.com/DocAmaroo/M1Aigle/tree/master/s2/HMIN210/td/helloWorld/q9.jpg)