;; ---------------------------------------------------
;;|                     BOOLEANS                      |
;; ---------------------------------------------------

;; --- Compare
(defun compile-comp (exp env fenv namef)
  (let 
		(
			(op (car exp))
			(end (gensym "endTest"))
		)
    (append (compiler (cadr exp) env fenv namef)
	    '((PUSH :R0))           
	    (compiler (caddr exp) env fenv namef)
	    '((PUSH :R0))
	    '((POP :R0))
	    '((POP :R1))
	    '((CMP :R1 :R0)) 
	    '((MOVE (:DIESE T) :R0)) 
	    (case op
	      ('= `((JEQ (@ ,end))))
	      ('< `((JL (@ ,end))))
	      ('> `((JG (@ ,end))))
	      ('<= `((JLE (@ ,end))))
	      ('>= `((JGE (@ ,end))))
	      )
	    '((MOVE (:DIESE NIL) :R0))
	    `((@ ,end))
		)
	)
)

;; --- And
(defun compile-and (exp endLabel env fenv namef)
  (if (null exp) 
		(append '((MOVE (:DIESE T) :R0)) `((@ ,endLabel)))
    (append (compiler (car exp) env fenv namef) 
	    '((CMP :R0 (:DIESE T)))
	    `((JNE (@ ,endLabel)))
	    (compile-and (cdr exp) endLabel env fenv namef))
	)
)

;; --- Or
(defun compile-or (exp endLabel env fenv namef)
  	(if (null exp) 
      (append '((MOVE (:DIESE NIL) :R0)) `((@ ,endLabel)))
    (append (compiler (car exp) env fenv namef)
	    '((CMP :R0 (:DIESE T)))
	    `((JEQ (@ ,endLabel)))
	    (compile-or (cdr exp) endLabel env fenv namef))
	)
)