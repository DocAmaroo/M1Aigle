;; ---------------------------------------------------
;;|                       STATE                       |
;; ---------------------------------------------------

(defun inst-ok (vm)
  (consp (get-mem-pc vm))
)

(defun not-halt (vm)
  (not (is-inst vm 'STOP))
)

(defun vm-running (&optional (vm 'vm))
  (and (inst-ok vm)  (not-halt vm))
)

(defun vm-overflow (vm)
  (>= (get-sp vm) (get-pc vm))
)