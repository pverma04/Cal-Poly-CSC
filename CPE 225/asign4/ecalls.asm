.global printint
printint: 
	li a7, 1
	ecall
	ret

.global printstring
printstring:
	li a7, 4
	ecall
	ret

.global readchar
readchar:
	li a7, 12
	ecall
	ret
	
.global readint
readint:
	li a7, 5
	ecall
	ret

.global exit0
exit0: 
	li a7, 10
	ecall
	ret