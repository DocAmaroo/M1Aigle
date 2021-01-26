;; ---------------------------------------------------
;;|                      LOOPS                        |
;; ---------------------------------------------------
(defun compile-progn (exp env fenv namef)
  (if (null exp) 
		()
    (append (compiler (car exp) env fenv namef) (compile-progn (cdr exp) env fenv namef))
	)
)

(defun compile-loop (exp env fenv namef)
  (case (car exp)
		('else (compile-else (cdr exp) env fenv namef))
    ('until (compile-until (cdr exp) env fenv namef))
	)
)

;; --- else
(defun compile-else (exp env fenv namef)
  (let 
		(
			(end (gensym "endelse"))
			(loop (gensym "else"))
		) 
    (if (eql (cadr exp) 'do) 
			(append `((@ ,loop))
				(compiler (car exp) env fenv namef) 
				`((CMP :R0 (:DIESE nil)))
				`((JEQ (@ ,end)))
				(compiler (caddr exp) env fenv namef)
				`((JMP (@ ,loop)))
				`((@, end))
			)
			(error "Syntax error: ~s" exp)
		)
	)
)

;; --- For (until)
(defun compile-until (exp env fenv namef)  
  (let 
		(
			(end (gensym "endUntil"))
			(loop (gensym "until"))
		)
    (append `((@ ,loop))
	    (compiler (car exp) env fenv namef)
	    '((CMP :R0 (:DIESE T)))
	    `((JEQ (@ ,end)))
	    (compiler (caddr exp) env fenv namef)
	    `((JMP (@ ,loop)))
	    `((@ ,end))
		)
	)
)