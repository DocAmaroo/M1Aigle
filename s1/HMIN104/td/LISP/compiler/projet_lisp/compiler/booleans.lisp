;; ---------------------------------------------------
;;|                     BOOLEANS                      |
;; ---------------------------------------------------

;; --- Compare
(defun compile-comp (exp env fenv namef)
  (let ((op (car exp))
	(fin (gensym "finTest")))
    (append (compiler (cadr exp) env fenv namef)
	    '((PUSH :R0))           
	    (compiler (caddr exp) env fenv namef)
	    '((PUSH :R0))
	    '((POP :R0))
	    '((POP :R1))
	    '((CMP :R1 :R0)) 
	    '((MOVE (:DIESE T) :R0)) 
	    (case op
	      ('= `((JEQ (@ ,fin))))
	      ('< `((JL (@ ,fin))))
	      ('> `((JG (@ ,fin))))
	      ('<= `((JLE (@ ,fin))))
	      ('>= `((JGE (@ ,fin))))
	      )
	    '((MOVE (:DIESE NIL) :R0))
	    `((@ ,fin))
		)
	)
)

;; --- And
(defun compile-and (exp labelfin env fenv namef)
  (if (null exp) 
		(append '((MOVE (:DIESE T) :R0)) `((@ ,labelfin)))
    (append (compiler (car exp) env fenv namef) 
	    '((CMP :R0 (:DIESE T)))
	    `((JNE (@ ,labelfin)))
	    (compile-and (cdr exp) labelfin env fenv namef))
	)
)

;; --- Or
(defun compile-or (exp labelfin env fenv namef)
  	(if (null exp) 
      (append '((MOVE (:DIESE NIL) :R0)) `((@ ,labelfin)))
    (append (compiler (car exp) env fenv namef)
	    '((CMP :R0 (:DIESE T)))
	    `((JEQ (@ ,labelfin)))
	    (compile-or (cdr exp) labelfin env fenv namef))
	)
)