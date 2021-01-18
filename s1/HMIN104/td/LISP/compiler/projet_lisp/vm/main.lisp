;; ---------------------------------------------------
;;|                  VIRTUAL MACHINE                  |
;; ---------------------------------------------------

(require "vm/functions.lisp")
(require "vm/instructions.lisp")
(require "vm/state.lisp")

;; Initialisation de la vm
(defun make-vm (&optional (name 'vm) (tmem 10000) (aff ()))
  (set-prop name :name name)
  (set-prop name :R0 0)
  (set-prop name :R1 0)
  (set-prop name :R2 0)
  (set-prop name :R3 0)
  (set-prop name :BP 100)
  (set-prop name :SP 100)
  (set-prop name :VP 1)
  (set-prop name :FP 0)
  (set-prop name :DPP 0)
  (set-prop name :DE 0)
  (set-prop name :DPG 0)
  (reset-vm name tmem)
  (if (not (null aff)) (print-vm name))
)

;; Reset de la vm
(defun reset-vm (&optional (name 'vm) (tmem 10000))
  (let 
		(
			(length (max tmem 1000))
		)
    (set-length name length)
    (set-prop name :memtab (make-array length))
    (set-pc name (- length 1))
    (set-lc name (- length 1))
    (set-prop name :label (make-hash-table :size length))
    (set-prop name :labelNR (make-hash-table :size length))
    (set-labelNR name 'nb 0)
	)
)


;; Charge le code dans la vm
(defun load-machine (vm code)
  (let 
		(
			(exp code)
			(inst (car code))
			(labelLoc (make-hash-table :size (get-length vm)))
			(labelLocNR (make-hash-table :size (get-length vm)))
		)
    (set-hash labelLocNR 'nb 0)
    (loop while exp 
			do (case (car inst)
				('@ (case-addr vm exp inst labelLoc labelLocNR))
				('VARG (case-varg vm exp inst))
				('JSR (case-saut vm exp inst))
				('FENTRY (case-function vm exp inst))
				(otherwise (case-other vm exp inst labelLoc labelLocNR))
			)
	  	do (setf exp (cdr exp))
	  	do (setf inst (car exp))
		)
	)
)

  
;; Execute la vm
(defun run-machine (&optional (name 'vm) (aff ()))
  (set-mem-lc name '(STOP))
  (let 
		(
			(nbfun 0)
		)
		(loop while (vm-running name) do 
			(if (in-fun name) 
				(jump-to-function name nbfun)
				(exec-inst name (get-mem-pc name) aff)
			)
		)
  )
  (if (vm-overflow name) 
		(error "Stack overflow")
    (get-reg name :R0)
	)
)

;; Execute une instruction
(defun exec-inst (vm exp &optional (aff ()))
  (let 
		(
			(inst (car exp))
			(param (cadr exp))
			(param-bis (caddr exp))
		)
    (if (null exp)
			(vm-nop vm)
      (case inst
				('MOVE (vm-move vm param param-bis))
				('ADD (vm-add vm param param-bis))
				('SUB (vm-sub vm param param-bis))
				('MULT (vm-mult vm param param-bis))
				('DIV (vm-div vm param param-bis))
				('PUSH (vm-push vm param))
				('POP (vm-pop vm param))
				('INCR (vm-inc vm param))
				('DECR (vm-dec vm param))
				('JMP  (vm-jmp vm param))
				('CMP  (vm-cmp vm param param-bis))
				('JEQ (vm-jeq vm param))
				('JL (vm-jl vm param))
				('JLE (vm-jle vm param))
				('JG (vm-jg vm param))
				('JGE (vm-jge vm param))
				('JNE (vm-jne  vm param))
				('JSR (vm-jsr vm param))
				('RTN (vm-rtn vm))
				('FENTRY (vm-nop vm))
				('FEXIT (vm-nop vm))
				('ERR (vm-err vm))
				(otherwise (vm-err vm exp))
			)
		)
    (if (not (null aff)) (format t "~S~%" (get-mem-pc vm)))
    (dec-pc vm)
	)
)


;; Affiche la vm
(defun print-vm (&optional (name 'vm))
  (format t "~%Machine virtuelle : ~%--- name : ~S ~%--- Length : ~D" name (get-length name))
  (format t "~%- Registres : ~%--- R0 : ~D ~%--- R1 : ~D ~%--- R2 : ~D ~%--- R3 : ~D" 
	  (get-reg name :R0) (get-reg name :R1) (get-reg name :R2) (get-reg name :R3))
  (format t "~%- Pointeurs : ~%--- BP : ~D ~%--- SP : ~D ~%--- VP : ~D ~%--- FP : ~D"
	  (get-prop name :BP) (get-prop name :SP) (get-prop name :VP) (get-prop name :FP))
  (format t "~%- Drapeaux : ~%--- DPP : ~D ~%--- DE : ~D ~%--- DPG : ~D"
	  (get-prop name :DPP) (get-prop name :DE) (get-prop name :DPG))
  (format t "~%- Compteurs : ~%--- PC : ~D ~%--- LC : ~D ~%"
	  (get-pc name) (get-lc name))
  )

