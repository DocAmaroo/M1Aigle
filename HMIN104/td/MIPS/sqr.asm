# sqr / sum

.data
	msg: .asciiz "Enter a value : "	

.text
main:
	# disp msg
	li $v0, 4 
	la $a0, msg
	syscall

	# wait for value
	li $v0, 5
	syscall
	
	#save it to $t0
	move $t0, $v0

	# disp msg
	li $v0, 4 
	la $a0, msg
	syscall

	# wait for value
	li $v0, 5
	syscall
	
	#save it to $t1
	move $t1, $v0
	
	# ---------
	
	jal sum

sqr:
	mul $t0, $t0, $t0
	
	li $v0, 1
	move $a0, $t0
	syscall
	
	jr $ra
	
sum:
	move $ra, $t2
	jal sqr
	
	