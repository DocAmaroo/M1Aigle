# Exercice 1
1. Que fait la fonction *f* ?

**Test**
f(0) => 0

f(1) => 2 - 0 - 1 = 1

f(2) => 2*2 - 1 - 1 = 2

f(3) => 6 - 2 - 1 = 3

f(4) => 8 - 3 - 1 = 4

f est la fonction identité: f(n) => n

Si n < 0 la réponse est indéterminable car appel récursif sur f(-n-1), donc n >= 0.

1. fonction f en PP 

```ocaml
function f(n : integer) : integer
	if n <= 0 then
		f := 0
	else
		f := (2*n) - f(n-1) - 1
```

3. fonction f en UPP
```ocaml
function f(n);
begin
	f := 0;
	if n <= 0 then
		f := 0
	else
		f := (2*n) - f(n-1) - 1
end;
```

4. fonction f en RTL
```mips
function f(%0) : %1
var %0, %1, %2, %3, %4, %5
entry f1
exit f0
f1: li %1, 0 -> f2			;; %1 := 0
f2: blez %0 -> f3, f4		;; if (n <= 0)
;; then
f4: mul %2, %0, 2 -> f5		;; %2 := 2*n
f5: addiu %3, %2, -1 -> f6	;; %3 := (2*n)-1 
f6: addiu %4, %0, -1 -> f7 	;; %4 := n-1
f7: call %5, f(%4) -> f6	;; %5 := f(n-1)
f6: sub %1, %3, %5 -> f0 	;; %1 := (2*n)-1-f(n-1)
;; else
f3: li %1, 0 -> f0			;; %1 := 0
```

5. fonction f en ERTL
```mips
procedure f(1)
var %0, %1, %2, %3, %4, %5, %6, %7, %8
entry f1
f1: newframe -> f2
f2: move %6, $ra -> f3		  ;; Sauvegarde
f3: move %8, $s0 -> f4	  	  ;; ...
f4: move %0, $a0 -> f5	  	  ;; ...
f5: li %1, 0 -> f6			  ;; %1 := 0
f6: blez %0 -> f7, f8		  ;; if (n <= 0)
;; then
f8: mul %2, %0, 2 -> f9		  ;; %2 := 2*n
f9: addiu %3, %2, -1 -> f10	  ;; %3 := (2*n)-1 
f10: addiu %4, %0, -1 -> f11  ;; %4 := n-1
f11: move $a0, %4 -> f12	  ;; $a0 := n-1
f12: call f(1) -> f13		  ;; appel f(n-1)
f13: move $s0, $v0 -> f14	  ;; $s0 := f(n-1)
f14: sub %1, %3, $s0 -> f0 	  ;; %1 := (2*n)-1-f(n-1)
f0: j -> f15
f15: move $v0, %1 -> f16	  ;; Reset
f15: move $ra, %6 -> f16	  ;; ...
f16: move $s0, %8 -> f17	  ;; ...
f17: delframe -> f18		  ;; end
f18: jr $ra
;; else
f7: li %1, 0 -> f0			  ;; %1 := 0
```

# Exercice 2
1. Graphe du flot de contrôle

Soit :
x -> %5
y -> %6
z -> %7
t -> %8
u -> %9
v -> %10

start -> 
| li %8, 1 | -> 
| move %9, %5 | ->
| move %9, %10 | -> 
| addu %6, %7, %8 | -> 
| addu %6, %7, %9 | -> 
| addu %8, %6, %9 | -> 

2. Analyse durée de vie des variables
```c
t := 1; 	// {z,v,x}
u := x;		// {z,t,v,x}
u := v;		// {z,t,v}
y := z + t;	// {u,z,t}
y := z + u;	// {u,z}
t := y + u;	// {y,u}
			// {}
```

3. Graphe d'interférences

**Arêtes d'interférence:** 

(t,z), (t,v), (t,x), (t,u),

(u,z), (u,v),

(y,u), (y,z)

**Arêtes de préférences:**

(u,x)

4. Coloriage

Avec **3 couleurs** possible sans spiller

Etape de retrait des sommets:

	1. G\{y}
	2. G\{y,v}
	3. G\{y,v,z}
	4. G\{y,v,z,x}

et en remontant on obtient un coloriage comme:

:red_circle: {u,x}.

:black_circle: {v,z}.

:white_circle: {t,y}.

Avec **2 couleurs** il faudras spiller

Etape de retrait des sommets:

	1. G\{y}
	2. G\{y,t} // t est spiller
	3. G\{y,t,v}
	4. G\{y,t,v,x}

et en remontant on obtient un coloriage comme:

:red_circle: {u,x,y}.

:black_circle: {v,z}.

{t} est spiller