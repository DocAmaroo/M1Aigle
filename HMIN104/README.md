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
      - [Instructions de transfert](#instructions-de-transfert)
      - [Instructions de calcul](#instructions-de-calcul)
        - [nullaire](#nullaire)
        - [unaire](#unaire)
        - [binaire](#binaire)
      - [Instructions de saut](#instructions-de-saut)
        - [Saut inconditionnel](#saut-inconditionnel)
        - [Saut conditionnel](#saut-conditionnel)
        - [spécial](#spécial)
    - [Un premier programme](#un-premier-programme)
  - [Analyse syntaxique (AST)](#analyse-syntaxique-ast)
    - [Introduction](#introduction-1)
    - [ANTRL](#antrl)
    - [Hello World](#hello-world)
  - [De UPP vers PP](#de-upp-vers-pp)
    - [UPP](#upp)
    - [RTL (Register Transfer Language)](#rtl-register-transfer-language)


## Liens utiles :

[_Accès au moodle_](https://moodle.umontpellier.fr/course/view.php?id=5906 "Accèder au moodle") | `clé: compil;2020`

## Introduction

### Définitions

:bulb: compilateur &rarr; `traduit un lang. de haut niv. vers du bas niv.`

:bulb: analyse syntax. &rarr; `passer du txt. à une structure arborescente sur laquelle on peut travailler.`

:bulb: compilat° à la volée <small>_('Just-In-Time' JIT)_</small> &rarr; `Combinaison de la compilation native et bytecode pour offrir portabilité et performance.`

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

:unlock: $r0 &rarr; `toujours égal à 0`

:unlock: ($a0 - $a3, $ra) &rarr; `passage d'argument`

:unlock: ($v0 - $v1) &rarr; `renvoie de résultat`

:unlock: ($s0 - $s7) &rarr; `sauvegardé par l'appelé (une fonction)`

:unlock: ($t0 - $t9) &rarr; `non sauvegardé par l'appelé (une fonction)`

:unlock: ($sp, $fp) &rarr; `pointeurs vers la pile`

:unlock: $gp &rarr; `pointeur vers les données`

:unlock: (k0 - k1) &rarr; `réservé par le noyau`

:unlock: at &rarr; `réservé par l'assembleur`

### Les instructions

:triangular_flag_on_post: transfert entre registres et mémoire

:triangular_flag_on_post: calcul

:triangular_flag_on_post: saut

#### Instructions de transfert

:bulb: Lecture

```mips
lw dest, offset(base)
;offset (16bits) + base => address.
;dest = valeur de 'address'
```

:bulb: Écriture

```mips
sw src, offset(base)
;offset (16bits) + base => address.
;valeur de 'address' = src
```

#### Instructions de calcul

##### nullaire

:bulb: Lecture d'une constante

```mips
li dest, const ;dest = const
```


:bulb: Lecture d'une adresse

```mips
la dest, address ;dest = address
```


##### unaire

:bulb: Addition d'une constante

```mips
addi dest, src, const ;dest = const + src
```


:bulb: Déplacement

```mips
move dest, src ;dest = src
```

:bulb: Négation

```mips
neg dest, src ;dest = !src
```

##### binaire

:bulb: Opérations

```mips
add dest, src1, src2 ;dest = src1 + src2
sub dest, src1, src2 ;dest = src1 - src2
mul dest, src1, src2 ;dest = src1 * src2
div dest, src1, src2 ;dest = src1 / src2
```

:bulb: Comparaison

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

:bulb: Saut

```mips
j address ;saute sur 'address'
```

:bulb: Saut avec retour

```mips
jal address 
;sauvegarde l'adresse actuelle dans $ra puis
;saute sur 'address'
```


:bulb: Saut vers adresse variable

```mips
jr address ;saute dans le registre contenu dans address
```

_NB: `jr $ra` est typiquement utilisée pour rendre la main à l'appelant à la fin d'une fonction/procédure_

##### Saut conditionnel

:bulb: Saut cond. unaire

```mips
bgtz src, address ;saute sur 'address' si src > 0
bgez src, address ;saute sur 'address' si src >= 0
blez src, address ;saute sur 'address' si src <= 0
bltz src, address ;saute sur 'address' si src < 0
```

:bulb: Saut cond. binaire

```mips
blt src1, src2, address ;saute sur 'address' si src1 < src2
beq src1, src2, address ;saute sur 'address' si src1 == src2
bne src1, src2, address ;saute sur 'address' si src1 != src2
```

##### spécial
:bulb: Appel système

```mips
syscall
;Communique avec le noyau système
;La fonction utilisée est déterminée selon la valeur de *$v0*
```


| *$v0* | cmd          | args                                           | result           |
| ----- | ------------ | ---------------------------------------------- | ---------------- |
| 1     | print_int    | *$a0*: entier à lire                           |
| 4     | print_string | *$a0*: adresse de chaine                       |
| 5     | read_int     |                                                | *$v0*: entier lu |
| 8     | read_string  | *$a0*: adresse de chaine, *\$a1*: longueur max |
| 10    | exit         |                                                |


### Un premier programme
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
:bulb: vérifie qu'une prog. est bien formé

:bulb: syntaxe sous forme d'arbre (abstraite)

### ANTRL
:bulb: Outils d'analyse syntaxique

:bulb: Générateur de parser 

[Télécharger ANTLR](http://www.antlr.org/)

### Hello World
```antlr4
grammar Hello;

r : 'hello' ID;
ID : [a-z]+;
WS : [ \t\r\n]+ -> skip;
```

Pour plus, voir ce TP assez complet: [antlr-parser](https://github.com/DocAmaroo/M1Aigle/tree/master/HMIN104/td/ANTLR)


## De UPP vers PP

### UPP

- type supprimé
- variable global distinguées par leur adresses (même nom / offset)
- accès aux tableaux en utilisant `lw` et `sw`

```mips
// Allocation de taille e
alloc (4*e)

// Accès array -- e1[e2]
lw( e1 + 4*e2 )

// Affect array -- e1[e2] := e3
sw( e1 + 4*e2 ) e3
```

- op. arith. utilise ceux de MIPS
- toute variable = 4 octets (32 bits)

### RTL (Register Transfer Language)

- Expression et instruction sont décomposées en inst. élémentaire.
- Var. local deviennent des pseudos registres _(nbr. infinis)_
- Notion d'arbre **IMPORTANTE**
