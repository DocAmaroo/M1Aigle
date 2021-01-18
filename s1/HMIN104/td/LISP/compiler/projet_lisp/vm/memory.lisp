;; ---------------------------------------------------
;;|                     MEMORY                        |
;; ---------------------------------------------------

(defun get-mem-pc (&optional (vm 'vm))
  (get-mem vm (get-pc vm))
)

(defun set-mem-pc (vm val)
  (set-mem vm (get-pc vm) val)
)

(defun get-mem-lc (&optional (vm 'vm))
  (get-mem vm (get-lc vm))
)

(defun set-mem-lc (vm val)
  (set-mem vm (get-lc vm) val)
)

(defun is-inst (vm inst)
  (eql (car (get-mem-pc vm)) inst)
)

(defun in-fun (vm)
  (is-inst vm 'FENTRY)
  )

(defun out-fun (vm)
  (is-inst vm 'FEXIT)
)

(defun jump-to-function (vm nbfun)
  (setf nbfun (+ nbfun 1))
  (loop while (< 0 nbfun) do 
		(dec-pc vm)
		(cond
			((in-fun vm) (setf nbfun (+ nbfun 1)))
			((out-fun vm) (setf nbfun (- nbfun 1)))
		)
	)
)

(defun vm-op (vm op src dst)
  (let 
		(
			(addr (get-dst vm dst))
			(res (apply op (list (get-src vm (get-dst vm dst)) (get-src vm src))))
		)
		(set-reg vm addr res)
	)
)

(defun vm-jcond (vm dst cond)
  (if (apply cond (list vm)) 
		(vm-jmp vm dst))
)