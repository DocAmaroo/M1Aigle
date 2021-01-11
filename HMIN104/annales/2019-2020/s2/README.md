# Exercice 1
## 1. Que fait la fonction *f* ?

**Test**

f(0) => 0

f(1) => 2 - 0 - 1 = 1

f(2) => 2*2 - 1 - 1 = 2

f(3) => 6 - 2 - 1 = 3

f(4) => 8 - 3 - 1 = 4

f est la fonction identité: f(n) => n

Si n < 0 la réponse est indéterminable car appel récursif sur f(-n-1), donc n >= 0.

## 2. Fonction *f* en PP

```ocaml
function f(n : integer) : integer
begin
  if n <= 0 then
    f := 0
  else
    f := (2*n) - f(n-1) - 1
end;
```

## 3. Fonction *f* en UPP
```ocaml
function f(n);
begin
  if n <= 0 then
    f := 0
  else
    f := (2*n) - f(n-1) - 1
end;
```

## 4. Fonction *f* en RTL
```mips
function f(%0) : %1
var %0, %1, %2, %3, %4, %5
entry f1
exit f0
f1: blez %0 -> f2, f3       ;; if (n <= 0)
;; then
f3: mul %2, %0, 2 -> f4     ;; %2 := 2*n
f4: addiu %3, %2, -1 -> f5  ;; %3 := (2*n)-1 
f5: addiu %4, %0, -1 -> f6  ;; %4 := n-1
f6: call %5, f(%4) -> f7    ;; %5 := f(n-1)
f7: sub %1, %3, %5 -> f0    ;; %1 := (2*n)-1-f(n-1)
;; else
f2: li %1, 0 -> f0          ;; %1 := 0
```

## 5. Fonction *f* en ERTL
```mips
procedure f(1)
var %0, %1, %2, %3, %4, %5, %6, %7, %8
entry f1
f1: newframe -> f2
f2: move %6, $ra -> f3        ;; Sauvegarde
f3: move %8, $s0 -> f4        ;; ...
f4: move %0, $a0 -> f5        ;; ...
f5: blez %0 -> f6, f7         ;; if (n <= 0)
f6: li %1, 0 -> f7            ;; %1 := 0
f7: j -> f15
f8: mul %2, %0, 2 -> f9       ;; %2 := 2*n
f9: addiu %3, %2, -1 -> f10   ;; %3 := (2*n)-1 
f10: addiu %4, %0, -1 -> f11  ;; %4 := n-1
f11: move $a0, %4 -> f12      ;; $a0 := n-1
f12: call f(1) -> f13         ;; appel f(n-1)
f13: move $s0, $v0 -> f14     ;; $s0 := f(n-1)
f14: sub %1, %3, $s0 -> f15   ;; %1 := (2*n)-1-f(n-1)
f15: move $v0, %1 -> f16      ;; Reset
f16: move $ra, %6 -> f17      ;; ...
f17: move $s0, %8 -> f18      ;; ...
f18: delframe -> f19          ;; end
f19: jr $ra
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
(JEQ E3)      ;saute à l'état 3
(CMP R1 'b')  ;si c'est un 'b'
(JEQ E1)      ;saute à l'état 1
(JMP ERR)     ;sinon erreur

; --- Etat 1
(LABEL E1)
(CAR R0 R1)
(CMP R1)
(BNULL ERR)
(CDR R0 R0)
(CMP R1 'a')
(JEQ E3)
(CMP R1 'b')
(JEQ E1)
(JMP ERR)

; --- Etat 2
(LABEL E2)
(CAR R0 R1)
(CMP R1)
(BNULL ERR)
(CDR R0 R0)
(CMP R1 'a')
(JEQ E0)
(CMP R1 'b')
(JEQ E2)
(JMP ERR)

; --- Etat 3
(LABEL E2)
(CAR R0 R1)
(CMP R1)
(BNULL END)
(CDR R0 R0)
(CMP R1 'b')
(JEQ E2)
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

## Question 2

### 1. Principe de génération

La fonction auto2vm est le résultat de la concaténation de trois fonctions auxiliaire:

1. Permet de se brancher inconditionnellement sur l'état init. en ré-utilisant la fonction auto-init
2. Permet de réaliser le traitement de chaque état à l'aide d'une fonction map. Celle-ci permet d'appliquer une fonction sur chaque état via auto-etat-liste.
3. Retourne la liste d'instructions qui termine la VM.

### 2. Décomposition du problème

```lisp
(defun auto2vm(auto)
 (append 
  '((jump (auto_init auto)))
  (vm_etats auto)
  (vm_auto_ok)
 )
)

(defun vm_etats(auto)
 (apply #'append
  (map 'list 
    (vm_etat_callback auto)
    (auto_etat_liste auto)
  )
 )
)

(defun vm_etats_callback (auto)
 (lambda (etat)
  (append '((label etat))
    (if (auto_final_p auto etat)
      '(
        (move etat R2)
        (cmp R0)
        (bnull ok)
      )
      nil ; cas FALSE
    )
    (vm_read_symbol)
    (apply #'append
      (map 'list
        (vm_transition_callback etat)
        (auto_trans_list auto etat)
      )
    )
    (vm_auto_not_ok)
  )
 )
)

(defun vm_read_symbol()
 '( (car R0 R1) (cdr R0 R0) )
)

(defun vm_transition_callback (etat)
 ;; le parametre transition est un doublet (symbole,etat)
 (lambda (transition)
  '(
    (cmp R1 (car transition))
    (jeq (cdr transition))
  )
 )
)

(defun vm_auto_not_ok ()
 '((move nil R0) (halt))
)

(defun vm_auto_ok ()
 '( (label ok) (move R2 R0) (halt))
)
```




# Mémo
## Question 2.1: méthodologie

```lisp
; -------------- START
(JMP <ETAT_INIT>)
;ex: (JMP E0)


; -------------- LABEL
(LABEL E<n>)
;ex: (LABEL E0)


; -------------- SAVE & CHECK
(CAR R0 R1)	;save l'el. à comparer ds R1
(CMP R1)	;check si R1 === null
(BNULL END)	;si vrai, on finis
(CDR R0 R0)	;sinon le del. de la list


; -------------- COMPARE
(CMP R1 <CHAR>)	;compare R1 avec le caractère donné
(JEQ <LABEL>)	;si vrai on saute vers le label (ou état) donné
;ex: (CMP R1 'a')
;    (JEQ E1)
;    (JMP ERR)
;check si a est reconnu, si oui saute sur E1, sinon sort en renvoyant null


; -------------- ON ERROR
(LABEL ERR)
(MOVE NIL R0)
(STOP)

; -------------- ON SUCCESS
(LABEL END)
(MOVE R2 R0) ;on met l'état final atteint quand le mot a été reconnu dans R0
(STOP)


; --- Legendes
(STOP) 	;arrêt
(JEQ)  	;saut si égale
(LABEL)	;étiquette
```