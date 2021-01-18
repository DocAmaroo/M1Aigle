;; ---------------------------------------------------
;;|                    OPERATIONS                     |
;; ---------------------------------------------------
(defun compile-op (exp env fenv namef)
  (let 
		(
			(op (car exp))
			(arg (cdr exp))
		)
		(if (null (cddr arg))
			(append (compiler (car arg) env fenv namef)
				'((PUSH :R0))
				(compiler (cadr arg) env fenv namef)
				'((PUSH :R0))
				'((POP :R1))
				'((POP :R0))
				(case op
					('+ '((ADD :R1 :R0)))
					('- '((SUB :R1 :R0)))
					('* '((MULT :R1 :R0)))
					('/ '((DIV :R1 :R0))))
			) 
			(append (compiler  `(,op ,(list op (car arg) (cadr arg)) ,@(cddr arg)) env fenv namef))
		)
	)
)