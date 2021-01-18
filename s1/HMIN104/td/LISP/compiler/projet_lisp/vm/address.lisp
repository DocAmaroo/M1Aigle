;; ---------------------------------------------------
;;|                     ADRESS                        |
;; ---------------------------------------------------

(defun is-prop (e) 
  (member e (list :R0 :R1 :R2 :R3 :SP :VP :FP :DPP :DE :DPG :PC :LC))
)


(defun is-const (e)
  (eql (car e) :DIESE)
)


(defun is-index (e)
  (numberp (car e))
)


(defun get-index (vm e)
  (get-mem vm (+ (car e) (get-src vm (cadr e))))
)


(defun is-label (e) 
  (eql (car e) :@)
)


(defun is-indir (e) 
  (eql (car e) :*)
)


(defun get-indir-src (vm e)
  (get-mem vm 
		(if (null (cddr e))
		  (get-src vm (cadr e))
			(get-src vm (cdr e))
		)
	)
)


(defun get-indir-dst (vm e)
  (if (null (cddr e))
		(get-src vm (cadr e))
    (if (is-index (cdr e))
			(+ (cadr e) (get-src vm (caddr e)))
      (if (is-label (cdr e)) 
	  		(get-src vm (cdr e))
			)
		)
	)
)


(defun is-loc (e)
  (eql (car e) 'LOC)
)


(defun get-newFP (vm e)
  (let 
		(
			(newFP (get-fp vm))
		)
    (loop while (> (get-mem vm (+ 3 newFP)) (caddr e))
	  do 
			(setf newFP (get-mem vm (+ 2 newFP)))
	  )
    newFP
	)
)


(defun get-loc-src (vm e)
  (if (eql (caddr e) ()) (setf (caddr e) 0))
  (let 
		(
			(newFP (get-newFP vm e))
		)
    (if (< (cadr e) 0)
			(get-mem vm (- newFP (get-mem vm newFP) 1 (cadr e)))
      (get-mem vm ( + 4 newFP (cadr e)))
		)
	)
)


(defun get-loc-dst (vm e)
  (let ((newFP (get-newFP vm e)))
    (if (< (cadr e) 0)
			(- newFP (get-mem vm newFP) 1 (cadr e))  
      (+  4 newFP  (cadr e))
		)
	)
)


(defun get-src (vm exp)
  (if (atom exp)
		(cond
			((null exp) 0)
			((numberp exp) (get-mem vm exp))
			((is-prop exp) (get-prop vm exp))
		)
    (if (consp exp)
			(cond
				((is-const exp) (cadr exp))
				((is-index exp) (get-index vm exp))
				((is-label exp) (get-label vm (cadr exp))) 
				((is-indir exp) (get-indir-src vm exp))
				((is-loc exp) (get-loc-src vm exp))
			)
		)
	)
)

(defun get-dst (vm exp)
  (if (atom exp)
		(cond
			((null exp) 0)
			((numberp exp) exp)
			((is-prop exp) exp)
		)
    (if (consp exp)
			(cond
				((is-index exp) exp)
				((is-label exp) (get-label vm (cadr exp)))
				((is-const exp) (cadr exp))
				((is-indir exp) (get-indir-dst vm exp))
				((is-loc exp) (get-loc-dst vm exp))
			)
		)
	)
)