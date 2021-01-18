;; ---------------------------------------------------
;;|                      STACK                        |
;; ---------------------------------------------------

(defun get-sp (&optional (vm 'vm))
  (get-prop vm :SP)
)

(defun set-sp (vm val)
  (set-prop vm :SP val)
)

(defun inc-sp (vm)
  (inc-prop vm :SP)
)

(defun dec-sp (vm)
  (dec-prop vm :SP)
)

(defun get-fp (&optional (vm 'vm))
  (get-prop vm :FP)
)

(defun set-fp (vm val)
  (set-prop vm :FP val)
)