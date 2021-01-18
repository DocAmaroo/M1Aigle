;; ---------------------------------------------------
;;|                     FUNCTIONS                     |
;; ---------------------------------------------------
(defun param-env (exp env dep nivem)   
  (if (atom exp) 
		env
    (param-env (cdr exp) (cons (cons (car exp) `(LOC ,(- 0 dep) ,nivem)) env) (+ 1 dep) nivem)
	)
)

(defun fun-env (exp fenv nivem)
  (if (atom exp) 
		fenv
    (fun-env (cdr exp) (cons `(,(caar exp) ,nivem) fenv) nivem)
	)
)


(defun compile-param (exp env fenv namef)
  (if (atom exp) 
    ()
    (append (compiler (car exp) env fenv namef)
	    `((PUSH :R0))
	    (compile-param (cdr exp) env fenv namef)
		)
	)
)

(defun compile-defun (exp env fenv namef)
  (let 
		(
			(nivem (assoc namef fenv))
		)
    (append '((FENTRY))
	    `((@ ,(car exp)))
	    (compile-progn (cddr exp)
				(param-env (cadr exp) env 1 (if nivem (+ 1 (cadr nivem)) 0))
				(fun-env (list exp) fenv (if nivem (+ 1 (cadr nivem)) 0))
				(car exp)
			)
	    '((RTN))
	    '((FEXIT))
		)
	)
)

(defun compile-funcall (exp env fenv namef)
  (let 
		(
			(n (length (cdr exp))) 
			(nivem (assoc (car exp) fenv))
		)
		(append (compile-param (cdr exp) env fenv namef)
			`((PUSH (:DIESE ,n)))
			`((MOVE :FP :R1))
			`((MOVE :SP :FP))
			`((MOVE :SP :R2))
			`((SUB  (:DIESE ,n) :R2))
			`((SUB  (:DIESE 1) :R2))
			`((PUSH :R2)) 
			`((PUSH :R1))
			(if nivem  `((PUSH (:DIESE ,(cadr nivem))))  `((PUSH (:DIESE ,0))))
			`((JSR (@ ,(car exp))))
		)
	)
)


;; (defun compile-lambda (exp env fenv namef)
;;   (let 
;; 		(
;; 			(lambdaexpr (gensym "lambdaexpr"))
;; 			(n (length (cdr exp)))
;; 			(nivem (assoc namef fenv))
;; 		)
;;     (append (compile-param (cdr exp) env fenv namef)
;; 	    `((PUSH (:DIESE ,n)))
;; 	    `((MOVE :FP :R1))
;; 	    `((MOVE :SP :FP))
;; 	    `((MOVE :SP :R2))
;; 	    `((SUB  (:DIESE ,n) :R2))	
;; 	    `((SUB  (:DIESE 1) :R2))
;; 	    `((PUSH :R2))
;; 	    `((PUSH :R1))
;; 	    (if nivem  
;; 				`((PUSH (:DIESE ,(+ 1 (cadr nivem)))))  
;; 				`((PUSH (:DIESE ,0)))
;; 			)
;; 	    `((PUSH (:DIESE 0)))
;; 	    (compiler (caddar exp)
;; 				(param-env (cadar exp) env 1 (if nivem   (+ 1 (cadr nivem)) 0)) 
;; 				(fun-env  (list (cons lambdaexpr (cdar exp))) fenv (if nivem (+ 1 (cadr nivem)) 0))
;; 				lambdaexpr
;; 			)
;; 	    `((MOVE ( 1 :FP) :SP))
;; 	    `((MOVE ( 2 :FP) :FP))
;; 		)
;; 	)
;; )