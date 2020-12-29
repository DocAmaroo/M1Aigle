# HMIN102 - Compilation et interprétation

_"D. Delahaye :heart:"_

## Sommaire
- [HMIN102 - Compilation et interprétation](#hmin102---compilation-et-interprétation)
  - [Sommaire](#sommaire)
  - [Liens utiles :](#liens-utiles-)
  - [Introduction](#introduction)
    - [Définitions](#définitions)
    - [Difficultées](#difficultées)
    - [Attentes d'un compilateur](#attentes-dun-compilateur)
  - [Programme](#programme)
  - [Le langage](#le-langage)
  - [MIPS](#mips)
    - [Les registres](#les-registres)
    - [Les instructions](#les-instructions)
    - [Hello World](#hello-world)
  - [Analyse syntaxique (AST)](#analyse-syntaxique-ast)
    - [Introduction](#introduction-1)
    - [ANTRL](#antrl)
    - [Hello World](#hello-world-1)
    - [Des exemples plus concret](#des-exemples-plus-concret)
  - [De PP &rarr; UPP &rarr; RTL &rarr; ERTL](#de-pp--upp--rtl--ertl)
    - [UPP (Untyped Pseudo-Pascal)](#upp-untyped-pseudo-pascal)
    - [RTL (Register Transfer Language)](#rtl-register-transfer-language)
    - [ERTL (Explicit Register Transfer Language)](#ertl-explicit-register-transfer-language)
    - [Exemples](#exemples)
  - [Analyse de la durée de vie](#analyse-de-la-durée-de-vie)
    - [Définition](#définition)
    - [Exemples](#exemples-1)
  - [Graphe d'interférences](#graphe-dinterférences)
    - [Définition](#définition-1)
    - [Exemple](#exemple)


## Liens utiles :

[_Accès au moodle_](https://moodle.umontpellier.fr/course/view.php?id=5906 "Accèder au moodle") | `clé: compil;2020`

## Introduction

### Définitions

:bulb: `compilateur` &rarr; traduit un lang. de haut niv. vers du bas niv.

:bulb: `analyse syntax`. &rarr; passer du txt. à une structure arborescente sur laquelle on peut travailler.

:bulb: `compilat° à la volée` <small>_('Just-In-Time' JIT)_</small> &rarr; Combinaison de la compilation native et bytecode pour offrir portabilité et performance.

### Difficultées

:children_crossing: Choix de la structure de data (AST)

:children_crossing: Décomposit° en étape intermédiaire

:children_crossing: Bonne connaissance du lang. cible

:children_crossing: Gestion des erreurs

### Attentes d'un compilateur

:triangular_flag_on_post: **Correction** &rarr; le prog. traduit fait ce qu'on att. de lui)

:triangular_flag_on_post: **Efficacité**

## Programme

- Compilat° native (+opti. | lang. c)
- Compilat° bytecode (+portable | lang. Java)

## Le langage

_Voir diapos 14 &rarr; [intro.pdf](https://github.com/DocAmaroo/M1Aigle/blob/master/HMIN104/cours/intro.pdf "cours d'introduction HMIN104")_

:fire: Le langage utilisé est Turing-complet.

## MIPS

### Les registres

:unlock: `$r0` &rarr; toujours égal à 0

:unlock: `($a0 - $a3, $ra)` &rarr; passage d'argument

:unlock: `($v0 - $v1)` &rarr; renvoie de résultat

:unlock: `($s0 - $s7)` &rarr; sauvegardé par l'appelé (une fonction)

:unlock: `($t0 - $t9)` &rarr; non sauvegardé par l'appelé (une fonction)

:unlock: `($sp, $fp)` &rarr; pointeurs vers la pile

:unlock: `$gp` &rarr; pointeur vers les données

:unlock: `(k0 - k1)` &rarr; réservé par le noyau

:unlock: `at` &rarr; réservé par l'assembleur

### Les instructions

:triangular_flag_on_post: transfert entre registres et mémoire

:triangular_flag_on_post: calcul

:triangular_flag_on_post: saut

#### Instructions de transfert

:bulb: `Lecture`

```mips
lw dest, offset(base)
;offset (16bits) + base => address.
;dest = valeur de 'address'
```

:bulb: `Écriture`

```mips
sw src, offset(base)
;offset (16bits) + base => address.
;valeur de 'address' = src
```

#### Instructions de calcul

##### nullaire

:bulb: `Lecture d'une constante`

```mips
li dest, const ;dest = const
```


:bulb: `Lecture d'une adresse`

```mips
la dest, address ;dest = address
```


##### unaire

:bulb: `Addition d'une constante`

```mips
addi dest, src, const ;dest = const + src
```


:bulb: `Déplacement`

```mips
move dest, src ;dest = src
```

:bulb: `Négation`

```mips
neg dest, src ;dest = !src
```

##### binaire

:bulb: `Opérations`

```mips
add dest, src1, src2 ;dest = src1 + src2
sub dest, src1, src2 ;dest = src1 - src2
mul dest, src1, src2 ;dest = src1 * src2
div dest, src1, src2 ;dest = src1 / src2
```

:bulb: `Comparaison`

```mips
slt dest, src1, src2 ;dest = src1 < src2
sle dest, src1, src2 ;dest = src1 <= src2
sgt dest, src1, src2 ;dest = src1 > src2
sge dest, src1, src2 ;dest = src1 >= src2
seq dest, src1, src2 ;dest = src1 == src2
sne dest, src1, src2 ;dest = src1 != src2
```



#### Instructions de saut

##### Saut inconditionnel

:bulb: `Saut`

```mips
j address ;saute sur 'address'
```

:bulb: `Saut avec retour`

```mips
jal address 
;sauvegarde l'adresse actuelle dans $ra puis
;saute sur 'address'
```


:bulb: `Saut vers adresse variable`

```mips
jr address ;saute dans le registre contenu dans address
```

_NB: `jr $ra` est typiquement utilisée pour rendre la main à l'appelant à la fin d'une fonction/procédure_

##### Saut conditionnel

:bulb: `Saut cond. unaire`

```mips
bgtz src, address ;saute sur 'address' si src > 0
bgez src, address ;saute sur 'address' si src >= 0
blez src, address ;saute sur 'address' si src <= 0
bltz src, address ;saute sur 'address' si src < 0
```

:bulb: `Saut cond. binaire`

```mips
blt src1, src2, address ;saute sur 'address' si src1 < src2
beq src1, src2, address ;saute sur 'address' si src1 == src2
bne src1, src2, address ;saute sur 'address' si src1 != src2
```

##### spécial
:bulb: `Appel système`

```mips
syscall
;communique avec le noyau système
;la fonction utilisée est déterminée selon la valeur de $v0
```


| *$v0* | cmd          | args                                           | result           |
| ----- | ------------ | ---------------------------------------------- | ---------------- |
| 1     | print_int    | *$a0*: entier à lire                           |
| 4     | print_string | *$a0*: adresse de chaine                       |
| 5     | read_int     |                                                | *$v0*: entier lu |
| 8     | read_string  | *$a0*: adresse de chaine, *\$a1*: longueur max |
| 10    | exit         |                                                |


### Hello World
```mips
;; --- Affiche le message "hello world"
.data
msg: .asciiz "hello world\n"

.text
main: li $v0, 4   ; cmd: print_string
      la $a0, msg ; args: msg
      syscall     ; execute
```

Pour plus d'exemples voir les TP associé à MIPS [ici](https://github.com/DocAmaroo/M1Aigle/tree/master/HMIN104/td/MIPS)

## Analyse syntaxique (AST)

### Introduction
:triangular_flag_on_post: vérifie qu'une prog. est bien formé

:triangular_flag_on_post: syntaxe sous forme d'arbre (abstraite)

### ANTRL
:triangular_flag_on_post: Outils d'analyse syntaxique

:triangular_flag_on_post: Générateur de parser 

[Télécharger ANTLR](http://www.antlr.org/)

### Hello World
```g4
grammar Hello;

r : 'hello' ID;
ID : [a-z]+;
WS : [ \t\r\n]+ -> skip;
```

### Des exemples plus concret
Voir [2_analyse-syntaxique](https://github.com/DocAmaroo/M1Aigle/blob/master/HMIN104/cours/2_analyse-syntaxique.pdf), à partir de la diapo 8

Voir [antlr-parser](https://github.com/DocAmaroo/M1Aigle/tree/master/HMIN104/td/ANTLR), mini projet mélangeant ANTLR et Java.


## De PP &rarr; UPP &rarr; RTL &rarr; ERTL

### UPP (Untyped Pseudo-Pascal)

- type supprimé
- variable global distinguées par leur adresses (même nom / offset)
- accès aux tableaux en utilisant `lw` et `sw`

```mips
;; --- Allocation de taille e
alloc (4*e)

;; --- Accès : e1[e2]
lw (e1 + 4*e2)

;; --- Affect : e1[e2] := e3
sw (e1 + 4*e2) e3
```

- op. arith. utilise ceux de MIPS
- toute variable = 4 octets (32 bits)

### RTL (Register Transfer Language)

- Expression et instruction sont décomposées en inst. élémentaire.
- Var. local deviennent des pseudos registres _(nbr. infinis)_
- Notion d'arbre ([voir diapo 10/11](https://github.com/DocAmaroo/M1Aigle/blob/master/HMIN104/cours/3_pp-upp-rtl.pdf)) **IMPORTANTE**

### ERTL (Explicit Register Transfer Language)

- param et res des fonctions/proc. stocké dans des registres/piles.
- proc. == fonction.
- adresse de retour devient un param explicite.
- il faut penser à allouer et désallouer à la main.
- "callee-save" ($s0-\$s7) sauv. de façon explicite.

*Mémo*
```mips
call f(n) ;;appel proc. f avec n param.
```

### Exemples
Prenons pour exemple la fonction factorielle
Version PP
```ocaml
function f (n : integer) : integer
begin
  if n <= 0 then
    f := 1
  else
    f := n * f (n - 1)
end;
```
Version UPP
```ocaml
function f(n);
begin
  f := 0;
  if n <= 0 then
    f := 1
  else
    f := n * f ((-1 +) n)
end;
```
Version RTL
```mips
function f(%0) : %1
var %0,%1,%2,%3
entry f6
exit f0
f6: li %1,0 -> f5
f5: blez %0 -> f4,f3
f3: addiu %3, %0, -1 -> f2
f2: call %2, f(%3) -> f1
f1: mul %1,%0,%2 -> f0
f4: li %1,1 -> f0
```

Version ERTL (Explicit Register Transfer Language)
```mips
procedure f(1)
var %0, %1, %2, %3, %4, %5, %6
entry f11
f11: newframe -> f10
f10: move %6, $ra -> f9     ;;%6 := $ra (adresse de retour)
f9: move %5, $s1 -> f8      ;;%5 := $s1 (???)
f8: move %4, $s0 -> f7      ;;%4 := $s0 (???)
f7: move %0, $a0 -> f6      ;;%0 := $a0 (n)
f6: li %1,0 -> f5           ;;%1 := 0
f5: blez %0 -> f4,f3        ;;si n <= 0 => f4, sinon f3
f3: addiu %3, %0, -1 -> f2  ;;%3 := (n-1)
f2: j -> f20                
f20: move $a0, %3  -> f19    ;;$a0 := %3 (n-1)
f19: call f(1) -> f18       ;;appel récursif sur f(1)
f18: move %2, $v0 -> f1     ;;%2 := res du call
f1: mul %1,%0,%2 -> f0      ;;%1 := n * f(n-1)
f0: j -> f17                
f17: move $v0, %1 -> f16    ;;$v0 := n * f(n-1)
f16: move $ra, %6 -> f15    ;;$ra := %6
f15: move $s1, %5 -> f14    ;;$s1 := %5
f14: move $s0, %4 -> f13    ;;$s0 := %4
f13: delframe -> f12
f12: jr $ra
f4: li %1,1 -> f0           ;;%1 := 1
```

## Analyse de la durée de vie
### Définition
:bulb: `naissance d'une variable` &rarr; Une var. (v) est engendrée par une instruc. (i) si i utilise (= lit dans) v.

:bulb: `décès d'une variable` &rarr; Une var. (v) est tuée par une instruc. (i) si i définit (= écrit dans) v.

:bulb: `variable vivante` &rarr; Une var. est utilisée et/ou n'est pas affectée d'un point x à un point y pendant la durée du programme

:bulb: `variable morte` &rarr; Une var. n'est pas utilisée et/ou est affectée d'un point x à un point y pendant la durée du programme

:triangular_flag_on_post: Pour déterminer les variables vivantes aux différents points du programme on fait une analyse arrière (en remontant)

### Exemples

**Question:** Quels sont les variables vivantes dans les différents points de chaque programme ?
```c
t := x;   // ➦ t meurt, x naît => {y,x} ↑ (iii)
x := y;   // ➦ x meurt, y naît => {t,y} ↑ (ii)
y := t;   // ➦ y meurt, t naît => {x,t} ↑ (i)
          // départ: {x,y}
```

```c
t := x;   // ➦ t meurt, x naît => {y,x} ↑ (iii)
x := y;   // ➦ y naît => {t,y}          ↑ (ii)
y := t;   // ➦ t naît => {t}            ↑ (i)
          // départ: {}
```

```c
x := x + 1;   // ➦ x meurt, x naît => {x} ↑ (ii)
y := x + 1;   // ➦ y meurt, x naît => {x} ↑ (i)
              // départ: {x,y}
```

**Question:** Quels sont les variables vivantes à l’entrée du programme sachant qu’à la fin du programme, les variables vivantes sont d, k et j?

```c
g := j + 12;   // ➦ {j,k}   
h := k − 1;    // ➦ {j,g,k}   
f := g * h;    // ➦ {j,g,h}
e := j + 8;    // ➦ {f,j}
m := j + 16;   // ➦ {e,f,j} 
b := f;        // ➦ {m,e,f}
c := e + 8;    // ➦ {b,m,e}
d := c;        // ➦ {b,m,c}
k := m + 4;    // ➦ {d,b,m}
j := b         // ➦ {d,k,b}
               // départ: {d,j,k}
```

## Graphe d'interférences
### Définition
:bulb: `Graphe d'interférences` &rarr; les sommets sont les var., les arêtes les relations d'interférences/préférences. Ce graphe spécifie à l'alloc. de reg. les contraintes sous lesquelles il doit travailler.

:bulb: `Arête d'interférence` &rarr; relie deux var. (sommets) qui interfèrent.

:bulb: `Arête de préférence` &rarr; relie deux var. (sommets) reliée par une intr. move.


:bulb: `Interférence` &rarr; Deux var. interfèrent si l'une est vivante à la sortie d'une instr. qui définit l'autre

:bulb: `Préférence` &rarr; Si une var. y est vivante à la sortie d'une instr. qui définit une var. x et dont la valeur reçu dans x et celle de y (x := y, c'est une instr. move).

### Exemple

**Question:** Dessiner le graphe d’interférences de ce programme
```c
v := 0;     // ➦ {x,z,t}
y := z + t; // ➦ {x,z,t}
u := t;     // ➦ {x,y,t}
z := x + y; // ➦ {x,y}
v := z;     // ➦ {z}
            // départ: {}
```

Interférences: (v,x), (v,t), (v,z), (y,x), (y,t), (u,x), (u,y)

Préférences: (u,t)

**Question:** Dessiner le graphe d’interférences de ce programme sachant qu’à la fin du programme, les variables vivantes sont d, k et j?

```c
g := j + 12;   // ➦ {j,k}
h := k − 1;    // ➦ {j,g,k} => (g,j), (g,k)   
f := g * h;    // ➦ {j,g,h} => (h,j), (h,g)
e := j + 8;    // ➦ {f,j} => (f,j)
m := j + 16;   // ➦ {e,f,j} => (e,f), (e,j)
b := f;        // ➦ {m,e,f} => (m,e), (m,f)
c := e + 8;    // ➦ {b,m,e} => !(b,f)
d := c;        // ➦ {b,m,c} => (c,b), (c,m)
k := m + 4;    // ➦ {d,b,m} => !(d,c)
j := b;        // ➦ {d,k,b} => (k,d), (k,b) && !(j,b)
               // départ: {d,k,j}
```

Interférences: (g,j), (g,k), (h,j), (h,g), (f,j), (e,f), (e,j), (m,e), (m,f), (c,b), (c,m), (k,d), (k,b)

Préférences: (b,f), (d,c), (j,b)