;; ---------------------------------------------------
;;|                     DISPLAY                       |
;; ---------------------------------------------------

(defun disp-zone (vm ind fin)
  (if (and (<= ind fin) (< ind (get-length vm)) (>= ind 0))
    (progn (disp-case vm ind) (disp-zone vm (+ ind 1) fin))
  )
)

(defun disp-case (vm ind)
  (if (= (mod ind 5) 0) (format t "~%~D " ind))
  (format t ".~S" (get-mem vm ind))
)

(defun aff-globals (&optional (vm 'vm))
  (disp-zone vm 0 100)
)

(defun aff-all (&optional (vm 'vm))
  (disp-zone vm 0 (get-length vm))
)