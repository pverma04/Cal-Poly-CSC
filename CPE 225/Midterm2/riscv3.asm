.global main
.text
main:
# a0 has the dest pointer, and the actual array starts at sp (reversed)
lw t0, (sp)
li t1, '\0'
li t2, 0 #count
li t3, 1
while1:
	beq t0, t1, end
	addi t2, t2, 1
	addi t0, t0, 1
	b while1
while1End:
	beq t2, zero, end
	sw t0, (a0)
	lw a0, 1(a0)
	lw t0, -1(t0)
	sub t2, t2, t3
	b while1End
	
end:
	mv a0, a0
	ret