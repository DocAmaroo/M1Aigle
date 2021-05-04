# HMIN233 - FIM - TD2

:mortar_board: Canta Thomas

:email: thomas.canta@etu.umontpellier.fr

## Exercice 1

### Question 1

M<sub>1</sub> = { T<sub>i</sub> ∈ D | ∀T<sub>j</sub> ∈ D : (i ≠ j) ^ (T<sub>i</sub> ⊄ T<sub>j</sub>) }


### Question 2

On peut avoir ∀θ : F<sub>θ</sub> = M<sub>θ</sub> = C<sub>θ</sub> avec un dataset comme :

:pushpin: L'ensemble d'item I = {A, B, C, D, E, F}

:pushpin: L'ensemble des transactions T suivantes:

	T1 = A
	T2 = B
	T3 = C
	T4 = D

Alors :

:round_pushpin: Fréquent: F<sub>1</sub> = {A, B, C, D}

:round_pushpin: Maximum: M<sub>1</sub> = {T1, T2, T3, T4}

:round_pushpin: Clos: C<sub>1</sub> = {T1, T2, T3, T4}

:bulb: Conclusion :
![Equation](imgs/formula1.png)

## Exercice 2

### Question 1

:pushpin: M<sub>&theta; = {ABC<sup>3</sup>, DE<sup>2</sup>, EF<sup>5</sup>}

:round_pushpin: Fréquent : F = {A, B, C, D, E, F, AB, AC, BC, DE, EF, ABC}


### Question 2

:round_pushpin: Clos : C = {ABC<sup>3</sup>, ABE<sup>5</sup>, DE<sup>2</sup>, EF<sup>5</sup>}

:round_pushpin: Fréquent : F = { 
A<sup>5</sup>, 
B<sup>5</sup>, 
C<sup>3</sup>,
D<sup>2</sup>, 
E<sup>5</sup>, 
F<sup>5</sup>, 
AB<sup>5</sup>, 
AC<sup>3</sup>, 
BC<sup>3</sup>, 
DE<sup>2</sup>, 
EF<sup>5</sup>, 
ABC<sup>3</sup>,
ABE<sup>5</sup>
}

### Question 3

:round_pushpin: Maximum :
M<sub>3</sub> = {BCE<sup>4</sup>, AC<sup>3</sup>}

:round_pushpin: Clos :
C<sub>3</sub> = {BE<sup>5</sup>, C<sup>5</sup>, BCE<sup>4</sup>, AC<sup>3</sup>}

:round_pushpin: Fréquent :
F<sub>3</sub> = {A<sup>3</sup>, C<sup>5</sup>, AC<sup>3</sup>, B<sup>5</sup>, E<sup>5</sup>, BC<sup>4</sup>, BE<sup>5</sup>, CE<sup>4</sup>, BCE<sup>4</sup>
}

