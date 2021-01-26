;; ---------------------------------------------------
;;|                    VARIABLES                      |
;; ---------------------------------------------------

(defun local-env (exp env dep nivem)  
  (if (atom exp) 
    env
    (local-env (cdr exp) (cons (cons (caar exp) `(LOC ,dep ,nivem)) env) (+ 1 dep) nivem)
	)
)

;; --- setf
;; (defun compile-setf (exp env fenv namef)
;;   (if (null exp) 
;; 		()
;; 		(append (compiler (cadr exp) env fenv namef)
;; 			(let 
;; 				(
;; 					(var (assoc (car exp) env))
;; 				)
;; 				(if var
;; 					(if (eql (cadr exp) 'loc) 
;; 						'((MOVE :R0 (cdr var)))
;; 					)
;; 					`((VARG ,(car exp)))
;; 				)
;; 			)
;; 			`((MOVE :R0 ,(cadar (compiler (car exp) env fenv namef))))
;; 			(compile-setf (cddr exp) env fenv namef)
;; 		)
;; 	)
;; )

;; --- let
;; (defun compile-let (exp env fenv namef)
;;   (let 
;; 		(
;; 			(nivem (assoc namef fenv))
;; 		)
;;     (append (compile-local (car exp) env fenv namef)
;; 	    (compiler (cadr exp) (local-env (car exp) env 1 (cadr nivem)) fenv namef)
;; 	    (depile-local (car exp) env fenv namef)
;; 		)
;; 	)
;; )

;; --- local
;; (defun compile-local (exp env fenv namef)
;;   (if (null exp) 
;;     ()
;;     (append (compiler (cadar exp) env fenv namef)
;; 	    '((PUSH :R0))
;; 	    (compile-local (cdr exp) env fenv namef )
;; 		)
;; 	)
;; )

;; (defun depile-local (exp env fenv namef)
;;   (if (null exp) 
;; 		()
;;     (append '((POP :R1)) (depile-local (cdr exp) env fenv namef))
;; 	)
;; )