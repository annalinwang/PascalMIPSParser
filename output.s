	#author Anna Wang
	#version 3/20/18
	.data
	line: .asciiz "\n"
	x: .word 0
	y: .word 0
	ignore: .word 0
	.text
	.globl main
main:
	li $v0 2
	sw $v0 x	 #loading value of expression to v0
	lw $v0 x
	subu $sp, $sp, 4
	sw $v0, ($sp)		#pushes value onto stack of addresses
	li $v0 1
	lw $t0, ($sp)
	addu $sp, $sp, 4		#pop value off stack of addresses
	addu $v0, $t0, $v0	 #adds
	sw $v0 y	 #loading value of expression to v0
	lw $v0 x
	subu $sp, $sp, 4
	sw $v0, ($sp)		#pushes value onto stack of addresses
	lw $v0 y
	lw $t0, ($sp)
	addu $sp, $sp, 4		#pop value off stack of addresses
	addu $v0, $t0, $v0	 #adds
	sw $v0 x	 #loading value of expression to v0
	lw $v0 x
	subu $sp, $sp, 4
	sw $v0, ($sp)		#pushes value onto stack of addresses
	lw $v0 y
	lw $t0, ($sp)
	addu $sp, $sp, 4		#pop value off stack of addresses
	mult $v0, $t0
	mflo $v0	 #multiplies
	move $a0 $v0
	li $v0 1
	syscall
	la $a0, line
	li $v0 4
	syscall
	lw $v0 x
	move $t1 $v0
	lw $v0 y
	bgt $t1 $v0 endif1
	j mergeendif1
endif1:
	lw $v0 x
	move $a0 $v0
	li $v0 1
	syscall
	la $a0, line
	li $v0 4
	syscall
	lw $v0 y
	move $a0 $v0
	li $v0 1
	syscall
	la $a0, line
	li $v0 4
	syscall
	j mergeendif1
mergeendif1:
	li $v0 0
	sw $v0 x	 #loading value of expression to v0
	lw $v0 x
	move $t1 $v0
	li $v0 10
	blt $t1 $v0 loop
	j exit
loop:
	lw $v0 x
	move $a0 $v0
	li $v0 1
	syscall
	la $a0, line
	li $v0 4
	syscall
	lw $v0 x
	subu $sp, $sp, 4
	sw $v0, ($sp)		#pushes value onto stack of addresses
	li $v0 1
	lw $t0, ($sp)
	addu $sp, $sp, 4		#pop value off stack of addresses
	addu $v0, $t0, $v0	 #adds
	sw $v0 x	 #loading value of expression to v0
	lw $v0 x
	move $t1 $v0
	li $v0 10
	blt $t1 $v0 loop
exit:
	lw $v0 y
	move $a0 $v0
	li $v0 1
	syscall
	la $a0, line
	li $v0 4
	syscall
	subu $sp, $sp, 4
	sw $ra, ($sp)		#pushes value onto stack of addresses
	li $v0 4
	subu $sp, $sp, 4
	sw $v0, ($sp)		#pushes value onto stack of addresses
	lw $v0 x
	subu $sp, $sp, 4
	sw $v0, ($sp)		#pushes value onto stack of addresses
	lw $v0 y
	subu $sp, $sp, 4
	sw $v0, ($sp)		#pushes value onto stack of addresses
	jal procAdd
	lw $a0, ($sp)
	addu $sp, $sp, 4		#pop value off stack of addresses
	lw $a0, ($sp)
	addu $sp, $sp, 4		#pop value off stack of addresses
	lw $a0, ($sp)
	addu $sp, $sp, 4		#pop value off stack of addresses
	lw $ra, ($sp)
	addu $sp, $sp, 4		#pop value off stack of addresses
	sw $v0 ignore	 #loading value of expression to v0
	lw $v0 x
	move $a0 $v0
	li $v0 1
	syscall
	la $a0, line
	li $v0 4
	syscall
	li $v0, 10
	syscall
procAdd:
	li $v0 0
	subu $sp, $sp, 4
	sw $v0, ($sp)		#pushes value onto stack of addresses
	subu $sp, $sp, 4
	sw $ra, ($sp)		#pushes value onto stack of addresses
	lw $v0 16($sp)
	move $a0 $v0
	li $v0 1
	syscall
	la $a0, line
	li $v0 4
	syscall
	lw $v0 12($sp)
	move $a0 $v0
	li $v0 1
	syscall
	la $a0, line
	li $v0 4
	syscall
	lw $v0 8($sp)
	move $a0 $v0
	li $v0 1
	syscall
	la $a0, line
	li $v0 4
	syscall
	lw $v0 x
	subu $sp, $sp, 4
	sw $v0, ($sp)		#pushes value onto stack of addresses
	lw $v0 20($sp)
	lw $t0, ($sp)
	addu $sp, $sp, 4		#pop value off stack of addresses
	addu $v0, $t0, $v0	 #adds
	sw $v0 x	 #loading value of expression to v0
	lw $ra, ($sp)
	addu $sp, $sp, 4		#pop value off stack of addresses
	jr $ra
	lw $v0, ($sp)
	addu $sp, $sp, 4		#pop value off stack of addresses
