# sum

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
	
	move $t0, $v0
	
	mult $t0, $t0
	mflo $t0
	
	li $v0, 1
	move $a0, $t0
	syscall