# HMIN233 - TD1

# 1. Assemblage de voitures sur une chaîne de montage

## Question 1

- Echanger deux voiture côte à côte => n(n-1)/2
- Echanger deux voitures quelconque => n-1
- On coupe en deux et on échange les deux morceaux => (c'est pas opti du tout)
- On change le type des voitures => (c'est pas opti du tout)

## Question 2

A B C B A C B

On as 2 pénalités :
- [B C B]
- [A C]

**Coût total:** 2

## Question 3

Cela retourne un minimum local

## Question 4

La méthode Tabu permet d'éviter les cycles.