# odd / even

.data
	msg: .asciiz  "Enter an integer : "
	msgEven: .asciiz "C'est un nombre pair"
	msgOdd: .asciiz "C'est un nombre impair"

.text
main:
	# disp msg
	li $v0, 4 
	la $a0, msg
	syscall
	
	# wait for value
	li $v0, 5
	syscall
	
	# define a divisor
	li $t0, 2
	div $v0, $t0
	
	# get the rest of the division
	mfhi $t0
	
	# if == 0, disp even message
	beqz $t0, even
	
	# else disp odd message
	li $v0, 4
	la $a0, msgOdd
	syscall
	
	j exit
	
even:
	li $v0, 4
	la $a0, msgEven
	syscall
	j exit

exit: