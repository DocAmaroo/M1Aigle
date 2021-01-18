# Exécution

```lisp
(load "main.lsp")
(make-vm)
(run 'vm '(fibo 4) '(defun fibo (n) (if (= n 0) 0 (if (= n 1) 1 (+ (fibo (- n 1)) (fibo (- n 2)))))))
(run 'vm '(fibo 3))
```