;; ---------------------------------------------------
;;|                   INSTRUCTIONS                    |
;; ---------------------------------------------------

;; --- MOVE
(defun vm-move (vm src dst)
  (let 
		(
			(addr (get-dst vm dst))
			(res (get-src vm src))
		)
    (if (numberp addr)
			(set-mem vm addr res)
      (set-prop vm addr res)
		)
	)
)


;; --- PUSH
(defun vm-push (vm src)
  (inc-sp vm)
  (vm-move vm src '(:* :SP))
)

;; --- POP
(defun vm-pop (vm dst)
  (vm-move vm '(:* :SP) dst)
  (dec-sp vm)
)

;; --- ADD
(defun vm-add (vm src dst)
  (vm-op vm '+ src dst)
)

;; --- SUB
(defun vm-sub (vm src dst)
  (vm-op vm '- src dst)
)

;; --- MULT
(defun vm-mult (vm src dst)
  (vm-op vm '* src dst)
)

;; --- DIV
(defun vm-div (vm src dst)
  (vm-op vm '/ src dst)
)

;; --- CMP
(defun vm-cmp (vm recto verso)
  (let 
		(
			(r (get-src vm recto))
			(v (get-src vm verso))
		)
    (if (and (numberp r) (numberp v))
			(cond
				((eql r v) (set-flags vm 0 1 0))
				((< r v) (set-flags vm 1 0 0))
				((> r v) (set-flags vm 0 0 1))
			)
      (if (eql r v) 
	  		(set-flags vm 0 1 0)
				(set-flags vm 0 0 0)
			)
		)
	)
)

;; --- Incrément de registre
(defun vm-inc (vm dst)
  (vm-add vm '(:DIESE 1) dst)
)

;; --- Décrément de registre
(defun vm-dec (vm dst)
  (vm-sub vm '(:DIESE 1) dst)
)

;; --- JUMP
(defun vm-jmp (vm dst)
  (if (numberp dst)  
		(set-pc vm dst)
    (vm-move vm dest :PC)
	)
)

(defun vm-jsr (vm etq)
  (vm-push vm :PC)
  (vm-jmp vm etq)
) 

(defun vm-jl (vm dst)
  (vm-jcond vm dst 'is-lt)
)

(defun vm-jeq (vm dst)
  (vm-jcond vm dst 'is-eq)
)
      
(defun vm-jg (vm dst)
  (vm-jcond vm dst 'is-gt)
)

(defun vm-jle (vm dst)
  (vm-jcond vm dst 'is-ngt)
)


(defun vm-jge (vm dst)
  (vm-jcond vm dst 'is-nlt)
)

(defun vm-jne (vm dst)
  (vm-jcond vm dst 'is-neq)
)


;; ********** Instructions de gestion de la machine.

(defun vm-rtn (vm)
  (vm-move vm '( 1 :FP) :SP)
  (vm-move vm '( 4 :FP) :PC)
  (vm-move vm '( 2 :FP) :FP)
)

(defun vm-nop (vm))

(defun vm-err (vm exp)
  (format t "Erreur : ~S~%" exp)
)