# int permutation

.data
	int1: .word 1
	int2: .word 2

.text
main:
	# add spaces in the pile for int
	sub $sp, $sp, 4
	
	# read global var.
	lw $t0, int1
	lw $t1, int2
	
	# add $t0 to the pile
	sw $t0, ($sp)
	
	# stock $t1 (int2) on int1
	sw $t1, int1
	
	# the value stack on the pile
    	lw $t0, ($sp)
    	
    	# stock it on int2
    	sw $t0, int2
    	add $sp, $sp, 4