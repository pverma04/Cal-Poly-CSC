#Parth Verma and Andrew Okerlund
	.globl printInt
	.text
printInt:
	lw t1, TCR		# Load address of TCR
	lw t0, (t1)		# Load active bit
	andi t0, t0, 1		# Check if bit is set (presence of char)
	beq zero, t0, printInt
	
	lw t1, TDR		# Load address of TDR
	addi a0, a0, 48		# Increase int value by 48 to map it to an ASCII val
	sw a0, (t1)		# Store ascii into TDR
		
	#mv a0, t2

	li a7, 11  #Loads primitive char
	ecall     #Prints char
	ret

	
	.globl printString
printString:
	#t1 Loaded with ascii value of "enter" each run through the loop
	#t2 Contains increasing offset value
	#t3 Contains the array passed to the subroutine (Copied from a0)
	
	addi sp, sp, -16
	sw ra, 12(sp)		#Store the return address in a stack
	sw s3, 8(sp)		# Preserve s3
	mv s3, a0 		# Move the array from a0 to s3
	
pwhile:	
	lb a0, (s3)		#Store the entered char into the array.
	li t1, 0		#Load "\0" as an ascii value into t1
	beq a0, t1, pend_while 	#Is the entered char = ascii val 0?
	
	jal ra, printChar	#Jump to printChar subroutine
	
	
	
	addi s3, s3, 1		#increase the offset by 1 each iteration.
	b pwhile			
	
pend_while:
	
	# Teardown
	lw ra, 12(sp)		#restore the ra before the ret
	lw s3, 8(sp)
	addi sp, sp, 16
	li a0, 0
	ret
	

	.globl printChar
printChar:
tpoll:
	lw t1, TCR 		# Load address of TCR
	lw t0, (t1)		# Load active bit
	andi t0, t0, 1		# Test for presence of char
	beq zero, t0, tpoll
	
	
	lw t1, TDR		# Load address of TDR
	sw a0, (t1)		# Move the ascii into the TDR
	

	li a7, 11 #Loads primitive char
	ecall     #Prints char
	ret
	
	.globl readChar
readChar:
rpoll:
	lw t1, RCR		# Get RCR address
	lw t0, (t1)		# Get RCR value
	andi t0, t0, 1		# Test mask 
	beq t0, zero, rpoll	
	lw t1, RDR		# Load address of RDR
	lbu t2, (t1)		# Load ascii from RDR to t2
	mv a0, t2
	
	li	a7, 11
	ecall
	ret
	
	.globl readInt
readInt:

	lw t1, RCR		# Get RCR address
	lw t0, (t1)		# Get RCR value
	andi t0, t0, 1		# Test mask 
	beq t0, zero, readInt	
	lw t1, RDR		# Load address of RDR
	lbu t2, (t1)		# Load ascii
	mv a0, t2
	addi a0, a0, -48	# convert ASCII to an int
	
	li a7, 1
	ecall
	ret
	
	.globl exitProgram
exitProgram:

	li a7, 10          #Exits the program
        ecall
        ret
        
	.globl readString
readString:
	#li t0, 0	#int i = 0
	#t1 Loaded with ascii value of "enter" each run through the loop
	#t2 Contains increasing offset value
	#t3 Contains the array passed to the subroutine (Copied from a0)
	
	addi sp, sp, -16
	sw ra, 12(sp)		#Store the return address in a .word #Must be stored elsewhere
	sw s3, 8(sp)
	sw s1, 4(sp)
	mv s3, a0 		#Move the array from a0 to s3
	li s1, 0
while:	
	jal ra, readChar	#Jump to reacChar subroutine
	
	li t1, 10		#Load "enter" as an ascii value into t1
	beq a0, t1, end_while 	#Is the entered char = ascii val 10?
	
	sb a0, (s3)		#Store the entered char into the array.
	addi s3, s3, 1		#increase the offset by 1 each iteration.
	addi s1, s1, 1
	b while			
	
end_while:
	sb zero, (s3)		#Store the entered char into the array. ( (s3) marks the index in the array that the byte will go into))
	mv a0, s1
	lw ra, 12(sp)		#restore the ra before the ret
	lw s3, 8(sp)
	lw s1, 4(sp)
	addi sp, sp, 16
	ret
	
	.data
#retAd: .word 0
#sreg:  .word 0
#cnt:   .word -1
	
RCR:	.word	0xffff0000
RDR:	.word	0xffff0004
TCR:	.word	0xffff0008
TDR:	.word	0xffff000c

	
	



