.global mullet
mullet:
	#a0 = m, a1 = n (m * n)
	li t0, 0 #int i
	li t1, 0 #answer
	forStart:
		bge t0, a1, forEnd #condition
		
		add t1, a0, a0 #add m to m n times
		addi t0, t0, 1 #i = i++
		b forStart
	forEnd:
		mv a0, t1
		ret
	