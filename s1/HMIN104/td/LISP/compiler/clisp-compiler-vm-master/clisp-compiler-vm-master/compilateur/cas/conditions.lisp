;; Compilation de structures de condition.

(defun compilation-if (exp env fenv nomf)
  (let ((sinon (gensym "sinon"))
	(finSi (gensym "finSi")))
    (append (compilation (car exp) env fenv nomf)  
	    '((CMP :R0 (:DIESE nil)))
	    `((JEQ (@ ,sinon)))
	    (compilation (cadr exp) env fenv nomf) 
	    `((JMP (@ ,finSi)))
	    `((@ ,sinon))  
	    (compilation (caddr exp) env fenv nomf)
	    `((@ ,finSi)))
    )
  )

(defun compilation-cond (exp etiqfin env fenv nomf)
  (if (null exp)
      (append '((MOVE (:DIESE NIL) :R0)) `((@ , etiqfin)))      
    (let ((etiqcond (gensym "etiqcond")))                              
      (append (compilation (caar exp) env fenv nomf)                
	      '((CMP :R0 (:DIESE NIL)))                                
	      `((JEQ (@ ,etiqcond)))                             
	      (compilation (cadar exp) env fenv nomf)              
	      `((JMP (@ ,etiqfin)))                               
	      `((@ ,etiqcond))                                    
	      (compilation-cond (cdr exp) etiqfin env fenv nomf))
      )
    )
  )