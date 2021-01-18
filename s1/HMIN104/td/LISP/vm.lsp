(defun get-register (vm register)
    (get vm register))

(defun set-register (vm register value)
    (setf (get vm register) value))

(defun get-memory (vm index)
    (aref (get vm 'memory) index))

(defun set-memory (vm index value)
    (setf (aref (get vm 'memory) index) value))

(defun vm-exec-instr-move (vm src dest)
    (if (consp src)
        (set_registre vm dest (cadr src))
        (set-registre vm dest (get_registre vm src))
    )
)

(defun vm-exec-load (vm src dest)
)

(defun vm-exec-store (vm src dest)

)

(defun vm-exec-instr-push (vm register)

)

(defun vm-exec-instr-pop (vm register)

)

(defun vm-exec-instr (vm instr)

)

(defun vm-exec-add (vm dest r1 r2)
    (cond (
        (and (consp r1) (consp r2)
            (set-register vm dest (+ r1 r2))
        )
        (and (consp r1) (not(consp r2)) 
            (set-register vm dest (+ r1 (get-register vm r2)))
        )
        (and (not(consp r1) (consp r2) )
            (set-register vm dest (+ (get-register vm r1) r2))
        )
        (and (consp r1) (consp r2)
            (set-register vm dest (+ (get-register vm r1) (get-register vm r2)))
        )
    ))
)

(defun vm-exec-sub (vm dest r1 r2)
    (cond (
        (and (consp r1) (consp r2)
            (set-register vm dest (- r1 r2))
        )
        (and (consp r1) (not(consp r2)) 
            (set-register vm dest (- r1 (get-register vm r2)))
        )
        (and (not(consp r1) (consp r2) )
            (set-register vm dest (- (get-register vm r1) r2))
        )
        (and (consp r1) (consp r2)
            (set-register vm dest (- (get-register vm r1) (get-register vm r2)))
        )
    ))
)

(defun vm-exec-mul (vm dest r1 r2)
    (cond (
        (and (consp r1) (consp r2)
            (set-register vm dest (* r1 r2))
        )
        (and (consp r1) (not(consp r2)) 
            (set-register vm dest (* r1 (get-register vm r2)))
        )
        (and (not(consp r1) (consp r2) )
            (set-register vm dest (* (get-register vm r1) r2))
        )
        (and (consp r1) (consp r2)
            (set-register vm dest (* (get-register vm r1) (get-register vm r2)))
        )
    ))
)

(defun vm-exec-div (vm dest r1 r2)
    (cond (
        (and (consp r1) (consp r2)
            (set-register vm dest (/ r1 r2))
        )
        (and (consp r1) (not(consp r2)) 
            (set-register vm dest (/ r1 (get-register vm r2)))
        )
        (and (not(consp r1) (consp r2) )
            (set-register vm dest (/ (get-register vm r1) r2))
        )
        (and (consp r1) (consp r2)
            (set-register vm dest (/ (get-register vm r1) (get-register vm r2)))
        )
    ))
)

(defun vm-exec-instr-jmp (vm dest)
    (vm-exec-instr-move vm dest 'PC)
)

(defun vm-exec-instr-jsr (vm label)
    (vm-exec-instr-push vm 'PC)
    (vm-exec-instr-jmp vm label)
)


(defun vm-exec-instr-halt (vm)
    (set-register vm 'RUN nil)
)


(defun vm-load-code (vm code)

)




(defun vm-exec (vm) 
    (while (get-registre vm 'RUN) do
        (vm-exec-instr-load vm 'PC 'INSTR) ;;equiv d'executer (LOAD 'PC 'INSTR)
        (vm-incr-registre vm 'PC) ;; on incrémente PC
        (vm-exec-instr (get-registre 'vm 'INSTR)) ;; on exécute l'instruction chargé dans le registre 'INSTR
    )
)



(defun make-vm (name length)
    (set-register name 'A0 0)
    (set-register name 'A1 0)
    (set-register name 'A2 0)
    (set-register name 'A3 0)
    (set-register name 'V0 0)
    (set-register name 'V1 0)
    (set-register name 'RA 0)
    (set-register name 'T1 0)
    (set-register name 'T2 0) 
    (set-register name 'T3 0) 
    (set-register name 'T4 0)
    (set-register name 'T5 0)
    (set-register name 'T6 0)
    (set-register name 'T7 0)
    (set-register name 'T8 0)
    (set-register name 'T9 0)
    (set-register name 'SP 0)
    (set-register name 'FP 0)
    (set-register name 'PC 0)
    (set-register name 'RUN 0)
    (set-register name 'INSTR 0)
    (setf (get name 'memory) (make-array length))
)

(print (make-vm 'myvm 20))

;; * TEST ON MOVE
;; (print (get-register 'myvm 'V0))
;; (print (set-register 'myvm 'V0 2))
;; (print (vm-exec-move 'myvm 'V0 'V1))
;; (print (get-register 'myvm 'V1))

;; * VM-COMPILE-LOAD-EXEC
;; vm-compiler-load-exec   
;; (vm-compiler-load-exec 'myvm '(fibo 15))
;; ;; équivalent à mon cul:
;; (vm-load-code 'myvm (compiler '(fibo 15)))
;; (vm-exec 'myvm)

;; * UTILISATION DE LOAD ET MOVE
;;(LOAD 500 R0) /// va cherche le contenu de la case mémoire a l adresse 500 et le met dans R0
;; (LOAD FP R0)

;; (MOVE 500 R0)
;; (LOAD FP R0)
;; avec index
;; (LOAD (- FP 1) R0) // met dans R0 le contenu de la case mémoire à l'adresse FP - 1

;; (LOAD (- FP 2) R0)
;; (LOAD (+ FP 5) R0)
;; (LOAD (- FP 5) R0)

;; alternativement - et c'est plus simple / plus clair
;; (LOAD (:REF FP -5) R0)
;; (LOAD (:REF FP 10) R0)
