.global parity
parity: #number in a0 already
	li t0, 0 #set-bit counter
	li t1, 1 #number to be leftshifted
	li t2, 0 #current AND result
	li t3, 0 #remainder
	li t4, 2 #to divide
	for:
		blt t1, zero, forEnd
		and t2, a0, t1 #t2 holds a 0 or 1 
		add t0, t0, t2 #t0 = t0 + t2 (update the conuter)
		slli t1, t1, 1 #t1 = t1 << 1
	forEnd:
		rem t3, t0, t4
		beqz t3, ret0
		li a0, 1
		ret
	ret0:
		li a0, 0
		ret 
	
	