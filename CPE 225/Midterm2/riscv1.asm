.global main
.text
main: 
lw t0, (sp) #pointer
li t1, 0 #counter
li t2, '\0'
forCond:
	beq t0, t2, forEnd
forLoop:
	addi t1, t1, 1
	addi t0, t0, 4
	b forCond
forEnd:
	mv t1, a0
