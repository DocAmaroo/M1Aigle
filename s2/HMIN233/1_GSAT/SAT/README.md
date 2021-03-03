# HMIN233 - TD1

# 1. Assemblage de voitures sur une chaîne de montage

## Question 1

- Echanger deux voiture côte à côte => n(n-1)/2
- Echanger deux voitures quelconque => n-1

## Question 2

A B C B A C B

On as 2 pénalités :
- [B C B]
- [A C]

**Coût total:** 2

## Question 3/4

voir cours1 diapo 7

# 2. Codage d'une métaheuristique dédiée à SAT
Méthode de compilation:
```bash
javac Main.java
java Main CNF_FILE_PATH
```

:pencil2: Exemple avec le fichier *mycnf.cnf* fournis
```bash
java Main ./mycnf.cnf
```

:pushpin: D'autre fichier *.cnf* sont utilisable dans le dossier *uf20-91*


:children_crossing: NB: Si un résultat n'est pas obtenu lors des premier essaie il suffit de réessayer plusieurs fois. Ceci est dû à l'initialisation aléatoire des littéraux. Ou alors c'est tout bonnement impossible.