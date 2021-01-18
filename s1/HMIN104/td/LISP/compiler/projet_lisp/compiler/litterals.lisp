;; ---------------------------------------------------
;;|                     LITERALS                      |
;; ---------------------------------------------------
(defun compile-const (exp)
  `((MOVE (:DIESE ,exp) :R0))
)

(defun compile-varg (exp)
  `((MOVE (:* :@ ,exp) :R0))
)

(defun compile-litt (exp env fenv namef)
  (let ((var (assoc exp env)))
    (cond
     	((not (null var))
      	(if (eql (cadr var) 'loc) 
	  			`((MOVE ,(cdr var) :R0))
					(if (numberp (cadr var))
	    			(compile-const (cdr var))
					)
				)
      )
     	((and (symbolp exp) (not (null exp))) (compile-varg exp))
     	(t (compile-const exp))
		)
	)
)