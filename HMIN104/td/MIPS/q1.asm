#TP1

.data
msg:	.asciiz "Enter an integer : "

.text
main:		
	li $v0, 4 # on affiche un message
	la $a0, msg
	syscall
	li $v0, 5 # on demande l'entier
	syscall
	li $t0, 0
	bge $v0, $t0, disp
	neg $v0, $v0
disp:	move $a0, $v0
	li $v0, 1
	syscall
	

# --- autre manière possible
#li $v0, 5 #on demande un entier à l'utilisateur
#syscall
#abs $a0, $v0
#li $v0, 1
#syscall