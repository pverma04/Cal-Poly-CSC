.global char2Num
char2Num: #char c is in a0
	#if a0 is less than 48, or greater than 57 it is not a valid number
	li t1, 48
	li t2, 57
	blt a0, t1, invalid
	bgt a0, t2, invalid
	ret #return a0, since it is valid
	invalid:
		li a0, -1
		ret
		