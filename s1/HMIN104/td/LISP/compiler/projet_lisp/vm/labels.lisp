;; ---------------------------------------------------
;;|                      LABELS                       |
;; ---------------------------------------------------

(defun get-hash (tab key)
  (gethash key tab)
)

(defun set-hash (tab key val)
  (setf (gethash key tab) val)
)

(defun inc-hash (tab)
  (set-hash tab 'nb (+ (get-hash tab 'nb) 1))
)

(defun get-label (vm key)
  (get-hash (get-prop vm :label) key)
)

(defun set-label (vm key val)
  (set-hash (get-prop vm :label) key val)
)

(defun inc-label (vm)
  (inc-hash (get-prop vm :label))
)

(defun get-labelNR (vm key)
  (get-hash (get-prop vm :labelNR) key)
)

(defun set-labelNR (vm key val)
  (set-hash (get-prop vm :labelNR) key val)
)

(defun inc-labelNR (vm)
  (inc-hash (get-prop vm :labelNR))
)

(defun res-label (vm exp addr)
  (if (null exp) 
		()
    (progn
      (set-mem vm (car exp) (list (car (get-mem vm (car exp))) addr))
      (res-label vm (cdr exp) addr)
		)
	)
)