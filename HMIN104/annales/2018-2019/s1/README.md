# Exercice 1
## 1. Que fait la fonction *f* ?

f(0) = 0

f(1) = 1

f(2) = 2

f(3) = 4

f(4) = 6

f(5) = 9

f(6) = 12

f(7) = 16

f(8) = 20

Cette fonction additionne les nombres de même paritée.

## 2. Fonction *f* en PP

```ocaml
function f(n : integer) : integer
begin
  if n = 0
  then f := 0
    else if n = 1 
      then f := 1
      else f := n + f(n-2)
end;
```

## 3. Fonction *f* en UPP
```ocaml
function f(n)
begin
  if n = 0 
  then f := 0
    else if n = 1 
      then f := 1
      else f := n + f(n-2)
end;
```


## 3. Fonction *f* en RTL
```mips
function f(%0) : %1
var %0, %1, %2, %3, %4
entry f1
exit f0
f1: beq %0, 0 -> f2, f3 		;; (n == 0)
f2: li %1, %1 -> f0			;; return 0
f3: beq %0, 1 -> f4, f5		;; (n == 1)
f4: li %1, %1 -> f0			;; return 1
f5: addiu %2, %0, -2 -> f6	;; %2 := n-2
f6: call %3, f(%2) -> f7	;; %3 := f(n-2)
f7: add %4, %0, %3 -> f0	;; %4 := n + f(n-2)
```

## 3. Fonction *f* en ERTL
```mips
procedure f(1)
var %0, %1, %2, %3, %4, %5, %6
entry f1
f1: newframe -> f2
f2: move %4, $ra -> f3			;; Save...
f3: move %5, $a0 -> f4
f4: move %6, $s0 -> f5
f5: beq %0, $r0 -> f6, f8 		;; (n == 0)
f6: li %1, $r0 -> f7			;; return 0
f7: j -> f
f8: beq %0, 1 -> f9, f11		;; (n == 1)
f9: li %1, 1 -> f10				;; return 1
f10: j -> f
f11: addiu %2, %0, -2 -> f12	;; %2 := n-2
f12: move $a0, %2 -> f13
f13: call f(1) -> f14			;; appel f(n-2)
f14: move $s0, $v0 -> f15
f15: add %3, %0, $s0 -> f16		;; %3 := n + f(n-2)
f16 : move $v0, %1 -> f17		;; Reset...
f17 : move $ra, %4 -> f18
f18 : move $a0, %5 -> f19
f19 : move $s0, %6 -> f20
f20 : delframe -> f21			;; end
f21: jr $ra
```

# Exercice 2
## 1. Graphe du flot de contrôle

Soit :
| var. name | var. reg |
| --------- | -------- |
| x         | %0       |
| y         | %1       |
| z         | %2       |
| t         | %3       |
| u         | %4       |
| v         | %5       |


start 

↓

\----------\
| li %3, 1 |\
\----------

↓

\-----------------\
| move %4, %0 |\
\-----------------

↓

\------------------\
| move %4, %5 |\
\------------------

↓
 
\---------------------\
| addu %1, %2, %3 |\
\--------------------- 

↓

\---------------------\
| addu %1, %2, %4 |\
\--------------------- 

↓
 
\---------------------\
| addu %3, %1, %4 |\
\--------------------- 

## 2. Analyse durée de vie des variables
```c
t := 1;     // {z,v,x}
u := x;     // {z,t,v,x}
u := v;     // {z,t,v}
y := z + t; // {u,z,t}
y := z + u; // {u,z}
t := y + u; // {y,u}
            // {t,u}
```

## 3. Graphe d'interférences

**Arêtes d'interférence:** 

(t,z), (t,v), (t,x), (t,u),

(u,z), (u,v),

(y,u), (y,z)

**Arêtes de préférences:**

(u,x)

## 4. Coloriage

### Avec 3 couleurs

Il est possible de colorier ce graphe avec 3 couleurs sans spiller:

Etape de retrait des sommets:

	1. G\{y}
	2. G\{y,v}
	3. G\{y,v,z}
	4. G\{y,v,z,x}

et en remontant on obtient un coloriage comme:

:red_circle: {u,x}.

:black_circle: {v,z}.

:white_circle: {t,y}.

### Avec 2 couleurs

Il est impossible de colorier ce graphe avec 2 couleurs sans spiller:

Etape de retrait des sommets:

	1. G\{y}
	2. G\{y,t} |=> t est spiller
	3. G\{y,t,v}
	4. G\{y,t,v,x}

et en remontant on obtient un coloriage comme:

:red_circle: {u,x,y}.

:black_circle: {v,z}.

{t} est spiller

# Exercice 3

## Question 1

### 1. Traduction de l'automate

On représente chaque état d'un automate par un label (contenant une adresse) suivis du traitement des transitions sortantes de cet état. Ceci permet de passer aisément entre les différents état avec la méthode JMP.

### 2. Code VM

```lisp
(JMP E0)

; --- Etat 0
(LABEL E0)
(CAR R0 R1)
(CMP R1)
(BNULL ERR)   ;si null => error
(CDR R0 R0)
(CMP R1 'a')  ;si c'est un 'a'
(JMP E1)      ;saute à l'état 1
(JMP ERR)     ;sinon erreur

; --- Etat 1
(LABEL E1)
(CAR R0 R1)
(CMP R1)
(BNULL END)
(CDR R0 R0)
(CMP R1 'a')
(JEQ E1)
(CMP R1 'b')
(JMP E2)
(JMP ERR)

; --- Etat 2
(LABEL E2)
(CAR R0 R1)
(CMP R1)
(BNULL ERR)
(CDR R0 R0)
(CMP R1 'b')
(JMP E1)
(JMP ERR)

; --- ON ERROR
(LABEL ERR)
(MOVE NIL R0)
(STOP)

; --- ON SUCCESS
(LABEL END)
(MOVE R2 R0)
(STOP)


; --- Legendes
(STOP) 	;arrêt
(JEQ)  	;saut si égale
(LABEL)	;étiquette
```