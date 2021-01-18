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