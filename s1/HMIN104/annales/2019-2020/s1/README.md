# HMIN104 - Session 2 2019/2020

# Exercice 1

## 1. Que fait la fonction *f* ?

u0 = 5
u1 = 16
u2 = 8
u3 = 4
u4 = 2
u5 = 1

Durée du vol = 6
Altitude max. = 16

## 2. Fonction *f* en PP

```ocaml
function f(n : integer) : integer
begin
	if n = 1
		then f := 1
		else if n%2 = 0 
			then f := f(n/2)
			else f := f((3*n)+1);
end;
```

## 2. Fonction *f* en UPP
```ocaml
function f(n);
begin
	if n = 1
		then f := 1
		else if n%2 = 0 
			then f := f(n/2)
			else f := f((3*n)+1);
end;
```

## 3. Fonction *f* en RTL
```mips
function f(%0) : %1
var %0, %1, %2, %3, %4, %5
entry f1
exit f0
f1: beq %0, 1 -> f2, f3		;; if (n == 1)
f2: li %1, 1 -> f0			;; return 1
f3: move %2, %0	-> f4		;; save n
f4: div %2, 2 -> f5
f5: mfhi %3 -> f6			;; recup le reste de la div
f6: beq %3, 0 -> f7, f8
f7: li %1, %2 -> f0			;; return n/2
f8: mul %4, %0, 3 -> f9		;; 3*n
f9: addiu %5, %4, 1 -> f10	;; (3*n) + 1
f10: li %1, %5 -> f0		;; return (3*n) + 1
```

## 3. Fonction *f* en ERTL
```mips
procedure f(1)
var %0, %1, %2, %3, %4, %5, %6, %7
entry f1
f1: newframe -> f2
f2: move %6, $ra -> f3
f3: move %7, $s0 -> f4
f4: move %0, $a0 -> f5
f5: beq %0, 1 -> f6, f7		;; if (n == 1)
f6: li %1, 1 -> f			;; return 1
f7: move %2, %0	-> f8		;; save n
f8: div %2, 2 -> f9
f9: mfhi %3 -> f10			;; recup le reste de la div
f10: beq %3, 0 -> f11, f14
f11: move $a0, %2 -> f12
f12: call f(1) -> f13
f13: li %1, $v0 -> f		;; return f(n/2)
f14: mul %4, %0, 3 -> f16	;; 3*n
f13: addiu %5, %4, 1 -> f14	;; (3*n) + 1
f14: move %5, $a0 -> f15
f15: call f(1) -> f16
f16: move $v0, %1 -> f17	;; return f((3*n) + 1)
f17: move $ra, %6 -> f18	;; restore 
f18: move $s0, %7 -> f19
f19: delframe -> f20
f20: jr $ra
```