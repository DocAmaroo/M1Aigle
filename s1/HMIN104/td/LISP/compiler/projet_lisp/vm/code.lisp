;; ---------------------------------------------------
;;|                       CODE                        |
;; ---------------------------------------------------

(defun is-saut (inst)
  (member (car inst) '(JMP JEQ JL JG JLE JGE JNE))
)

(defun case-addr (vm exp inst labelLoc labelLocNR)
  (if (get-hash labelLoc (cadadr exp))
		(error "error labels : ~S" (cadr inst))
    (progn
      (set-hash labelLoc (cadr inst) (+ (get-lc vm) 1))
      (res-label vm (get-hash labelLocNR (cadr inst)) (+ (get-lc vm) 1))
      (if (get-hash labelLocNR (cadr inst))
				(progn
					(set-hash labelLocNR (cadr inst) ())
					(inc-hash labelLocNR)
				)
			)
		)
	)
)

(defun case-varg (vm exp inst)
  (if (null (get-label vm (cadr inst)))
		(progn
			(set-label vm (cadr inst) (get-prop vm :VP))
			(if (< (get-prop vm :VP) (- (get-prop vm :BP) 1)) 
	    	(inc-prop vm :VP)
	  	)
		)
	)
)

(defun case-saut (vm exp inst)
  (if (get-label vm (cadadr inst))
		(set-mem-lc vm (list (car inst) (get-label vm (cadadr inst))))
    (progn                                    
      (if (null (get-labelNR vm (cadadr inst)))
	  		(inc-labelNR vm)
			)
      (set-labelNR vm (cadadr inst) (list* (get-lc vm) (get-labelNR vm (cadadr inst))))
      (set-mem-lc vm inst)
		)
	)
  (dec-lc vm)
)

(defun case-function (vm exp inst)
  (if (get-label vm (cadadr exp))
		(error "This function already exist : ~S" (cadadr exp))
    (progn
      (set-mem-lc vm inst)
      (set-label vm (cadadr exp) (get-lc vm))
      (res-label vm (get-labelNR vm (cadadr exp)) (get-lc vm))
      (if (get-labelNR vm (cadadr exp))
				(progn
					(set-labelNR (cadadr exp) ())
					(inc-labelNR vm)
					)
			)
			(dec-lc vm)
		)
	)
)

(defun case-other (vm exp inst labelLoc labelLocNR)
  (if (is-saut inst)
		(progn
			(if (get-hash labelLoc (cadadr inst))
				(set-mem-lc vm (list (car inst) (get-hash labelLoc (cadadr inst))))
				(progn
					(if (null (get-hash labelLocNR (cadadr inst)))
						(inc-hash labelLocNR)
					)
					(set-hash labelLocNR (cadadr inst) (list* (get-lc vm) (get-hash labelLocNR (cadadr inst))))
					(set-mem-lc vm inst)
				)
			)
		)
    (set-mem-lc vm inst)
	)
  (dec-lc vm)
)
