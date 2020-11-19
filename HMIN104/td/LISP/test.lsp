; lambda-expressions
((lambda (x) (+ (* 2 x) 3)) 4) => 11
((lambda (x y) (* (+ x 2) (+ y 6))) 7 8) => 126
((lambda (v) ((lambda (x) (* 3 x)) (+ v 2))) 8) => 30
((lambda (v) ((lambda (x) (* v x)) (+ v 2))) 8) => 80
((lambda (v) ((lambda (v) (* 3 v)) (+ v 2))) 8) => 30
((lambda (x y z) (+ (* x y) (* y z))) 2 3 4) => 18
((lambda (x y) (* x x y y)) 4) => "TOO FEW ARGUMENT"
((lambda (x) (* x x 2)) 4 5) => "TROP D'ARGUMENT"
(lambda (x) (* x x 2)) => "#<FUNCTION:LAMBDA(X) (* 2 X X 2)>"

; fonctions globales
(defun f (x) (+ 3 x))
(defun g (v) (* 5 (f (+ v 2))))
(g 8) => 65

(defun f (x) (+ v x))
(g 8) => "no value for V"

; factorielle
(defun fact (n) (if (= n 0) 1 (* n (fact(- n 1)))))

; fibonnaci
; O(fibo(n))
(defun fibo (n)
    (if (= n 0) 0
        (if (= n 1) 1 (+ (fibo (- n 1)) (fibo (- n 2)))))

; les listes et les cellules
(car '()) => "NIL"
(car '(())) => "NIL"
(car '((()))) => "(NIL)"

(cdr '()) => "NIL"
(cdr '(())) => "NIL"
(cdr '((()))) => "NIL"

; en doublets
(car '(() ())) => "NIL"
(car '((()) (()))) => "(NIL)"
(car '(((())) ((())))) => "((NIL))"

;eq & equal
(eq '() (car '())) => T
(equal '() (car '())) => T
(= '() (car '())) => "NIL IS NOT A NUMBER"
(eq '() (car '(()))) => T
(equal '() (car '(()))) => T
(= '() (car '(()))) => "NIL IS NOT A NUMBER"
(eq '() (car '((())))) => NIL
(equal '() (car '((())))) => NIL
(= '() (car '((())))) => "NIL IS NOT A NUMBER"

; nb cellules
(1 2 3 4) ⇒ 4
(1 (2 3) 4) ⇒ 3+2 = 5
(1 (2) (3) 4) ⇒ 4 + 1 + 1 = 6

; en doublets
(cons 1 '(2 3 4)) => (1 2 3 4)
(cons 1 '((2 3) 4)) => (1 (2 3) 4)
(cons 1 '((2) (3) 4)) => (1 (2) (3) 4)

; inversion de car et cdr
(cons '(2 3 4) 1) => ((2 3 4) . 1)
(cons '((2 3) 4) 1) => (((2 3) 4) . 1))
(cons '((2) (3) 4) 1) => (((2) (3) 4) . 1)


; fonctions sur les listes plates
(defun nmember (x l)
    (if (atom l) nil
        (if (eql x (car l)) l 
            (nmember x (cdr l)))))
(nmember 'b '(a b c))

; calcul la taille d'une liste
(defun mylength (l)
    (if (atom l) 0 (+ 1 (mylength (cdr l)))))
(mylength '(a b c))

; return le dernier élément d'une liste
(defun mylast (l)
    (if (eq nil (cdr l)) (car l) (mylast (cdr l))))
(mylast '(a b c))

; return la liste de 1 à n
(defun makelist (n) 
    (if (equal 0 n) '(0) (cons n (monmakelist (- n 1)))))
(makelist 3)

; recopie une liste
(defun copylist (l)
    (if (atom l) l
        (cons (car l) (copylist (cdr l)))))

; supprime tout les éléments choisis dans une liste
(defun nremove (x l)
    (if (atom l) l
        (if (eql x (car l)) (nremove x (cdr l))
            (progn (setf (cdr l) (nremove x (cdr l))) l))))

(defun nremovebis (x l)
    (if (atom l) l
        (if (eql x (car l)) (cdr l)
            (cons (car l) (nremovebis x (cdr l))))))

; concatene deux listes
(defun myappend (l1 l2)
    (if (equal (cdr l1) nil) (append l1 l2)
        (myappend (cdr l1) l2)))

; ajoute un élement en fin de liste (si il n'existe pas)
(defun nadjoin (x l)
    (if (equal x (car l)) l
        (if (equal (cdr l) nil) (append l (list x))
            (cons (car l) (nadjoin x (cdr l))))))

(defun size-tree (tree)
    (cond 
        ((null tree) 0)
        ((atom tree) 1)
        (t (+ 1 (size-leaf (car tree)) (size-leaf (cdr tree))))))
(size-tree '(3(5(6)2)))

(defun size-leaf (tree) 
    (cond 
        ((null tree) 0)
        ((atom tree) 1)
        (t (+ (size-leaf (car tree)) (size-leaf (cdr tree))))))
(size-leaf '(3(5(6)2)))