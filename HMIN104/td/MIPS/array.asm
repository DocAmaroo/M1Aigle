#array

.data
	array: .space 12

.text
main:
	la $t0, array # définis le tableau dans $t0
	
	li $t1, 1
	sw $t1, ($t0) # 1st case du tableau de valeur $t1 = 1
	
	li $t1, 2
	sw $t1, 4($t0) # 2nd case du tableau de valeur $t1 = 2
	
	li $t1, 3
	sw $t1, 8($t0) # 3rd case du tableau de valeur $t1 = 3
	
	lw $t1, ($t0) # val du 1st el
	lw $t2, 8($t0) # val du 2nd el
	
	sw $t2, ($t0) # 1st el = val du 3rd el
	sw $t1, 8($t0) # 3rd el = val du 1s el
