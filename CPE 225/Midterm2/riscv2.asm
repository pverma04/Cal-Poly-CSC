.global main
.text
main: 
lw t0, 0(sp) #pointer
li t1, 0 #counter
li t2, '\0'

forCond:
	beq t0, t2, forEnd
forLoop:
	sw t0, 0(a1)
	addi t1, t1, 1
	addi t0, t0, 4
	lw t0, 0(a0)
	b forCond
forEnd:
	mv a0, t1
	ret
