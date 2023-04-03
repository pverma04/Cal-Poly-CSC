
	.globl printInt
	.text
printInt:
	li a7, 1       #Loads primitive int
	ecall          #Prints int
	ret
	
	.globl printString
printString:
	li a7, 4    #loads primitve string
	ecall       #Prints STRING 
	ret

	.globl printChar
printChar:
	li a7, 11 #Loads primitive char
	ecall     #Prints char
	ret
	
	.globl readChar
readChar:
	li a7, 12     #READS char
	ecall
	ret
	
	.globl readInt
readInt:
	li a7, 5    #loads primitve user response and READS it
	ecall       #READS int
	ret
	
	.globl Exiting
Exiting:
	li a7, 10          #Exits the program
        ecall
        ret
        
	.globl readstring
readstring:
	#li t0, 0	#int i = 0
	#t1 Loaded with ascii value of "enter" each run through the loop
	#t2 Contains increasing offset value
	#t3 Contains the array passed to the subroutine (Copied from a0)
	
	sw ra, retAd, t0		#Store the return address in a .word
	sw s3, sreg, t0
	#sw s1, cnt, t0
	mv s3, a0 		#Move the array from a0 to s3
	li s1, 0
while:	
	jal ra, readChar	#Jump to reacChar subroutine
	
	li t1, 10		#Load "enter" as an ascii value into t1
	beq a0, t1, end_while 	#Is the entered char = ascii val 10?
	
	sb a0, (s3)		#Store the entered char into the array.
	addi s3, s3, 1		#increase the offset by 1 each iteration.
	#addi s1, s1, 1
	b while			
	
end_while:
	sb zero, (s3)		#Store the entered char into the array. ( (s3) marks the index in the array that the byte will go into))
	#mv a0, s1
	lw ra, retAd		#restore the ra before the ret
	lw s3, sreg
	#lw s1, cnt
	ret
	
	.data
retAd: .word 0
sreg:  .word 0
#cnt:   .word -1
	
	
	



