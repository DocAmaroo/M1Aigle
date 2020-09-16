# Nombre premier

.data
	msg: .asciiz "Enter an integer : "

.text
main:
	# disp msg
	li $v0, 4 
	la $a0, msg
	syscall
	
	# wait for value
	li $v0, 5
	syscall
	
	# check > 0
	bgtz $v0, disp
	
	# if < 0
	j exit
	
disp: 	
	move $t0, $v0 # save the value
	li $t1, 0 # init $t1 à 1
loop:
    	beq $t0, $t1, exit # condition de sortie $t1 == $t0
    	addi $t1, $t1, 1 #incr. le counter
    	
    	# disp value
    	move $a0, $t1
    	li $v0, 1
    	syscall
    	j loop
exit:
	li $v0, 10
    	syscall