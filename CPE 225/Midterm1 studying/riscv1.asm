.global main
.text
main:
	li s0, 10 #var i
	li s1, 5 #var x
	li s2, 10 #var y
	li s3, 0 #var z
	
	
	forCont:
		blez s0, forEnd #condition check
		
		mv, a0, s1 #set arg's for getErDone
		li a1, 2
		mv a2, s2
		li a3, -1
		jal getErDone
		mv s3, a0 #z = subroutine
		
		mv a0, s3 #set arg's for doItToIt
		mv a1, s0
		jal doItToIt
		
		add s1, s1, a0 #x = x + doItToIt
		sub s2, s3, s1 #y = z - x
		
		addi s0, s0, -1 #decrement i
	forEnd:
		#DoALottaNuttin (x, y)
		mv a0, s1 #set arg's
		mv a1, s2
		li a7, 10
		ecall
		
		
		