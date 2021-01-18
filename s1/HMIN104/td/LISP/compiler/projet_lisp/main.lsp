(require "compiler/main.lisp")
(require "vm/main.lisp")

;; ---------------------------------------------------
;;|                  VIRTUAL MACHINE                  |
;; ---------------------------------------------------

;; --- Compilation et exécution du code
(defun run (vm code &optional (code-bis ()))
  (if (not (null code-bis)) (load-machine vm (compiler code-bis)))
  (load-machine vm (compiler code))
  (run-machine vm)
)