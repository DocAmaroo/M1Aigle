;; ---------------------------------------------------
;;|                      FLAGS                        |
;; ---------------------------------------------------

(defun set-flags (vm dpp de dpg)
  (set-prop vm :DPP dpp)
  (set-prop vm :DE de)
  (set-prop vm :DPG dpg)
)

;; --- is equal
(defun is-eq (vm)
  (eql (get-prop vm :DE) 1)
)

;; --- is not equal
(defun is-neq (vm)
  (not (is-eq vm))
)

;; --- is less than
(defun is-lt (vm)
  (eql (get-prop vm :DPP) 1)
)

;; --- is not less than
(defun is-nlt (vm)
  (not (is-lt vm))
)

;; --- is greater than
(defun is-gt (vm)
  (eql (get-prop vm :DPG) 1)
)

;; --- is not greater than
(defun is-ngt (vm)
  (not (is-gt vm))
)