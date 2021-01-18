;; ---------------------------------------------------
;;|                    FUNCTIONS                      |
;; ---------------------------------------------------

;; --- Attributs
(defun get-prop (vm prop)
  (get vm prop)
)

(defun set-prop (vm prop val)
  (setf (get vm prop) val)
)

(defun inc-prop (vm prop)
  (set-prop vm prop (+ (get-prop vm prop) 1))
)

(defun dec-prop (vm prop)
  (set-prop vm prop (- (get-prop vm prop) 1))
)

(defun set-tab (tab key val)
  (setf (aref tab key) val)
)


;; --- VM Length
(defun get-length (&optional (vm 'vm))
  (get-prop vm :length)
)

(defun set-length (vm tmem)
  (set-prop vm :length tmem)
)

;; --- Memory
(defun get-mem (vm addr)
  (aref (get vm :memtab) addr)
)

(defun set-mem (vm addr val)
  (set-tab (get vm :memtab) addr val)
)


;; --- Register
(defun get-reg (vm reg)
  (get-prop vm reg)
)

(defun set-reg (vm reg val)
  (set-prop vm reg val)
)

;; --- PC handling
(defun get-pc (&optional (vm 'vm))
  (get-prop vm :PC)
  )

(defun set-pc (vm val)
  (set-prop vm :PC val)
)

(defun inc-pc (vm)
  (inc-prop vm :PC)
)

(defun dec-pc (vm)
  (dec-prop vm :PC)
)

;; --- LC handling
(defun get-lc (&optional (vm 'vm))
  (get-prop vm :LC)
)

(defun set-lc (vm val)
  (set-prop vm :LC val)
)

(defun inc-lc (vm)
  (inc-prop vm :LC)
)

(defun dec-lc (vm)
  (dec-prop vm :LC)
)

;; --- Adress
(require "vm/address.lisp")

;; --- Code
(require "vm/code.lisp")

;; --- Instructions
(require "vm/instructions.lisp")

;; --- Labels
(require "vm/labels.lisp")

;; --- Flags
(require "vm/flags.lisp")

;; --- Stack
(require "vm/stack.lisp")

;; --- Memory
(require "vm/memory.lisp")