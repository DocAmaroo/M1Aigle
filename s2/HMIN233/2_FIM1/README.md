# HMIN233 - TD1

- [HMIN233 - TD1](#hmin233---td1)
	- [Informations](#informations)
	- [Exercice 1](#exercice-1)
		- [Question 1](#question-1)
		- [Question 2](#question-2)
		- [Question 3](#question-3)
		- [Question 4](#question-4)
	- [Exercice 2 (skip)](#exercice-2-skip)
		- [Question 1](#question-1-1)
		- [Question 2](#question-2-1)
	- [Exercice 3](#exercice-3)
		- [Question 1](#question-1-2)
		- [Question 2](#question-2-2)

## Informations
:mortar_board: Canta Thomas

:email: thomas.canta@etu.umontpellier.fr

## Exercice 1

### Question 1
:pencil2: Représentation vertical


| A   | B   | C   | D   | E   | F   |
| --- | --- | --- | --- | --- | --- |
| 2   | 1   | 1   | 1   | 2   | 3   |
| 3   | 2   | 2   | 3   | 4   | 6   |
| 5   | 3   | 3   | 4   | 6   | 7   |
| 6   | 5   | 6   | 8   | 7   | 8   |
| 7   | 7   | 9   |     | 9   | 10  |
| 10  | 10  |     |     |     |     |

:pencil2: Représentation matricielle

| i   | A   | B   | C   | D   | E   | F   |
| --- | --- | --- | --- | --- | --- | --- |
| 1   | 0   | 1   | 1   | 1   | 0   | 0   |
| 2   | 1   | 1   | 1   | 0   | 1   | 0   |
| 3   | 1   | 1   | 1   | 1   | 0   | 1   |
| 4   | 0   | 0   | 0   | 1   | 1   | 0   |
| 5   | 1   | 1   | 0   | 0   | 0   | 0   |
| 6   | 1   | 1   | 0   | 0   | 1   | 1   |
| 7   | 1   | 1   | 0   | 0   | 1   | 1   |
| 8   | 0   | 0   | 0   | 1   | 0   | 1   |
| 9   | 0   | 0   | 1   | 0   | 1   | 0   |
| 10  | 0   | 0   | 0   | 1   | 0   | 1   |

### Question 2
L = {ACD, CE, BCE, ABCE, E, D, BC, F, CDF, EF}

```d
// ACD
cover(ACD) = {3}
freq(ACD) = 1
freqRelative(ACD) = 1/10

// CE
cover(CE) = {2, 6, 9}
freq(CE) = 3
freqRelative = 3/10

// BCE
... = {2}
... = 1
... = 1/10

// ABCE
... = {2}
... = 1
... = 1/10

// E
... = {2, 4, 6, 7 , 9}
... = 5
... = 5/10 (1/2)

// D
... = {1, 3, 4, 8}
... = 4
... = 4/10 (2/5)

// BC
... = {1, 2, 3}
... = 3
... = 3/10

// F
... = {3, 6, 7, 8, 10}
... = 5
... = 5/10 (1/2)

// CDF
... = {3}
... = 1
... = 1/10

// EF
... = {6, 7}
... = 2
... = 2/10 (1/5)
```

### Question 3
θ ∈ {5, 6, 7, 8, 9, 10}

θ = 5: {A<sub>6</sub>, B<sub>6</sub>, C<sub>5</sub>, E<sub>5</sub>, F<sub>5</sub>, AB<sub>5</sub>}

θ = 6: {A<sub>6</sub>, B<sub>6</sub>}

θ ≥ 7: Ø


### Question 4

:pencil2: itemsets comparables &rarr; {AB} et {ABC}
> {AB} ⊆ {ABC} v {ABC} ⊄ {AB} ⇒ true

:pencil2: itemsets non comparables &rarr; {AB} et {BC}
> {AB} ⊄ {BC} v {BC} ⊄ {AB} ⇒ false

## Exercice 2 (skip)

### Question 1

### Question 2


## Exercice 3 


### Question 1

Trace Apriori(D<sub>2</sub>, 3)

k &larr; 1

L<sub>1</sub> &larr; {A<sub>3</sub>, B<sub>5</sub>, C<sub>5</sub>, E<sub>5</sub>}

| k   | L<sub>k</sub>                    | C                        |
| --- | -------------------------------- | ------------------------ |
| 1   | L<sub>1</sub> = {A, B, C, E}     | {AB, AC, AE, BC, BE, CE} |
| 2   | L<sub>2</sub> = {AC, BC, BE, CE} | {ABC, ACE, BCE}          |
| 3   | L<sub>3</sub> = {BCE}            | Ø                        |
| 4   | L<sub>4</sub> = Ø                |                          |

**Valeur de retour:** {A<sub>3</sub>, B<sub>5</sub>, C<sub>5</sub>, E<sub>5</sub>, AC<sub>3</sub>, BC<sub>4</sub>, BE<sub>5</sub>, CE<sub>4</sub>, BCE<sub>4</sub>}

### Question 2

Trace AprioriChild(D<sub>2</sub>, 3) avec l'opérateur child avec un ordre lexicographique.

k &larr; 1

L<sub>k</sub> &larr; {A<sub>3</sub>, B<sub>5</sub>, C<sub>5</sub>, E<sub>5</sub>}

| k   | L<sub>k</sub>                    | C                                    |
| --- | -------------------------------- | ------------------------------------ |
| 1   | L<sub>1</sub> = {A, B, C, E}     | {AB, AC, AD, AE, BC, BD, BE, CD, CE} |
| 2   | L<sub>2</sub> = {AC, BC, BE, CE} | {ACD, ACE, BCD, BCE}                 |
| 3   | L<sub>3</sub> = {BCE}            | Ø                                    |
| 4   | L<sub>4</sub> = Ø                |                                      |

**Valeur de retour:** {A<sub>3</sub>, AC<sub>3</sub>, B<sub>5</sub>, BC<sub>4</sub>, BE<sub>5</sub>, BCE<sub>4</sub>, C<sub>5</sub>, CE<sub>4</sub>, E<sub>5</sub>}