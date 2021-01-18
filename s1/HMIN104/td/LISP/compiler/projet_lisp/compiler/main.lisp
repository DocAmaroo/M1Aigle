;; ---------------------------------------------------
;;|                     COMPILER                      |
;; ---------------------------------------------------

;; --- Compilateur Lisp -> ASM
(defun compiler (exp &optional (env ()) (fenv ())  (namef ()) )
	(let ((arg (if (atom exp) () (cdr exp))))
		(cond
			;; litterals
			((atom exp) (compile-litt exp env fenv namef))

			;; operations
			((member (car exp) '(+ - * /)) (compile-op exp env fenv namef))

			;; booleans
			((member (car exp) '(< > = <= >= )) (compile-comp exp env fenv namef))
			((isCase exp 'and) (compile-and arg (gensym "finAnd") env fenv namef))
			((isCase exp 'or) (compile-or arg (gensym "finOr") env fenv namef))

			;; conditions
			((isCase exp 'if) (compile-if arg env fenv namef))
			((isCase exp 'cond) (compile-cond arg (gensym "fincond") env fenv namef))

			;; variables
			((isCase exp 'setf) (compile-setf arg env fenv namef))
			((isCase exp 'let ) (compile-let arg env fenv namef))
			((isCase exp 'labels) (compile-labels arg env fenv namef))

			;; loops
			((isCase exp 'progn) (compile-progn arg env fenv namef))
			((isCase exp 'loop) (compile-loop arg env fenv namef))

			;; function
			((isCase exp 'defun) (compile-defun arg env fenv namef))
			((and (consp (car exp)) (eql (caar exp) 'lambda)) (compile-lambda exp env fenv namef))
			(`(function ,(car exp)) (compile-funcall exp env fenv namef))
		)
	)
)

(defun isCase (exp inst)
  (eql (car exp) inst)
)

;; --- is litteral
(require "compiler/litterals.lisp")

;; --- is operation
(require "compiler/operations.lisp")

;; --- is boolean
(require "compiler/booleans.lisp")

;; --- is condition
(require "compiler/conditions.lisp")

;; --- is variable
(require "compiler/variables.lisp")

;; --- is loop
(require "compiler/loops.lisp")

;; --- is function
(require "compiler/functions.lisp")